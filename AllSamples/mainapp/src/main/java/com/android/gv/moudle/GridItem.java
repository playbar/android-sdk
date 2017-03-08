package com.android.gv.moudle;

/**
 * 定义GridView的一个item对象的实体类
 * Created by maiyu on 2016/7/17.
 */
public class GridItem {
    private String  imageUrl ;      //item图片地址
    private String  text ;          //item的文字标签

    //构造函数
    public  GridItem(String imageUrl , String text){
        this.imageUrl   =   imageUrl ;
        this.text       =   text     ;
    }
    //得到图片地址
    public String   getImageUrl(){
        return imageUrl ;
    }
    //得到文字标签值
    public String   getText(){
        return text ;
    }
}
