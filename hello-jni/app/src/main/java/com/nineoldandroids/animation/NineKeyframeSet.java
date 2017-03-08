/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.nineoldandroids.animation;

import java.util.ArrayList;
import java.util.Arrays;

import android.view.animation.Interpolator;

import com.nineoldandroids.animation.NineKeyframe.FloatKeyframe;
import com.nineoldandroids.animation.NineKeyframe.IntKeyframe;
import com.nineoldandroids.animation.NineKeyframe.ObjectKeyframe;

/**
 * This class holds a collection of NineKeyframe objects and is called by NineValueAnimator to calculate
 * values between those keyframes for a given animation. The class internal to the animation
 * package because it is an implementation detail of how Keyframes are stored and used.
 */
class NineKeyframeSet {

    int mNumKeyframes;

    NineKeyframe mFirstKeyframe;
    NineKeyframe mLastKeyframe;
    /*Time*/Interpolator mInterpolator; // only used in the 2-keyframe case
    ArrayList<NineKeyframe> mKeyframes; // only used when there are not 2 keyframes
    NineTypeEvaluator mEvaluator;


    public NineKeyframeSet(NineKeyframe... keyframes) {
        mNumKeyframes = keyframes.length;
        mKeyframes = new ArrayList<NineKeyframe>();
        mKeyframes.addAll(Arrays.asList(keyframes));
        mFirstKeyframe = mKeyframes.get(0);
        mLastKeyframe = mKeyframes.get(mNumKeyframes - 1);
        mInterpolator = mLastKeyframe.getInterpolator();
    }

    public static NineKeyframeSet ofInt(int... values) {
        int numKeyframes = values.length;
        IntKeyframe keyframes[] = new IntKeyframe[Math.max(numKeyframes,2)];
        if (numKeyframes == 1) {
            keyframes[0] = (IntKeyframe) NineKeyframe.ofInt(0f);
            keyframes[1] = (IntKeyframe) NineKeyframe.ofInt(1f, values[0]);
        } else {
            keyframes[0] = (IntKeyframe) NineKeyframe.ofInt(0f, values[0]);
            for (int i = 1; i < numKeyframes; ++i) {
                keyframes[i] = (IntKeyframe) NineKeyframe.ofInt((float) i / (numKeyframes - 1), values[i]);
            }
        }
        return new NineIntKeyframeSet(keyframes);
    }

    public static NineKeyframeSet ofFloat(float... values) {
        int numKeyframes = values.length;
        FloatKeyframe keyframes[] = new FloatKeyframe[Math.max(numKeyframes,2)];
        if (numKeyframes == 1) {
            keyframes[0] = (FloatKeyframe) NineKeyframe.ofFloat(0f);
            keyframes[1] = (FloatKeyframe) NineKeyframe.ofFloat(1f, values[0]);
        } else {
            keyframes[0] = (FloatKeyframe) NineKeyframe.ofFloat(0f, values[0]);
            for (int i = 1; i < numKeyframes; ++i) {
                keyframes[i] = (FloatKeyframe) NineKeyframe.ofFloat((float) i / (numKeyframes - 1), values[i]);
            }
        }
        return new NineFloatKeyframeSet(keyframes);
    }

    public static NineKeyframeSet ofKeyframe(NineKeyframe... keyframes) {
        // if all keyframes of same primitive type, create the appropriate NineKeyframeSet
        int numKeyframes = keyframes.length;
        boolean hasFloat = false;
        boolean hasInt = false;
        boolean hasOther = false;
        for (int i = 0; i < numKeyframes; ++i) {
            if (keyframes[i] instanceof FloatKeyframe) {
                hasFloat = true;
            } else if (keyframes[i] instanceof IntKeyframe) {
                hasInt = true;
            } else {
                hasOther = true;
            }
        }
        if (hasFloat && !hasInt && !hasOther) {
            FloatKeyframe floatKeyframes[] = new FloatKeyframe[numKeyframes];
            for (int i = 0; i < numKeyframes; ++i) {
                floatKeyframes[i] = (FloatKeyframe) keyframes[i];
            }
            return new NineFloatKeyframeSet(floatKeyframes);
        } else if (hasInt && !hasFloat && !hasOther) {
            IntKeyframe intKeyframes[] = new IntKeyframe[numKeyframes];
            for (int i = 0; i < numKeyframes; ++i) {
                intKeyframes[i] = (IntKeyframe) keyframes[i];
            }
            return new NineIntKeyframeSet(intKeyframes);
        } else {
            return new NineKeyframeSet(keyframes);
        }
    }

