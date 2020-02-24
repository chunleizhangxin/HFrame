package com.android.styy.common.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "token")
public class TokenEntity {

    @Id(autoincrement = true)
    private Long id;

    private String access_token;

    private String refresh_token;

    private long updateTime;

    private int tokenType;

    @Generated(hash = 1202529205)
    public TokenEntity(Long id, String access_token, String refresh_token,
            long updateTime, int tokenType) {
        this.id = id;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.updateTime = updateTime;
        this.tokenType = tokenType;
    }

    @Generated(hash = 697107377)
    public TokenEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }
}
