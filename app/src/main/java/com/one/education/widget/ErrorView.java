package com.one.education.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.one.education.commons.Tool;
import com.one.education.education.R;

/**
 */
public class ErrorView extends FrameLayout {

    private Context mCtx;
    private View mView;

    public ErrorView(Context context) {
        super(context);
        this.mCtx = context;
        initView();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;
        initView();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCtx = context;
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(mCtx).inflate(R.layout.view_error, this,
                true);
    }

    public void hide() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    public void show(ViewGroup viewParent) {
        if (viewParent == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            if (!Tool.equals(viewGroup, viewParent)) {
                viewGroup.removeView(this);
            }
        }
        int index = -1;
        if (viewParent instanceof LinearLayout) {
            index = 0;
        }
        viewParent.addView(this, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
