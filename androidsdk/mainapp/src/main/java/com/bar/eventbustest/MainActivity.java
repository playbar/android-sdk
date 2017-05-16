package com.bar.eventbustest;

import com.bar.R;
import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btn;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_eventbus);

		EventBus.getDefault().register(this);

		btn = (Button) findViewById(R.id.btn_try);
		tv = (TextView)findViewById(R.id.tv);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
				startActivity(intent);
			}
		});
	}

	public void onEventMainThread(FirstEvent event) {
		// 不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行，接收事件就会在UI线程中运行，同 @Subscribe(threadMode = ThreadMode.MAIN）
		String msg = "onEventMainThread recive msg " + event.getMsg();
		Log.d("harvic", msg);
		tv.setText(msg);
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void onEvent(FirstEvent event) {
		//事件在哪个线程发布出来的，onEvent就会在这个线程中运行， 同 @Subscribe(threadMode = ThreadMode.POSTING）
	}

	public void onEventBackgroundThread(FirstEvent event) {
		//那么如果事件是在UI线程中发布出来的，那么onEventBackground就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行,同 @Subscribe(threadMode = ThreadMode.BACKGROUND）
	}


	public void onEventAsync(FirstEvent event) {
		//使用这个函数作为订阅函数，那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync，同 @Subscribe(threadMode = ThreadMode.ASYNC）
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
