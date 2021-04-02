package com.newtonacademic.newtontutors.adapter;

import android.view.View;

public interface OnItemLongClickListener<T>
{
    void onItemLongClick(BaseRecyclerViewAdapter<T> adapter, View view, int position);
}
