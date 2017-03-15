/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bar.view;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import android.view.View;
import android.view.animation.Interpolator;
import com.bar.animation.NineAnimator;
import com.bar.animation.NineValueAnimator;
import com.bar.view.animation.NineAnimatorProxy;

class NineViewPropertyAnimatorPreHC extends NineViewPropertyAnimator {
    /**
     * Proxy animation class which will allow us access to post-Honeycomb properties that were not
     * otherwise available.
     */
    private final NineAnimatorProxy mProxy;

    /**
     * A WeakReference holding the View whose properties are being animated by this class. This is
     * set at construction time.
     */
    private final WeakReference<View> mView;

    /**
     * The duration of the underlying NineAnimator object. By default, we don't set the duration
     * on the NineAnimator and just use its default duration. If the duration is ever set on this
     * NineAnimator, then we use the duration that it was set to.
     */
    private long mDuration;

    /**
     * A flag indicating whether the duration has been set on this object. If not, we don't set
     * the duration on the underlying NineAnimator, but instead just use its default duration.
     */
    private boolean mDurationSet = false;

    /**
     * The startDelay of the underlying NineAnimator object. By default, we don't set the startDelay
     * on the NineAnimator and just use its default startDelay. If the startDelay is ever set on this
     * NineAnimator, then we use the startDelay that it was set to.
     */
    private long mStartDelay = 0;

    /**
     * A flag indicating whether the startDelay has been set on this object. If not, we don't set
     * the startDelay on the underlying NineAnimator, but instead just use its default startDelay.
     */
    private boolean mStartDelaySet = false;

    /**
     * The interpolator of the underlying NineAnimator object. By default, we don't set the interpolator
     * on the NineAnimator and just use its default interpolator. If the interpolator is ever set on
     * this NineAnimator, then we use the interpolator that it was set to.
     */
    private /*Time*/Interpolator mInterpolator;

    /**
     * A flag indicating whether the interpolator has been set on this object. If not, we don't set
     * the interpolator on the underlying NineAnimator, but instead just use its default interpolator.
     */
    private boolean mInterpolatorSet = false;

    /**
     * Listener for the lifecycle events of the underlying
     */
    private NineAnimator.AnimatorListener mListener = null;

    /**
     * This listener is the mechanism by which the underlying NineAnimator causes changes to the
     * properties currently being animated, as well as the cleanup after an animation is
     * complete.
     */
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();

    /**
     * This list holds the properties that have been asked to animate. We allow the caller to
     * request several animations prior to actually starting the underlying animator. This
     * enables us to run one single animator to handle several properties in parallel. Each
     * property is tossed onto the pending list until the animation actually starts (which is
     * done by posting it onto mView), at which time the pending list is cleared and the properties
     * on that list are added to the list of properties associated with that animator.
     */
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList<NameValuesHolder>();

    /**
     * Constants used to associate a property being requested and the mechanism used to set
     * the property (this class calls directly into View to set the properties in question).
     */
    private static final int NONE           = 0x0000;
    private static final int TRANSLATION_X  = 0x0001;
    private static final int TRANSLATION_Y  = 0x0002;
    private static final int SCALE_X        = 0x0004;
    private static final int SCALE_Y        = 0x0008;
    private static final int ROTATION       = 0x0010;
    private static final int ROTATION_X     = 0x0020;
    private static final int ROTATION_Y     = 0x0040;
    private static final int X              = 0x0080;
    private static final int Y              = 0x0100;
    private static final int ALPHA          = 0x0200;

    private static final int TRANSFORM_MASK = TRANSLATION_X | TRANSLATION_Y | SCALE_X | SCALE_Y |
            ROTATION | ROTATION_X | ROTATION_Y | X | Y;

    /**
     * The mechanism by which the user can request several properties that are then animated
     * together works by posting this Runnable to start the underlying NineAnimator. Every time
     * a property animation is requested, we cancel any previous postings of the Runnable
     * and re-post it. This means that we will only ever run the Runnable (and thus start the
     * underlying animator) after the caller is done setting the properties that should be
     * animated together.
     */
    private Runnable mAnimationStarter = new Runnable() {
        @Override
        public void run() {
            startAnimation();
        }
    };

