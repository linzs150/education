package com.newtonacademic.newtontutors.classappointment;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;

/**
 * @author laiyongyang
 * @date 2020-06-22
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class TopSmoothScroller extends LinearSmoothScroller {
    TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

}
