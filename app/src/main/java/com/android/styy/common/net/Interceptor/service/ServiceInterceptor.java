package com.android.styy.common.net.Interceptor.service;



import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {

    ServiceNetController serviceNetController;

    public ServiceInterceptor(){
        serviceNetController = new ServiceNetController();
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        return serviceNetController.intercept(chain);
    }

}
