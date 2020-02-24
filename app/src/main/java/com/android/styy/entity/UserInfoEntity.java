package com.android.styy.entity;

import com.android.styy.common.dao.UserEntity;

public class UserInfoEntity {
    private String id;
    private String userType;
    private String nickName;
    private String status;
    private String username;
    private UserEntity tbGovernmentInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserEntity getTbGovernmentInfo() {
        return tbGovernmentInfo;
    }

    public void setTbGovernmentInfo(UserEntity tbGovernmentInfo) {
        this.tbGovernmentInfo = tbGovernmentInfo;
    }
}
