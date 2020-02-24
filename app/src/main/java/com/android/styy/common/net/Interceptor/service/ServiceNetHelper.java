package com.android.styy.common.net.Interceptor.service;

import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.net.NetDataManager;
import com.android.styy.common.util.HttpUtil;
import com.android.styy.common.util.TextUtil;

import okhttp3.Request;
import okhttp3.Response;

public class ServiceNetHelper {


    // 检查URL 是否需要过滤
    public static boolean checkTokenFilterUrl(Request request){

        boolean tokenFilter = false;

        String url = HttpUtil.getUrl(request);
        for(String s: NetDataManager.tokenFilterUrls()){
            if(url.contains(s)){
                tokenFilter = true;
            }
        }
        return tokenFilter;
    }


    // 检查code 是否需要重新登录
    public static boolean checkCodeReLogin(String code){
        return "9902".equals(code) ||
                "9903".equals(code)  ||
                "9904".equals(code)  ||
                "9905".equals(code) ||
                "9906".equals(code) ||
                "1210".equals(code) ;
    }

    // 检查code是否需要重新刷新 token
    public static boolean checkCodeRefreshToken(String code){
        return "9901".equals(code);
    }


    public static TokenEntity getTokenByResponse(Response response, AppConfig.TokenType tokenType){

        if(null == response){
            return null;
        }

        String accessToken = response.header(AppConfig.ACCESS_TOKEN_KEY);
        String refreshToken = response.header(AppConfig.REFRESH_TOKEN_KEY);

        if(!TextUtil.isEmpty(accessToken) && !TextUtil.isEmpty(refreshToken)){
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setAccess_token(accessToken);
            tokenEntity.setRefresh_token(refreshToken);
            tokenEntity.setTokenType(tokenType.getState());
            return tokenEntity;
        }
        return null;
    }
}
