package com.android.styy.entity;

public class UserTermDeviceEntity {

    private String appKey;
    private String appSecret;
    private String cid;
    private String cipherText;
    private String currentAppversion;
    private String id;
    private String mobileImei;
    private String mobileMode;
    private String osType;
    private String osVersion;
    private String regId;
    private int userType;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getCurrentAppversion() {
        return currentAppversion;
    }

    public void setCurrentAppversion(String currentAppversion) {
        this.currentAppversion = currentAppversion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileImei() {
        return mobileImei;
    }

    public void setMobileImei(String mobileImei) {
        this.mobileImei = mobileImei;
    }

    public String getMobileMode() {
        return mobileMode;
    }

    public void setMobileMode(String mobileMode) {
        this.mobileMode = mobileMode;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}