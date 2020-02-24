package com.android.styy.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import com.android.styy.R;

public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.tv_title)
    protected TextView mTvTitle;
    @BindView(R.id.app_bar_layout)
    protected AppBarLayout mAppBarLayout;

    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }

    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        if (this.mToolbar == null || this.mAppBarLayout == null)
            return;
        mToolbar.setTitle("");
        this.setSupportActionBar(this.mToolbar);
        setTitle(toolBarTitle());
        showBack();
    }
    protected void setElevation(float elevation){
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(elevation);
        }
    }

    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    protected void showBack() {
        if (this.mAppBarLayout != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected void hideBack() {
        if (this.mAppBarLayout != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    protected void setTitle(String title){
        mTvTitle.setText(title);
    }
}
