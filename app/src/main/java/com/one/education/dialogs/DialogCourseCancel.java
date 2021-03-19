package com.one.education.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.one.education.education.R;

public class DialogCourseCancel {
    private Dialog dialog;
    private View view;
    private Context mContext;

    private Button btn_save;
    private Button btn_cancel;

    public DialogCourseCancel(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.course_cancel_dialog, null);
        dialog = new Dialog(context, R.style.CustomDialog);
        initView();
    }


    private void initView() {

        btn_save = view.findViewById(R.id.btn_save);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        setCancelable(false);
        setCancelableOnTouchOutside(false);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentInterface.save(teacherId);
            }
        });
    }

    private long teacherId;

    public void setSave(long id) {
        this.teacherId = id;
    }

    public void setCancel(View.OnClickListener listener) {
        btn_cancel.setOnClickListener(listener);
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
        void save(long ee);
    }

}
