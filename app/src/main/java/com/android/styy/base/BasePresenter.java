package com.android.styy.base;

import android.app.Dialog;

import com.android.styy.common.config.AppConfig;
import com.android.styy.common.net.NetDataManager;
import com.android.styy.common.net.NetWorkManager;

public abstract class BasePresenter<T extends BaseDependView> {

    protected NetDataManager mNetDataManager;

    protected Dialog mLoadingDialog;

    protected T mDependView;

    private BasePresenter BasePresenter(Builder builder){
        mDependView = builder.mDependView;
        mLoadingDialog = builder.mLoadingDialog;
        mNetDataManager = builder.mNetDataManager;
        onAttach();
        return this;
    }

    protected void onAttach(){}

    public Builder newBuilder(){
        return new Builder();
    }

    public void showErrorMsg(){
        mDependView.showMsg("网络错误，请重试");
    }

    public final class Builder{

        private NetDataManager mNetDataManager;

        private Dialog mLoadingDialog;

        private T mDependView;

        public Builder proceed(T view){
            mDependView = view;
            mNetDataManager = NetWorkManager.getInstance().getNetDataManager();
            return this;
        }

        public Builder loadingDialog(Dialog mLoadingDialog){
            this.mLoadingDialog = mLoadingDialog;
            return this;
        }

        public <P extends BasePresenter> P build(){
            return (P) BasePresenter.this.BasePresenter(this);
        }
    }

    protected boolean isCanLoadMore(int total,int pageNo){
        return pageNo * AppConfig.PAGE_COUNT < total;
    }
}