    /**
     * This class holds information about the overall animation being run on the set of
     * properties. The mask describes which properties are being animated and the
     * values holder is the list of all property/value objects.
     */
    private static class PropertyBundle {
        int mPropertyMask;
        ArrayList<NameValuesHolder> mNameValuesHolder;

        PropertyBundle(int propertyMask, ArrayList<NameValuesHolder> nameValuesHolder) {
            mPropertyMask = propertyMask;
            mNameValuesHolder = nameValuesHolder;
        }

        /**
         * Removes the given property from being animated as a part of this
         * PropertyBundle. If the property was a part of this bundle, it returns
         * true to indicate that it was, in fact, canceled. This is an indication
         * to the caller that a cancellation actually occurred.
         *
         * @param propertyConstant The property whose cancellation is requested.
         * @return true if the given property is a part of this bundle and if it
         * has therefore been canceled.
         */
        boolean cancel(int propertyConstant) {
            if ((mPropertyMask & propertyConstant) != 0 && mNameValuesHolder != null) {
                int count = mNameValuesHolder.size();
                for (int i = 0; i < count; ++i) {
                    NameValuesHolder nameValuesHolder = mNameValuesHolder.get(i);
                    if (nameValuesHolder.mNameConstant == propertyConstant) {
                        mNameValuesHolder.remove(i);
                        mPropertyMask &= ~propertyConstant;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * This list tracks the list of properties being animated by any particular animator.
     * In most situations, there would only ever be one animator running at a time. But it is
     * possible to request some properties to animate together, then while those properties
     * are animating, to request some other properties to animate together. The way that
     * works is by having this map associate the group of properties being animated with the
     * animator handling the animation. On every update event for an NineAnimator, we ask the
     * map for the associated properties and set them accordingly.
     */
    private HashMap<NineAnimator, PropertyBundle> mAnimatorMap =
            new HashMap<NineAnimator, PropertyBundle>();

    /**
     * This is the information we need to set each property during the animation.
     * mNameConstant is used to set the appropriate field in View, and the from/delta
     * values are used to calculate the animated value for a given animation fraction
     * during the animation.
     */
    private static class NameValuesHolder {
        int mNameConstant;
        float mFromValue;
        float mDeltaValue;
        NameValuesHolder(int nameConstant, float fromValue, float deltaValue) {
            mNameConstant = nameConstant;
            mFromValue = fromValue;
            mDeltaValue = deltaValue;
        }
    }

    /**
     * Constructor, called by View. This is private by design, as the user should only
     * get a NineViewPropertyAnimator by calling View.animate().
     *
     * @param view The View associated with this NineViewPropertyAnimator
     */
    NineViewPropertyAnimatorPreHC(View view) {
        mView = new WeakReference<View>(view);
        mProxy = NineAnimatorProxy.wrap(view);
    }

    /**
     * Sets the duration for the underlying animator that animates the requested properties.
     * By default, the animator uses the default value for NineValueAnimator. Calling this method
     * will cause the declared value to be used instead.
     * @param duration The length of ensuing property animations, in milliseconds. The value
     * cannot be negative.
     * @return This object, allowing calls to methods in this class to be chained.
     */
    public NineViewPropertyAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " +
                    duration);
        }
        mDurationSet = true;
        mDuration = duration;
        return this;
    }

    /**
     * Returns the current duration of property animations. If the duration was set on this
     * object, that value is returned. Otherwise, the default value of the underlying NineAnimator
     * is returned.
     *
     * @see #setDuration(long)
     * @return The duration of animations, in milliseconds.
     */
    public long getDuration() {
        if (mDurationSet) {
            return mDuration;
        } else {
            // Just return the default from NineValueAnimator, since that's what we'd get if
            // the value has not been set otherwise
            return new NineValueAnimator().getDuration();
        }
    }

    @Override
    public long getStartDelay() {
        if (mStartDelaySet) {
            return mStartDelay;
        } else {
            // Just return the default from NineValueAnimator (0), since that's what we'd get if
            // the value has not been set otherwise
            return 0;
        }
    }

    @Override
    public NineViewPropertyAnimator setStartDelay(long startDelay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " +
                    startDelay);
        }
        mStartDelaySet = true;
        mStartDelay = startDelay;
        return this;
    }

