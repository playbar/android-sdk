package com.bar.animation;

import android.view.View;
import com.bar.util.NineFloatProperty;
import com.bar.util.NineIntProperty;
import com.bar.util.NineProperty;
import com.bar.view.animation.NineAnimatorProxy;

final class NinePreHoneycombCompat {
    static NineProperty<View, Float> ALPHA = new NineFloatProperty<View>("alpha") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setAlpha(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getAlpha();
        }
    };
    static NineProperty<View, Float> PIVOT_X = new NineFloatProperty<View>("pivotX") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setPivotX(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getPivotX();
        }
    };
    static NineProperty<View, Float> PIVOT_Y = new NineFloatProperty<View>("pivotY") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setPivotY(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getPivotY();
        }
    };
    static NineProperty<View, Float> TRANSLATION_X = new NineFloatProperty<View>("translationX") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setTranslationX(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getTranslationX();
        }
    };
    static NineProperty<View, Float> TRANSLATION_Y = new NineFloatProperty<View>("translationY") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setTranslationY(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getTranslationY();
        }
    };
    static NineProperty<View, Float> ROTATION = new NineFloatProperty<View>("rotation") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setRotation(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getRotation();
        }
    };
    static NineProperty<View, Float> ROTATION_X = new NineFloatProperty<View>("rotationX") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setRotationX(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getRotationX();
        }
    };
    static NineProperty<View, Float> ROTATION_Y = new NineFloatProperty<View>("rotationY") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setRotationY(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getRotationY();
        }
    };
    static NineProperty<View, Float> SCALE_X = new NineFloatProperty<View>("scaleX") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setScaleX(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getScaleX();
        }
    };
    static NineProperty<View, Float> SCALE_Y = new NineFloatProperty<View>("scaleY") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setScaleY(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getScaleY();
        }
    };
    static NineProperty<View, Integer> SCROLL_X = new NineIntProperty<View>("scrollX") {
        @Override
        public void setValue(View object, int value) {
            NineAnimatorProxy.wrap(object).setScrollX(value);
        }

        @Override
        public Integer get(View object) {
            return NineAnimatorProxy.wrap(object).getScrollX();
        }
    };
    static NineProperty<View, Integer> SCROLL_Y = new NineIntProperty<View>("scrollY") {
        @Override
        public void setValue(View object, int value) {
            NineAnimatorProxy.wrap(object).setScrollY(value);
        }

        @Override
        public Integer get(View object) {
            return NineAnimatorProxy.wrap(object).getScrollY();
        }
    };
    static NineProperty<View, Float> X = new NineFloatProperty<View>("x") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setX(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getX();
        }
    };
    static NineProperty<View, Float> Y = new NineFloatProperty<View>("y") {
        @Override
        public void setValue(View object, float value) {
            NineAnimatorProxy.wrap(object).setY(value);
        }

        @Override
        public Float get(View object) {
            return NineAnimatorProxy.wrap(object).getY();
        }
    };


    //No instances
    private NinePreHoneycombCompat() {}
}
