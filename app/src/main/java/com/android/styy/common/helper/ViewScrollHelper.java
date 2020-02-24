package com.android.styy.common.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.styy.common.widget.ObservableScrollView;


public class ViewScrollHelper {

    String TAG = getClass().getSimpleName();

    private View scrollView;
    private View offSetView;
    private float totalOffsetX;
    private float totalOffsetY;
    private int offSetViewWidth;
    private int offSetViewHeight;


    private ViewScrollHelper(View scrollView, View offSetView){
        this.scrollView = scrollView;
        this.offSetView = offSetView;
        attacheRecyclerView();
    }
    public static ViewScrollHelper create(View scrollView, View offSetView){
        return new ViewScrollHelper(scrollView,offSetView);
    }

    private void attacheRecyclerView(){
        if(scrollView instanceof RecyclerView){

            RecyclerView recyclerView = (RecyclerView) scrollView;

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalOffsetX += dx;

                    totalOffsetY += dy;

                    onScroll(totalOffsetX,totalOffsetY);
                }
            });
        }
        if(scrollView instanceof ObservableScrollView){

            ObservableScrollView observableScrollView = (ObservableScrollView) scrollView;
            observableScrollView.setOnScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                @Override
                public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                    onScroll(x,y);
                }
            });
        }
    }

    private void onScroll(float dx,float dy){

        offSetViewWidth = offSetView.getWidth();

        offSetViewHeight = offSetView.getHeight();

        float percentX = dx / offSetViewWidth;

        float percentY = dy / offSetViewHeight;

        if(null != onRecyViewOffsetScrollListener){

            onRecyViewOffsetScrollListener.onScrollX(percentX,totalOffsetX,dx);

            onRecyViewOffsetScrollListener.onScrollY(percentY,totalOffsetY,dy);
        }

    }

    private RecyViewOffsetScrollListener onRecyViewOffsetScrollListener;

    public void setOnRecyViewOffsetScrollListener(RecyViewOffsetScrollListener onRecyViewOffsetScrollListener){
        this.onRecyViewOffsetScrollListener = onRecyViewOffsetScrollListener;
    }

    public interface RecyViewOffsetScrollListener{

        void onScrollX(float percent,float totalOffset, float dx);

        void onScrollY(float percent,float totalOffset, float dy);

    }
}
