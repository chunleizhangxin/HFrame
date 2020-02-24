package com.android.styy.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private int lastPosition;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
