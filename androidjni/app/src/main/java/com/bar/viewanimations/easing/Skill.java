/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bar.viewanimations.easing;

import com.bar.viewanimations.easing.back.BackEaseIn;
import com.bar.viewanimations.easing.back.BackEaseInOut;
import com.bar.viewanimations.easing.back.BackEaseOut;
import com.bar.viewanimations.easing.bounce.BounceEaseIn;
import com.bar.viewanimations.easing.bounce.BounceEaseInOut;
import com.bar.viewanimations.easing.bounce.BounceEaseOut;
import com.bar.viewanimations.easing.circ.CircEaseIn;
import com.bar.viewanimations.easing.circ.CircEaseInOut;
import com.bar.viewanimations.easing.circ.CircEaseOut;
import com.bar.viewanimations.easing.cubic.CubicEaseIn;
import com.bar.viewanimations.easing.cubic.CubicEaseInOut;
import com.bar.viewanimations.easing.cubic.CubicEaseOut;
import com.bar.viewanimations.easing.elastic.ElasticEaseIn;
import com.bar.viewanimations.easing.elastic.ElasticEaseOut;
import com.bar.viewanimations.easing.expo.ExpoEaseIn;
import com.bar.viewanimations.easing.expo.ExpoEaseInOut;
import com.bar.viewanimations.easing.expo.ExpoEaseOut;
import com.bar.viewanimations.easing.quad.QuadEaseIn;
import com.bar.viewanimations.easing.quad.QuadEaseInOut;
import com.bar.viewanimations.easing.quad.QuadEaseOut;
import com.bar.viewanimations.easing.quint.QuintEaseIn;
import com.bar.viewanimations.easing.quint.QuintEaseInOut;
import com.bar.viewanimations.easing.quint.QuintEaseOut;
import com.bar.viewanimations.easing.sine.SineEaseIn;
import com.bar.viewanimations.easing.sine.SineEaseInOut;
import com.bar.viewanimations.easing.sine.SineEaseOut;
import com.bar.viewanimations.easing.linear.Linear;


public enum  Skill {

    BackEaseIn(BackEaseIn.class),
    BackEaseOut(BackEaseOut.class),
    BackEaseInOut(BackEaseInOut.class),

    BounceEaseIn(BounceEaseIn.class),
    BounceEaseOut(BounceEaseOut.class),
    BounceEaseInOut(BounceEaseInOut.class),

    CircEaseIn(CircEaseIn.class),
    CircEaseOut(CircEaseOut.class),
    CircEaseInOut(CircEaseInOut.class),

    CubicEaseIn(CubicEaseIn.class),
    CubicEaseOut(CubicEaseOut.class),
    CubicEaseInOut(CubicEaseInOut.class),

    ElasticEaseIn(ElasticEaseIn.class),
    ElasticEaseOut(ElasticEaseOut.class),

    ExpoEaseIn(ExpoEaseIn.class),
    ExpoEaseOut(ExpoEaseOut.class),
    ExpoEaseInOut(ExpoEaseInOut.class),

    QuadEaseIn(QuadEaseIn.class),
    QuadEaseOut(QuadEaseOut.class),
    QuadEaseInOut(QuadEaseInOut.class),

    QuintEaseIn(QuintEaseIn.class),
    QuintEaseOut(QuintEaseOut.class),
    QuintEaseInOut(QuintEaseInOut.class),

    SineEaseIn(SineEaseIn.class),
    SineEaseOut(SineEaseOut.class),
    SineEaseInOut(SineEaseInOut.class),

    Linear(Linear.class);


    private Class easingMethod;

    private Skill(Class clazz) {
        easingMethod = clazz;
    }

    public BaseEasingMethod getMethod(float duration) {
        try {
            return (BaseEasingMethod)easingMethod.getConstructor(float.class).newInstance(duration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init easingMethod instance");
        }
    }
}
