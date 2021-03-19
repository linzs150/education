package com.one.education.utils.toast;

import android.content.Context;

public class ToastWhiteStyle extends ToastBlackStyle {

    public ToastWhiteStyle(Context context) {
        super(context);
    }

    @Override
    public int getBackgroundColor() {
        return 0XFFEAEAEA;
    }

    @Override
    public int getTextColor() {
        return 0XBB000000;
    }
}