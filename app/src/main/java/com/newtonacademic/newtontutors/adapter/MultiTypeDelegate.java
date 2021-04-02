package com.newtonacademic.newtontutors.adapter;

public interface MultiTypeDelegate<T> {

    /**
     * recycleview子项布局的res
     * @param data 参数
     * @return res
     */
    int getItemTypeLayoutRes(T data);
}
