package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class EnhanceWebView extends WebView {
    private int scrollY;
    public EnhanceWebView(Context context) {
        super(context);
    }

    public EnhanceWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhanceWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollY = t;
    }

    public int getWebScrollY(){
        return scrollY;
    }
}
