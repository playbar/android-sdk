package com.bar.gv.domain;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.R;


/**定义视图的占位符类
 * Created by maiyu on 2016/7/17.
 */
public class ViewCache2 {
    private View baseView ;   //定义基本视图对象
    private TextView textViewname , textViewinfo ;  //定义要显示的标题，图片，信息
    private ImageView imageView ;

    //初始化基本视图
    public ViewCache2(View baseView){
        this.baseView = baseView ;
    }
    //3个get方法，分别得到图片，标题，信息
    public TextView getTextViewname(){
        if(textViewname == null){
            textViewname    =   (TextView)baseView.findViewById(R.id.ItemTvName);
        }
        return  textViewname ;
    }
    public TextView getTextViewinfo(){
        if(textViewinfo == null){
            textViewinfo    =   (TextView)baseView.findViewById(R.id.ItemTvInfo);
        }
        return  textViewinfo;
    }
    public ImageView getImageView(){
        if(imageView == null){
            imageView   =   (ImageView)baseView.findViewById(R.id.ItemIv);
        }
        return imageView ;
    }
}
