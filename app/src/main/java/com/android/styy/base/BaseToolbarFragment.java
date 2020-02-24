package com.android.styy.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import com.android.styy.R;

public abstract class BaseToolbarFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.tv_title)
    protected TextView mTvTitle;
    @BindView(R.id.app_bar_layout)
    protected AppBarLayout mAppBarLayout;



    @Override
    protected void initToolbar(View ChildView,Bundle savedInstanceState) {
        this.initToolbarHelper();
    }
    protected void initToolbarHelper() {

        if(!(mContext instanceof AppCompatActivity)){
                return;
        }

        if (this.mToolbar == null || this.mAppBarLayout == null)
            return;

        mToolbar.setTitle("");
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mContext;
        mAppCompatActivity.setSupportActionBar(this.mToolbar);
        setTitle(toolBarTitle());
        showBack();
    }
    public void setElevation(float elevation){
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(elevation);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    protected void showBack() {
        if (this.mAppBarLayout != null) {
            AppCompatActivity mAppCompatActivity = (AppCompatActivity) mContext;
            mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    protected void hideBack() {
        if (this.mAppBarLayout != null) {
            AppCompatActivity mAppCompatActivity = (AppCompatActivity) mContext;
            mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    protected void setTitle(String title){
        if(null != mTvTitle){
            mTvTitle.setText(title);
        }
    }

    public void onShowDataErrorView(Throwable throwable){
        BaseToolbarActivity activity = (BaseToolbarActivity) getActivity();
        activity.onShowDataErrorView(throwable);
    }
}
