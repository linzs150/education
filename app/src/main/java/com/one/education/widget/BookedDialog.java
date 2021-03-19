package com.one.education.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.beans.TaughtSubjects;
import com.one.education.beans.TeacherBean;
import com.one.education.education.R;

import java.util.List;

public class BookedDialog extends Center1Dialog {

    private TextView cancel;
    private Click click;
    private LinearLayout subject_layout;
    private Context mCtx;

    public BookedDialog(Context context) {
        super(context, R.layout.book_selected_dialog);
        setCanceledOnTouchOutside(false);
        mCtx = context;
        initView();
        setData();
//        findViewById(R.id.let_ok).setOnClickListener(v -> dismiss());
    }

    public void setListener(Click listener) {
        this.click = listener;
    }

    private void initView() {
        subject_layout = findViewById(R.id.subject_layout);
        cancel = findViewById(R.id.cancel);
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
                View itemView = View.inflate(mCtx, R.layout.book_selected_dialog_item, null);
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
