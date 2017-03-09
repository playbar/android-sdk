package com.android.test;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.android.R;

public class MainActivity extends Activity {
	private List<FileState> list = new ArrayList<FileState>();
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main);
		initFileState();// 先对FileState进行初始化
		initUI();// 对界面进行初始化
	}

	/**
	 * 把数据放进list中，因为是测试所以我手工添加数据
	 **/
	private void initFileState() {
		// 给FileState赋值
		for (int i = 1; i < 3; i++) {
			FileState fileState = new FileState();
			fileState.setFileName(i + ".mp3");// 名字
			fileState.setCompleteSize(100);// 初始化下载程度
			fileState.setState(true);
			list.add(fileState);
		}
		FileState f = new FileState();
		f.setFileName("8.mp3");
		f.setCompleteSize(0);
		f.setState(false);
		list.add(f);
	}

	private void initUI() {
		listView = (ListView) this.findViewById(R.id.listview);
		MyAdapter adapter = new MyAdapter(list, this);
		listView.setAdapter(adapter);
	}
}
