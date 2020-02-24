package com.android.styy.launch.contract.login;

import android.text.TextUtils;

import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.UserEntity;
import com.android.styy.common.net.impl.DefaultNetSubscriber;
import com.android.styy.common.net.impl.NetModel;
import com.android.styy.common.util.MD5Util;
import com.android.styy.entity.UserInfoEntity;

public class LoginPresenter extends LoginContract.LoginBasePresenter{

    @Override
    public void login(final String mobile, final String pwd, final String userType) {

        if(TextUtils.isEmpty(mobile)){
            mDependView.showMsg("请输入用户名");
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            mDependView.showMsg("请输密码");
            return;
        }
        mNetDataManager.get_pwd_salt(mobile)
                .subscribe(new DefaultNetSubscriber<String>(mLoadingDialog) {
                    @Override
                    public void onCompleted(String netModel) {
                        if (TextUtils.isEmpty(netModel)) {
                            return;
                        }
                        String pwdCode = MD5Util.toMD5(pwd + "{" +netModel +"}");
                        loginSaltCode(mobile, pwdCode, userType);
                    }
                });
    }

    private void loginSaltCode(final String mobile, final String pwd, String userType){
        mNetDataManager.post_login_by_password(mobile, pwd, userType)
                .subscribe(new DefaultNetSubscriber<NetModel<UserInfoEntity>>(mLoadingDialog) {
                    @Override
                    public void onCompleted(NetModel<UserInfoEntity> netModel) {
                        if(null == netModel){
                            return;
                        }
                        if(!(AppConfig.NET_SUCCESS_CODE == netModel.getStatus())){
                            mDependView.showMsg(netModel.getMessage());
                            return;
                        }
                        UserInfoEntity infoEntity = netModel.getData();
                        UserEntity userEntity = infoEntity.getTbGovernmentInfo();
                        userEntity.setId(infoEntity.getId());
                        userEntity.setUsername(infoEntity.getUsername());
                        userEntity.setUserType(infoEntity.getUserType());
                        userEntity.setNickName(infoEntity.getNickName());
                        userEntity.setStatus(infoEntity.getStatus());
                        mDependView.loginSuccess(userEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}