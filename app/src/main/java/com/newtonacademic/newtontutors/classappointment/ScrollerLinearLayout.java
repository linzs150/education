package com.newtonacademic.newtontutors.classappointment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerLinearLayout extends LinearLayout {
    private final Scroller mScroller;

    public ScrollerLinearLayout(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public ScrollerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public ScrollerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    /**
     * 自定义方法：平滑移动本布局<br/>
     * 用于按钮点击时执行滑动
     *
     * @param dx       x轴移动距离
     * @param duration 时长 cuiweiyou.com
     */
    public void doScrollTo(int dx, int duration) {
        // 开始滑动（x轴出发点，y轴出发点，x轴距离，y轴距离，时长）
        mScroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY(), duration);
        // 刷新布局
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 当滑动执行完毕
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX(); // 本布局停止后的位置
            int oldY = getScrollY();

            int x = mScroller.getCurrX(); // 获取Scroller停止后当前水平位置
            int y = mScroller.getCurrY();

            if (oldX != x || oldY != y) {
                // 自身scrollTo的参数是偏移量！！！
                scrollTo(x, y); // 将View中内容滑动到相应的位置,参考的坐标系原点为parent View的左上角
            }

            invalidate();
        }
    }
}
