package com.bar.test;

import java.util.Map;
import android.os.Handler;
import android.os.Message;

public class Downloader {
	private String fileName;
	private Handler mHandler;

	public Downloader(String fileName, Handler handler) {
		super();
		this.fileName = fileName;
		mHandler = handler;
	}

	public void download() {
		new MyThread().start();
	}

	class MyThread extends Thread {
		public void run()
		{
			for(int i=0;i<=2;i++)
			{
				System.out.println("i:"+i);
				try {
					this.currentThread().sleep(1000);
				} catch (InterruptedException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
				}
			Message msg=Message.obtain();
			msg.what=1;
			msg.obj=fileName;
			msg.arg1=i;
			mHandler.sendMessage(msg);
			}
		}
	}

}
