package com.android.gv.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.android.gv.domain.AsyncImageLoader;
import com.android.gv.domain.ViewCache;
import com.android.gv.moudle.GridItem;
import com.bar.R;

/**自定义Adapter,继承自ArrayAdapter
 * Created by maiyu on 2016/7/17.
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {

    //定义GridView对象
    private GridView gridView ;
    //定义异步图片加载对象
    private AsyncImageLoader asyncImageLoader ;

    public GridViewAdapter(Context context, List<GridItem> imageAndTexts , GridView gridView) {
        super(context , 0 , imageAndTexts);
        //初始化数据
        this.gridView   =   gridView ;
        asyncImageLoader    =   new AsyncImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity    activity = (Activity)getContext();
        //定义GridView的item布局，读取griditem.xml布局
        View  rowView = convertView ;
        ViewCache viewCache ;
        if(rowView == null){
            //读取griditem.xml为item的布局
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView  = inflater.inflate(R.layout.griditem_first , null);
            //通过当前的布局视图，初始化缓存视图
            viewCache = new ViewCache(rowView);
            //设置当前视图的标签
            rowView.setTag(viewCache);
        }else {
            viewCache   =   (ViewCache)rowView.getTag();
        }
        //得到需要显示的对象
        GridItem    imageAndeText = getItem(position);

        //读取对象的相应内容值
        String  imageUrl = imageAndeText.getImageUrl();
        ImageView imageView = viewCache.getImageView();
        imageView.setTag(imageUrl);

        //读取缓存图片
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl ,
                new AsyncImageLoader.ImageCallback(){
                    @Override
                    public void imageLoaded(Drawable imageDrawable, String imageUrl){
                        ImageView imageViewByTag = (ImageView)gridView.findViewWithTag(imageUrl);
                        if(imageViewByTag != null){
                            imageViewByTag.setImageDrawable(imageDrawable);
                        }
            }
        });
        //如果缓存图片为空，则显示默认图片
        if(cachedImage == null){
            imageView.setImageResource(R.drawable.ic_launcher);
        }else {
            imageView.setImageDrawable(cachedImage);
        }
        //显示文字
        TextView textView = viewCache.getTextView();
        textView.setText(imageAndeText.getText());
        return rowView ;
    }
}
