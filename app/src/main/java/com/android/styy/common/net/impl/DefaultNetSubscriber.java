package com.android.styy.common.net.impl;

import android.app.Dialog;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.entity.QMsgEntity;

public abstract class DefaultNetSubscriber<T> extends Subscriber<T> {

    T data;

    protected Dialog dialog;


    public DefaultNetSubscriber(Dialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void onCompleted() {
        if(dialog != null){
            dialog.dismiss();
        }
        onCompleted(data);
    }

    public abstract void onCompleted(T t);

    @Override
    public void onError(Throwable e) {
        Logger.e(e,"net" + e);
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

        int errorCode = 0;


        if(e instanceof ServiceException){
            ServiceException exception = (ServiceException) e;
            errorCode = exception.getCode();
            if(errorCode == AppConfig.NET_NO_CODE || errorCode == AppConfig.NET_ERROR_CODE){
                return;
            }
        }

        QMsgEntity msgEntity = new QMsgEntity();
        msgEntity.msg = e.getMessage();
        msgEntity.code = errorCode;
        EventBus.getDefault().post(msgEntity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        data = t;
    }
}
