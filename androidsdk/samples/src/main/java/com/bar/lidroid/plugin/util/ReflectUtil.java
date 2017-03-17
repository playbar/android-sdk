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

package com.bar.lidroid.plugin.util;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: PM 9:20
 */
public class ReflectUtil {

    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    @SuppressWarnings("unchecked")
    public static Method getMethod(Class clazz, String methodName, Object... args) {
        Method result = null;
        Class[] types = null;
        if (args != null) {
            types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
                if (types[i].equals(Integer.class)) {
                    types[i] = int.class;
                } else if (types[i].equals(Boolean.class)) {
                    types[i] = boolean.class;
                } else if (types[i].equals(Float.class)) {
                    types[i] = float.class;
                } else if (types[i].equals(Double.class)) {
                    types[i] = double.class;
                } else if (types[i].equals(Byte.class)) {
                    types[i] = byte.class;
                } else if (types[i].equals(Character.class)) {
                    types[i] = char.class;
                } else if (types[i].equals(Long.class)) {
                    types[i] = long.class;
                } else if (types[i].equals(Short.class)) {
                    types[i] = short.class;
                }
            }
        }
        try {
            result = clazz.getMethod(methodName, types);
        } catch (NoSuchMethodException e) {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    types[i] = args[i].getClass();
                }
                try {
                    result = clazz.getMethod(methodName, types);
                } catch (NoSuchMethodException e1) {
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (methodName.equals(method.getName())) {
                            Class<?>[] paramTypes = method.getParameterTypes();
                            if (paramTypes != null && paramTypes.length == args.length) {
                                result = method;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
