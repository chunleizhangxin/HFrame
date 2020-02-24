package com.android.styy.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.styy.common.util.DeviceUtils;

public class ScoreCircleView extends View {

    Paint mBgPaint;

    Paint mCirclePaint;

    Paint mTextPaint;

    float percent;
    float radius;

    String msg;

    RectF containerRectf;
    RectF progressRectf;

    int strokeWidth = DeviceUtils.dp2px(5);

    public ScoreCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#ecefef"));
        mBgPaint.setStrokeWidth(strokeWidth);
        mBgPaint.setStyle(Paint.Style.STROKE);


        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.parseColor("#ffaa04"));
        mCirclePaint.setStrokeWidth(strokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#4a4e56"));
        mTextPaint.setTextSize(DeviceUtils.sp2px(13));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        containerRectf = new RectF();
        containerRectf.left = 0;
        containerRectf.top = 0;
        containerRectf.right = containerRectf.left + getMeasuredWidth();
        containerRectf.bottom = containerRectf.top + getMeasuredHeight();

        int width = getMeasuredWidth() / 2;
        int height = getMeasuredHeight() / 2;
        float cx = width;
        float cy = height;
        radius = Math.min(cx,cy) - strokeWidth;

        progressRectf = new RectF();
        progressRectf.left = containerRectf.centerX() - radius;
        progressRectf.top = containerRectf.centerY() - radius;
        progressRectf.right = progressRectf.left + radius * 2;
        progressRectf.bottom = progressRectf.top + radius * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(containerRectf.centerX(),containerRectf.centerY(),radius,mBgPaint);

        canvas.drawArc(progressRectf,-90, -( 360 * percent ),false,mCirclePaint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        int baseLineY = (int) (progressRectf.centerY() - top / 2 - bottom / 2);
        canvas.drawText(msg,progressRectf.centerX(),baseLineY,mTextPaint);
    }

    public void setPercent(float per){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,per)
                .setDuration(800);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ScoreCircleView.this.percent = Float.valueOf(
                                (Float) animation.getAnimatedValue()).floatValue();
                        invalidate();
                    }
                });

    }

    public void setMsg(String msg){
        this.msg = msg;
    }

}
