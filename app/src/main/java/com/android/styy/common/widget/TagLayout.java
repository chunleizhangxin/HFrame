package com.android.styy.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.android.styy.R;
import com.android.styy.common.util.TextUtil;

public class TagLayout extends LinearLayout {

    private List<String> tags = new ArrayList<>();

    float marginLeft;

    float contentSplitMarginLeft;
    float contentSplitMarginRight;

    int textColor;
    float textSize;

    String contentSplit;
    int contentSplitTxtColor;

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.TagLayout);
        marginLeft = typedArray.getDimension(R.styleable.TagLayout_content_marginLeft,0);
        textColor = typedArray.getColor(R.styleable.TagLayout_content_textClor, Color.parseColor("#282828"));
        textSize = typedArray.getDimension(R.styleable.TagLayout_content_textSize,13);
        contentSplit = typedArray.getString(R.styleable.TagLayout_content_split);
        contentSplitMarginLeft = typedArray.getDimension(R.styleable.TagLayout_content_split_left,0);
        contentSplitMarginRight = typedArray.getDimension(R.styleable.TagLayout_content_split_right,0);
        contentSplitTxtColor = typedArray.getColor(R.styleable.TagLayout_content_split_textColor,Color.TRANSPARENT);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void update(List<String> data){
        tags.clear();
        if(null == data){
            return;
        }
        removeAllViews();
        tags.addAll(data);
        notifyLayout();
    }

    private void notifyLayout(){

        for(int i = 0 ; i < tags.size() ; i ++){
            String tag = tags.get(i);
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = (int) marginLeft;
            tv.setLayoutParams(lp);
            tv.setTextColor(textColor);
            tv.setText(tag);
            tv.setTextSize(textSize);
            addView(tv);

            if(!TextUtil.isEmpty(contentSplit) && i < tags.size() - 1){
                TextView tvSplit = new TextView(getContext());
                tvSplit.setText(contentSplit);
                tvSplit.setTextColor(contentSplitTxtColor);
                LayoutParams splitLp = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                splitLp.leftMargin = (int) contentSplitMarginLeft;
                splitLp.rightMargin = (int) contentSplitMarginRight;

                tvSplit.setLayoutParams(splitLp);
                addView(tvSplit);
            }
        }
    }
}
