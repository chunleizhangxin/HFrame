package com.android.styy.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

import com.android.styy.base.BaseRecyclerAdapter;
import com.android.styy.base.BaseRecyclerViewHolder;
import com.android.styy.common.glide.GlideUtil;

public abstract class ImgAdapter extends BaseRecyclerAdapter<ImgAdapter.ViewHolder,String> {


    public ImgAdapter(Context context) {
        super(context);
    }

    public ImgAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ImageView(mContext));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String data = mData.get(position);

        GlideUtil.displayUrl(holder.draweeView,data);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onClickListener){
                    onClickListener.onItem(position,mData);
                }
            }
        });
    }

    public abstract RecyclerView.LayoutParams getItemLayoutParams(ViewHolder holder, int position);

    public class ViewHolder extends BaseRecyclerViewHolder{

        ImageView draweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = (ImageView) itemView;

            draweeView.setLayoutParams(getItemLayoutParams(this,getAdapterPosition()));

        }
    }

    private ClickListener onClickListener;

    public void setOnClickListener(ClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface ClickListener{
        void onItem(int position,List<String> data);
    }
}
