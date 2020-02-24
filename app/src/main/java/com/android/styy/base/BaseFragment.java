package com.android.styy.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.android.styy.R;
import com.android.styy.common.util.NetUtil;

public abstract class BaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected String TAG = getClass().getSimpleName();

    protected ViewGroup parentView;

    protected View childView;

    protected Context mContext;

    Unbinder mUnbinder;

    Dialog mLoadingDialog;

    protected View mErrorView;

    private ImageView iconError;

    private TextView tvError;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.parentView == null){
            parentView = new FrameLayout(container.getContext());
        }
        if (this.childView == null) {
            this.childView = inflater.inflate(this.getLayoutId(), container, false);
            parentView.addView(childView);
        }
        if (parentView.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.parentView.getParent();
            parent.removeView(this.parentView);
        }
        mUnbinder = ButterKnife.bind(this, childView);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        if(getActivity()==null){
            return;
        }
        if(getActivity().isFinishing()){
            return;
        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        mErrorView = LayoutInflater.from(mContext).inflate(R.layout.layout_widget_error,parentView,false);

        iconError = mErrorView.findViewById(R.id.icon_error);
        tvError = mErrorView.findViewById(R.id.tv_error);

        mErrorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        BaseAppCompatActivity activity = (BaseAppCompatActivity) mContext;
        mLoadingDialog = activity.mLoadingDialog;

        initViews(childView, savedInstanceState);
        initToolbar(childView, savedInstanceState);
        initData();

    }
    @Subscribe
    public void test(Void avoid){}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    protected <V extends View> V findViewById(int id) {
        return (V) this.childView.findViewById(id);
    }

    public boolean checkLogin(){
        BaseAppCompatActivity activity = (BaseAppCompatActivity) getActivity();
        return activity.checkLogin();
    }

    public void showMsg(String msg){
        Snackbar.make(childView,msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void finish(){
        getActivity().finish();
    }

    public <T extends BasePresenter,V extends BaseDependView> T  newPresenter(T t ,V v){
        return (T) t.newBuilder()
                .proceed(v)
                .loadingDialog(mLoadingDialog)
                .build();
    }

    public void onShowDialog(BaseDialog mDialog) {
        BaseAppCompatActivity activity = (BaseAppCompatActivity) getActivity();
        activity.onShowDialog(mDialog);
    }

    protected abstract int getLayoutId();

    protected abstract void initToolbar(View ChildView,Bundle savedInstanceState);

    public abstract String toolBarTitle();

    protected abstract void initViews(View ChildView,Bundle savedInstanceState);

    protected abstract void initData();

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

    private void onShowErrorView(int resErrorImg,String txt){
        if(null == iconError || null == tvError || null == mErrorView){
            return;
        }
        iconError.setImageResource(resErrorImg);
        tvError.setText(txt);

        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }

        parentView.addView(mErrorView);
    }

    public void onHideErrorView(){
        if(null == mErrorView){
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected  boolean isActivityFinishing(){
        if(getActivity() == null || getActivity().isFinishing()){
            return true;
        }
        return false;
    }
}
