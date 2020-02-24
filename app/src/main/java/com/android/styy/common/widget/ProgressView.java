package com.android.styy.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.styy.common.util.DeviceUtils;

public class ProgressView extends View {

    String TAG = getClass().getSimpleName();

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    int borderWidth = DeviceUtils.dp2px(3);

    RectF roundRectf;

    Paint mCirclePaint;

    private void init(){

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(borderWidth);
    }


    public void setProgressColor(int color){
        mCirclePaint.setColor(color);//Color.parseColor("#00d3ba")
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int roundR = Math.min(width,height);

        roundRectf = new RectF();

        roundRectf.left = width - roundR + borderWidth;
        roundRectf.top = height - roundR + borderWidth;
        roundRectf.right = roundRectf.left + roundR - borderWidth * 2;
        roundRectf.bottom = roundRectf.top + roundR - borderWidth * 2;

    }

    private float percent;

    public void setPercent(float percent){
        this.percent = percent;
        postInvalidate();
    }

    private boolean isShowProgress;

    public void setShowProgress(boolean isShowProgress){
        this.isShowProgress = isShowProgress;
        this.percent = 0;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isShowProgress){
            canvas.drawArc(roundRectf,-90,360 * percent ,false,mCirclePaint);
        }
    }
}
