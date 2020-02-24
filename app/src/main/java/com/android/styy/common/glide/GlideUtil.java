package com.android.styy.common.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.android.styy.common.glide.transform.CircleBorderformation;
import com.android.styy.common.util.DeviceUtils;

public class GlideUtil {

    /**
     * 加载圆形图片
     * @param imageView
     * @param url
     */
    public static void displayCircleUrl(ImageView imageView, String url){

        displayUrl(imageView,url,new CircleCrop());
    }

    /**
     * 加载圆角图片
     * @param imageView
     * @param roundingRadius
     * @param url
     */
    public static void displayRoundUrl(ImageView imageView,/* dp */int roundingRadius, String url){

        displayUrl(imageView,url,new RoundedCorners(DeviceUtils.dp2px(roundingRadius)));
    }

    /**
     * 加载带边框的圆形图片
     * @param imageView
     * @param borderWidth
     * @param borderColor
     * @param url
     */
    public static void displayCircleBorderUrl(ImageView imageView,int borderWidth,int borderColor, String url){

        displayUrl(imageView,url,new CircleBorderformation(borderWidth,borderColor));
    }

    /**
     * 加载图片
     * @param imageView
     * @param url
     */
    public static void displayUrl(ImageView imageView, String url){

        displayUrl(imageView,url,0);

    }

    public static void displayUrl(ImageView imageView, String url, int defaultRes){
        displayUrl(imageView,url,null,defaultRes);
    }

    public static void displayUrl(ImageView imageView, String url, Transformation transformation){

        displayUrl(imageView,url,transformation,0);
    }

    public static void displayUrl(ImageView imageView, String url, Transformation transformation, int defaultRes){

        displayImgReqBuilder(imageView,url,transformation,defaultRes)
                .into(imageView);

    }

    public static RequestBuilder<Drawable> displayImgReqBuilder(ImageView imageView, String url){
        return displayImgReqBuilder(imageView,url,null,0);

    }


    public static RequestBuilder<Drawable> displayImgReqBuilder(ImageView imageView, String url, Transformation transformation, int defaultRes){

        if(ImageView.ScaleType.FIT_CENTER == imageView.getScaleType()){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        return displayImgReqBuilder(imageView.getContext(),url,transformation,defaultRes);

    }

    public static RequestBuilder<Drawable> displayImgReqBuilder(Context context, String url, Transformation transformation, int defaultRes){

        RequestOptions options;
        if(null != transformation) {
            options = RequestOptions.bitmapTransform(new MultiTransformation(new CenterCrop(),transformation));
        }else{
            options = RequestOptions.fitCenterTransform();
        }
        options.error(defaultRes)
                .placeholder(defaultRes)
                .fallback(defaultRes);
        return Glide.with(context)
                .asDrawable()
                .load(url)
                .apply(options);

    }
}
