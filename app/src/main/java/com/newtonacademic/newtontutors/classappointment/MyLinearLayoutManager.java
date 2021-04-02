package com.newtonacademic.newtontutors.classappointment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * @author laiyongyang
 * @date 2020-06-21
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class MyLinearLayoutManager extends LinearLayoutManager {

    private boolean canScrollHorizontally = true;
    private boolean isScrollEnabled = true;

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setCanScrollHorizontally(boolean canScrollHorizontally) {
        this.canScrollHorizontally = canScrollHorizontally;
    }

    @Override
    public boolean canScrollHorizontally() {
        return canScrollHorizontally && super.canScrollHorizontally();
    }
}
