package com.android.styy.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.android.styy.R;

public abstract class BaseDialog extends DialogFragment {

    Unbinder mUnbinder;

    protected View mDecoView;

    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDecoView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this,mDecoView);
        mDecoView.setBackgroundColor(getBackgroundColor());

        return mDecoView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initViews();

        initData();
    }

    @Override
    public void onStart() {
        initParams();
        super.onStart();
    }

    protected void initParams() {
        if(null == getDialog()){
            return;
        }
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
        setCancelable(cancelable());
    }

    public BaseDialog show(FragmentManager manager) {
        try {
            Field mDismissedField = getClass().getSuperclass().getSuperclass().getDeclaredField("mDismissed");
            mDismissedField.setAccessible(true);
            mDismissedField.set(this,false);


            Field mmShownByMeField = getClass().getSuperclass().getSuperclass().getDeclaredField("mShownByMe");
            mmShownByMeField.setAccessible(true);
            mmShownByMeField.set(this,true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract boolean cancelable();

    protected int getBackgroundColor(){
        return Color.parseColor("#b3000000");
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
