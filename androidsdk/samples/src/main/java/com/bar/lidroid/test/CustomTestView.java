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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bar.R;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-18
 * Time: 下午7:37
 */
public class CustomTestView extends LinearLayout implements View.OnClickListener {

    private Button mButton;
    private ImageView mImageView;

    private Context mContext;

    public CustomTestView(Context context) {
        super(context);

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.test_view, this);

        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
    }
}
