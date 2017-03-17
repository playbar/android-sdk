package com.bar.lidroid.plugin.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 14-4-17
 * Time: PM 2:21
 * <p/>
 * the container of all modules
 */
public class ModuleActivity extends LinearLayout {

    protected TemplateActivity templateActivity;

    private Context mContext;
    private Intent intent;
    private LayoutInflater mInflater;

    public ModuleActivity(Context context) {
        super(context);

        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void setContentView(int layoutResID) {
        mInflater.inflate(layoutResID, this);
    }

    public void setTemplateActivity(TemplateActivity templateActivity) {
        this.templateActivity = templateActivity;
    }

    public final void finish() {
        this.templateActivity.finish();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public void onPostCreate(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onRestart() {
    }

    public void onResume() {
    }

    public CharSequence onCreateDescription() {
        return null;
    }

    public void onPostResume() {
    }

    public void onNewIntent(Intent intent) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onPause() {
    }

    public void onUserLeaveHint() {
    }

    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        return false;
    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onLowMemory() {
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void onBackPressed() {
    }

    public void onUserInteraction() {
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
    }

    public void onContentChanged() {
    }

    public View onCreatePanelView(int featureId) {
        return null;
    }

    public Dialog onCreateDialog(int id) {
        return null;
    }

    public void onPrepareDialog(int id, Dialog dialog) {
    }

    public boolean onSearchRequested() {
        return false;
    }

    public void onPrepareDialog(int id, Dialog dialog, Bundle args) {
    }

    public Dialog onCreateDialog(int id, Bundle args) {
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onTitleChanged(CharSequence title, int color) {
    }

    public void onChildTitleChanged(Activity childActivity, CharSequence title) {
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }
}
