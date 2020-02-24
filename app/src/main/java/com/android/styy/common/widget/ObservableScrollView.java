package com.android.styy.common.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

public class ObservableScrollView extends NestedScrollView {

    private ScrollViewListener onScrollViewListener;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != onScrollViewListener) {
            onScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollViewListener(ScrollViewListener onScrollViewListener){
        this.onScrollViewListener = onScrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
