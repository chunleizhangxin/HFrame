package com.android.styy.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.styy.R;
import com.android.styy.common.util.DeviceUtils;

public class PlayControllerLayout extends LinearLayout{

    ProgressView progressView;

    public PlayControllerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init( AttributeSet attrs){

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PlayControllerLayout);

        int drawableRes = array.getResourceId(R.styleable.PlayControllerLayout_drawableRes,0);

        int progressColor = array.getColor(R.styleable.PlayControllerLayout_progressColor,Color.parseColor("#019793"));

        LayoutInflater.from(getContext()).inflate(R.layout.view_wdiget_playconller,this,true);

        ImageView iconContrller = findViewById(R.id.icon_controller);

        iconContrller.setImageResource(drawableRes);

        progressView = findViewById(R.id.progress_view);

        progressView.setProgressColor(progressColor);

        array.recycle();
    }


    public void setPercent(float percent){
        progressView.setPercent(percent);
    }



    public void setShowProgress(boolean isShowProgress){
        progressView.setShowProgress(isShowProgress);
    }


}
