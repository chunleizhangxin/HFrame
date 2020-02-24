package com.android.styy.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.styy.R;
import com.android.styy.common.util.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LettersView extends View {

    String TAG = getClass().getSimpleName();

    public List<String> letters;
    private Paint mPaint;
    private int selectPosition = -1;
    private TextView mLetter;
    public void setLetter(TextView mLetter) {
        this.mLetter = mLetter;
    }
    public LettersView(Context context) {
        this(context,null);
    }
    public LettersView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public LettersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        letters = new ArrayList<>();
        for (int i = 65; i < 91; i++) {
            letters.add(String.format(Locale.CHINA,"%c",i));
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(DeviceUtils.sp2px(11));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = letters.size();
        for (int i = 0; i < size; i++) {
            float textWidth = mPaint.measureText(letters.get(i));
            int singleHeight = height / size;
            if (selectPosition == i){//被选中的
                mPaint.setColor(getResources().getColor(R.color.colorAccent));
            }else{
                mPaint.setColor(getResources().getColor(R.color.colorAccent));
            }
            canvas.drawText(letters.get(i),(width - textWidth)/2, singleHeight * (i + 1),mPaint);
            invalidate();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int measuredHeight = getMeasuredHeight();
        int singleHeight = measuredHeight / letters.size();
        int position = (int) (y / singleHeight);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                selectPosition = position;
                if (mLetter != null) {
                    mLetter.setVisibility(View.VISIBLE);
                    if (position < letters.size() && position >= 0){
                        String firstChar = letters.get(position);
                        mLetter.setText(firstChar);
                        onLettersListener.onItem(position,firstChar);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                selectPosition = -1;
                if (mLetter != null) {
                    mLetter.setVisibility(View.GONE);
                }
                break;
        }
        return true;
    }

    private LettersListener onLettersListener;

    public void setOnLettersListener(LettersListener onLettersListener){
        this.onLettersListener = onLettersListener;
    }
    public interface LettersListener{

        void onItem(int position,String firstChar);
    }
}
