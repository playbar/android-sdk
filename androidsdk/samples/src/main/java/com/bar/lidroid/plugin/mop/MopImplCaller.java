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

import com.bar.lidroid.plugin.Container;
import com.bar.lidroid.plugin.Module;
import com.bar.lidroid.plugin.Plugin;
import com.bar.lidroid.plugin.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 10:32
 */
public class MopImplCaller {

    private String methodName;

    private List<Object> targets;

    private Object[] args;

    private MopAgent mopAgent;

    protected MopImplCaller(StackTraceElement callerStackTraceElement, Object... args) {

        this.args = args;

        String callerClassName = callerStackTraceElement.getClassName();
        methodName = callerStackTraceElement.getMethodName();

        Container container = Plugin.getContainer();

        boolean isContainer = !callerClassName.endsWith(Module.APPOINT_MODULE_CLASS_NAME);

        // get mopAgent
        if (isContainer) {
            mopAgent = MopAgentUtil.getMopAgentAnnotation(
                    container.getContext().getClass(),
                    methodName, args);
            if (mopAgent == null) {
                mopAgent = MopAgentUtil.getMopAgentAnnotation(
                        container.getClass(),
                        methodName, args);
            }
        } else {
            String packageName = callerClassName.substring(0, callerClassName.lastIndexOf("."));
            Class clazz = Plugin.getModule(packageName).getClass();
            mopAgent = MopAgentUtil.getMopAgentAnnotation(
                    clazz,
                    methodName, args);
        }

        if (mopAgent == null) {
            try {
                Class clazz = Class.forName(callerClassName);
                mopAgent = MopAgentUtil.getMopAgentAnnotation(
                        clazz,
                        methodName, args);
            } catch (Exception e) {
            }
        }

        // add target object
        if (mopAgent != null) {
            switch (mopAgent.targetType()) {
                case Container: {
                    targets = new ArrayList<Object>(2);
                    targets.add(container.getAttachedActivity());
                    targets.add(container);
                    break;
                }
                case Module: {
                    targets = new ArrayList<Object>(1);
                    targets.add(Plugin.getModule(mopAgent.packageName()));
                    break;
                }
                case AllModules: {
                    targets = new ArrayList<Object>();
                    Map<String, Module> modules = Plugin.getAllModules();
                    if (modules != null) {
                        for (Module module : modules.values()) {
                            targets.add(module);
                        }
                    }
                    break;
                }
                case All: {
                    targets = new ArrayList<Object>();
                    targets.add(container.getAttachedActivity());
                    targets.add(container);
                    Map<String, Module> modules = Plugin.getAllModules();
                    if (modules != null) {
                        for (Module module : modules.values()) {
                            targets.add(module);
                        }
                    }
                    break;
                }
            }
        }
    }

    protected void execAction() {
        if (targets != null) {
            for (Object receiver : targets) {
                execOneAction(receiver, this.methodName, this.args);
            }
        }
    }

    protected List<Object> execFunc() {
        List<Object> results = null;
        if (targets != null) {
            for (Object receiver : targets) {
                Object ret = execOneFunc(receiver, this.methodName, this.args);
                if (ret != null) {
                    results.add(ret);
                }
            }
        }
        return results;
    }

    protected Object execFuncSingle() {
        if (targets != null) {
            for (Object receiver : targets) {
                Object result = execOneFunc(receiver, this.methodName, this.args);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private void execOneAction(Object receiver, String methodName, Object... args) {
        Method method = ReflectUtil.getMethod(receiver.getClass(), methodName, args);
        if (method != null &&
                method.getAnnotation(MopAgent.class) == null &&
                (mopAgent.ignoreMopImpl() || method.getAnnotation(MopImpl.class) != null)) {
            try {
                method.invoke(receiver, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        }
    }

    private Object execOneFunc(Object receiver, String methodName, Object... args) {
        Method method = ReflectUtil.getMethod(receiver.getClass(), methodName, args);
        if (method != null &&
                method.getAnnotation(MopAgent.class) == null &&
                (mopAgent.ignoreMopImpl() || method.getAnnotation(MopImpl.class) != null)) {
            try {
                return method.invoke(receiver, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
