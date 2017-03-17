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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.bar.lidroid.plugin.app.ModuleActivity;
import com.bar.lidroid.plugin.app.TemplateActivity;
import com.bar.lidroid.plugin.mop.MopAdapter;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:22
 * <p/>
 * the parent of Container and Module
 */
public abstract class Plugin extends MopAdapter {

    private PluginInfo info;

    protected final Context mContext;

    protected Plugin(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null.");
        }

        Context appContext = context.getApplicationContext();
        if (appContext != null) {
            this.mContext = appContext;
        } else {
            this.mContext = context;
        }
    }

    public final Context getContext() {
        return mContext;
    }

    public static Container getContainer() {
        return PluginManager.getInstance().getContainer();
    }

    public static Module getModule(String pkgName) {
        return PluginManager.getInstance().getModuleByPackageName(pkgName);
    }

    public static <T extends Module> T getModule(Class<T> moduleClazz) {
        return (T) PluginManager.getInstance().getModuleByPackageName(moduleClazz.getPackage().getName());
    }

    public static Map<String, Module> getAllModules() {
        return PluginManager.getInstance().getAllModules();
    }

    public static void setModuleDisabled(Module module, boolean disabled) {
        PluginManager.getInstance().setModuleDisabled(module, disabled);
    }

    public static void stopModule(Module module) {
        PluginManager.getInstance().stopModule(module);
    }

    // #region startModuleActivity
    public static Class<? extends ModuleActivity> CurrModuleClazz;
    public static Context CurrModuleContext;

    public synchronized void startModuleActivity(
            Context acContext,
            Class<? extends TemplateActivity> templateClazz,
            Class<? extends ModuleActivity> moduleClazz) {
        startModuleActivity(acContext, templateClazz, moduleClazz, null);
    }

    public synchronized void startModuleActivity(
            Context acContext,
            Class<? extends TemplateActivity> templateClazz,
            Class<? extends ModuleActivity> moduleClazz,
            Intent extras) {
        Intent intent = new Intent(acContext, templateClazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        CurrModuleClazz = moduleClazz;
        CurrModuleContext = mContext;
        acContext.startActivity(intent);
    }

    public synchronized void startModuleActivityForResult(
            Activity acContext,
            Class<? extends TemplateActivity> templateClazz,
            Class<? extends ModuleActivity> moduleClazz,
            int requestCode) {
        startModuleActivityForResult(acContext, templateClazz, moduleClazz, 0, null);
    }

    public synchronized void startModuleActivityForResult(
            Activity acContext,
            Class<? extends TemplateActivity> templateClazz,
            Class<? extends ModuleActivity> moduleClazz,
            int requestCode,
            Intent extras) {
        Intent intent = new Intent(acContext, templateClazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        CurrModuleClazz = moduleClazz;
        CurrModuleContext = mContext;
        acContext.startActivityForResult(intent, requestCode);
    }
    // #endregion startModuleActivity

    /**
     * send a msg among plugins,
     * the format of msg comply with appointments of receiver.
     *
     * @param msg
     * @param to
     */
    public final void sendMessage(PluginMessage msg, final Plugin to) {
        PluginManager.getInstance().sendMessage(msg, this, to);
    }

    /**
     * send a msg among plugins,
     * the format of msg comply with appointments of receiver.
     *
     * @param msg
     * @param to
     */
    public final void respondMessage(PluginMessage msg, final Plugin to, Object result) {
        PluginManager.getInstance().respondMessage(msg, this, to, result);
    }

    /**
     * deal with the msg that it received,
     * and return the result to the plugin(arg. from).
     * use PluginManager.respondMessage(msg, this, from，result); to reply.
     *
     * @param msg
     * @param from
     */
    public abstract void onMessageReceived(PluginMessage msg, final Plugin from);

    /**
     * receive the result from the plugin(arg. from).
     *
     * @param msg
     * @param from
     * @param result
     */
    public abstract void onMessageResponded(PluginMessage msg, final Plugin from, Object result);

    public final PluginInfo getPluginInfo() {
        if (info == null) {
            Context context = getContext();
            String packageName = context.getPackageName();
            PackageManager pm = context.getPackageManager();

            try {
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);

                Drawable icon = pm.getApplicationIcon(packageName);
                String name = (String) applicationInfo.loadLabel(pm);
                int versionCode = packageInfo.versionCode;
                String versionName = packageInfo.versionName;

                info = new PluginInfo(
                        name,
                        icon,
                        packageName,
                        versionCode,
                        versionName
                );

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plugin)) return false;

        Plugin plugin = (Plugin) o;

        if (!info.equals(plugin.info)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return info.hashCode();
    }
}
