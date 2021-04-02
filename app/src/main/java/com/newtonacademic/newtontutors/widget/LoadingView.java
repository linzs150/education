package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.newtonacademic.newtontutors.commons.Tool;
import com.newtonacademic.newtontutors.R;


/**
 *
 *
 */
public class LoadingView extends FrameLayout {
    ImageView mIconLoading;
    FrameLayout loadingView;
    private View mView;
    private Context mCtx;

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public LoadingView(Context context) {
        super(context);
        this.mCtx = context;
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void initView() {
        mView = LayoutInflater.from(mCtx).inflate(R.layout.view_loading, this,
                true);
        mIconLoading = (ImageView) mView.findViewById(R.id.loading);
        loadingView = (FrameLayout) mView.findViewById(R.id.loading_rl);
        ((AnimationDrawable) mIconLoading.getDrawable()).start();
    }

    public void setBackground(int resId) {
        loadingView.setBackgroundResource(resId);
    }

    public void hide() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    public void show(ViewGroup viewParent) {
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
        viewParent.addView(this, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

}
