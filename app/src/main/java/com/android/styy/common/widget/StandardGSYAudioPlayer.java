package com.android.styy.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.android.styy.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class StandardGSYAudioPlayer extends StandardGSYVideoPlayer {

    public StandardGSYAudioPlayer(Context context) {
        super(context);
    }

    public StandardGSYAudioPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public StandardGSYAudioPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.audio_layout_standard;
    }

    @Override
    protected void onClickUiToggle() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }

    @Override
    protected void hideAllWidget() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToNormal() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToPreparingShow() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToPlayingShow() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToPauseShow() {
        updateStartImage();
        mStartButton.setVisibility(VISIBLE);
    }
}
