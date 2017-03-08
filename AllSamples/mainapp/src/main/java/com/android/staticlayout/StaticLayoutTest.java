package com.android.staticlayout;

/**
 * Created by mac on 16/9/1.
 */


import android.support.v4.app.Fragment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.app.Activity;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class StaticLayoutTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new StaticLayoutView(this));
    }
    public class MyView extends View {

        Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
        public MyView(Context context) {
            super(context);
        }

        public MyView(Context context, AttributeSet attrs){
            super(context, attrs);
        }

        public void onDraw(Canvas canvas){
            super.onDraw(canvas);

            TextPaint tp = new TextPaint();
            tp.setColor(Color.BLUE);
            tp.setStyle(Style.FILL);
            tp.setTextSize(50);
//            String message = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
            String message = "123456";
            StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            myStaticLayout.draw(canvas);
            canvas.restore();
        }
    }
}