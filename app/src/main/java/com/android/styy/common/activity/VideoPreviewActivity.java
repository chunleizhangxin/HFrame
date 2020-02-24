package com.android.styy.common.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.styy.R;
import com.android.styy.base.BaseAppCompatActivity;

import butterknife.BindView;

public class VideoPreviewActivity extends BaseAppCompatActivity {

    public static String KEY_DATA = "KEY_DATA";


    @BindView(R.id.video_view)
    VideoView videoView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_previe_video;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected String toolBarTitle() {
        return null;
    }

    @Override
    protected void initData() {

        String url = getIntent().getStringExtra(KEY_DATA);

        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.requestFocus();

    }
}
