package com.android.styy.base;

import android.content.Context;
import android.view.LayoutInflater;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseRecyclerAdapter <VH extends BaseRecyclerViewHolder,T>
        extends DelegateAdapter.Adapter<VH> {

    protected String TAG = getClass().getSimpleName();

    protected LayoutInflater mLayoutInflater;

    protected List<T> mData;

    protected Context mContext;

    public BaseRecyclerAdapter(Context context){
        this(context,new LinkedList<T>());
    }
    public BaseRecyclerAdapter(Context context, List<T> data){
        mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }
    public void update(T data){
        mData.clear();
        if(null != data){
            mData.add(data);
        }
        notifyDataSetChanged();
    }
    public void update(List<T> data){
        mData.clear();
        if(null != data){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void loadMore(T data){
        if(null != data){
            mData.add(data);
        }
        notifyDataSetChanged();
    }
    public void loadMore(List<T> data){
        if(null != data){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void remove(T data){
        mData.remove(data);
        notifyDataSetChanged();
    }

    public void remove(List<T> datas){
        for(T data: datas){
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mData;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public int getItemCount() {
        if(null == mData){
            mData = new ArrayList<>();
        }
        return mData.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
