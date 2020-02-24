package com.android.styy.launch.contract.login;

import com.android.styy.base.BaseDependView;
import com.android.styy.base.BasePresenter;
import com.android.styy.common.dao.UserEntity;

public interface LoginContract {

    abstract class LoginBasePresenter extends BasePresenter<LoginBaseView> {

        public abstract void login(String mobile, String pwd, String userType);
    }

    interface LoginBaseView extends BaseDependView<LoginBasePresenter> {
        void loginSuccess(UserEntity userEntity);
    }
}
