package com.vk;

import android.app.NativeActivity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.SurfaceHolder;

import com.vk.log.Loggvc;


public class BarNativeActivity extends NativeActivity {

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (shouldAskPermissions()) {
            askPermissions();
        }
        Loggvc.printTime();
    }

    public void onDestroy()
    {
        super.onDestroy();
        Loggvc.printTime();
    }

    public void onPause()
    {
        super.onPause();
        Loggvc.printTime();
    }

    public void onResume()
    {
        super.onResume();
        Loggvc.printTime();
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Loggvc.printTime();
    }

    public void onStart(){
        super.onStart();
        Loggvc.printTime();
    }

    public void onStop(){
        super.onStop();
        Loggvc.printTime();
    }

    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        Loggvc.printTime();
    }

    public void onLowMemory(){
        super.onLowMemory();
        Loggvc.printTime();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Loggvc.printTime();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        Loggvc.printTime();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
        Loggvc.printTime();
    }

    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        super.surfaceRedrawNeeded(holder);
        Loggvc.printTime();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        Loggvc.printTime();
    }

    public void onInputQueueCreated(InputQueue queue) {
        super.onInputQueueCreated(queue);
        Loggvc.printTime();
    }

    public void onInputQueueDestroyed(InputQueue queue) {
        super.onInputQueueDestroyed(queue);
        Loggvc.printTime();
    }

    public void onGlobalLayout() {
        super.onGlobalLayout();
        Loggvc.printTime();
    }

}

