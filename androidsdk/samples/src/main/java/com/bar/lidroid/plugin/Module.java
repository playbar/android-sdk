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

package com.bar.lidroid.plugin;

import android.content.Context;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:21
 */
public abstract class Module extends Plugin {

    public final static String APPOINT_MODULE_CLASS_NAME = "CustomModule";

    public Module(Context context) {
        super(context);
    }

    private ClassLoader classLoader;

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public abstract List<PluginDependence> getPluginDependencies();

    public abstract boolean isDisabled();

    public abstract void setDisabled(boolean disabled);

    public abstract void onStart();

    public abstract void onResume();

    public abstract void onStop();
}
