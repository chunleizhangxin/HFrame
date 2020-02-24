package com.android.styy.launch.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.styy.R;
import com.android.styy.base.BaseFragment;

import butterknife.BindView;

public class GuideItemFragment extends BaseFragment {

    @BindView(R.id.icon_item)
    ImageView iconItem;


    int imgRes;

    public static GuideItemFragment newInstance(int res){
        GuideItemFragment fragment = new GuideItemFragment();
        fragment.imgRes = res;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launch_guide_item;
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
    }

    @Override
    protected void initData() {

    }
}
