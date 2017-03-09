package com.bar.gv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.bar.gv.adapter.ListViewAdapter;
import com.bar.gv.moudle.ListItem;
import com.bar.R;

/**
 * Created by maiyu on 2016/7/17.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ListView listView = (ListView)findViewById(R.id.Lv);
        //定义数据list
        List<ListItem> list = new ArrayList<ListItem>();
        //添加数据
        list.add(new ListItem("http://p1.so.qhimg.com/t011e24c3af6772acf6.jpg" ,"C罗" ,"克里斯蒂亚诺·罗纳尔多（Cristiano Ronaldo），1985年2月5日出生于葡萄牙马德拉岛丰沙尔。简称C罗，葡萄牙足球运动员，司职边锋可兼任中锋，现效力于西甲皇家马德里足球俱乐部，并身兼葡萄牙国家队队长。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t019d0526e85eaa9ab6.jpg" ,"梅西","莱昂内尔·安德列斯·梅西（西班牙语：Lionel Andrés Messi），1987年6月24日生于阿根廷圣菲省罗萨里奥，绰号“新马拉多纳”，阿根廷著名足球运动员，司职前锋、边锋和前腰，现效力于西班牙足球甲级联赛巴塞罗那足球俱乐部。"));
        list.add(new ListItem("http://www.188score.com/Upload/2_img2013_10/730_932348_927160.jpg","纳尼","路易斯·卡洛斯·阿尔梅达·达·库尼亚(Luis Carlos Almeida da Cunha)，男，也叫\"纳尼\"，葡萄牙著名足球运动员，现效力于土耳其的费内巴切足球俱乐部，曾效力于曼彻斯特联足球俱乐部、里斯本竞技足球俱乐部。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t011e24c3af6772acf6.jpg" ,"C罗" ,"克里斯蒂亚诺·罗纳尔多（Cristiano Ronaldo），1985年2月5日出生于葡萄牙马德拉岛丰沙尔。简称C罗，葡萄牙足球运动员，司职边锋可兼任中锋，现效力于西甲皇家马德里足球俱乐部，并身兼葡萄牙国家队队长。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t019d0526e85eaa9ab6.jpg" ,"梅西","莱昂内尔·安德列斯·梅西（西班牙语：Lionel Andrés Messi），1987年6月24日生于阿根廷圣菲省罗萨里奥，绰号“新马拉多纳”，阿根廷著名足球运动员，司职前锋、边锋和前腰，现效力于西班牙足球甲级联赛巴塞罗那足球俱乐部。"));
        list.add(new ListItem("http://www.188score.com/Upload/2_img2013_10/730_932348_927160.jpg","纳尼","路易斯·卡洛斯·阿尔梅达·达·库尼亚(Luis Carlos Almeida da Cunha)，男，也叫\"纳尼\"，葡萄牙著名足球运动员，现效力于土耳其的费内巴切足球俱乐部，曾效力于曼彻斯特联足球俱乐部、里斯本竞技足球俱乐部。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t011e24c3af6772acf6.jpg" ,"C罗" ,"克里斯蒂亚诺·罗纳尔多（Cristiano Ronaldo），1985年2月5日出生于葡萄牙马德拉岛丰沙尔。简称C罗，葡萄牙足球运动员，司职边锋可兼任中锋，现效力于西甲皇家马德里足球俱乐部，并身兼葡萄牙国家队队长。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t019d0526e85eaa9ab6.jpg" ,"梅西","莱昂内尔·安德列斯·梅西（西班牙语：Lionel Andrés Messi），1987年6月24日生于阿根廷圣菲省罗萨里奥，绰号“新马拉多纳”，阿根廷著名足球运动员，司职前锋、边锋和前腰，现效力于西班牙足球甲级联赛巴塞罗那足球俱乐部。"));
        list.add(new ListItem("http://www.188score.com/Upload/2_img2013_10/730_932348_927160.jpg","纳尼","路易斯·卡洛斯·阿尔梅达·达·库尼亚(Luis Carlos Almeida da Cunha)，男，也叫\"纳尼\"，葡萄牙著名足球运动员，现效力于土耳其的费内巴切足球俱乐部，曾效力于曼彻斯特联足球俱乐部、里斯本竞技足球俱乐部。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t011e24c3af6772acf6.jpg" ,"C罗" ,"克里斯蒂亚诺·罗纳尔多（Cristiano Ronaldo），1985年2月5日出生于葡萄牙马德拉岛丰沙尔。简称C罗，葡萄牙足球运动员，司职边锋可兼任中锋，现效力于西甲皇家马德里足球俱乐部，并身兼葡萄牙国家队队长。"));
        list.add(new ListItem("http://p1.so.qhimg.com/t019d0526e85eaa9ab6.jpg" ,"梅西","莱昂内尔·安德列斯·梅西（西班牙语：Lionel Andrés Messi），1987年6月24日生于阿根廷圣菲省罗萨里奥，绰号“新马拉多纳”，阿根廷著名足球运动员，司职前锋、边锋和前腰，现效力于西班牙足球甲级联赛巴塞罗那足球俱乐部。"));
        list.add(new ListItem("http://www.188score.com/Upload/2_img2013_10/730_932348_927160.jpg","纳尼","路易斯·卡洛斯·阿尔梅达·达·库尼亚(Luis Carlos Almeida da Cunha)，男，也叫\"纳尼\"，葡萄牙著名足球运动员，现效力于土耳其的费内巴切足球俱乐部，曾效力于曼彻斯特联足球俱乐部、里斯本竞技足球俱乐部。"));

        listView.setAdapter(new ListViewAdapter(this , list , listView));
    }
}
