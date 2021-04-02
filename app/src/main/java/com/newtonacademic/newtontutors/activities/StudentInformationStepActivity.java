package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newtonacademic.newtontutors.adapters.CourseAdapter;
import com.newtonacademic.newtontutors.beans.CourseResponse;
import com.newtonacademic.newtontutors.beans.PerfectMyProfileResponse;
import com.newtonacademic.newtontutors.beans.StudentBeanInfo;
import com.newtonacademic.newtontutors.beans.SubjectResponse;
import com.newtonacademic.newtontutors.commons.AppUtils;
import com.newtonacademic.newtontutors.commons.LogUtils;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.commons.Utils;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/4 10:45
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class StudentInformationStepActivity extends BaseActivity {

    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private RadioGroup radioGroup;
    private List<CourseResponse.Course> courseList = new ArrayList<>();
    private List<SubjectResponse.Subject> subjectList = new ArrayList<>();
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private Button btn_step;
    private Context mCtx;
    private PerfectMyProfileResponse perfectMyProfileResponse;
    private StudentBeanInfo studentBeanInfo;

    private List<SubjectResponse.Subject> oldSubjectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_information_step_activity);
        initView();
        initData();
        setLinstener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        radioGroup = findViewById(R.id.course_radiogroup);
        courseRecyclerView = findViewById(R.id.course_recycler_view);
        btn_step = findViewById(R.id.btn_step);
        tvTitle.setText(getString(R.string.complete_student_info));
        perfectMyProfileResponse = (PerfectMyProfileResponse) getIntent().getSerializableExtra("profile");
        studentBeanInfo = (StudentBeanInfo) getIntent().getSerializableExtra("register_student");
        mCtx = this;
    }

    private void initData() {


        if (perfectMyProfileResponse != null) {
            oldSubjectList = perfectMyProfileResponse.getStudiedSubjects();
        }


        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        courseRecyclerView.setLayoutManager(manager);
        courseAdapter = new CourseAdapter(this, subjectList);
        courseRecyclerView.setAdapter(courseAdapter);
        //        test2();
        getCourseList();

    }

    //课程接口请求
    private void getCourseList() {
        addJob(NetmonitorManager.getCourseList(new RestNewCallBack<CourseResponse>() {
            @Override
            public void success(CourseResponse courseResponse) {
                if (courseResponse.isSuccess()) {
                    List<CourseResponse.Course> temp = new ArrayList<CourseResponse.Course>(courseResponse.getData());
                    initCourse(temp);
                    LogUtils.e("ceshi:", JSONObject.toJSONString(courseResponse));
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private int position = 0;

    //课程初始化
    private void initCourse(List<CourseResponse.Course> courseList) {

        this.courseList = courseList;
        if (courseList != null && courseList.size() > 0) {
            radioGroup.removeAllViews();

            for (int i = 0; i < courseList.size(); i++) {
                RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.radio_item, null);
                rb.setId(i);
                rb.setText(courseList.get(i).getCourseName());
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.MATCH_PARENT);
                //                                params.weight = 1;
                params.rightMargin = Utils.dip2px(getActivity(), 10);
                radioGroup.addView(rb, params);

                if (perfectMyProfileResponse != null && !TextUtils.isEmpty(perfectMyProfileResponse.getCourseName())) {
                    if (perfectMyProfileResponse.getCourseName().equals(courseList.get(i).getCourseName())) {
                        position = i;
                    }
                }
            }
            if (radioGroup.getChildCount() > 0) {
                //                radioGroup.check(0);
                radioGroup.check(position);
                //                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
                updateSubject(courseList.get(position).getCourseId() + "");
                //                test4();
            }
        } else {
//            courseList.add(new CourseResponse.Course());
//            courseList.add(new CourseResponse.Course());
//            radioGroup.removeAllViews();
//
//            for (int i = 0; i < courseList.size(); i++) {
//                RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.radio_item, null);
//                rb.setId(i);
//                rb.setText(Integer.parseInt(courseList.get(i).getCourseName()));
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
//                        RadioGroup.LayoutParams.MATCH_PARENT);
//                //                                params.weight = 1;
//                params.rightMargin = Utils.dip2px(getActivity(), 10);
//                radioGroup.addView(rb, params);
//            }
        }


    }

//    private int index = 0;

    private void test2() {
        Gson gson = new Gson();
        String jsonString = AppUtils.getJson(getActivity(), "course.json");
        //        AreaModel.Province province = new AreaModel.Province();
        //        province.setName(name);
        CourseResponse model = gson.fromJson(jsonString, CourseResponse.class);
        initCourse(model.getData());
        //        updateSubject();
    }

    private void setLinstener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //                updateSubjet(courseList.get(checkedId).getCourseId() + "");]


                if (checkedId > -1) {
                    if (position != checkedId) {
                        //                    showCustomLoading();
                        if (subjectMap != null) {
                            if (courseAdapter != null) {
                                if (courseAdapter.getSubjectList() != null && courseAdapter.getSubjectList().size() > 0) {
                                    subjectMap.put(position, courseAdapter.getSubjectList());
                                }
                            }
                        }
                        if (subjectMap.get(checkedId) != null && subjectMap.get(checkedId).size() > 0) {

                            List<SubjectResponse.Subject> subjects = subjectMap.get(checkedId);
                            Collections.sort(subjects, new Comparator<SubjectResponse.Subject>() {
                                @Override
                                public int compare(SubjectResponse.Subject o1, SubjectResponse.Subject o2) {
                                    return o1.getSubjectName().compareTo(o2.getSubjectName());
                                }
                            });
                            setSubject(subjects);
                        } else {
                            updateSubject(courseList.get(checkedId).getCourseId() + "");
                        }

                        position = checkedId;
                        //                        test4();
                    } else {
                        position = checkedId;
                    }

                }
            }
        });


        courseAdapter.setSearchListener(new CourseAdapter.SearchListener() {
            @Override
            public void click(SubjectResponse.Subject subject) {
                for (SubjectResponse.Subject subject1 : subjectList) {
                    if (subject.getCourseId() == subject1.getCourseId() && subject.getSubjectId() == subject1.getSubjectId()) {
                        subject1.setCheck(subject.isCheck());
                        subject1.setSubjectLevel(subject.getSubjectLevel());

                    }
                }

            }
        });

        btn_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                List<SubjectResponse.Subject> temp = courseAdapter.getSubjectInfo();

//                for (SubjectResponse.Subject subject : subjectList) {
//                    if(subject.isCheck()){
//
//                    }
//                }
                List<SubjectResponse.Subject> subjectResp = courseAdapter.getSubjectList();

                List<SubjectResponse.Subject> subjects = new ArrayList<>();
                for (SubjectResponse.Subject subject : subjectResp) {
                    if (subject.isCheck()) {

                        if (subject.getIsNeedLevel() == 1) {

                            if (subject.getSubjectLevel() != 1 && subject.getSubjectLevel() != 2) {
                                ToastUtils.showToastShort(getString(R.string.complete_info));
                                return;
                            }
                        }

                        if (subject.getIsNeedMark() == 1) {
                            if (TextUtils.isEmpty(subject.getMark())) {
                                ToastUtils.showToastShort(getString(R.string.complete_info));
                                return;
                            }
                        }

                        subjects.add(subject);
                    }
                }

                if (subjects.size() == 0) {
                    ToastUtils.showToastShort(getString(R.string.complete_info));
                    return;
                }


                perfectMyProfileResponse.setStudiedSubjects(subjects);
                perfectMyProfileResponse.setCourseName(courseList.get(position).getCourseName());
                perfectMyProfileResponse.setCourse(courseList.get(position).getCourseId() + "");

                Intent intent = new Intent(mCtx, StudentImproveSaveActivity.class);
                intent.putExtra("perfect", perfectMyProfileResponse);
                if (studentBeanInfo != null) {
                    intent.putExtra("register_student", studentBeanInfo);
                }
                startActivityForResult(intent, 11);
            }
        });
    }

    private void test4() {
        Gson gson = new Gson();
        String jsonString = AppUtils.getJson(getActivity(), "subject.json");
        //        AreaModel.Province province = new AreaModel.Province();
        //        province.setName(name);
        SubjectResponse model = gson.fromJson(jsonString, SubjectResponse.class);
        setSubject(model.getData());
    }

    //课程
    private String type;
    private HashMap<Integer, List<SubjectResponse.Subject>> subjectMap = new HashMap<>();

    private void updateSubject(String id) {
        showProgress();
        NetmonitorManager.getSubject(id, new RestNewCallBack<SubjectResponse>() {
            @Override
            public void success(SubjectResponse subjectResponse) {
                closeProgress();
                if (subjectResponse.isSuccess()) {
                    List<SubjectResponse.Subject> temp = new ArrayList<SubjectResponse.Subject>(subjectResponse.getData());
                    Collections.sort(temp, new Comparator<SubjectResponse.Subject>() {
                        @Override
                        public int compare(SubjectResponse.Subject o1, SubjectResponse.Subject o2) {
                            return o1.getSubjectName().compareTo(o2.getSubjectName());
                        }
                    });
                    setSubject(temp);
                    LogUtils.e("ceshi:", JSONObject.toJSONString(subjectResponse));
                }
            }

            @Override
            public void failure(RestError error) {
                closeProgress();
            }
        });
    }

    private void setSubject(List<SubjectResponse.Subject> subject) {

        this.subjectList = subject;

        if (oldSubjectList != null && oldSubjectList.size() > 0) {
            for (SubjectResponse.Subject subject1 : oldSubjectList) {
                for (SubjectResponse.Subject subject2 : subjectList) {
                    if (subject1.getCourseId() == subject2.getCourseId() && subject1.getSubjectId() == subject2.getSubjectId()) {
                        subject2.setCheck(true);
                        subject2.setSubjectLevel(subject1.getSubjectLevel());
                        subject2.setMark(subject1.getMark());
                    }
                }
            }
        }

        courseAdapter.updateTeacherInfo(subjectList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 91 && requestCode == 11) {
            setResult(92);
            finish();
        }
    }
}

