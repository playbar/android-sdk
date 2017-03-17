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

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bar.lidroid.plugin.Module;
import com.bar.lidroid.plugin.Plugin;
import com.bar.lidroid.plugin.PluginDependence;
import com.bar.lidroid.plugin.PluginMessage;
import com.bar.lidroid.plugin.mop.MopImpl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-18
 * Time: 下午3:14
 */
public class CustomModule extends Module {

    public final static String TAG = "wyouflf";

    public CustomModule(Context context) {
        super(context);
    }

    @Override
    public List<PluginDependence> getPluginDependencies() {
        return null;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public void setDisabled(boolean disabled) {
        Log.d(TAG, "setDisabled:" + disabled);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart:");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume:");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop:");
    }

    @Override
    public void onMessageReceived(PluginMessage msg, Plugin from) {
        Log.d(TAG, "onMessageReceived:" + msg.content + ", args:" + msg.args);

        this.respondMessage(msg, from, " i'm a reply from the test module. ");

        TextView textView = ((TextView) msg.args[1]);
        textView.setTextColor(0xff006699);
        textView.setText(textView.getText() + " the text color changed by msg receiver. ");
    }

    @Override
    public void onMessageResponded(PluginMessage msg, Plugin from, Object result) {
        Log.d(TAG, "onMessageResponded:" + result);
    }

    private static int test = 0;

    @MopImpl
    public View test(TextView y) {
        y.setBackgroundColor(0xff000000);
        y.setText(y.getText() + " the bg color changed by mop. ");
        return new CustomTestView(this.getContext());
    }
}
