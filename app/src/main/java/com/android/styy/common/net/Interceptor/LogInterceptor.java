package com.android.styy.common.net.Interceptor;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = null;

        try {
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
        }catch (Exception e){}

        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long tookTime = System.currentTimeMillis() - startTime;

        String rBody = null;
        if(response != null) {
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

        Logger.d("请求url：%s\nmethod：%s\nheaders: %s状态码: %s\n收到响应 %s\n请求body：%s\n响应body：%s\n请求时间：%s毫秒",
                response.request().url(),request.method(),request.headers(),response.code(),
                response.message(),body, rBody,tookTime);

        return response;
    }
}