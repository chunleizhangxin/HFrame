package com.android.styy.common.widget;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import com.android.styy.R;

public class MarqueeView extends ViewFlipper {

    private int interval = 1500;
    private int animDuration = 1000;


    private int inAnimResId;
    private int outAnimResId;

    private int position;
    private List notices = new ArrayList<>();
    private MarqueeListener onMarqueeListener;

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        inAnimResId = R.anim.anim_bottom_in;
        outAnimResId = R.anim.anim_top_out;
        setFlipInterval(interval);
    }

    /**
     * 根据字符串列表，启动翻页公告
     *
     */
    public void startWithList(List data) {
        notices.clear();
        if(null != data){
            notices.addAll(data);
        }
        start();
    }

    private boolean start() {
        removeAllViews();
        clearAnimation();

        if(notices.isEmpty()){
            return false;
        }
        position = 0;
        addView(createChildView(notices.get(position)));

        if (notices.size() > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResId);
            startFlipping();
        }

        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    position++;
                    if (position >= notices.size()) {
                        position = 0;
                    }
                    View view = createChildView(notices.get(position));
                    if (view.getParent() == null) {
                        addView(view);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return true;
    }

    private View createChildView(final Object obj) {
        View view =  getChildAt((getDisplayedChild() + 1) % 3);
        if (view == null) {
            view = onMarqueeListener.onCreateView(position,obj);
        }
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onMarqueeListener.onItemClick(position,obj,v);
            }
        });
        view.setTag(position);
        onMarqueeListener.bindView(position,obj,view);
        return view;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List getNotices() {
        return notices;
    }

    public void setOnMarqueeListener(MarqueeListener onMarqueeListener) {
        this.onMarqueeListener = onMarqueeListener;
    }

    public interface MarqueeListener {

        View onCreateView(int position,Object data);

        void bindView(int position,Object data,View v);

        void onItemClick(int position,Object data, View v);
    }

    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        setOutAnimation(outAnim);
    }

}
