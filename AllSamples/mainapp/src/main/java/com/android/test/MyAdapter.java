package com.android.test;

import java.util.List;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bar.R;

public class MyAdapter extends BaseAdapter {
	private List<FileState> list;
	private Context context;
	private LayoutInflater inflater = null;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String name = (String) msg.obj;
				int length = msg.arg1;
				for (int i = 0; i < list.size(); i++) {
					FileState fileState = list.get(i);
					if (fileState.getFileName().equals(name)) {
						fileState.setCompleteSize(length);
						list.set(i, fileState);
						break;
					}
				}
				notifyDataSetChanged();
			}
		}
	};

	public MyAdapter(List<FileState> list, Context context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
	}

	class ViewHolder {
		public TextView fileName;// 文件名称
		public ProgressBar progressBar;// 进度条
		public TextView percent;// 百分比
		public ImageView down;// 下载
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.test_main_item, null);
			holder = new ViewHolder();
			holder.fileName = (TextView) convertView.findViewById(R.id.fileName);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.down_progressBar);
			holder.percent = (TextView) convertView.findViewById(R.id.percent_text);
			holder.down = (ImageView) convertView.findViewById(R.id.down_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FileState fileState = list.get(position);
		final String name = fileState.getFileName();
		System.out.println(name + "---run getView");
		// 如果文件状态为已经下载
		if (fileState.isState() == true) {
			holder.fileName.setText(fileState.getFileName());
			// 下载完成的文件，进度条被隐藏
			holder.progressBar.setVisibility(ProgressBar.INVISIBLE);
			// 设置为已下载
			holder.percent.setText("已下载");
			// 下载完成的文件，下载按钮被隐藏，防止重复下载
			holder.down.setVisibility(ImageView.INVISIBLE);
		} else {
			holder.fileName.setText(fileState.getFileName());
			holder.progressBar.setVisibility(ProgressBar.VISIBLE);
			holder.progressBar.setProgress(fileState.getCompleteSize());
			holder.percent.setText(fileState.getCompleteSize() + "%");
			holder.down.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Downloader down = new Downloader(name, mHandler);
					down.download();
				}
			});
			if (fileState.getCompleteSize() == 100) {
				holder.progressBar.setVisibility(ProgressBar.INVISIBLE);
				holder.percent.setText("已下载");
				holder.down.setVisibility(ProgressBar.INVISIBLE);
				fileState.setState(true);
				list.set(position, fileState);
			}
		}
		return convertView;
	}

}

