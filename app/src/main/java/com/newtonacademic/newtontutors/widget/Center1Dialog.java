package com.newtonacademic.newtontutors.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.newtonacademic.newtontutors.commons.Utils;
import com.newtonacademic.newtontutors.R;

public class Center1Dialog extends Dialog {
    private Object mTag;

    public Center1Dialog(Context context, int layout) {
        super(context, R.style.MyDialog1);
        View view = getLayoutInflater().inflate(layout, null);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        @SuppressWarnings("deprecation")
        int padding = (int) Utils.GetDimension(context, R.dimen.dp_30);
        view.setMinimumWidth(wm.getDefaultDisplay().getWidth() - padding * 2);
        setContentView(view);

        //设置弹窗居中
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        onWindowAttributesChanged(lp);
    }

    public final void SetTag(Object tag) {
        mTag = tag;
    }

    public final Object GetTag() {
        return mTag;
    }
}
