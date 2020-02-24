package com.android.styy.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

    public static boolean checkNetConnection(Context context){
        //获取连接活动管理器
        final ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取链接网络信息
        final NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!= null && networkInfo.isAvailable());
    }

}
