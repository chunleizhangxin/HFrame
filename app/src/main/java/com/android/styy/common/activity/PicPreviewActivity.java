package com.android.styy.common.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.luck.picture.lib.widget.PreviewViewPager;
import com.android.styy.R;
import com.android.styy.base.BaseAppCompatActivity;
import com.android.styy.common.adapter.PicPreviewAdapter;

import java.util.List;

import butterknife.BindView;

public class PicPreviewActivity extends BaseAppCompatActivity {

    public static String KEY_POS = "KEY_POS";
    public static String KEY_DATA = "KEY_DATA";

    @BindView(R.id.preview_pager)
    PreviewViewPager previewPager;
    @BindView(R.id.tv_indicatePos)
    TextView tvIndicatePos;


    List<String> mData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_preview_pic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        previewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                notifyIndicatePos(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        int currentPos = getIntent().getIntExtra(KEY_POS, 0);
        mData = (List<String>) getIntent().getSerializableExtra(KEY_DATA);

        notifyIndicatePos(currentPos);

        PicPreviewAdapter mAdapter = new PicPreviewAdapter(mContext);

        previewPager.setAdapter(mAdapter);

        mAdapter.update(mData);

        previewPager.setCurrentItem(currentPos);
    }

    private void notifyIndicatePos(int pos){
        tvIndicatePos.setText((pos + 1) + "/" + mData.size());
    }
}
