package com.android.gv.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.android.gv.domain.AsyncImageLoader2;
import com.android.gv.domain.ViewCache2;
import com.android.gv.moudle.ListItem;
import com.bar.R;

/**
 * Created by maiyu on 2016/7/17.
 */
public class ListViewAdapter extends ArrayAdapter<ListItem> {
    private ListView listView ;
    //定义异步图片加载对象
    private AsyncImageLoader2 asyncImageLoader2 ;

    public ListViewAdapter(Context context , List<ListItem> imageAndTexts , ListView listView) {
        super(context, 0 , imageAndTexts);
        //初始化数据
        this.listView = listView ;
        asyncImageLoader2 = new AsyncImageLoader2();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity)getContext() ;

        //读取GridView的item布局，读取griditem.xml布局
        View rowView = convertView ;
        ViewCache2 viewCache2 ;
        if(rowView == null){
            //读取griditem.xml为item的布局
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView  = inflater.inflate(R.layout.listviewitem_second , null);
            //通过当前的布局视图，初始化缓存视图
            viewCache2 = new ViewCache2(rowView);
            //设置当前视图的标签
            rowView.setTag(viewCache2);
        }else{
            viewCache2 = (ViewCache2)rowView.getTag();
        }
        //得到需要显示的对象
        ListItem imageAndText = getItem(position);

        //读取对象的相应内容值
        String imageUrl = imageAndText.getImageUrl();
        ImageView imageView = viewCache2.getImageView() ;
        imageView.setTag(imageUrl);

        //读取缓存图片
        Drawable cachedImage = asyncImageLoader2.loadDrawable(imageUrl ,
              new   AsyncImageLoader2.ImageCallback(){

                  @Override
                  public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                      //得到对应的ImageView对象
                      ImageView imageViewByTag = (ImageView)listView.findViewWithTag(imageUrl);
                      //设置图片控件显示的图像
                      if(imageViewByTag != null){
                          imageViewByTag.setImageDrawable(imageDrawable);
                      }
                  }
              });

        //如果缓存图片为空，显示默认图片
        if(cachedImage == null){
            imageView.setImageResource(R.drawable.ic_launcher);
        }else {
            imageView.setImageDrawable(cachedImage);
        }

        //显示姓名和简介
        TextView textViewname = viewCache2.getTextViewname();
        textViewname.setText(imageAndText.getName());
        TextView textViewinfo = viewCache2.getTextViewinfo();
        textViewinfo.setText(imageAndText.getInfo());
        return rowView;
    }
}
