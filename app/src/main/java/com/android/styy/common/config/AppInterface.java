package com.android.styy.common.config;

public class AppInterface {

    //外网服务器
    public static String BASE_URL_USER = "http://139.159.138.181:8082/admin/";
    public static String BASE_URL_GOVERNMENT = "http://139.159.138.181:8082/admin/";


    /**
     * H5相关页面
     */
    // H5基础URL
    public static String HTML_BASE_URL = "http://xiaoq.online/apph5/index.html#/";
    // 首页
    public static String HTML_HOME_PAGE = "http://xiaoq.online/apph5/index.html#/goverIndex";

    /**
     * 登录
     */
    public static final String USER_LOGIN_BY_PASSWD = "authentication/mobile";

    /**
     * 账号注册登录，密码获取加盐
     */
    public static final String GET_PWD_SALT = "usercenters/getPwdSalt";

    /**
     * 获取用户信息
     */
    public static final String GET_USERINFO_DATA = "usercenters/users/{id}";
}