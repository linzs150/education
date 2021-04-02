package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.adapters.CourseWaveAdapter;
import com.newtonacademic.newtontutors.beans.SubjectResponse;
import com.newtonacademic.newtontutors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/22 0:43
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CourseWareActivity extends BaseActivity {

    private Context mCtx;
    private RecyclerView wave_recyclerview;
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private CourseWaveAdapter mAdapter;
    private List<SubjectResponse.Subject> subjectList = new ArrayList<>();
    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courseware_layout);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        wave_recyclerview = findViewById(R.id.wave_recyclerview);
        btn_download = findViewById(R.id.btn_download);
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        wave_recyclerview.setLayoutManager(manager);
        subjectList.add(new SubjectResponse.Subject());
        subjectList.add(new SubjectResponse.Subject());
        subjectList.add(new SubjectResponse.Subject());
        subjectList.add(new SubjectResponse.Subject());
        subjectList.add(new SubjectResponse.Subject());
        subjectList.add(new SubjectResponse.Subject());
        mAdapter = new CourseWaveAdapter(this, subjectList);
        wave_recyclerview.setAdapter(mAdapter);
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
