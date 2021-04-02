package com.newtonacademic.newtontutors.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.newtonacademic.newtontutors.R;

public class DialogSettingLogout {
    private Dialog dialog;
    private View view;
    private Context mContext;
    private TextView tv_exit;
    private TextView tv_cancel;

    public DialogSettingLogout(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.setting_logout_dialog, null);
        dialog = new Dialog(mContext, R.style.SettingCustomDialog);
        initView();
    }


    private void initView() {
        tv_exit = view.findViewById(R.id.tv_exit);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        setCancelable(false);
        setCancelableOnTouchOutside(false);
    }

    public void setExit(View.OnClickListener listener) {
        tv_exit.setOnClickListener(listener);
    }

    public void setCancel(View.OnClickListener listener) {
        tv_cancel.setOnClickListener(listener);
    }


    public void setCancelableOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (dialog != null)
            return dialog.isShowing();
        return false;
    }

    public void setCancelable(boolean flag) {
        dialog.setCancelable(flag);
    }


}
