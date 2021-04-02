package com.newtonacademic.newtontutors.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.newtonacademic.newtontutors.R;


public class PushUpInDialog extends Dialog {
    private Object mTag;
    protected Context mContext;

    public PushUpInDialog(Context context, int layout) {
        this(context, layout, R.style.ActionSheet);
    }

    public PushUpInDialog(Context context, int layout, int themeResId) {
        super(context, themeResId);
        mContext = context;
        View view = getLayoutInflater().inflate(layout, null);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        view.setMinimumWidth(size.x);
        setContentView(view);

        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected String getString(int string_id) {
        return getContext().getString(string_id);
    }

    public final void SetTag(Object tag) {
        mTag = tag;
    }

    public final Object GetTag() {
        return mTag;
    }
}
