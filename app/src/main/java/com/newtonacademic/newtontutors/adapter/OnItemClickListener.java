package com.newtonacademic.newtontutors.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(BaseRecyclerViewAdapter<T> adapter, View view, int position);
}
