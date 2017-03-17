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

import com.bar.lidroid.plugin.util.DoubleKeyValueMap;
import com.bar.lidroid.plugin.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 11:31
 */
public class MopAgentUtil {

    /**
     * key1: class
     * key2: methodName
     * value: mopAgent
     */
    private static DoubleKeyValueMap<Class, String, MopAgent> runAsLocalMap =
            new DoubleKeyValueMap<Class, String, MopAgent>();

    public static MopAgent getMopAgentAnnotation(Class clazz, String methodName, Object... args) {

        MopAgent result = null;
        if (runAsLocalMap.containsKey(clazz, methodName)) {
            result = runAsLocalMap.get(clazz, methodName);
        } else {
            Method method = ReflectUtil.getMethod(clazz, methodName, args);
            if (method != null) {
                result = method.getAnnotation(MopAgent.class);
            }
        }
        return result;
    }

}
