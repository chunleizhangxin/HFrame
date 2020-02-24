package com.android.styy.common.widget;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NoScrollViewPager extends ViewPager {

    String TAG = getClass().getSimpleName();

    Method scrollToItem;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            scrollToItem = getClass().getSuperclass().getDeclaredMethod(
                    "scrollToItem",int.class,boolean.class,int.class,boolean.class);
            scrollToItem.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(interceptTouch){
            srcollCurrent();
            return false;
        }
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            touchX = ev.getX();
            touchY = ev.getY();
        }
        if(ev.getAction() == MotionEvent.ACTION_UP){
            touchX = 0;
            touchY = 0;
        }
        return super.onInterceptTouchEvent(ev);
    }

    float touchX;
    float touchY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(interceptTouch){
            srcollCurrent();
            return false;
        }
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(touchX == ev.getX() && touchY == ev.getY()){
                return false;
            }
        }
        if(ev.getAction() == MotionEvent.ACTION_UP){
            touchX = 0;
            touchY = 0;
        }
        return super.onTouchEvent(ev);
    }


    private void srcollCurrent(){
        if(null != scrollToItem){
            try {
                scrollToItem.invoke(this,getCurrentItem(),false,0,false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean interceptTouch ;

    public void setInterceptTouch(boolean interceptTouch){
        this.interceptTouch = interceptTouch;
    }
}
