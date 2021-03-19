package com.one.education.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.education.beans.PerfectMyProfileResponse;
import com.one.education.beans.SubjectResponse;
import com.one.education.commons.ToastUtils;
import com.one.education.dialogs.DialogStudentSave;
import com.one.education.education.R;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.Utilts;
import com.one.education.widget.CourseLinearLayout1;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @创建者 Administrator
 * @创建时间 2020/4/27 23:33
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class StudentInformationActivity extends BaseActivity {

    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private LinearLayout edit_layout;
    private Context mCtx;
    private CircleImageView my_icon;
    private TextView name;
    private ImageView sex;
    private TextView tg;
    private TextView grade;
    private TextView date;
    private TextView location;
    private TextView email;
    private TextView skype;
    private TextView wechat;
    private TextView school;
    private TextView full;
    private TextView a_level;
    private LinearLayout contain;
    private TextView universities;
    private TextView degree_course;
    private PerfectMyProfileResponse perfectMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_information_activity);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        edit_layout = findViewById(R.id.edit_layout);
        edit_layout.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.edit_info));

        my_icon = findViewById(R.id.my_icon);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        tg = findViewById(R.id.tg);
        grade = findViewById(R.id.student_grade);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        email = findViewById(R.id.email);
        skype = findViewById(R.id.skype);
        wechat = findViewById(R.id.wechat);
        school = findViewById(R.id.school);
        full = findViewById(R.id.full);
        a_level = findViewById(R.id.a_level);
        contain = findViewById(R.id.contain);
        universities = findViewById(R.id.universities);
        degree_course = findViewById(R.id.degree_course);

        mCtx = this;
    }

    private void initData() {
        setProfile();
    }

    private void updateProfile(PerfectMyProfileResponse response) {
        if (response != null) {
//            if (!TextUtils.isEmpty(response.getStudentIdCard())) {
//                ImageLoader.loadAdImage(response.getStudentIdCard(), my_icon);
//            }

            if (!TextUtils.isEmpty(response.getUserPhotoUrl())) {
                ImageLoader.loadAdImage(response.getUserPhotoUrl(), my_icon);
            } else {
            }
            if (response.getSex() == 1) {
                sex.setImageResource(R.drawable.boy_sex);
            } else if (response.getSex() == 2) {
                sex.setImageResource(R.drawable.sex_female);
            } else {
                sex.setImageResource(R.drawable.sex_female);
            }

            name.setText(TextUtils.isEmpty(response.getUserName()) ? "" : response.getUserName());
            grade.setText(Utilts.schoolYear(this, Integer.valueOf(response.getSchoolYear())));
            date.setText(Utilts.dateToInt(Long.valueOf(response.getBirthday())));
            email.setText(TextUtils.isEmpty(response.getEmail()) ? "" : response.getEmail());
            skype.setText(TextUtils.isEmpty(response.getSkype()) ? "" : response.getSkype());
            wechat.setText(TextUtils.isEmpty(response.getWechat()) ? "" : response.getWechat());
            school.setText(TextUtils.isEmpty(response.getSchool()) ? "" : response.getSchool());
            a_level.setText(TextUtils.isEmpty(response.getCourseName()) ? "" : response.getCourseName());
            universities.setText(TextUtils.isEmpty(response.getTargetUniversitys()) ? "" : targets(response.getTargetUniversitys()));
            degree_course.setText(TextUtils.isEmpty(response.getTargetColleges()) ? "" : targets(response.getTargetColleges()));
            if (response.getStudiedSubjects() != null && response.getStudiedSubjects().size() > 0) {
                courseData(response.getStudiedSubjects());
            }
        }

    }

    private CourseLinearLayout1 courseLinearLayout;

    private void courseData(List<SubjectResponse.Subject> item) {
        contain.removeAllViews();
        if (courseLinearLayout == null) {
            courseLinearLayout = new CourseLinearLayout1(this);
        }
        courseLinearLayout.updateStudentData(item);
        contain.addView(courseLinearLayout);

    }


    private String targets(String targets) {

        StringBuffer buffer = new StringBuffer();
        if (!TextUtils.isEmpty(targets)) {

            String[] strings = targets.split("\\$_\\$");

            for (String s : strings) {
                buffer.append(s).append("\n");
            }

        }

        return buffer.toString().substring(0, buffer.toString().length() - 1);
    }


    private void setProfile() {
        addJob(NetmonitorManager.getMyProfile(new RestNewCallBack<PerfectMyProfileResponse>() {
            @Override
            public void success(PerfectMyProfileResponse perfectMyProfileResponse) {
                if (perfectMyProfileResponse != null) {
                    if (perfectMyProfileResponse.isSuccess()) {
                        perfectMyProfile = perfectMyProfileResponse;
                        updateProfile(perfectMyProfileResponse);
                    }
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentInformationActivity.this, StudentsImproveInformation.class);
                intent.putExtra("student", perfectMyProfile);

                startActivityForResult(intent, 13);

                //                if (studentSave == null) {
                //                    studentSave = new DialogStudentSave(mCtx);
                //                }
                //                studentSave.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 93 && requestCode == 13) {
            //            setResult(94);
            finish();
        }
    }

    private DialogStudentSave studentSave;
}
