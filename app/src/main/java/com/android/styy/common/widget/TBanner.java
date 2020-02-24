package com.android.styy.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.android.styy.common.glide.GlideUtil;
import com.android.styy.common.util.TextUtil;
import com.android.styy.entity.BannerEntity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Random;

public class TBanner extends Banner {

    public TBanner(Context context) {
        this(context,null);
    }

    public TBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){

        setImageLoader(new TBannerLoadListener());

        setDelayTime(8 * 1000);
    }

    class TBannerLoadListener extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            String data = null;
            if(path instanceof String){
                data = (String) path;
            }else if(path instanceof BannerEntity){
                data = ((BannerEntity) path).getMainImgUrl();
            }
            if(!TextUtil.isEmpty(data)){
                Glide.with(context)
                        .asBitmap()
                        .load(data)
                        .into(imageView);
            }
        }
    }
}
