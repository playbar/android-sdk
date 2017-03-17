/*
 * Copyright (c) 2013. wyouflf(wyouflf@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bar.lidroid.test;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bar.lidroid.plugin.*;
import com.bar.lidroid.plugin.mop.MopAgent;
import com.bar.lidroid.plugin.mop.TargetType;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-18
 * Time: 下午3:06
 */
public class MainContainer extends Container {

    public final static String TAG = "wyouflf";

    public MainContainer(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public boolean verifyPackage(String packageName) {
        return true;
    }

    @Override
    public void onModuleFound(String packageName) {
        Log.d(TAG, "onModuleFound:" + packageName);
    }

    @Override
    public void onModuleValidateError(String packageName, Throwable error) {
        Log.d(TAG, "onModuleValidateError:" + packageName + ", error:" + error.getMessage());
    }

    @Override
    public void onModuleDependenceError(Module module, PluginDependence dependence) {
        Log.d(TAG, "onModuleDependenceError:" + module.getPluginInfo().packageName + ", " + dependence);
    }

    @Override
    public void onModuleInitialised(Module module) {
        Log.d(TAG, "onModuleInitialised:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleLoaded(Module module) {
        Log.d(TAG, "onModuleLoaded:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleStopped(Module module) {
        Log.d(TAG, "onModuleStopped:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleDisableChanged(Module module, boolean disabled) {
        Log.d(TAG, "onModuleDisableChanged:" + module.getPluginInfo().name + ", disabled " + disabled);
    }

    @Override
    public void onModuleUninstalled(Module module) {
        Log.d(TAG, "onModuleUninstalled:" + module.getPluginInfo().name);
    }

    @Override
    public void onMessageReceived(PluginMessage msg, Plugin from) {
        Log.d(TAG, "onMessageReceived:" + msg.content + ", args:" + msg.args);
        this.respondMessage(msg, from, "response test");
    }

    @Override
    public void onMessageResponded(PluginMessage msg, Plugin from, Object result) {
        Log.d(TAG, "onMessageResponded:" + result);
        ((MainActivity) getAttachedActivity()).textViewSetText((String) result);
    }

    @MopAgent(targetType = TargetType.Module, packageName = "com.bar.lidroid.test.ModuleTest")
    public View test(TextView b) {
        return (View) mopFuncSingle(b);
    }
}
