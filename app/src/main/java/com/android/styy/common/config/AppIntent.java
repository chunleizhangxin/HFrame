package com.android.styy.common.config;

import android.content.Context;
import android.content.Intent;

import com.android.styy.common.activity.PicPreviewActivity;
import com.android.styy.common.activity.VideoPreviewActivity;
import com.android.styy.launch.LoginActivity;
import com.android.styy.launch.MainActivity;

public class AppIntent {

    public static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    public static final String KEY_ACTIVITY_TYPE = "KEY_ACTIVITY_TYPE";
    public static final String KEY_ACTIVITY_DATA = "KEY_ACTIVITY_DATA";
    public static final String KEY_FRAGMENT_BUNDLE_DATA = "KEY_FRAGMENT_BUNDLE_DATA";

    /****
     * 登录界面
     */
    public static Intent getLoginActivity(Context mContext) {
        return new Intent(mContext, LoginActivity.class);
    }

    /****
     * 首界面
     */
    public static Intent getMainActivity(Context mContext) {
        return new Intent(mContext, MainActivity.class);
    }

    /***
     * 图片查看器
     * @param mContext
     * @return
     */
    public static Intent getPicPreviewActivity(Context mContext) {
        return new Intent(mContext, PicPreviewActivity.class);
    }

    /***
     * 视频查看器
     * @param mContext
     * @return
     */
    public static Intent getVideoPreviewActivity(Context mContext) {
        return new Intent(mContext, VideoPreviewActivity.class);
    }
}