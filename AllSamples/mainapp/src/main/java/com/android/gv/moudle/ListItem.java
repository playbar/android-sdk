package com.android.gv.moudle;

/**定义为ListView的一个item对象的实体类
 * Created by maiyu on 2016/7/17.
 */
public class ListItem {
    private String imageUrl , name , info ;     //图片，名字，信息

    //构造方法
    public ListItem(String imageUrl , String name , String info){
        this.imageUrl   =   imageUrl ;
        this.name       =   name  ;
        this.info       =   info ;
    }

    //3个get方法
    public String getImageUrl(){
        return imageUrl;
    }
    public String getName(){
        return name ;
    }
    public String getInfo(){
        return info ;
    }
}
