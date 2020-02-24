package com.android.styy.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.styy.R;

public class JellyLayout extends LinearLayout implements NestedScrollingParent {

    /**
     * 指示器 view
     */
    View indicatorView;


    /**
     * 下方的 view
     */
    View bottomView;

    /**
     * 首先加载的下标
     */
    int firstLoadViewIndex;

    /**
     * 自动滚动动画时长
     */
    int autoScrollAnimaDuration;

    /**
     * 是否在自动滚动
     */
    boolean isAutoScroll;


    NestedScrollingParentHelper mParentHelper;

    public JellyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttr(attrs);
    }

    private void initView(){

        setOrientation(LinearLayout.VERTICAL);

        mParentHelper = new NestedScrollingParentHelper(this);
    }

    private void initAttr(AttributeSet attrs){

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.JellyLayout);

        firstLoadViewIndex = array.getInt(R.styleable.JellyLayout_firstLoadViewIndex,0);

        autoScrollAnimaDuration = array.getInteger(R.styleable.JellyLayout_autoScrollAnimaDuration,400);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        int adjustHeight = height * (getChildCount() - 1);

        for(int i = 0 ; i < getChildCount() ; i ++){

            View itemView = getChildAt(i);

            if(itemView != indicatorView){
                measureChildren(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
            }else{
                measureChildren(widthMeasureSpec,heightMeasureSpec);
            }
        }

        if(indicatorView.getVisibility() == VISIBLE){
            adjustHeight += indicatorView.getHeight();
        }

        setMeasuredDimension(width,adjustHeight);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        scrollTo(0,getInitScrollY());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if( getChildCount() != 2){
            throw new RuntimeException("JellyLayout 只支持两个布局");
        }

        indicatorView = getChildAt(0);

        bottomView = getChildAt(1);

    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onStopNestedScroll(View child) {
        mParentHelper.onStopNestedScroll(child);

        if(canScrollNext()){

            if(null != onJellyLayoutListener){
                onJellyLayoutListener.onNext();
            }
        }
        onRestScroll();
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);

        if(dy < 0){
            onAutoScroll(dy);
            consumed[1] = dy;
        }
    }


    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if(dyConsumed >= 0 && getScrollY() < getInitScrollY()){

            onAutoScroll(dyConsumed);

        }


    }

    /**
     * 能否滚动
     * @return
     */
    private boolean canScroll(float distance){
        distance = getScrollY() + distance;
        return distance >= 0 && distance <= getHeight();
    }

    /**
     * 能否滚动下一页
     * @return
     */
    private boolean canScrollNext(){
        return getInitScrollY() - getScrollY() >= indicatorView.getHeight() / 2;
    }

    /**
     * 滚动是否最大值了
     */
    private boolean interceptScrollNext(float distance){
        return getInitScrollY() - getScrollY() - distance >= indicatorView.getHeight();
    }

    /**
     * 初始化高度
     * @return
     */
    private int getInitScrollY(){

        int scrollY = 0;

//        if(firstLoadViewIndex == 0){
//            scrollY = topView.getTop();
//        }

        if(firstLoadViewIndex == 0){
            scrollY = indicatorView.getTop();
        }

        if(firstLoadViewIndex == 1){
            scrollY = bottomView.getTop();
        }

        return scrollY;
    }


    /**
     * 自动滚动
     * @param distanceY
     * @return
     */
    private boolean onAutoScroll(float distanceY){

        System.out.println("distanceY = " + distanceY);

        // 是否在自动滚动
        if(isAutoScroll){
            return true;
        }
        // 到了顶部 ，或者底部
        if(!canScroll(distanceY)){
            return false;
        }

        // 是否需要自动滚动了
        if(!interceptScrollNext(distanceY)){
            scrollBy(0, (int) distanceY);
        }

        boolean canScrollNext = canScrollNext();

        if(null != onJellyLayoutListener){
            onJellyLayoutListener.onChange(canScrollNext,getInitScrollY() - getScrollY());
        }

        return false;
    }

    /**
     * 重置滚动
     * @return
     */
    public boolean onRestScroll(){
        animaScroll(getScrollY(),getInitScrollY());
        return true;
    }

    ValueAnimator valueAnimator;

    private void animaScroll(int start, int end){

        if(null != valueAnimator){
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofInt(start,end)
                .setDuration(autoScrollAnimaDuration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo(0, (Integer) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAutoScroll = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAutoScroll = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAutoScroll = true;
            }
        });
        valueAnimator.start();
    }

    private JellyLayoutListener onJellyLayoutListener;

    public void setOnJellyLayoutListener(JellyLayoutListener onJellyLayoutListener){
        this.onJellyLayoutListener = onJellyLayoutListener;
    }

    public interface JellyLayoutListener{

        void onChange(boolean canScroll,int scrollY);

        void onNext();

    }
}
