package com.android.styy.common.config;

public class AppConfig {

    //缓存文件名字
    public static final String CACHENAME = "CACHE.DAT";

    //网络请求 access_token KEY
    public static final String ACCESS_TOKEN_KEY = "access_token";
    //网络请求 rp_refresh_token KEY
    public static final String REFRESH_TOKEN_KEY = "refresh_token";

    public static final String NET_ERROR = "网络错误，请重试";

    public static final int NET_SUCCESS_CODE = 200;

    // 网络错误码
    public static final int NET_ERROR_CODE = -999;

    // 没有网络
    public static final int NET_NO_CODE = -998;

    public static final int PAGE_COUNT = 10;

    //AppKey
    public static final String APPKEY = "c1ebe466-1cdc-4bd3-ab69-77c3561b9dee";
    //appSecret
    public static final String APPSECRET = "lifestyle365-secret-20160411-20160510";
    //cid
    public static final String CID = "123456";
    //OsType
    public static final String OSTYPE = "Android";

    // 即时通讯 key
    public static String YWIM_KEY = "24850619";

    // 真实姓名缓存KEY
    public static String KEY_CACHE_USERREALNAME = "KEY_CACHE_USERREALNAME";

    public enum TokenType{
        // 正常
        NORMAL(1),
        // 匿名
        ANONYMITY(2);
        int state;
        TokenType(int state){
            this.state = state;
        }

        public int getState(){
            return state;
        }
    }
}
