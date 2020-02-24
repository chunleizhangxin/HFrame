package com.android.styy.common.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luck.picture.lib.photoview.PhotoView;
import com.android.styy.common.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class PicPreviewAdapter extends PagerAdapter {

    private Context mContext;

    private List<String> mData = new ArrayList<>();

    public PicPreviewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void update(List<String> data){
        mData.clear();
        if(null != data){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        GlideUtil.displayUrl(photoView,mData.get(position));
        try {
            container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoView;
    }
}
