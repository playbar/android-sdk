package com.bar.gv.domain;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.R;


/**定义视图的占位符类
 * Created by maiyu on 2016/7/17.
 */
public class ViewCache {
    private View baseView ;         //定义基本的视图对象
    private TextView textView ;     //定义显示的视图对象
    private ImageView imageView ;   //定义显示的图片对象

    //ViewCache的构造方法
    public ViewCache(View baseView){
        this.baseView   =   baseView ;
    }

    //得到文字标签对象
    public TextView getTextView(){
        if(textView ==  null){
            //得到TextView对象
            textView    =   (TextView)baseView.findViewById(R.id.ItemTv);
        }
        return textView ;
    }

    //得到图片控件对象
    public ImageView    getImageView(){
        if(imageView    ==  null){
            //得到ImageView对象
            imageView   =   (ImageView)baseView.findViewById(R.id.ItemIv);
        }
        return imageView ;
    }
}
