package com.android.styy.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.styy.R;
import com.android.styy.base.BaseAppCompatActivity;
import com.android.styy.common.activity.PicPreviewActivity;
import com.android.styy.common.config.AppIntent;
import com.android.styy.common.manager.ActivitiesManager;
import com.android.styy.common.util.FileAddedView;
import com.android.styy.common.util.ImmersiveUtil;
import com.android.styy.common.widget.StandardGSYAudioPlayer;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity{

    @BindView(R.id.file_add_linear)
    LinearLayout mFileParent;
    @BindView(R.id.audio_player)
    StandardGSYAudioPlayer mAudioPlayer;
    @BindView(R.id.txt_btn)
    View mBtnView;

    FileAddedView mFileAddedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveUtil.initImmersive(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected String toolBarTitle() {
        return "首页";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mFileAddedView = new FileAddedView(this, mFileParent, true);
        mFileAddedView.setIsCanDelete(true);
        mFileAddedView.setIsCanLook(true);
        mFileAddedView.setOnItemClickListener(new FileAddedView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<String> files) {
                Intent intent = AppIntent.getPicPreviewActivity(mContext);
                intent.putExtra(PicPreviewActivity.KEY_POS, position);
                intent.putExtra(PicPreviewActivity.KEY_DATA, (Serializable) files);
                mContext.startActivity(intent);
            }
        });
        mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testAudioUrl = "http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3";
                mAudioPlayer.setUp(testAudioUrl, true, "");
                mAudioPlayer.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                    if (mediaList != null) {
                        mFileAddedView.addFile(mediaList);
                    }
                    break;
            }
        }
    }

    long preTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            // 如果时间间隔大于2秒，不处理
            if ((currentTime - preTime) > 800) {
                // 显示消息
                showMsg("再按一次，将退出程序");
                //更新时间
                preTime = currentTime;
                //截获事件，不再处理
                return true;
            }else{
                finish();
                ActivitiesManager.clear();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}