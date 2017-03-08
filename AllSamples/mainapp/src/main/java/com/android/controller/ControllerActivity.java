package com.android.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.test.FileState;
import com.android.test.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class ControllerActivity extends Activity {
	private List<FileState> list = new ArrayList<FileState>();
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置界面的布局
		RelativeLayout.LayoutParams releparam = new RelativeLayout.LayoutParams( 400, 400 );
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.layout( 20, 20, 0, 0);
		relativeLayout.setBackgroundColor( 0xff00ff);


		relativeLayout.setHorizontalGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
		relativeLayout.setLayoutParams(releparam);
		setContentView(relativeLayout);

//		//添加一个AbsoluteLayout子布局,并给这个布局添加一个button
//		AbsoluteLayout abslayout=new AbsoluteLayout (this);
//
//		abslayout.setId(11);
		Button btn1 = new Button(this);
		btn1.setText("this is a abslayout button");
		btn1.setId(1);
		
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("onCreate", "btn1 onclick");
			}
		});

//		AbsoluteLayout.LayoutParams lp0 = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT,0,0);
//
//		relativeLayout.addView(btn1, lp0 );
		//将这个子布局添加到主布局中
		RelativeLayout.LayoutParams layoutparam1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutparam1.setMargins(50, 50, 100, 100);

		layoutparam1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layoutparam1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		relativeLayout.addView(btn1, layoutparam1);

//		//再添加一个子布局
//		RelativeLayout relativeLayout1 = new RelativeLayout(this);
//		Button btn2 = new Button(this);
//		btn2.setText("this is a relativeLayout1 button");
//		btn2.setId(2);
//		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		lp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//		relativeLayout1.addView(btn2 ,lp2);
//
//		//将这个布局添加到主布局中
//		RelativeLayout.LayoutParams lp11 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		lp11.addRule(RelativeLayout.BELOW ,11);
//		relativeLayout.addView(relativeLayout1 ,lp11);

	}


}
