package com.android.styy.common.util;

import android.app.Activity;

import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.UserEntity;
import com.android.styy.common.helper.DBUserHelper;
import com.android.styy.common.manager.ActivitiesManager;
import com.android.styy.entity.UserTermDeviceEntity;


public class LoginHelper {

    static String TAG = "LoginHelper";

    public static UserTermDeviceEntity getUserTermDeviceEntity(){
        UserTermDeviceEntity termDeviceEntity = new UserTermDeviceEntity();
        termDeviceEntity.setAppKey(AppConfig.APPKEY);
        termDeviceEntity.setAppSecret(AppConfig.APPSECRET);
        termDeviceEntity.setCid(AppConfig.CID);
        termDeviceEntity.setCurrentAppversion(DeviceUtils.getVersionName());
        termDeviceEntity.setMobileImei(DeviceUtils.getDeviceId());
        termDeviceEntity.setMobileMode(DeviceUtils.getPhoneModel());
        termDeviceEntity.setOsType(AppConfig.OSTYPE);
        termDeviceEntity.setOsVersion(DeviceUtils.getBuildVersion());
        termDeviceEntity.setCipherText(getCipherText(termDeviceEntity));

        return termDeviceEntity;
    }

    private static String getCipherText(UserTermDeviceEntity bean){
        return MD5Util.toMD5(  bean.getMobileImei() +
                bean.getMobileMode() +
                bean.getOsType() +
                bean.getOsVersion() +
                bean.getCurrentAppversion() +
                bean.getCid() +
                bean.getAppKey() +
                bean.getAppSecret()
        );
    }

    public static void onLoginSuccess(UserEntity userEntity, Activity activity){
        userEntity.setLogout(false);
        DBUserHelper.onInsertUser(userEntity);
        ActivitiesManager.clear();
    }
}
