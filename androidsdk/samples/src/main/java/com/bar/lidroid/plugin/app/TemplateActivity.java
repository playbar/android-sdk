package com.bar.lidroid.plugin.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.bar.lidroid.plugin.Plugin;

import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 14-4-17
 * Time: PM 2:21
 * <p/>
 * the container of all modules
 */
public class TemplateActivity extends Activity {

    private ModuleActivity moduleView;
    private Context mModuleContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<? extends ModuleActivity> moduleClazz = Plugin.CurrModuleClazz;
        mModuleContext = Plugin.CurrModuleContext;

        try {
            Constructor<? extends ModuleActivity> constructor = moduleClazz.getConstructor(Context.class);
            moduleView = constructor.newInstance(mModuleContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        moduleView.setTemplateActivity(this);

        //创建自定义view
        moduleView.onCreate(savedInstanceState);

        //加入自定义view
        this.setContentView(moduleView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        moduleView.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        moduleView.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        moduleView.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        moduleView.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        moduleView.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        moduleView.onPostResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        moduleView.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        moduleView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        moduleView.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        moduleView.onUserLeaveHint();
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        return moduleView.onCreateThumbnail(outBitmap, canvas);
    }

    @Override
    public CharSequence onCreateDescription() {
        return moduleView.onCreateDescription();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moduleView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moduleView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        moduleView.onLowMemory();
    }

    @Override
    public Intent getIntent() {
        return moduleView.getIntent();
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        moduleView.setIntent(newIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!moduleView.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (!moduleView.onKeyLongPress(keyCode, event)) {
            return super.onKeyLongPress(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!moduleView.onKeyUp(keyCode, event)) {
            return super.onKeyUp(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        if (!moduleView.onKeyMultiple(keyCode, repeatCount, event)) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moduleView.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!moduleView.onTouchEvent(event)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        if (!moduleView.onTrackballEvent(event)) {
            return super.onTrackballEvent(event);
        }
        return true;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        moduleView.onUserInteraction();
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        moduleView.onWindowAttributesChanged(params);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        moduleView.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        moduleView.onWindowFocusChanged(hasFocus);
    }

    @Override
    public View onCreatePanelView(int featureId) {
        return moduleView.onCreatePanelView(featureId);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return moduleView.onCreateDialog(id);
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        return moduleView.onCreateDialog(id, args);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        moduleView.onPrepareDialog(id, dialog);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
        moduleView.onPrepareDialog(id, dialog, args);
    }

    @Override
    public boolean onSearchRequested() {
        return moduleView.onSearchRequested();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        moduleView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        moduleView.onTitleChanged(title, color);
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        moduleView.onChildTitleChanged(childActivity, title);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return moduleView.onCreateView(name, context, attrs);
    }
}
