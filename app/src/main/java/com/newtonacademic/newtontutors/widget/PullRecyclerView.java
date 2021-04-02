package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newtonacademic.newtontutors.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 */
public class PullRecyclerView extends LinearLayout {
    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    RecyclerView mRecyclerView;
    PtrFrameLayout mPtrFrameLayout;

    LoadMoreRecyclerViewContainer mLoadMoreContainer;
    private OnPullListener mListener;
    void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_pull_recyclerview, this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mPtrFrameLayout = findViewById(R.id.ptr_frame);
        mLoadMoreContainer = findViewById(R.id.load_more_container);
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
                return mRecyclerView.getChildCount() == 0 || mRecyclerView.getChildAt(0).getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (mListener != null) {
                    mListener.onRefresh(mRecyclerView);
                }
            }
        });

        //        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
        //            @Override
        //            public void onLoadNextPage(View view) {
        //                if (mListener != null) {
        //                    mListener.onLoadMore(mRecyclerView);
        //                }
        //            }
        //        });

        mLoadMoreContainer.useDefaultFooter();
        mLoadMoreContainer.setAutoLoadMore(true);
        mLoadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mListener.onLoadMore(mRecyclerView);
            }
        });
    }

    public HeaderAndFooterRecyclerViewAdapter getRealAdapter() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) {
            return null;
        }
        if (adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            return (HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter();
        }
        throw new IllegalStateException("Adapter 必须是HeaderAndFooterRecyclerViewAdapter");
    }


    public RecyclerView.Adapter getInnerAdapter() {
        if (getRealAdapter() != null) {
            return getRealAdapter().getInnerAdapter();
        }
        return null;
    }

    public void notifyDataSetChanged() {
        getRealAdapter().notifyDataSetChanged();
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }


    public void disableWhenHorizontalMove(boolean disable) {
        mPtrFrameLayout.disableWhenHorizontalMove(disable);

    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    public void finishLoad(boolean hasMore) {
        mLoadMoreContainer.loadMoreFinish(hasMore);
        mPtrFrameLayout.refreshComplete();
    }


    public void setHeaderView(View headerView) {
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(new HeaderAndFooterRecyclerViewAdapter(adapter));
    }


    public PtrFrameLayout getRefreshView() {
        return mPtrFrameLayout;
    }

    public void setRefreshHeader(View header) {
        mPtrFrameLayout.setHeaderView(header);
    }

    public void setOnPullListener(OnPullListener listener) {
        mListener = listener;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        mPtrFrameLayout.setPullToRefresh(pullToRefresh);
    }

    public void enableRefresh(boolean enable) {
        mPtrFrameLayout.setEnabled(enable);
    }

    public void enableLoadMore(boolean enable) {
        mLoadMoreContainer.enableLoadMore(enable);
    }

    public void autoRefresh() {
        mPtrFrameLayout.autoRefresh();
    }

    public interface OnPullListener {
        void onRefresh(RecyclerView recyclerView);

        void onLoadMore(RecyclerView recyclerView);
    }
}
