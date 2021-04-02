package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.newtonacademic.newtontutors.adapters.CourseItemAdapter;
import com.newtonacademic.mylibrary.TaughtSubjects;
import com.newtonacademic.newtontutors.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

public class CourseLinearLayout extends LinearLayout {

    private Context mCtx;
    private RecyclerView course_recyclerview;
    //    private GridLayoutManager manager;
    private LinearLayoutManager manager;
    private CourseItemAdapter courseItemAdapter;
    private List<TaughtSubjects> courseString = new ArrayList<>();


    public CourseLinearLayout(Context context) {
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
        initRecyclerView();
    }

    private void initRecyclerView() {
        manager = new LinearLayoutManager(mCtx) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }

            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        course_recyclerview.setLayoutManager(manager);
//                courseString.add("AAAAAAAA");
//                courseString.add("BBBBBAAAAA");
//                courseString.add("CCCCCCC");
//                courseString.add("DDDDDDDD");
//                courseString.add("EEE");
        courseItemAdapter = new CourseItemAdapter(mCtx, courseString);
        course_recyclerview.setAdapter(courseItemAdapter);
    }

    public void updateCourseData(List<TaughtSubjects> subjects) {
        this.courseString = subjects;
        courseItemAdapter.updateTeacherInfo(courseString);
    }

}
