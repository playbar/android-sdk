package com.bar.grid;

import java.util.ArrayList;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.bar.R;

public class ListActivity extends Activity {
	/** Called when the activity is first created. */
	ListView lv;
	ArrayAdapter<String> Adapter;
	ArrayList<String> arr = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(R.id.lv);
		arr.add("111");
		arr.add("222");
		arr.add("333");
		Adapter = new ArrayAdapter<String>(this, R.layout.playlist, arr);
		lv.setAdapter(Adapter);
		lv.setOnItemClickListener(lvLis);
		editItem edit = new editItem();
		edit.execute("0", "第1项");// 把第一项内容改为"第一项"
		Handler handler = new Handler();
		handler.postDelayed(add, 3000);// 延迟3秒执行
	}

	Runnable add = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			arr.add("增加一项");// 增加一项
			Adapter.notifyDataSetChanged();
		}
	};

	class editItem extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			arr.set(Integer.parseInt(params[0]), params[1]);
			// params得到的是一个数组，params[0]在这里是"0",params[1]是"第1项"
			Adapter.notifyDataSetChanged();
			return null;
		}
	}

	private OnItemClickListener lvLis = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// 点击条目时触发
			// arg2即为点中项的位置
			setTitle(String.valueOf(arr.get(arg2)));

		}

	};

}