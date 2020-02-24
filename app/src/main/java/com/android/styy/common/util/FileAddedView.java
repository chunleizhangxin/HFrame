package com.android.styy.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.styy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangchunlei on 2019/8/1
 * <p>
 * Explain:
 * Used to control the addition of pictures and videos
 */
public class FileAddedView {

    public static final int TYPE_PICTURE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int MAX_NUMBER = 4;


    private View mAddView = null;
    private LinearLayout mParentView = null;
    private Activity mActivity = null;
    private List<LocalMedia> mFileMedias = null;
    private int mMaxSize = MAX_NUMBER;
    private int mCanAddSize = mMaxSize;
    private boolean mIsCanAddFile = true;
    private boolean mIsCanDelete = true;
    private boolean mIsCanLook = false;
    private int mSelectFileType = PictureMimeType.ofAll();


    public FileAddedView(Activity activity, LinearLayout linearLayout) {
        this(activity, linearLayout, true);
    }

    public FileAddedView(Activity activity, LinearLayout linearLayout, boolean isCanAddFile) {
        mActivity = activity;
        mParentView = linearLayout;
        mFileMedias = new ArrayList<LocalMedia>();
        mIsCanAddFile = isCanAddFile;
        initParentView(activity);
    }

    private void initParentView(Context context) {
        if (mIsCanAddFile) {
            mAddView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.btn_add_file_view_layout,
                    mParentView, false);
            mAddView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewUtil.hideSoftInput(mActivity);
                    if (mSelectFileType == PictureMimeType.ofAll()) {
                        ImagePickerUtil.startMedia(mActivity, 1024 * 1024 * 10, mCanAddSize);
                    } else if (mSelectFileType == PictureMimeType.ofImage()) {
                        ImagePickerUtil.startPhoto(mActivity, true, mCanAddSize);
                    } else if (mSelectFileType == PictureMimeType.ofVideo()) {

                    }
                }
            });
            mParentView.addView(mAddView);
        }
    }

    public void updateCanAddSize() {
        mCanAddSize = mMaxSize - mFileMedias.size();
        updateCanAddSizeView();
    }

    public void addFile(LocalMedia localMedia) {
        if (localMedia != null) {
            mFileMedias.add(localMedia);
            addFileView(localMedia);
            updateCanAddSize();
        }
    }

    public void removeAllFiles() {
        mFileMedias.clear();
        if (mIsCanAddFile) {
            mParentView.removeViews(0, mParentView.getChildCount() - 1);
        } else {
            mParentView.removeAllViews();
        }
    }

    public void addFile(List<LocalMedia> localMediaList) {
        if (localMediaList != null && localMediaList.size() > 0) {
            int mediaListSize = localMediaList.size();
            for (int i = 0; i < mediaListSize; i++) {
                LocalMedia localMedia = localMediaList.get(i);
                mFileMedias.add(localMedia);
                addFileView(localMedia);
            }
            updateCanAddSize();
        }
    }

    private void addFileView(LocalMedia localMedia) {
        View pictureView = LayoutInflater.from(mActivity.getApplicationContext()).
                inflate(R.layout.adapter_add_file_item, mParentView, false);
        pictureView.setTag(localMedia);
        if (localMedia.getPictureType().startsWith(PictureConfig.VIDEO)) {
            View videoIcon = pictureView.findViewById(R.id.complaint_file_video_img);
            videoIcon.setVisibility(View.VISIBLE);
        }
        final ImageView imageView = (ImageView) pictureView.findViewById(R.id.complaint_file_img);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(160, 160);
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(localMedia.getPath())
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.
                                        create(mActivity.getApplicationContext().getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(5);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
        int size = mParentView.getChildCount();
        View deleteView = pictureView.findViewById(R.id.complaint_file_delete);
        if (!mIsCanDelete) {
            deleteView.setVisibility(View.GONE);
        } else {
            deleteView.setVisibility(View.VISIBLE);
        }
        deleteView.setTag(localMedia);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = view.getTag();
                if (object instanceof LocalMedia) {
                    LocalMedia deleteMedia = (LocalMedia) object;
                    mFileMedias.remove(deleteMedia);
                    mParentView.removeView(mParentView.findViewWithTag(deleteMedia));
                    updateCanAddSize();
                }
            }
        });
        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null && mIsCanLook) {
                    Object object = view.getTag();
                    if (object instanceof LocalMedia) {
                        LocalMedia deleteMedia = (LocalMedia) object;
                        int position = mFileMedias.indexOf(deleteMedia);
                        mOnItemClickListener.onItemClick(position, getMediaPaths());
                    }
                }
            }
        });
        mParentView.addView(pictureView, size - 1);
    }

    public void updateCanAddSizeView() {
        if (!mIsCanAddFile) {
            return;
        }
        if (mCanAddSize == 0) {
            if (mFileMedias.size() == mMaxSize) {
                mParentView.removeView(mAddView);
            } else if (mFileMedias.size() < mMaxSize) {
                mAddView.setClickable(false);
            }
        } else {
            if (mParentView.getChildCount() == mFileMedias.size()) {
                mParentView.addView(mAddView);
            }
            mAddView.setClickable(true);
        }
    }

    public void setSelectFileType(int mSelectFileType) {
        this.mSelectFileType = mSelectFileType;
    }

    public void setMaxSize(int mMaxSize) {
        this.mMaxSize = mMaxSize;
        updateCanAddSize();
    }

    public void setIsCanLook(boolean isCanLook) {
        mIsCanLook = isCanLook;
    }

    public View getParentView() {
        return mParentView;
    }

    public List<LocalMedia> getFileMeidas() {
        return mFileMedias;
    }

    public void setIsCanDelete(boolean isCanDelete) {
        mIsCanDelete = isCanDelete;
    }

    public static void updateFileAddView(FileAddedView fileAddedView,
                                         List<String> thumbnailList, List<String> annexType) {
        if (thumbnailList == null || thumbnailList.size() == 0) {
            fileAddedView.getParentView().setVisibility(View.GONE);
            return;
        }
        fileAddedView.removeAllFiles();
        fileAddedView.getParentView().setVisibility(View.VISIBLE);
        List<LocalMedia> mediaList = new ArrayList<>();
        for (int i = 0; i < thumbnailList.size(); i++) {
            String buildPath = thumbnailList.get(i);
            LocalMedia localMedia = null;
            String type = null;
            if (i < annexType.size()) {
                type = annexType.get(i);
            }
            if (!"视频".equals(type)) {
                localMedia = new LocalMedia(buildPath, 0, FileAddedView.TYPE_PICTURE, "img/jpg");
            } else {
                localMedia = new LocalMedia(buildPath, 0, FileAddedView.TYPE_VIDEO, "video/mp4");
            }
            mediaList.add(localMedia);
        }
        fileAddedView.addFile(mediaList);
    }

    private List<String> getMediaPaths() {
        List<String> mediaPaths = new ArrayList<>();
        for (int i = 0; i < mFileMedias.size(); i++) {
            mediaPaths.add(mFileMedias.get(i).getPath());
        }
        return mediaPaths;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, List<String> files);
    }
}
