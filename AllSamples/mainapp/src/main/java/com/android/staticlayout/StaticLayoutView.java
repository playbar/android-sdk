package com.android.staticlayout;

/**
 * Created by mac on 16/9/1.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;


public class StaticLayoutView extends View {

    TextPaint textPaint = null;
    StaticLayout staticLayout = null;
    Paint paint = null;
    int width = 250;
    int height = 0;
    String txt = null;
    boolean running = false;

    public StaticLayoutView(Context context) {
        super(context);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        txt = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、" +
                "钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
        staticLayout = new StaticLayout(txt, textPaint, width, Alignment.ALIGN_NORMAL, 1, 0, false);
        height = staticLayout.getHeight();
        paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                running = !running;
                if(running){
                    new Thread(){
                        public void run() {
                            while(running){
                                width += 50;
                                staticLayout = new StaticLayout(txt, textPaint, width, Alignment.ALIGN_NORMAL, 1, 0, false);
                                height = staticLayout.getHeight();
                                postInvalidate();
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                if(width >= 1000){
                                    width = 250;
                                }
                            }
                        };
                    }.start();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.translate(20, 20);
        staticLayout.draw(canvas);
        canvas.drawRect(0, 0, width, height, paint);
        super.onDraw(canvas);
    }

}
