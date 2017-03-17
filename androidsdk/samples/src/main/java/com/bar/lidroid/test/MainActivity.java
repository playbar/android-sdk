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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bar.R;
import com.bar.lidroid.plugin.Module;
import com.bar.lidroid.plugin.Plugin;
import com.bar.lidroid.plugin.PluginMessage;

import java.util.Date;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private MainContainer mContainer;

    private TextView mTextView;
    private Button mButton;
    private LinearLayout mViewHost;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

        mViewHost = (LinearLayout) findViewById(R.id.view_host);
        mTextView = (TextView) findViewById(R.id.result_txt);
        mButton = (Button) findViewById(R.id.send_msg_button);
        mButton.setOnClickListener(this);

        mContainer = new MainContainer(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContainer.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContainer.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mContainer.onStop();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        // send test msg
        Map<String, Module> modules = Plugin.getAllModules();
        PluginMessage msg = new PluginMessage(123, "biu biu, i'm a msg.", new Date(), mTextView);
        for (Module module : modules.values()) {
            mContainer.sendMessage(msg, module);
        }

        // test mop
        View view = mContainer.test(mTextView);
        if (view != null) {
            mViewHost.addView(view);
        }
    }

    public void textViewSetText(String result) {
        mTextView.setText(result);
    }
}
