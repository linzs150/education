package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.api.SystemInterface;
import com.newtonacademic.newtontutors.beans.BaseBean;
import com.newtonacademic.newtontutors.beans.PerfectMyProfileResponse;
import com.newtonacademic.newtontutors.beans.StudentBeanInfo;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.dialogs.DialogStudentSave;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.login.LoginService;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.retrofit.ResponseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/30 10:54
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class StudentImproveSaveActivity extends BaseActivity {

    private TextView tvTitle;
    private RelativeLayout leftBtnLayout;
    private Context mCtx;
    private TextView iv_save;
    private DialogStudentSave mDialogStudentSave;
    private EditText etInputTarget;
    private EditText etInputCourse;
    private EditText etInputTarget2;
    private EditText etInputCourse2;
    private EditText etInputTarget3;
    private EditText etInputCourse3;
    private EditText etInputTarget4;
    private EditText etInputCourse4;
    private EditText etInputTarget5;
    private EditText etInputCourse5;
    private PerfectMyProfileResponse perfectMyProfileResponse;
    private List<String> targets = new ArrayList<>();
    private List<String> courses = new ArrayList<>();
    private StudentBeanInfo studentBeanInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_improve_save_activity);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.complete_student_info));
        iv_save = findViewById(R.id.iv_save);
        etInputTarget = findViewById(R.id.etInputTarget);
        etInputCourse = findViewById(R.id.etInputCourse);

        etInputTarget2 = findViewById(R.id.etInputTarget2);
        etInputCourse2 = findViewById(R.id.etInputCourse2);

        etInputTarget3 = findViewById(R.id.etInputTarget3);
        etInputCourse3 = findViewById(R.id.etInputCourse3);

        etInputTarget4 = findViewById(R.id.etInputTarget4);
        etInputCourse4 = findViewById(R.id.etInputCourse4);

        etInputTarget5 = findViewById(R.id.etInputTarget5);
        etInputCourse5 = findViewById(R.id.etInputCourse5);

        mCtx = this;
    }

    private void initData() {

        perfectMyProfileResponse = (PerfectMyProfileResponse) getIntent().getSerializableExtra("perfect");
        studentBeanInfo = (StudentBeanInfo) getIntent().getSerializableExtra("register_student");
        if (perfectMyProfileResponse != null && (!TextUtils.isEmpty(perfectMyProfileResponse.getTargetUniversitys())) && !TextUtils.isEmpty(perfectMyProfileResponse.getTargetColleges())) {
            targets = spliteData(perfectMyProfileResponse.getTargetUniversitys());
            courses = spliteData(perfectMyProfileResponse.getTargetColleges());
            if (targets != null && targets.size() > 0 && targets.get(0) != null) {
                etInputTarget.setText(targets.get(0));
            } else {
                etInputTarget.setText("");
            }


            if (targets != null && targets.size() > 0 && targets.size() > 1 && targets.get(1) != null) {
                etInputTarget2.setText(targets.get(1));
            } else {
                etInputTarget2.setText("");
            }


            if (targets != null && targets.size() > 0 && targets.size() > 2 && targets.get(2) != null) {
                etInputTarget3.setText(targets.get(2));
            } else {
                etInputTarget3.setText("");
            }


            if (targets != null && targets.size() > 0 && targets.size() > 3 && targets.get(3) != null) {
                etInputTarget4.setText(targets.get(3));
            } else {
                etInputTarget4.setText("");
            }


            if (targets != null && targets.size() > 0 && targets.size() > 4 && targets.get(4) != null) {
                etInputTarget5.setText(targets.get(4));
            } else {
                etInputTarget5.setText("");
            }


            if (courses != null && courses.size() > 0 && courses.get(0) != null) {
                etInputCourse.setText(courses.get(0));
            } else {
                etInputCourse.setText("");
            }

            if (courses != null && courses.size() > 0 && courses.size() > 1 && courses.get(1) != null) {
                etInputCourse2.setText(courses.get(1));
            } else {
                etInputCourse2.setText("");
            }

            if (courses != null && courses.size() > 0 && courses.size() > 2 && courses.get(2) != null) {
                etInputCourse3.setText(courses.get(2));
            } else {
                etInputCourse3.setText("");
            }

            if (courses != null && courses.size() > 0 && courses.size() > 3 && courses.get(3) != null) {
                etInputCourse4.setText(courses.get(3));
            } else {
                etInputCourse4.setText("");
            }

            if (courses != null && courses.size() > 0 && courses.size() > 4 && courses.get(4) != null) {
                etInputCourse5.setText(courses.get(4));
            } else {
                etInputCourse5.setText("");
            }


            //            etInputCourse.setText();
            //            etInputCourse2.setText();
            //            etInputCourse3.setText();
            //            etInputCourse4.setText();
            //            etInputCourse5.setText();

        }

    }

    private void setListener() {
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveStudents();

            }
        });

        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dialogTo() {
        if (mDialogStudentSave == null) {
            mDialogStudentSave = new DialogStudentSave(mCtx);
        }
        mDialogStudentSave.show();
        mDialogStudentSave.setSave(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogStudentSave.dismiss();

                setResult(91);
                finish();
            }
        });
    }

    private void saveStudents() {


        if (TextUtils.isEmpty(etInputTarget.getText().toString().trim()) && TextUtils.isEmpty(etInputCourse.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.one_target_university));
            return;
        }

        String targets = targetData(etInputTarget.getText().toString().trim(), etInputTarget2.getText().toString().trim(), etInputTarget3.getText().toString().trim(), etInputTarget4.getText().toString().trim(), etInputTarget5.getText().toString().trim());
        String courses = targetData(etInputCourse.getText().toString().trim(), etInputCourse2.getText().toString().trim(), etInputCourse3.getText().toString().trim(), etInputCourse4.getText().toString().trim(), etInputCourse5.getText().toString().trim());


        perfectMyProfileResponse.setTargetUniversitys(targets);
        perfectMyProfileResponse.setTargetColleges(courses);

        showProgress();

        if (studentBeanInfo != null) { //注册流程
            addJob(NetmonitorManager.registerStudent(studentBeanInfo, perfectMyProfileResponse, new RestNewCallBack<BaseBean>() {
                @Override
                public void success(BaseBean baseBean) {
                    closeProgress();
                    setResult(91);
//                    EventBusUtils.post("register_success");
                    if (baseBean.isSuccess()) {
                        SystemInterface.getInstance().login(studentBeanInfo.getAccount(), studentBeanInfo.getPwd(), mLoginCallBack);
                    }
                    finish();

                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.showToastShort(error.msg);
                }
            }));
        } else { //完善流程

            addJob(NetmonitorManager.perfectMyProfile(perfectMyProfileResponse, new RestNewCallBack<BaseBean>() {
                @Override
                public void success(BaseBean baseBean) {
                    closeProgress();
//                    dialogTo();
                    setResult(91);
                   finish();
                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.showToastShort(error.msg);
                }
            }));
        }

    }


    //登录情况
    private final LoginService.ILoginCallBack mLoginCallBack = (userLoginInfo) -> runOnUiThread(() -> {
        if (isFinishing()) {
            return;
        }

        closeProgress();
        if (userLoginInfo.getUserType() == 20) {//老师
            ToastUtils.showToastShort(getString(R.string.only_student));
            return;
        }
        if (userLoginInfo.getStatus() == ResponseResult.ResponseCode.SUCCESS) {
            setResult(91);
        } else {
            ToastUtils.showToastShort(userLoginInfo.getDescript());
        }
    });


    private String targetData(String s1, String s2, String s3, String s4, String s5) {
        StringBuffer target = new StringBuffer();
        String string = "";

        if (!TextUtils.isEmpty(s1)) {
            target.append(s1).append("$_$");
        }
        if (!TextUtils.isEmpty(s2)) {
            target.append(s2).append("$_$");
        }
        if (!TextUtils.isEmpty(s3)) {
            target.append(s3).append("$_$");
        }
        if (!TextUtils.isEmpty(s4)) {
            target.append(s4).append("$_$");
        }
        if (!TextUtils.isEmpty(s5)) {
            target.append(s5).append("$_$");
        }

        if (!TextUtils.isEmpty(target.toString())) {
            string = target.toString().substring(0, target.toString().length() - 3);
        }


        return string;
    }


    private List<String> spliteData(String data) {
        List<String> temp = new ArrayList<>();
        if (!TextUtils.isEmpty(data)) {
            String[] strings = data.split("\\$_\\$");

            for (String s : strings) {
                temp.add(s);
            }


        }

        return temp;
    }

}
