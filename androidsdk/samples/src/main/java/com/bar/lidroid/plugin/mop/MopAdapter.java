/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bar.lidroid.plugin.mop;

import com.bar.lidroid.plugin.util.ReflectUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 9:34
 */
public class MopAdapter {

    public void mopAction(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerStackTraceElement();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            try {
                mopImplCaller.execAction();
            } catch (Exception e) {
            }
        }
    }

    public List<Object> mopFunc(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerStackTraceElement();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            try {
                return mopImplCaller.execFunc();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public Object mopFuncSingle(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerStackTraceElement();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            try {
                return mopImplCaller.execFuncSingle();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