    @Override
    public NineViewPropertyAnimator setInterpolator(/*Time*/Interpolator interpolator) {
        mInterpolatorSet = true;
        mInterpolator = interpolator;
        return this;
    }

    @Override
    public NineViewPropertyAnimator setListener(NineAnimator.AnimatorListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void start() {
        startAnimation();
    }

    @Override
    public void cancel() {
        if (mAnimatorMap.size() > 0) {
            HashMap<NineAnimator, PropertyBundle> mAnimatorMapCopy =
                    (HashMap<NineAnimator, PropertyBundle>)mAnimatorMap.clone();
            Set<NineAnimator> animatorSet = mAnimatorMapCopy.keySet();
            for (NineAnimator runningAnim : animatorSet) {
                runningAnim.cancel();
            }
        }
        mPendingAnimations.clear();
        View v = mView.get();
        if (v != null) {
            v.removeCallbacks(mAnimationStarter);
        }
    }

    @Override
    public NineViewPropertyAnimator x(float value) {
        animateProperty(X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator xBy(float value) {
        animatePropertyBy(X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator y(float value) {
        animateProperty(Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator yBy(float value) {
        animatePropertyBy(Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotation(float value) {
        animateProperty(ROTATION, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotationBy(float value) {
        animatePropertyBy(ROTATION, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotationX(float value) {
        animateProperty(ROTATION_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotationXBy(float value) {
        animatePropertyBy(ROTATION_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotationY(float value) {
        animateProperty(ROTATION_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator rotationYBy(float value) {
        animatePropertyBy(ROTATION_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator translationX(float value) {
        animateProperty(TRANSLATION_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator translationXBy(float value) {
        animatePropertyBy(TRANSLATION_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator translationY(float value) {
        animateProperty(TRANSLATION_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator translationYBy(float value) {
        animatePropertyBy(TRANSLATION_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator scaleX(float value) {
        animateProperty(SCALE_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator scaleXBy(float value) {
        animatePropertyBy(SCALE_X, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator scaleY(float value) {
        animateProperty(SCALE_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator scaleYBy(float value) {
        animatePropertyBy(SCALE_Y, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator alpha(float value) {
        animateProperty(ALPHA, value);
        return this;
    }

    @Override
    public NineViewPropertyAnimator alphaBy(float value) {
        animatePropertyBy(ALPHA, value);
        return this;
    }

    /**
     * Starts the underlying NineAnimator for a set of properties. We use a single animator that
     * simply runs from 0 to 1, and then use that fractional value to set each property
     * value accordingly.
     */
    private void startAnimation() {
        NineValueAnimator animator = NineValueAnimator.ofFloat(1.0f);
        ArrayList<NameValuesHolder> nameValueList =
                (ArrayList<NameValuesHolder>) mPendingAnimations.clone();
        mPendingAnimations.clear();
        int propertyMask = 0;
        int propertyCount = nameValueList.size();
        for (int i = 0; i < propertyCount; ++i) {
            NameValuesHolder nameValuesHolder = nameValueList.get(i);
            propertyMask |= nameValuesHolder.mNameConstant;
        }
        mAnimatorMap.put(animator, new PropertyBundle(propertyMask, nameValueList));
        animator.addUpdateListener(mAnimatorEventListener);
        animator.addListener(mAnimatorEventListener);
        if (mStartDelaySet) {
            animator.setStartDelay(mStartDelay);
        }
        if (mDurationSet) {
            animator.setDuration(mDuration);
        }
        if (mInterpolatorSet) {
            animator.setInterpolator(mInterpolator);
        }
        animator.start();
    }

    /**
     * Utility function, called by the various x(), y(), etc. methods. This stores the
     * constant name for the property along with the from/delta values that will be used to
     * calculate and set the property during the animation. This structure is added to the
     * pending animations, awaiting the eventual start() of the underlying animator. A
     * Runnable is posted to start the animation, and any pending such Runnable is canceled
     * (which enables us to end up starting just one animator for all of the properties
     * specified at one time).
     *
     * @param constantName The specifier for the property being animated
     * @param toValue The value to which the property will animate
     */
    private void animateProperty(int constantName, float toValue) {
        float fromValue = getValue(constantName);
        float deltaValue = toValue - fromValue;
        animatePropertyBy(constantName, fromValue, deltaValue);
    }

    /**
     * Utility function, called by the various xBy(), yBy(), etc. methods. This method is
     * just like animateProperty(), except the value is an offset from the property's
     * current value, instead of an absolute "to" value.
     *
     * @param constantName The specifier for the property being animated
     * @param byValue The amount by which the property will change
     */
    private void animatePropertyBy(int constantName, float byValue) {
        float fromValue = getValue(constantName);
        animatePropertyBy(constantName, fromValue, byValue);
    }

    /**
     * Utility function, called by animateProperty() and animatePropertyBy(), which handles the
     * details of adding a pending animation and posting the request to start the animation.
     *
     * @param constantName The specifier for the property being animated
     * @param startValue The starting value of the property
     * @param byValue The amount by which the property will change
     */
    private void animatePropertyBy(int constantName, float startValue, float byValue) {
        // First, cancel any existing animations on this property
        if (mAnimatorMap.size() > 0) {
            NineAnimator animatorToCancel = null;
            Set<NineAnimator> animatorSet = mAnimatorMap.keySet();
            for (NineAnimator runningAnim : animatorSet) {
                PropertyBundle bundle = mAnimatorMap.get(runningAnim);
                if (bundle.cancel(constantName)) {
                    // property was canceled - cancel the animation if it's now empty
                    // Note that it's safe to break out here because every new animation
                    // on a property will cancel a previous animation on that property, so
                    // there can only ever be one such animation running.
                    if (bundle.mPropertyMask == NONE) {
                        // the animation is no longer changing anything - cancel it
                        animatorToCancel = runningAnim;
                        break;
                    }
                }
            }
            if (animatorToCancel != null) {
                animatorToCancel.cancel();
            }
        }

        NameValuesHolder nameValuePair = new NameValuesHolder(constantName, startValue, byValue);
        mPendingAnimations.add(nameValuePair);
        View v = mView.get();
        if (v != null) {
            v.removeCallbacks(mAnimationStarter);
            v.post(mAnimationStarter);
        }
    }

    /**
     * This method handles setting the property values directly in the View object's fields.
     * propertyConstant tells it which property should be set, value is the value to set
     * the property to.
     *
     * @param propertyConstant The property to be set
     * @param value The value to set the property to
     */
    private void setValue(int propertyConstant, float value) {
        //final View.TransformationInfo info = mView.mTransformationInfo;
        switch (propertyConstant) {
            case TRANSLATION_X:
                //info.mTranslationX = value;
                mProxy.setTranslationX(value);
                break;
            case TRANSLATION_Y:
                //info.mTranslationY = value;
                mProxy.setTranslationY(value);
                break;
            case ROTATION:
                //info.mRotation = value;
                mProxy.setRotation(value);
                break;
            case ROTATION_X:
                //info.mRotationX = value;
                mProxy.setRotationX(value);
                break;
            case ROTATION_Y:
                //info.mRotationY = value;
                mProxy.setRotationY(value);
                break;
            case SCALE_X:
                //info.mScaleX = value;
                mProxy.setScaleX(value);
                break;
            case SCALE_Y:
                //info.mScaleY = value;
                mProxy.setScaleY(value);
                break;
            case X:
                //info.mTranslationX = value - mView.mLeft;
                mProxy.setX(value);
                break;
            case Y:
                //info.mTranslationY = value - mView.mTop;
                mProxy.setY(value);
                break;
            case ALPHA:
                //info.mAlpha = value;
                mProxy.setAlpha(value);
                break;
        }
    }

    /**
     * This method gets the value of the named property from the View object.
     *
     * @param propertyConstant The property whose value should be returned
     * @return float The value of the named property
     */
    private float getValue(int propertyConstant) {
        //final View.TransformationInfo info = mView.mTransformationInfo;
        switch (propertyConstant) {
            case TRANSLATION_X:
                //return info.mTranslationX;
                return mProxy.getTranslationX();
            case TRANSLATION_Y:
                //return info.mTranslationY;
                return mProxy.getTranslationY();
            case ROTATION:
                //return info.mRotation;
                return mProxy.getRotation();
            case ROTATION_X:
                //return info.mRotationX;
                return mProxy.getRotationX();
            case ROTATION_Y:
                //return info.mRotationY;
                return mProxy.getRotationY();
            case SCALE_X:
                //return info.mScaleX;
                return mProxy.getScaleX();
            case SCALE_Y:
                //return info.mScaleY;
                return mProxy.getScaleY();
            case X:
                //return mView.mLeft + info.mTranslationX;
                return mProxy.getX();
            case Y:
                //return mView.mTop + info.mTranslationY;
                return mProxy.getY();
            case ALPHA:
                //return info.mAlpha;
                return mProxy.getAlpha();
        }
        return 0;
    }

    /**
     * Utility class that handles the various NineAnimator events. The only ones we care
     * about are the end event (which we use to clean up the animator map when an animator
     * finishes) and the update event (which we use to calculate the current value of each
     * property and then set it on the view object).
     */
    private class AnimatorEventListener
            implements NineAnimator.AnimatorListener, NineValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationStart(NineAnimator animation) {
            if (mListener != null) {
                mListener.onAnimationStart(animation);
            }
        }

        @Override
        public void onAnimationCancel(NineAnimator animation) {
            if (mListener != null) {
                mListener.onAnimationCancel(animation);
            }
        }

        @Override
        public void onAnimationRepeat(NineAnimator animation) {
            if (mListener != null) {
                mListener.onAnimationRepeat(animation);
            }
        }

        @Override
        public void onAnimationEnd(NineAnimator animation) {
            if (mListener != null) {
                mListener.onAnimationEnd(animation);
            }
            mAnimatorMap.remove(animation);
            // If the map is empty, it means all animation are done or canceled, so the listener
            // isn't needed anymore. Not nulling it would cause it to leak any objects used in
            // its implementation
            if (mAnimatorMap.isEmpty()) {
                mListener = null;
            }
        }

        /**
         * Calculate the current value for each property and set it on the view. Invalidate
         * the view object appropriately, depending on which properties are being animated.
         *
         * @param animation The animator associated with the properties that need to be
         * set. This animator holds the animation fraction which we will use to calculate
         * the current value of each property.
         */
        @Override
        public void onAnimationUpdate(NineValueAnimator animation) {
            // alpha requires slightly different treatment than the other (transform) properties.
            // The logic in setAlpha() is not simply setting mAlpha, plus the invalidation
            // logic is dependent on how the view handles an internal call to onSetAlpha().
            // We track what kinds of properties are set, and how alpha is handled when it is
            // set, and perform the invalidation steps appropriately.
            //boolean alphaHandled = false;
            //mView.invalidateParentCaches();
            float fraction = animation.getAnimatedFraction();
            PropertyBundle propertyBundle = mAnimatorMap.get(animation);
            int propertyMask = propertyBundle.mPropertyMask;
            if ((propertyMask & TRANSFORM_MASK) != 0) {
                View v = mView.get();
                if (v != null) {
                    v.invalidate(/*false*/);
                }
            }
            ArrayList<NameValuesHolder> valueList = propertyBundle.mNameValuesHolder;
            if (valueList != null) {
                int count = valueList.size();
                for (int i = 0; i < count; ++i) {
                    NameValuesHolder values = valueList.get(i);
                    float value = values.mFromValue + fraction * values.mDeltaValue;
                    //if (values.mNameConstant == ALPHA) {
                    //    alphaHandled = mView.setAlphaNoInvalidation(value);
                    //} else {
                        setValue(values.mNameConstant, value);
                    //}
                }
            }
            /*if ((propertyMask & TRANSFORM_MASK) != 0) {
                mView.mTransformationInfo.mMatrixDirty = true;
                mView.mPrivateFlags |= View.DRAWN; // force another invalidation
            }*/
            // invalidate(false) in all cases except if alphaHandled gets set to true
            // via the call to setAlphaNoInvalidation(), above
            View v = mView.get();
            if (v != null) {
                v.invalidate(/*alphaHandled*/);
            }
        }
    }
}
