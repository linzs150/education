package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.newtonacademic.newtontutors.commons.Utils;
import com.newtonacademic.newtontutors.R;

public class AutoAdjustRecylerView extends RecyclerView {
    private final String TAG = "AutoAdjustRecylerView";
    private Scroller mScroller = null;
    private int mLastx = 0;
    //用于设置自动平移时候的速度
    private float mPxPerMillsec = 0;

    public AutoAdjustRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initData(context);
    }

    public AutoAdjustRecylerView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initData(context);
    }

    public AutoAdjustRecylerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initData(context);
    }

    private void initData(Context context) {
        mScroller = new Scroller(context, t -> t);
    }

    public void setScroller(Scroller scroller) {
        if (mScroller != scroller) {
            mScroller = scroller;
        }
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        super.computeScroll();
        if (mScroller != null) {
            if (mScroller.computeScrollOffset())//如果mScroller没有调用startScroll，这里将会返回false。
            {
                scrollBy(mLastx - mScroller.getCurrX(), 0);
                mLastx = mScroller.getCurrX();
                //继续让系统重绘
                postInvalidate();
            }
        }
    }

    public float getPxPerMillsec() {
        return mPxPerMillsec;
    }

    public void setPxPerMillsec(float pxPerMillsec) {
        this.mPxPerMillsec = pxPerMillsec;
    }

    public boolean checkAutoAdjust(Context context, int srcPosition, int desPosition) {
        if (desPosition < 0) {
            return false;
        }

        int index = Utils.GetDimension(context, R.dimen.dp_116);
        if (desPosition > srcPosition) {
            mScroller.startScroll(srcPosition * index, 0, -index, 0, 200);
        } else {
            mScroller.startScroll(srcPosition * index, 0, index, 0, 200);
        }

        postInvalidate();
        return true;
//        int childcount = getChildCount();
//        //获取可视范围内的选项的头尾位置
//        int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
//        int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
//        if (position < mP>) {
//            //当前位置需要向右平移
//            leftScrollBy(position, firstvisiableposition);
//        } else if (position == (lastvisiableposition - 1) || position == lastvisiableposition) {
//            //当前位置需要向做平移
//            rightScrollBy(position, lastvisiableposition);
//        }
    }

    private void leftScrollBy(int position, int firstVisiableposition) {
        View leftChild = getChildAt(0);
        if (leftChild != null) {
            int leftx = leftChild.getLeft();
            int startleft = leftx;
            int endleft = position == firstVisiableposition ? leftChild.getWidth() : 0;
            autoAdjustScroll(startleft, endleft);
        }
    }


    private void rightScrollBy(int position, int lastVisiablePosition) {
        int childcount = getChildCount();
        View rightChild = getChildAt(childcount - 1);
        if (rightChild != null) {
            int rightx = rightChild.getRight();
            int dx = rightx - getWidth();
            int startright = dx;
            int endright = position == lastVisiablePosition ? -1 * rightChild.getWidth() : 0;
            autoAdjustScroll(startright, endright);
        }
    }

    /**
     * @param start 滑动起始位置
     * @param end   滑动结束位置
     */
    private void autoAdjustScroll(int start, int end) {
        int duration = 0;
        if (mPxPerMillsec != 0) {
            duration = (int) ((Math.abs(end - start) / mPxPerMillsec));
        }
        mLastx = start;
        mScroller.startScroll(start, 0, end - start, 0, duration);
        postInvalidate();
    }
}
