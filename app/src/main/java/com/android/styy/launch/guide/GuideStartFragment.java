package com.android.styy.launch.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.styy.R;
import com.android.styy.base.BaseFragment;

import butterknife.BindView;

public class GuideStartFragment extends BaseFragment {

    @BindView(R.id.icon_item)
    ImageView iconItem;
    @BindView(R.id.btn_start)
    TextView btnStart;

    int imgRes;


    public static GuideStartFragment newInstance(int res) {
        GuideStartFragment fragment = new GuideStartFragment();
        fragment.imgRes = res;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launch_guide_start;
    }

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    @Override
    public String toolBarTitle() {
        return null;
    }

    @Override
    protected void initViews(View ChildView, Bundle savedInstanceState) {

        iconItem.setImageResource(imgRes);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
