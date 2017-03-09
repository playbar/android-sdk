package com.bar.gv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import com.bar.gv.adapter.GridViewAdapter;
import com.bar.gv.moudle.GridItem;
import com.bar.R;

/**
 * Created by maiyu on 2016/7/17.
 */
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //得到GridView控件
        GridView  gridView  = (GridView)findViewById(R.id.Gv);
        //定义数据源list
        List<GridItem> list = new ArrayList<GridItem>();
        //添加数据
        list.add(new GridItem("http://p1.so.qhimg.com/t011e24c3af6772acf6.jpg" ,"C罗"));
        list.add(new GridItem("http://p1.so.qhimg.com/t019d0526e85eaa9ab6.jpg" , "梅西"));
        list.add(new GridItem("http://news.shangdu.com/category/10006/2007/12/24/images/10006_20071224_67.jpg" ,"罗纳尔多"));
        list.add(new GridItem("http://www.caiez.com/UploadFiles/2015-12/2/20151204220622810.jpg","纳尼"));
        list.add(new GridItem("http://www.188score.com/Upload/2_img2013_10/730_932348_927160.jpg" , "纳尼"));
        list.add(new GridItem("http://img1.gtimg.com/sports/pics/hv1/52/81/1479/96192682.jpg" , "梅西"));
        gridView.setAdapter(new GridViewAdapter(this , list , gridView));
    }
}
