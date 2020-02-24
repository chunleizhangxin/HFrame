package com.android.styy.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.android.styy.R;

public class MessageOperateView extends ImageView {

    private int mCurrentType = -1;
    private int[] mIconRes = new int[]{
            R.mipmap.icon_message_item_emergency,
            R.mipmap.icon_message_item_warning,
            R.mipmap.icon_message_item_instruct,
            R.mipmap.icon_message_item_deal,
            R.mipmap.icon_message_item_deal
    };

    public MessageOperateView(Context context) {
        super(context);
    }

    public MessageOperateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageOperateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(mCurrentType > 0 && mCurrentType <= mIconRes.length) {
            setImageResource(mIconRes[mCurrentType - 1]);
        }
    }

    public int getCurrentType() {
        return mCurrentType;
    }

    public void setCurrentType(int type){
        mCurrentType = type;
    }
}
