package com.newtonacademic.newtontutors.widget.smartrefresh.layout.listener;

import android.support.annotation.NonNull;

import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.RefreshLayout;

/**
 * 加载更多监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
