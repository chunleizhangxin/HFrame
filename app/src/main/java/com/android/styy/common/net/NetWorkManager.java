package com.android.styy.common.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.styy.common.net.Interceptor.LogInterceptor;
import com.android.styy.common.net.Interceptor.service.ServiceInterceptor;

public class NetWorkManager {

    private static NetWorkManager Instance;


    public static NetWorkManager getInstance() {
        if (Instance == null) {
            Instance = new NetWorkManager();
        }
        return Instance;
    }
    private NetWorkManager(){}

    public <T> T build(Class<T> service,String url){

        OkHttpClient okHttpClient = getDefaultHttpBuilder()
                .addInterceptor(new ServiceInterceptor())
                .addInterceptor(new LogInterceptor())
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

       return retrofit.create(service);
    }

    public static OkHttpClient.Builder getDefaultHttpBuilder(){
        return new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS);
    }

    public NetDataManager getNetDataManager(){
        return NetDataManager.getInstance();
    }
}
