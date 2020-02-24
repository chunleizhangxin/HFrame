package com.android.styy.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.android.styy.common.config.AppIntent;
import com.android.styy.launch.LoginActivity;
import com.orhanobut.logger.Logger;
import com.android.styy.LaunchApp;
import com.android.styy.R;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.dao.UserEntity;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.common.helper.DBTokenHelper;
import com.android.styy.common.helper.DBUserHelper;
import com.android.styy.common.manager.ActivitiesManager;
import com.android.styy.common.util.NetUtil;
import com.android.styy.common.util.PermissionsUtil;
import com.android.styy.common.util.TextUtil;
import com.android.styy.common.util.ToastUtils;
import com.android.styy.entity.NetWorkTypeEntity;
import com.android.styy.entity.ReLogin;
import com.android.styy.entity.QMsgEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected String mSimpleName = getClass().getSimpleName();

    protected Context mContext;

    protected ViewGroup parent;

    protected View mDecoView;

    protected Unbinder mUnbinder;

    protected Dialog mLoadingDialog;

    protected View mErrorView;

    private ImageView iconError;

    private TextView tvError;
    protected RxPermissions mRxPermissions = null;
    private boolean mIsFirstPermissionResume = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutResId = this.getLayoutId();
        parent = new FrameLayout(this);
        ActivitiesManager.add(this);
        if(0 != layoutResId){
            mDecoView = View.inflate(this,layoutResId,null);
            parent.addView(mDecoView);
            setContentView(parent);
        }
        mUnbinder = ButterKnife.bind(this);
        mContext = this;

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        mErrorView = LayoutInflater.from(this).inflate(R.layout.layout_widget_error,parent,false);

        iconError = mErrorView.findViewById(R.id.icon_error);
        tvError = mErrorView.findViewById(R.id.tv_error);

        mErrorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        onNetChangedCheckd();

        mLoadingDialog = createLoadingDialog();
        mRxPermissions = new RxPermissions(this);
        this.initViews(savedInstanceState);
        this.initData();
        this.initToolbar(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    protected Dialog createLoadingDialog(){
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNetError(QMsgEntity bean){
        showMsg(bean.msg);
        if(bean.code == AppConfig.NET_ERROR_CODE){

        }else{

        }
    }

    // 重新登录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLogin(ReLogin login){

        if (ActivitiesManager.getTopActivity().equals(LoginActivity.class.getName())) {
            return;
        }
        UserEntity userEntity = DBUserHelper.onLoadUser();
        if(null != userEntity && !userEntity.isLogout()){
            userEntity.setLogout(true);
            DBUserHelper.onInsertUser(userEntity);
        }
        Intent intent = AppIntent.getLoginActivity(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void clear(){
        DBTokenHelper.deleteAll();
        LaunchApp.Instance.tokenEntity = null;
    }

    /**
     * 检查是否登录
     *
     * false 未登录
     *
     * true 登录
     */
    public boolean checkLogin(){
        if(!DBTokenHelper.isLogin()){
            reLogin(new ReLogin());
            return false;
        }
        return true;
    }

    public void showMsg(String msg){
        if(TextUtil.isEmpty(msg)){
            return;
        }
        ToastUtils.showCenter(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netChangedCheckd(NetWorkTypeEntity entity){
        onNetChangedCheckd();
    }

    public void onNetChangedCheckd(){

        if(!NetUtil.checkNetConnection(mContext)){
            onShowNetErrorView();
        }else{
            onHideErrorView();
        }
    }

    public void onShowNetErrorView(){
        onShowErrorView(R.mipmap.icon_net_error,"网络罢工了，请重试！");
    }

    public void onShowDataErrorView(Throwable throwable){
        if(throwable instanceof ServiceException){
            ServiceException exception = (ServiceException) throwable;
            if(exception.getCode() ==  AppConfig.NET_ERROR_CODE){
                onShowErrorView(R.mipmap.icon_net_error,"网络错误，请重试！");
            }
        }
    }

    protected void onShowErrorView(int resErrorImg,String txt,ViewGroup group){

        iconError.setImageResource(resErrorImg);
        tvError.setText(txt);

        if(null != mLoadingDialog && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }

        group.addView(mErrorView);
    }

    protected void onShowErrorView(int resErrorImg,String txt){
        onShowErrorView(resErrorImg,txt,parent);
    }

    protected void onHideErrorView(){
        onHideErrorView(parent);
    }

    protected void onHideErrorView(ViewGroup group){
        if(null == mErrorView){
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }
    }

    @Override
    protected void onResume() {
        checkLoginState();
        super.onResume();
    }

    private AlertDialog mPermissionDialog = null;

    protected void checkNecessaryPermission() {
        if (!mIsFirstPermissionResume) {
            return;
        }
        boolean hasPermissions = PermissionsUtil.checkPermissions(this, PermissionsUtil.NECESSARY_PERMISSION);
        if (hasPermissions) {
            return;
        }
        PermissionsUtil.checkDynamicPermissions(mRxPermissions, new PermissionsUtil.CheckPermissionsListener() {
            @Override
            public void checkPermissionsResult(boolean result) {
                if(!result){
                    if(mPermissionDialog == null){
                        mPermissionDialog = PermissionsUtil.createPermissionDialog(BaseAppCompatActivity.this,
                                getBaseContext().getResources().getString(R.string.permission_dialog_app_remind_msg),
                                new PermissionsUtil.PermissionDialogListener() {
                                    @Override
                                    public void positiveButtonClick() {
                                        Intent intent = PermissionsUtil.getSettingPermissionItent();
                                        try {
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException ex) {
                                            Logger.e(mSimpleName,
                                                    "checkDynamicPermissions ActivityNotFoundException occured.");
                                        }
                                    }

                                    @Override
                                    public void negativeButtonClick() {

                                    }
                                });
                        mPermissionDialog.show();
                    }
                }
            }
        }, PermissionsUtil.NECESSARY_PERMISSION);
        mIsFirstPermissionResume = false;
    }

    private void checkLoginState(){

    }

    @Override
    protected void onDestroy() {
        if (mPermissionDialog != null && mPermissionDialog.isShowing()) {
            mPermissionDialog.dismiss();
        }
        mRxPermissions = null;
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public <T extends BasePresenter,V extends BaseDependView> T  newPresenter(T t ,V v){
        return (T) t.newBuilder()
                .proceed(v)
                .loadingDialog(mLoadingDialog)
                .build();
    }

    public void onShowDialog(BaseDialog mDialog) {
        BaseDialog baseDialog = mDialog;

        Dialog dialog = baseDialog.getDialog();

        if (null != dialog && dialog.isShowing()) {
            return;
        }
        baseDialog.show(getSupportFragmentManager());
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract String toolBarTitle();

    protected abstract void initData();

}