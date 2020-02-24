package com.android.styy.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.styy.R;
import com.android.styy.base.BaseAppCompatActivity;
import com.android.styy.common.config.AppIntent;
import com.android.styy.common.dao.UserEntity;
import com.android.styy.common.helper.DBUserHelper;
import com.android.styy.common.util.ImmersiveUtil;
import com.android.styy.launch.contract.login.LoginContract;
import com.android.styy.launch.contract.login.LoginPresenter;

import java.io.Serializable;

import butterknife.BindView;

public class LoginActivity extends BaseAppCompatActivity implements LoginContract.LoginBaseView {

    private static final int TYPE_PASSWORD = 1;
    private static final int TYPE_CODE = 2;

    @BindView(R.id.login_type_code)
    View mCodeParent;
    @BindView(R.id.login_type_password)
    View mPasswordParent;
    @BindView(R.id.login_edt_username)
    EditText mEditUserName;
    @BindView(R.id.login_edt_password)
    EditText mEditPassword;
    @BindView(R.id.login_btn_login_type)
    TextView mBtnLoginType;
    @BindView(R.id.login_btn_login)
    View mBtnLogin;

    private int mCurrentType = TYPE_PASSWORD;
    LoginContract.LoginBasePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveUtil.initImmersive(this, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected String toolBarTitle() {
        return null;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBtnLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentType == TYPE_PASSWORD) {
                    mCurrentType = TYPE_CODE;
                    switchLoginType(mCurrentType);
                    mBtnLoginType.setText("密码登录");
                } else {
                    mCurrentType = TYPE_PASSWORD;
                    switchLoginType(mCurrentType);
                    mBtnLoginType.setText("验证码登录");
                }
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentType == TYPE_PASSWORD) {
                    mPresenter.login(mEditUserName.getText().toString(), mEditPassword.getText().toString(), "1");
                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new LoginPresenter(), this);
    }

    @Override
    public void loginSuccess(UserEntity userEntity) {
        userEntity.setLogout(false);
        DBUserHelper.onInsertUser(userEntity);
        switchHomePage();
        showMsg("登录成功");
        finish();
    }

    private void switchHomePage() {
        Intent intent = AppIntent.getMainActivity(getBaseContext());
        startActivity(intent);
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }


    private void switchLoginType(int type) {
        if (type == TYPE_PASSWORD) {
            Animation rightOut = AnimationUtils.loadAnimation(this, R.anim.anim_login_right_out);
            Animation leftIn = AnimationUtils.loadAnimation(this, R.anim.anim_login_left_in);
            rightOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCodeParent.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            leftIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPasswordParent.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCodeParent.startAnimation(rightOut);
            mPasswordParent.startAnimation(leftIn);
        } else {
            Animation rightIn = AnimationUtils.loadAnimation(this, R.anim.anim_login_right_in);
            Animation leftOut = AnimationUtils.loadAnimation(this, R.anim.anim_login_left_out);
            rightIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCodeParent.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            leftOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPasswordParent.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCodeParent.startAnimation(rightIn);
            mPasswordParent.startAnimation(leftOut);
        }
    }
}