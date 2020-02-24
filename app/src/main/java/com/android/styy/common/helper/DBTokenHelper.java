package com.android.styy.common.helper;

import com.android.styy.LaunchApp;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.dao.TokenEntityDao;

public class DBTokenHelper {

    public static TokenEntity onLoadToken(){
        if(isEmpty()){
            return null;
        }
        return LaunchApp.mDBManager
                .getDaoSession()
                .getTokenEntityDao()
                .loadAll()
                .get(0);
    }

    public static long onInsertToken(TokenEntity entity){
        TokenEntityDao tokenEntityDao = LaunchApp.mDBManager
                .getDaoSession()
                .getTokenEntityDao();
        tokenEntityDao.deleteAll();
        return tokenEntityDao.insert(entity);
    }

    public static void deleteAll(){
        TokenEntityDao tokenEntityDao = LaunchApp.mDBManager
                .getDaoSession()
                .getTokenEntityDao();
        tokenEntityDao.deleteAll();
    }

    /**
     * true 未登录
     *
     * false 登录
     * @return
     */
    public static boolean isLogin(){
        return !LaunchApp.mDBManager
                .getDaoSession()
                .getTokenEntityDao()
                .queryBuilder()
                .where(TokenEntityDao.Properties.TokenType.eq(AppConfig.TokenType.NORMAL.getState()))
                .list()
                .isEmpty();
    }
    public static boolean isEmpty(){
        return LaunchApp.mDBManager
                .getDaoSession()
                .getTokenEntityDao()
                .queryBuilder()
                .count() == 0;
    }
}
