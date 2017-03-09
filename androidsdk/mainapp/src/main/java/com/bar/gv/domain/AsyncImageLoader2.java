package com.bar.gv.domain;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

/**定义异步加载图片类
 * Created by maiyu on 2016/7/17.
 */
public class AsyncImageLoader2 {
    //定义异步加载图片的缓存哈希图
    private HashMap<String ,SoftReference<Drawable>> imageCache ;
    public AsyncImageLoader2(){
        imageCache = new HashMap<String ,SoftReference<Drawable>>();
    }

    //获取图片的方法
    public Drawable loadDrawable(final  String imageUrl , final  ImageCallback imageCallback){

        //判断图片缓存中是否已经有此图片，有则返回
        if(imageCache.containsKey(imageUrl)){
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable  drawable = softReference.get();
            if(drawable != null){
                return drawable ;
            }
        }

        //定义handler对象来发送message
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                imageCallback.imageLoaded((Drawable)msg.obj , imageUrl);
                //super.handleMessage(msg);
            }
        };

        //定义一个新的线程用来读取网络上的图片地址
        new Thread(){
            @Override
            public void run() {
                //读取图片地址imageUrl
                Drawable drawable = loadImageFromUrl(imageUrl);
                //将图片读取到的地址添加到缓存列表中
                imageCache.put(imageUrl , new SoftReference<Drawable>(drawable));
                //定义message对象，设置内容为加载到的图片对象
                Message message = handler.obtainMessage(0 , drawable);
                //发送图片
                handler.sendMessage(message);
              //  super.run();
            }
        }.start();
        return  null ;
    }

    //从图片地址读取图片
    public  static  Drawable loadImageFromUrl(String url){
        URL m ;
        InputStream i = null ;      //定义url对象，及inputstream对象
        try{
            //通过图片的url得到图片的inputStream对象
            m = new URL(url);
            i = (InputStream)m.getContent();
        }catch(Exception e){
            e.printStackTrace();
        }
        //通过inputStream对象得到图片的Drawable对象
        Drawable d  =   Drawable.createFromStream(i , "src");
        return d ;
    }

    //图片获取到后的接口
    public interface ImageCallback{
        public void imageLoaded(Drawable imageDrawable, String imageUrl);
    }
}
