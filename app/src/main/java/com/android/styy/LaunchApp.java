package com.android.styy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.helper.DBTokenHelper;
import com.android.styy.common.manager.DBManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class LaunchApp extends Application {

    String TAG = getClass().getSimpleName();

    public static LaunchApp Instance;
    public static DBManager mDBManager;
    public TokenEntity tokenEntity;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        initLogger();
        initDatabase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    // 本地数据库初始化
    private void initDatabase() {
        mDBManager = DBManager.newBuilder(this)
                .build();

        tokenEntity = DBTokenHelper.onLoadToken();
    }

    // log框架初始化
    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
