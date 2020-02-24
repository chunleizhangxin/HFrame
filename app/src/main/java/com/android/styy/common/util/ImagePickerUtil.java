package com.android.styy.common.util;

import android.app.Activity;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

public class ImagePickerUtil {

    // 打开多媒体
    public static void startMedia(Activity activity,long maxFileSize){
        init(activity,false,true, PictureMimeType.ofAll(),1,maxFileSize);
    }

    // 打开多媒体
    public static void startMedia(Activity activity,long maxFileSize, int maxSize){
        init(activity,false,true, PictureMimeType.ofAll(),maxSize,maxFileSize);
    }

    // 打开相机
    public static void startCamera(Activity activity){
        init(activity,true,true, PictureMimeType.ofImage(),1,-1);
    }

    // 打开相册
    public static void startPhoto(Activity activity,boolean showCamera,int maxSelectNum){
        init(activity,false,showCamera,PictureMimeType.ofImage(),maxSelectNum,-1);
    }
    /**
     *
     * @param activity
     * @param starCamera 跳转相机
     * @param showCamera 显示相机
     * @param mimeType   多媒体类型
     * @param maxSelectNum 选择数量
     */
    private static void init(Activity activity, boolean starCamera,boolean showCamera,
                            int mimeType,int maxSelectNum,long maxFileSize){
        PictureSelectionModel selectionModel;
        if(starCamera){
            selectionModel = PictureSelector.create(activity).openCamera(mimeType);
        }else{
            selectionModel = PictureSelector.create(activity).openGallery(mimeType);
        }

        selectionModel
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(maxSelectNum > 1 ?
                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(showCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality(0)// 视频录制质量 0 or 1
                .videoMaxSecond(10)//显示多少秒以内的视频or音频也可适用
                .recordVideoSecond(10)//录制视频秒数 默认60s
                .maxFileSize(maxFileSize)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
