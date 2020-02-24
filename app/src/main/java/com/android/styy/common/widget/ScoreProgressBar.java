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

public class ScoreProgressBar extends View {

    Paint mProgressPaint;
    Paint mBgPaint;

    RectF bgRectf;

    float percent;

    public ScoreProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(Color.parseColor("#feaa00"));
        mProgressPaint.setStrokeWidth(DeviceUtils.dp2px(3));
        mProgressPaint.setStyle(Paint.Style.FILL);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#e3e3e3"));
        mBgPaint.setStrokeWidth(DeviceUtils.dp2px(3));
        mBgPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        bgRectf = new RectF();
        bgRectf.left = 0;
        bgRectf.top = 0;
        bgRectf.right = bgRectf.left + getMeasuredWidth();
        bgRectf.bottom = bgRectf.top + getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF();
        rectF.left = bgRectf.left;
        rectF.top = bgRectf.top;
        rectF.right = bgRectf.right * percent;
        rectF.bottom = bgRectf.bottom;

        canvas.drawRoundRect(bgRectf,180,180,mBgPaint);
        canvas.drawRoundRect(rectF,180,180,mProgressPaint);
    }

    public void setPercent(float per){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,per)
                .setDuration(800);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ScoreProgressBar.this.percent = Float.valueOf(
                        (Float) animation.getAnimatedValue()).floatValue();
                invalidate();
            }
        });

    }
}
