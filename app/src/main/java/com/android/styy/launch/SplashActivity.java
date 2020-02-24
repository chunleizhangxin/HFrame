package com.android.styy.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.android.styy.R;
import com.android.styy.base.BaseAppCompatActivity;
import com.android.styy.common.config.AppIntent;
import com.android.styy.common.util.ImmersiveUtil;

public class SplashActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveUtil.initImmersive(this);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.img_splash_bg);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setContentView(imageView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        if (checkLogin()) {
//            toMainPage();
//        } else {
//            finish();
//        }
        Intent loginIntent = AppIntent.getLoginActivity(getBaseContext());
        startActivity(loginIntent);
        finish();
    }

    private void toMainPage() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected String toolBarTitle() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}