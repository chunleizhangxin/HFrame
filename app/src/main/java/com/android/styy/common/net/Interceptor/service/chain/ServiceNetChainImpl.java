package com.android.styy.common.net.Interceptor.service.chain;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.android.styy.LaunchApp;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.config.AppInterface;
import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.common.helper.DBTokenHelper;
import com.android.styy.common.net.Interceptor.service.ServiceNetController;
import com.android.styy.common.net.Interceptor.service.ServiceNetHelper;
import com.android.styy.common.net.NetWorkManager;
import com.android.styy.common.net.impl.NetModel;
import com.android.styy.common.util.HttpUtil;
import com.android.styy.common.util.JsonUtil;
import com.android.styy.common.util.TextUtil;
import com.android.styy.entity.ReLogin;
import com.android.styy.common.util.LoginHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceNetChainImpl implements IServiceNetChain {

    boolean refreshNewTokenTaskRunning = true;
    Response refreshNewTokenResp;

    @Override
    public Response executeBusiness(NetModel netModel,TokenEntity tokenEntity, Request oldReq, Response response) throws ServiceException {

        saveToken(tokenEntity);

        if(null == response){
//            return null;
            throw new ServiceException(AppConfig.NET_NO_CODE,"无网络");
        }
        if(!response.isSuccessful()){
            throw new ServiceException(AppConfig.NET_ERROR_CODE,netModel.getMessage());
        }

        String code = netModel.getRc();

        if(ServiceNetHelper.checkCodeReLogin(code)){
            checkReLogin(code);
        }
        if (ServiceNetHelper.checkCodeRefreshToken(code)) {
            return checkRefreshToken(tokenEntity.getRefresh_token(),oldReq);
        }
        return response;
    }

    public void saveToken(TokenEntity tokenEntity) {

        if(null == tokenEntity){
            return;
        }
        String responseAccessToken = tokenEntity.getAccess_token();
        String responseRefreshToken = tokenEntity.getRefresh_token();

        if(!TextUtil.isEmpty(responseAccessToken) &&
                !TextUtil.isEmpty(responseRefreshToken)){

            TokenEntity launchToken = LaunchApp.Instance.tokenEntity;

            if(null != launchToken){
                String launchAccessToken = launchToken.getAccess_token();
                String launchRefreshToken = launchToken.getRefresh_token();

                if(responseAccessToken.equals(launchAccessToken) && responseRefreshToken.equals(launchRefreshToken)){
                    return;
                }
            }

            DBTokenHelper.onInsertToken(tokenEntity);
            LaunchApp.Instance.tokenEntity = tokenEntity;
        }
    }

    private void checkReLogin(String code){
        EventBus.getDefault().post(new ReLogin());
    }

    private Response checkRefreshToken(String rp_refresh_token, final Request oldReq){


        Log.d(ServiceNetController.TAG,"5.进入刷新Token");

        FormBody.Builder refreshBody = new FormBody.Builder();
        refreshBody.add("client_id", "lifestyle365-client-key-app");
        refreshBody.add("client_secret", "lifestyle365-secret-201611120-20191119");
        refreshBody.add("grant_type", "refresh_token");
        refreshBody.add("refresh_token", rp_refresh_token);

        String address = new String(AppInterface.BASE_URL_USER);

        final Request refRequest = new Request.Builder()
                .url(address)
                .post(refreshBody.build())
                .build();

        NetWorkManager.getDefaultHttpBuilder().build().newCall(refRequest)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        refreshNewTokenTaskRunning = false;
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        TokenEntity tokenEntity = ServiceNetHelper.getTokenByResponse(response, AppConfig.TokenType.NORMAL);
                        saveToken(tokenEntity);

                        Log.d(ServiceNetController.TAG,"6.刷新Token成功");
                        String access_token = tokenEntity.getAccess_token();

                        if(!TextUtil.isEmpty(access_token)){
                            Request auldRequest = oldReq.newBuilder()
                                    .removeHeader("access_token")
                                    .addHeader("access_token", access_token)
                                    .build();
                            Log.d(ServiceNetController.TAG,"7.恢复上次请求");
                            Call requestCall = new OkHttpClient().newCall(auldRequest);
                            refreshNewTokenResp = requestCall.execute();
                            Log.d(ServiceNetController.TAG,"8..恢复上次请求成功");
                        }
                        refreshNewTokenTaskRunning = false;
                    }
                });

        while (refreshNewTokenTaskRunning){}
        refreshNewTokenTaskRunning = true;
        Log.d(ServiceNetController.TAG,"9.刷新TOKEN 结束");
        return refreshNewTokenResp;
    }
}
