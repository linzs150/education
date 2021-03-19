package com.one.education.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.education.education.R;

public class DialogStudentSave {
    private Dialog dialog;
    private View view;
    private Context mContext;

    private TextView btn_save;

    public DialogStudentSave(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.student_save_dialog, null);
        dialog = new Dialog(context, R.style.CustomDialog);
        initView();
    }


    private void initView() {

        btn_save = view.findViewById(R.id.btn_save);
        setCancelable(false);
        setCancelableOnTouchOutside(false);
    }

    public void setSave(View.OnClickListener listener) {
        btn_save.setOnClickListener(listener);
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


    public void setStudentListener(StudentInterface studentInterface) {
        this.studentInterface = studentInterface;
    }

    private StudentInterface studentInterface;

    public interface StudentInterface {
        void save(String ee);
    }

}
