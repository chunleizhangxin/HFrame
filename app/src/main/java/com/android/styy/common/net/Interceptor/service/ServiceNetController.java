package com.android.styy.common.net.Interceptor.service;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.android.styy.LaunchApp;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.common.net.Interceptor.service.chain.IServiceNetChain;
import com.android.styy.common.net.Interceptor.service.chain.ServiceNetChainImpl;
import com.android.styy.common.net.impl.NetModel;
import com.android.styy.common.util.HttpUtil;
import com.android.styy.common.util.JsonUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class ServiceNetController {

    public static String TAG = "ServiceNetController";

    private final Charset UTF8 = Charset.forName("UTF-8");
//
//    public static ServiceNetController getInstalce(){
//        if(null == instalce){
//            instalce = new ServiceNetController();
//        }
//        return instalce;
//    }

    public ServiceNetController() {
    }

    private IServiceNetChain iServiceNetChain = new ServiceNetChainImpl();

    public Response intercept(Interceptor.Chain chain) throws ServiceException {
        Request request = chain.request();
        Response response = null;

        TokenEntity tokenEntity = LaunchApp.Instance.tokenEntity;

        Log.d(TAG, "1.进入拦截器");

        Log.d(ServiceNetController.TAG, "3.获取token完成 ");

        // token 不为空 放入请求header
        if (null != tokenEntity) {
            Log.d(ServiceNetController.TAG, "3.获取token完成 token:" + tokenEntity.getAccess_token());
            request = request.newBuilder()
                    .addHeader("access_token", tokenEntity.getAccess_token())
                    .build();
        }

        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            json = HttpUtil.getBody(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TokenEntity repTokenEntity = ServiceNetHelper.getTokenByResponse(response, AppConfig.TokenType.NORMAL);
        if (null != repTokenEntity) {
            tokenEntity = repTokenEntity;
        }

        NetModel netModel = JsonUtil.jsonToBean(json, NetModel.class);
        Response rep = iServiceNetChain.executeBusiness(netModel, tokenEntity, request, response);
        Log.d(TAG, "10.返回 rep " + rep);
        Log.d(TAG, " -------------------------------------------------------------- ");
        log(request, response);
        return rep;
    }

    private void log(Request request, Response response) {
        RequestBody requestBody = request.body();

        try {
            String body = null;


            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                body = buffer.readString(charset);
            }


            String rBody = null;
            if (response != null) {
                ResponseBody responseBody = response.body();

                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        e.printStackTrace();
                    }
                }
                rBody = buffer.clone().readString(charset);
            }
            if (TextUtils.isEmpty(rBody)) {
                rBody = "body is null";
            } else if (rBody.length() > 500) {
                rBody = rBody.substring(0, 500);
            }

            if (TextUtils.isEmpty(rBody)) {
                rBody = "body is null";
            } else if (rBody.length() > 500) {
                rBody = rBody.substring(0, 500);
            }

            if (TextUtils.isEmpty(body)) {
                body = "body is null";
            } else if (body.length() > 500) {
                body = body.substring(0, 500);
            }

            Logger.d("请求url：%s\nmethod：%s\nheaders: %s状态码: %s\n收到响应 %s\n请求body：%s\n响应body：%s",
                    response.request().url(), request.method(), request.headers(), response.code(),
                    response.message(), body, rBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
