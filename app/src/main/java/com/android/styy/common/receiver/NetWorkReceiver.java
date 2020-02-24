package com.android.styy.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.styy.entity.NetWorkTypeEntity;

import org.greenrobot.eventbus.EventBus;

public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetWorkTypeEntity workChangedEntity = new NetWorkTypeEntity();
        if(null != workChangedEntity){
            EventBus.getDefault().post(workChangedEntity);
        }
    }
}
