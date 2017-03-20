package com.bar.nineoldandroids.animation;

import android.view.View;

import com.bar.nineoldandroids.util.IntProperty;
import com.bar.nineoldandroids.util.Property;

final class PreHoneycombCompat {
    static Property<View, Float> ALPHA = new com.bar.nineoldandroids.util.FloatProperty<View>("alpha") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setAlpha(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getAlpha();
        }
    };
    static Property<View, Float> PIVOT_X = new com.bar.nineoldandroids.util.FloatProperty<View>("pivotX") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setPivotX(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getPivotX();
        }
    };
    static Property<View, Float> PIVOT_Y = new com.bar.nineoldandroids.util.FloatProperty<View>("pivotY") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setPivotY(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getPivotY();
        }
    };
    static Property<View, Float> TRANSLATION_X = new com.bar.nineoldandroids.util.FloatProperty<View>("translationX") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setTranslationX(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getTranslationX();
        }
    };
    static Property<View, Float> TRANSLATION_Y = new com.bar.nineoldandroids.util.FloatProperty<View>("translationY") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setTranslationY(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getTranslationY();
        }
    };
    static Property<View, Float> ROTATION = new com.bar.nineoldandroids.util.FloatProperty<View>("rotation") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setRotation(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getRotation();
        }
    };
    static Property<View, Float> ROTATION_X = new com.bar.nineoldandroids.util.FloatProperty<View>("rotationX") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setRotationX(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getRotationX();
        }
    };
    static Property<View, Float> ROTATION_Y = new com.bar.nineoldandroids.util.FloatProperty<View>("rotationY") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setRotationY(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getRotationY();
        }
    };
    static Property<View, Float> SCALE_X = new com.bar.nineoldandroids.util.FloatProperty<View>("scaleX") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setScaleX(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getScaleX();
        }
    };
    static Property<View, Float> SCALE_Y = new com.bar.nineoldandroids.util.FloatProperty<View>("scaleY") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setScaleY(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getScaleY();
        }
    };
    static Property<View, Integer> SCROLL_X = new IntProperty<View>("scrollX") {
        @Override
        public void setValue(View object, int value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setScrollX(value);
        }

        @Override
        public Integer get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getScrollX();
        }
    };
    static Property<View, Integer> SCROLL_Y = new IntProperty<View>("scrollY") {
        @Override
        public void setValue(View object, int value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setScrollY(value);
        }

        @Override
        public Integer get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getScrollY();
        }
    };
    static Property<View, Float> X = new com.bar.nineoldandroids.util.FloatProperty<View>("x") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setX(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getX();
        }
    };
    static Property<View, Float> Y = new com.bar.nineoldandroids.util.FloatProperty<View>("y") {
        @Override
        public void setValue(View object, float value) {
            com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).setY(value);
        }

        @Override
        public Float get(View object) {
            return com.bar.nineoldandroids.view.animation.AnimatorProxy.wrap(object).getY();
        }
    };


    //No instances
    private PreHoneycombCompat() {}
}
