package com.ihc.cefet.cidadealerta;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;

import butterknife.Bind;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolBar;
    public @Bind(android.R.id.content)
    View rootView;

    public boolean isActivityRunning = false;
    public static boolean isShowingDialog = false;


    @Override
    public void onStart() {
        super.onStart();
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityRunning = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpToolBar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null)
            setSupportActionBar(mToolBar);

    }

    public View getRootView(){
        return rootView;
    }

    protected void configActionBar(int title, boolean showBackButton){
        setTitle(getString(title));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        }
    }

    protected void configActionBar(String title, boolean showBackButton){
        setTitle(title);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        }
    }

    protected void setToolBarTitle(int title){
        setTitle(title);
    }

    protected void hideOption(int id, Menu menu) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(false);
        }
    }

    protected void showOption(int id, Menu menu) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(true);
        }
    }

    protected boolean isNetworkAvailable(){
        return AndroidUtils.isNetworkAvailable(this);
    }

    public void openActivity(Class<?> openActivity){
        Intent intent = new Intent();
        intent.setClass(this, openActivity);
        startActivity(intent);
    }

    public void openActivityForResult(Class<?> openActivity, int code){
        Intent intent = new Intent();
        intent.setClass(this, openActivity);
        startActivityForResult(intent, code);
    }

    public void openActivityWithParam(Class<?> openActivity, String paramName, String param){
        Intent intent = new Intent(this, openActivity);
        intent.putExtra(paramName, param);
        startActivity(intent);
    }

    public void openActivityWithParams(Class<?> openActivity, String paramName, String[] params){
        Intent intent = new Intent(this, openActivity);
        intent.putExtra(paramName, params);
        startActivity(intent);
    }

    public void openActivityWithSerializable(Class<?> openActivity, String paramName, Serializable object){
        Intent intent = new Intent(this, openActivity);
        intent.putExtra(paramName, object);
        startActivity(intent);
    }

    public void openActivityWithSerializables(Class<?> openActivity, String firstParamName, Serializable firstObjetc,
                                              String secondParamName, Serializable secondObject){
        Intent intent = new Intent(this, openActivity);
        intent.putExtra(firstParamName, firstObjetc);
        intent.putExtra(secondParamName, secondObject);
        startActivity(intent);
    }

    protected void closeAllActivitiesAndOpenNew(Class<?> openActivity){
        Intent intent = new Intent(this, openActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void closeAllActivitiesAndOpenNewWithSerializable(Class<?> openActivity, String paramName, Serializable object){
        Intent intent = new Intent(this, openActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(paramName, object);
        startActivity(intent);
    }

    protected void closeAllActivitiesAndOpenNewWithParam(Class<?> openActivity, String paramName, String param){
        Intent intent = new Intent(this, openActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(paramName, param);
        startActivity(intent);
    }

    protected void alertDialog(String title, String message) {
        AndroidUtils.alertDialog(this, title, message);
    }

    protected void alertDialog(int title, int message){
        AndroidUtils.alertDialog(this, title, message);
    }

    protected void finishActivity(Activity activity){
        activity.finish();
    }

    protected int getColorFromResource(int color){
        return ContextCompat.getColor(this, color);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return null;
    }
}
