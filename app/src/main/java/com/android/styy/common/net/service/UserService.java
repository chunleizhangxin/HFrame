package com.android.styy.common.net.service;

import com.android.styy.common.config.AppInterface;
import com.android.styy.common.net.impl.NetModel;
import com.android.styy.entity.UserInfoEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface UserService {

    /**
     * 账号注册登录，密码获取加盐
     */
    @GET(AppInterface.GET_PWD_SALT)
    Observable<NetModel<String>> get_pwd_salt(@Query("account") String account);

    /***
     * 登陆
     * @return
     */
    @POST(AppInterface.USER_LOGIN_BY_PASSWD)
    Observable<NetModel<UserInfoEntity>> post_login_by_password(@QueryMap Map<String, String> map);
}