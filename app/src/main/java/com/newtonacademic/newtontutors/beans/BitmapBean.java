package com.newtonacademic.newtontutors.beans;

import android.graphics.Bitmap;

import com.newtonacademic.newtontutors.adapters.VideoAdapter;

public class BitmapBean {

    private Bitmap bitmap;
    private VideoAdapter.HolderView holderView;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public VideoAdapter.HolderView getHolderView() {
        return holderView;
    }

    public void setHolderView(VideoAdapter.HolderView holderView) {
        this.holderView = holderView;
    }
}
