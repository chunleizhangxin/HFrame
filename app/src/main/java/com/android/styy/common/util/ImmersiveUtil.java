package com.android.styy.common.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ImmersiveUtil {

    private static boolean sUseThemeStatusBarColor = false;
    private static boolean sUseStatusBarColor = true;

    public static void initImmersive(Activity activity){
        initImmersive(activity, false);
    }

    public static void initImmersive(Activity activity, boolean isTopWhite){
        if(activity == null || activity.isFinishing()){
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (sUseThemeStatusBarColor) {
                //window.setStatusBarColor(activity.getResources().getColor(R.color.colorTheme));//设置状态栏背景色
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            //Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && sUseStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            int systeUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if(isTopWhite){
                systeUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(systeUIFlag);
        }
    }
}