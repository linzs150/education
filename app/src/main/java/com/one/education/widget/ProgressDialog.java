package com.one.education.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.education.commons.Tool;
import com.one.education.education.R;

import java.util.ArrayList;


public class ProgressDialog extends LinearLayout {

    TextView tvMsg;

    private View view;
    private Context mCtx;


    public ProgressDialog(Context context) {
        this(context, null);

    }

    public ProgressDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;
        init();

    }

    private void init() {
        view = LayoutInflater.from(mCtx).inflate(R.layout.view_progress_dialog, this,
                true);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
    }

    public void setTipMsg(String msg) {
//        tvMsg.setText(TextUtils.isEmpty(msg) ? mCtx.getString(R.string.loadinga): msg);
        tvMsg.setText(msg);
    }

    public void setTipMsg(@StringRes int msgRes) {
        tvMsg.setText(msgRes);
    }


    public void removeFromView() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }


    public void addToView(ViewGroup viewParent) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            if (!Tool.equals(viewGroup, viewParent)) {
                viewGroup.removeView(this);
            }
        }
        if (viewGroup == null) {
            viewGroup = viewParent;
        }
        ViewGroup.LayoutParams params = null;
        if (viewParent instanceof LinearLayout) {
            RelativeLayout relate = new RelativeLayout(viewGroup.getContext());
            LinearLayout newLinear = new LinearLayout(viewGroup.getContext());
            newLinear.setLayoutParams(viewGroup.getLayoutParams());
            newLinear.setOrientation(((LinearLayout) viewParent).getOrientation());
            ArrayList<View> viewHolder = new ArrayList<>();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewHolder.add(viewGroup.getChildAt(i));
            }
            viewGroup.removeAllViewsInLayout();
            for (View view : viewHolder) {
                newLinear.addView(view);
            }
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_IN_PARENT);
            relate.addView(newLinear, params);
            relate.addView(this, params);
            viewGroup.addView(relate);
            return;
        } else if (viewParent instanceof RelativeLayout) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (viewParent instanceof FrameLayout) {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((FrameLayout.LayoutParams) params).gravity = Gravity.CENTER;
        }
        viewParent.addView(this, params);
    }


    public boolean isShowing() {
        return View.VISIBLE == this.getVisibility() && getParent() != null;
    }
}