    public static NineKeyframeSet ofObject(Object... values) {
        int numKeyframes = values.length;
        ObjectKeyframe keyframes[] = new ObjectKeyframe[Math.max(numKeyframes,2)];
        if (numKeyframes == 1) {
            keyframes[0] = (ObjectKeyframe) NineKeyframe.ofObject(0f);
            keyframes[1] = (ObjectKeyframe) NineKeyframe.ofObject(1f, values[0]);
        } else {
            keyframes[0] = (ObjectKeyframe) NineKeyframe.ofObject(0f, values[0]);
            for (int i = 1; i < numKeyframes; ++i) {
                keyframes[i] = (ObjectKeyframe) NineKeyframe.ofObject((float) i / (numKeyframes - 1), values[i]);
            }
        }
        return new NineKeyframeSet(keyframes);
    }

    /**
     * Sets the NineTypeEvaluator to be used when calculating animated values. This object
     * is required only for KeyframeSets that are not either NineIntKeyframeSet or NineFloatKeyframeSet,
     * both of which assume their own evaluator to speed up calculations with those primitive
     * types.
     *
     * @param evaluator The NineTypeEvaluator to be used to calculate animated values.
     */
    public void setEvaluator(NineTypeEvaluator evaluator) {
        mEvaluator = evaluator;
    }

    @Override
    public NineKeyframeSet clone() {
        ArrayList<NineKeyframe> keyframes = mKeyframes;
        int numKeyframes = mKeyframes.size();
        NineKeyframe[] newKeyframes = new NineKeyframe[numKeyframes];
        for (int i = 0; i < numKeyframes; ++i) {
            newKeyframes[i] = keyframes.get(i).clone();
        }
        NineKeyframeSet newSet = new NineKeyframeSet(newKeyframes);
        return newSet;
    }

    /**
     * Gets the animated value, given the elapsed fraction of the animation (interpolated by the
     * animation's interpolator) and the evaluator used to calculate in-between values. This
     * function maps the input fraction to the appropriate keyframe interval and a fraction
     * between them and returns the interpolated value. Note that the input fraction may fall
     * outside the [0-1] bounds, if the animation's interpolator made that happen (e.g., a
     * spring interpolation that might send the fraction past 1.0). We handle this situation by
     * just using the two keyframes at the appropriate end when the value is outside those bounds.
     *
     * @param fraction The elapsed fraction of the animation
     * @return The animated value.
     */
    public Object getValue(float fraction) {

        // Special-case optimization for the common case of only two keyframes
        if (mNumKeyframes == 2) {
            if (mInterpolator != null) {
                fraction = mInterpolator.getInterpolation(fraction);
            }
            return mEvaluator.evaluate(fraction, mFirstKeyframe.getValue(),
                    mLastKeyframe.getValue());
        }
        if (fraction <= 0f) {
            final NineKeyframe nextKeyframe = mKeyframes.get(1);
            final /*Time*/Interpolator interpolator = nextKeyframe.getInterpolator();
            if (interpolator != null) {
                fraction = interpolator.getInterpolation(fraction);
            }
            final float prevFraction = mFirstKeyframe.getFraction();
            float intervalFraction = (fraction - prevFraction) /
                (nextKeyframe.getFraction() - prevFraction);
            return mEvaluator.evaluate(intervalFraction, mFirstKeyframe.getValue(),
                    nextKeyframe.getValue());
        } else if (fraction >= 1f) {
            final NineKeyframe prevKeyframe = mKeyframes.get(mNumKeyframes - 2);
            final /*Time*/Interpolator interpolator = mLastKeyframe.getInterpolator();
            if (interpolator != null) {
                fraction = interpolator.getInterpolation(fraction);
            }
            final float prevFraction = prevKeyframe.getFraction();
            float intervalFraction = (fraction - prevFraction) /
                (mLastKeyframe.getFraction() - prevFraction);
            return mEvaluator.evaluate(intervalFraction, prevKeyframe.getValue(),
                    mLastKeyframe.getValue());
        }
        NineKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 1; i < mNumKeyframes; ++i) {
            NineKeyframe nextKeyframe = mKeyframes.get(i);
            if (fraction < nextKeyframe.getFraction()) {
                final /*Time*/Interpolator interpolator = nextKeyframe.getInterpolator();
                if (interpolator != null) {
                    fraction = interpolator.getInterpolation(fraction);
                }
                final float prevFraction = prevKeyframe.getFraction();
                float intervalFraction = (fraction - prevFraction) /
                    (nextKeyframe.getFraction() - prevFraction);
                return mEvaluator.evaluate(intervalFraction, prevKeyframe.getValue(),
                        nextKeyframe.getValue());
            }
            prevKeyframe = nextKeyframe;
        }
        // shouldn't reach here
        return mLastKeyframe.getValue();
    }

    @Override
    public String toString() {
        String returnVal = " ";
        for (int i = 0; i < mNumKeyframes; ++i) {
            returnVal += mKeyframes.get(i).getValue() + "  ";
        }
        return returnVal;
    }
}
