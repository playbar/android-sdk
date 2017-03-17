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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:18
 */
/* package */ class PluginFactory {

    public final Container container;

    private static String containerSharedUserID;

    protected PluginFactory(Container container) {
        this.container = container;
        try {
            PackageManager pm = container.mContext.getPackageManager();
            containerSharedUserID = pm.getPackageInfo(container.getPluginInfo().packageName, 0).sharedUserId;
        } catch (PackageManager.NameNotFoundException e) {
        } finally {
            if (TextUtils.isEmpty(containerSharedUserID)) {
                containerSharedUserID = "com.bar.lidroid.plugin";
            }
        }
    }

    /**
     * all loaded modules.
     * key: packageName
     * value: module
     */
    private ConcurrentHashMap<String, Module> modules = new ConcurrentHashMap<String, Module>();

    /**
     * load all modules，
     * if the module exists，it will not load again。
     * if you need to load it again，please remove it firstly.
     */
    protected void loadAllModules() {

        PackageManager pm = container.mContext.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        String containerPkgName = container.getPluginInfo().packageName;
        for (PackageInfo info : packageInfoList) {
            if (containerSharedUserID.equals(info.sharedUserId)
                    && !containerPkgName.equals(info.packageName)) {
                loadModule(info);
            }
        }

        analyzeDependencies();
    }

    /**
     * load one module.
     * if the module exists，it will not load again。
     * if you need to load it again，please remove it firstly.
     */
    @SuppressWarnings("unchecked")
    protected void loadModule(PackageInfo info) {
        if (!modules.containsKey(info.packageName)) {

            if (!container.verifyPackage(info.packageName)) {
                return;
            }

            // found one module
            container.onModuleFound(info.packageName);

            try {

                Context moduleContext = container.mContext.createPackageContext(
                        info.packageName,
                        Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

                PathClassLoader classLoader = new PathClassLoader(
                        info.applicationInfo.sourceDir,
                        container.mContext.getClassLoader());

                Class clazz = classLoader.loadClass(
                        info.packageName + "." + Module.APPOINT_MODULE_CLASS_NAME);

                Constructor constructor = clazz.getConstructor(Context.class);
                Module module = (Module) constructor.newInstance(moduleContext);
                module.setClassLoader(classLoader);

                if (module.isDisabled()) {
                    Plugin.setModuleDisabled(module, true);
                }

                // init the module completely.
                container.onModuleInitialised(module);


                modules.put(info.packageName, module);

                container.onModuleLoaded(module);
            } catch (Exception e) {
                container.onModuleValidateError(info.packageName, e);
            }
        }
    }

    protected void removeModule(Module module) {
        modules.remove(module.getPluginInfo().packageName);
    }

    protected Module getModuleByPackageName(String packageName) {
        Module result = null;
        if (modules.containsKey(packageName)) {
            result = modules.get(packageName);
        }
        return result;
    }

    /**
     * get all the loaded modules.
     * <p/>
     * key: packageName
     * value: module
     */
    protected ConcurrentHashMap<String, Module> getAllModules() {
        return modules;
    }

    private void analyzeDependencies() {
        if (modules.size() > 0) {
            for (Module module : modules.values()) {
                List<PluginDependence> dependencies = module.getPluginDependencies();
                if (dependencies != null && dependencies.size() > 0) {
                    for (PluginDependence dependence : dependencies) {
                        if (modules.containsKey(dependence.packageName)) {
                            if (!compareDependencies(dependence, modules.get(dependence.packageName))) {
                                container.onModuleDependenceError(module, dependence);
                            }
                        } else if (dependence.packageName.equals(container.getPluginInfo().packageName)) {
                            if (!compareDependencies(dependence, container)) {
                                container.onModuleDependenceError(module, dependence);
                            }
                        } else {
                            container.onModuleDependenceError(module, dependence);
                        }
                    }
                }
            }
        }
    }

    /**
     * compare the dependence
     *
     * @param dependence
     * @param plugin
     * @return whether match the dependence or not
     */
    private boolean compareDependencies(PluginDependence dependence, Plugin plugin) {
        if (dependence.packageName.equals(plugin.getPluginInfo().packageName)) {
            switch (dependence.relationship) {
                case eq: {
                    return dependence.versionCode == plugin.getPluginInfo().versionCode;
                }
                case ge: {
                    return dependence.versionCode >= plugin.getPluginInfo().versionCode;
                }
                case gt: {
                    return dependence.versionCode > plugin.getPluginInfo().versionCode;
                }
                case le: {
                    return dependence.versionCode < plugin.getPluginInfo().versionCode;
                }
                case lt: {
                    return dependence.versionCode <= plugin.getPluginInfo().versionCode;
                }
            }
        }
        return true;
    }
}
