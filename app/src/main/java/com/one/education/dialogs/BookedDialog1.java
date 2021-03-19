package com.one.education.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.beans.TaughtSubjects;
import com.one.education.education.R;
import com.one.education.widget.Center1Dialog;

import java.util.List;

public class BookedDialog1 {

    private TextView cancel;
    private Click click;
    private LinearLayout subject_layout;
    private Dialog dialog;
    private View view;
    private Context mContext;

    public BookedDialog1(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.book_selected_dialog, null);
        dialog = new Dialog(context, R.style.CustomDialog);
        setCancelable(false);
        setCancelableOnTouchOutside(false);
        initView();
        setData();
//        findViewById(R.id.let_ok).setOnClickListener(v -> dismiss());
    }

    public void setCancelableOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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


    public void setListener(Click listener) {
        this.click = listener;
    }

    private void initView() {
        subject_layout = view.findViewById(R.id.subject_layout);
        cancel = view.findViewById(R.id.cancel);
    }

    private void setData() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setSubjectName(List<TaughtSubjects> subjects) {
        if (subjects != null && subjects.size() > 0) {
            if (subject_layout != null) {
                subject_layout.removeAllViews();
            }
            for (int i = 0; i < subjects.size(); i++) {
                View itemView = View.inflate(mContext, R.layout.book_selected_dialog_item, null);
                TextView textView = itemView.findViewById(R.id.ib);
                textView.setText(subjects.get(i).getCourseName());
                itemView.setTag(i);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (Integer) v.getTag();
                        click.setBook(subjects.get(pos));
                    }
                });
                subject_layout.addView(itemView);
            }
        }
    }

    public interface Click {
        void setBook(TaughtSubjects subject);
    }


}
