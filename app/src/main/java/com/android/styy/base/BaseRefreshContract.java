package com.android.styy.base;

import java.util.List;

import com.android.styy.common.widget.refreshlayout.RefreshLayout;

public interface BaseRefreshContract {

    abstract class BaseRefreshPresenter<T extends BaseRefreshView> extends BasePresenter<T> implements RefreshLayout.RefreshListener{

        protected int pageNo;

        @Override
        protected void onAttach() {
            super.onAttach();
            mDependView.onRefreshPrepare(this);
        }

        @Override
        public void onRefresh(RefreshLayout layout) {
            pageNo = 1;
            loadData(true,layout);
        }

        @Override
        public void onLoadMore(RefreshLayout layout) {
            pageNo += 1;
            loadData(false,layout);
        }


        protected boolean isCanLoadMore(int total){
            return super.isCanLoadMore(total,pageNo);
        }

        public abstract void loadData(final boolean isRefresh, final RefreshLayout layout);
    }

    interface BaseRefreshView<T extends BaseRefreshPresenter,D> extends BaseDependView<T> {

        void onRefreshPrepare(RefreshLayout.RefreshListener listener);

        void onUpdate(List<D> data);

        void onLoadMore(List<D> data);
    }
}
