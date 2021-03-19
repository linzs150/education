package com.one.education.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.adapters.StudentItemAdapter;
import com.one.education.beans.SubjectResponse;
import com.one.education.education.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

public class CourseLinearLayout1 extends LinearLayout {

    private Context mCtx;
    private RecyclerView course_recyclerview;
    private GridLayoutManager manager;
    private StudentItemAdapter courseItemAdapter;
    private List<SubjectResponse.Subject> courseString = new ArrayList<>();
    private TextView tv_course;


    public CourseLinearLayout1(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context ctx) {

        this.mCtx = ctx;
        LayoutInflater.from(ctx).inflate(R.layout.course_item, this);
        initItem();

    }

    private void initItem() {
        course_recyclerview = findViewById(R.id.course_recyclerview);
        tv_course = findViewById(R.id.tv_course);
        initRecyclerView();
    }

    private void initRecyclerView() {
        manager = new GridLayoutManager(mCtx, 2) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        course_recyclerview.setLayoutManager(manager);
        //        courseString.add("AAAAAAAA");
        //        courseString.add("BBBBBAAAAA");
        //        courseString.add("CCCCCCC");
        //        courseString.add("DDDDDDDD");
        //        courseString.add("EEE");
        courseItemAdapter = new StudentItemAdapter(mCtx, courseString);
        course_recyclerview.setAdapter(courseItemAdapter);
    }

    public void updateStudentData(List<SubjectResponse.Subject> subjects) {
//        courseItemAdapter.updateTeacherInfo(subjects);
        StringBuffer buffer = new StringBuffer();
        for (SubjectResponse.Subject subject : subjects) {
            if (TextUtils.isEmpty(subject.getMark())) {
                buffer.append(subject.getSubjectName()).append("\n");
            } else {
                buffer.append(subject.getSubjectName()).append("-").append(subject.getMark()).append("\n");
            }
        }

        String substring = buffer.toString().substring(0, buffer.toString().length() - 1);
        tv_course.setText(substring);
    }

}
