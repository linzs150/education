package com.one.education.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author laiyongyang
 * @date 2020-06-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class MyEditText extends AppCompatEditText {

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (editScroll(this)) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * EditText竖直方向能否够滚动
     * false：不能够滚动
     * true：能够滚动
     */
    private boolean editScroll(EditText editText) {
        int scrollY = editText.getScrollY();//计算滚动的距离
        int scrollRange = editText.getLayout().getHeight();//获取控件内容的总高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom(); //获取控件实际显示的高度
        int scrollDifference = scrollRange - scrollExtent;//获取控件内容总高度与实际显示高度的差值

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}