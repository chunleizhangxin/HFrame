package com.android.styy.common.widget.refreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import com.android.styy.R;
import com.android.styy.common.widget.refreshlayout.layoutManager.WrapContentLinearManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class RefreshLayout extends FrameLayout implements NestedScrollingChild {

    RecyclerView recyclerView;

    SwipeToLoadLayout swipeToLoadLayout;

    View mSwipeHeaderView;

    View mErrorView;

    ImageView iconError;

    TextView tvError;

    // 刷新触摸 Y 坐标
    float swipeToLoadTouchY;

    // 刷新最终高度
    float refresh_final_offset;

    // 滚动消耗
    int[] mScrollConsumed = new int[2];

    // NestedScrollingChildHelper
    NestedScrollingChildHelper mNestedScrollingChildHelper;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroll(attrs);
    }

    public void initScroll(AttributeSet attrs){

        LayoutInflater.from(getContext()).inflate(
                R.layout.layout_widget_refresh_google_style,this,true);

        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);

        mSwipeHeaderView = findViewById(com.aspsine.swipetoloadlayout.R.id.swipe_refresh_header);

        recyclerView = findViewById(R.id.swipe_target);

        final WrapContentLinearManager wrapContentLinearManager = new WrapContentLinearManager(getContext());

        setLayoutManager(wrapContentLinearManager);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
        boolean refreshEnable = array.getBoolean(R.styleable.RefreshLayout_refreshEnabled,true);
        boolean loadMoreEnable = array.getBoolean(R.styleable.RefreshLayout_loadMoreEnabled,true);

        array.recycle();

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(null != onRefreshListener){
                    onRefreshListener.onRefresh(RefreshLayout.this);
                }
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(null != onRefreshListener){
                    onRefreshListener.onLoadMore(RefreshLayout.this);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        setLoadMoreEnabled(loadMoreEnable);
        setRefreshEnabled(refreshEnable);

        mErrorView = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_widget_error,this,false);

        iconError = mErrorView.findViewById(R.id.icon_error);

        tvError = mErrorView.findViewById(R.id.tv_error);

        mErrorView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);

        recyclerView.setNestedScrollingEnabled(false);

        setNestedScrollingEnabled(true);

        refresh_final_offset = getResources().getDimension(R.dimen.refresh_header_height_google) - getResources().getDimension(R.dimen.refresh_final_offset_google);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                swipeToLoadTouchY = (int) (x + 0.5f);
                swipeToLoadTouchY = (int) (y + 0.5f);
            }
            break;
            case MotionEvent.ACTION_MOVE: {

                int dx = (int) (swipeToLoadTouchY - x);
                int dy = (int) (swipeToLoadTouchY - y);

                if(canDispatchScorll()){
                    dispatchNestedPreScroll(dx, dy, mScrollConsumed, null);
                }

                dispatchNestedScroll(dx,dy,0,0,null);

                swipeToLoadTouchY = (int) x;
                swipeToLoadTouchY = (int) y;
            }
            break;
            case MotionEvent.ACTION_UP: {
                stopNestedScroll();
            }
            break;
        }

        return super.onInterceptTouchEvent(event);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView.LayoutManager getLayoutManager(){
        return recyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    public void firstRefresh(){
        setRefreshing(true);
        if(null != onRefreshListener){
            onRefreshListener.onRefresh(this);
        }
    }

    public void setRefreshing(boolean refreshing){
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    public void refreshAndLoadMoreStop(){
        if(swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }
        if(swipeToLoadLayout.isLoadingMore()){
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    public void setLoadMoreEnabled(boolean loadMoreEnable){
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    public void setRefreshEnabled(boolean refreshEnable){
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
    }

    public boolean isRefreshing(){
        return swipeToLoadLayout.isRefreshing();
    }

    public boolean isLoadingMore(){
        return swipeToLoadLayout.isLoadingMore();
    }

    public void cancelRefresh(){

        try {

            Class clazz = SwipeToLoadLayout.class;

            Method onActivePointerUpMethod = clazz.getDeclaredMethod("scrollSwipingToRefreshToDefault");

            onActivePointerUpMethod.setAccessible(true);

            onActivePointerUpMethod.invoke(swipeToLoadLayout);

            Field mStatusField = clazz.getDeclaredField("mStatus");

            mStatusField.setAccessible(true);

            mStatusField.set(swipeToLoadLayout,-1);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onShowEmptyView(String txt){
        onShowErrorView(R.mipmap.icon_empty_error,txt);
    }

    public void onHideErrorView(){
        if(null == mErrorView){
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }
    }

    private void onShowErrorView(int imgRes,String txt){
        iconError.setImageResource(imgRes);
        tvError.setText(txt);

        ViewGroup viewGroup = (ViewGroup) mErrorView.getParent();

        if(null != viewGroup){
            viewGroup.removeView(mErrorView);
        }

        addView(mErrorView);
    }



    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return true;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    // 是否需要派发滚动事件
    private boolean canDispatchScorll(){

        try {

            int top = mSwipeHeaderView.getTop();

            return top >= refresh_final_offset;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public float getRefreshOffset(){
        return refresh_final_offset;
    }

    RefreshListener onRefreshListener;

    public void setOnRefreshListener(RefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
    }

    public interface RefreshListener{

        void onRefresh(RefreshLayout layout);

        void onLoadMore(RefreshLayout layout);
    }
}
