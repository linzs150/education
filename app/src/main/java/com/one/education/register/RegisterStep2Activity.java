package com.one.education.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.StudentImproveSaveActivity;
import com.one.education.activities.StudentsImproveInformation;
import com.one.education.api.SystemInterface;
import com.one.education.beans.SmsCodeInfo;
import com.one.education.beans.StudentBeanInfo;
import com.one.education.commons.Constants;
import com.one.education.dialogs.DialogStudentSave;
import com.one.education.education.MainActivity;
import com.one.education.education.R;
import com.one.education.login.LoginActivity;
import com.one.education.login.LoginService;
import com.one.education.login.SelectCountryCodeActivity;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.ResponseResult;
import com.one.education.retrofit.model.RegisterInfo;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.EventBusUtils;
import com.one.education.utils.Utilts;
import com.one.education.utils.toast.ToastUtils;

/**
 * @author laiyongyang
 * @date 2020-04-27
 * @desc 注册流程2
 * @email fzhlaiyy@intretech.com
 */
public class RegisterStep2Activity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterStep2Activity.class);
    }

    private Context mCtx;
    private EditText mAccountEdt;
    private EditText mVerifyEdt;
    private EditText mPasswordEdt;
    private EditText mSecondPasswordEdt;
    private CheckBox mAcceptPrivacyCb;
    private TextView mPhoneTv;
    private String mCheckSum = null;
    private long mTime = 0l;
    private LinearLayout phone_layout;
    private LinearLayout email_layout;
    private RadioButton rb_phone;
    private RadioButton rb_email;
    private EditText email_edit;
    private int mType = 0; //0 手机 1邮箱
    private RadioGroup rg_choice;
    private String finalPhone = "";
    private String account = "";
    private String smsCode = "";
    private String pwd = "";
    private String pwd2 = "";
    private TextView accept_account_privacy;
    TextView getVerifyTv;
    private final int COUNT_NUM = 60;
    private LinearLayout account_cb_layout;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);
        mCtx = this;
        EventBusUtils.register(this);
        findViewById(R.id.back_btn).setOnClickListener(mOnClickListener);
        mPhoneTv = findViewById(R.id.country_phone_code);
        mPhoneTv.setOnClickListener(mOnClickListener);
        mAccountEdt = findViewById(R.id.phone_edit);
        mVerifyEdt = findViewById(R.id.phone_code_edit);
        mPasswordEdt = findViewById(R.id.password_edit);
        mSecondPasswordEdt = findViewById(R.id.confirm_password_edit);
        mAcceptPrivacyCb = findViewById(R.id.accept_account_privacy_cb);
        phone_layout = findViewById(R.id.phone_layout);
        email_layout = findViewById(R.id.email_layout);
        email_edit = findViewById(R.id.email_edit);
        rb_phone = findViewById(R.id.rb_phone);
        rb_email = findViewById(R.id.rb_email);
        rg_choice = findViewById(R.id.rg_choice);
        account_cb_layout = findViewById(R.id.account_cb_layout);
        accept_account_privacy = findViewById(R.id.accept_account_privacy);
        accept_account_privacy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        accept_account_privacy.getPaint().setAntiAlias(true);
        rg_choice.setOnCheckedChangeListener(mOnCheckListener);
        account_cb_layout.setOnClickListener(mOnClickListener);
        mAcceptPrivacyCb.setOnCheckedChangeListener(mOnCheckedChangeListener);
        getVerifyTv = findViewById(R.id.get_verify_code_tv);
        getVerifyTv.setOnClickListener(mOnClickListener);
        findViewById(R.id.next_tv).setOnClickListener(mOnClickListener);
        accept_account_privacy.setOnClickListener(mOnClickListener);
    }

    private boolean mIsCheck = false;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mIsCheck = true;
            } else {
                mIsCheck = false;
            }
            mAcceptPrivacyCb.setCompoundDrawablesRelativeWithIntrinsicBounds(isChecked ?
                    R.drawable.accept_account_privacy_choosed : R.drawable.accept_account_privacy_unchoosed, 0, 0, 0);
        }
    };

    private Runnable mRefreshCheck = new Runnable() {
        int count = COUNT_NUM;

        @Override
        public void run() {
            if (count > 0) {
                getVerifyTv.setEnabled(false);
                getVerifyTv.setText(count + "s");
                count--;
                mHandler.postDelayed(this, 1000);
            } else {
                count = COUNT_NUM;
                getVerifyTv.setEnabled(true);
                getVerifyTv.setText(R.string.get_verify_code);
                getVerifyTv.setOnClickListener(mOnClickListener);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
        if (mRefreshCheck != null) {
            mHandler.removeCallbacks(mRefreshCheck);
        }
    }

    private SmsCodeInfo codeInfo;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.back_btn) {
                finish();
            } else if (id == R.id.account_cb_layout) {
                mAcceptPrivacyCb.setChecked(!mIsCheck);
            } else if (id == R.id.get_verify_code_tv) {

                if (mType == 0) {
                    String phone = mAccountEdt.getText().toString();
                    finalPhone = mPhoneTv.getText().toString().trim() + phone;
                    if (!Utilts.isMobileNO(finalPhone)) {
                        ToastUtils.ShowToastLong(mCtx, getString(R.string.please_input_correct_phone));
                        return;
                    }

                } else if (mType == 1) {
                    finalPhone = email_edit.getText().toString();
                    if (!Utilts.isEmail(finalPhone)) {
                        ToastUtils.ShowToastLong(mCtx, getString(R.string.please_input_correct_email));
                        return;
                    }
                }

                if (mType == 0) {
                    addJob(NetmonitorManager.sendSmsCode(finalPhone, new RestNewCallBack<com.one.education.beans.SmsCodeInfo>() {
                        @Override
                        public void success(com.one.education.beans.SmsCodeInfo smsCodeInfo) {
                            closeProgress();
                            if (smsCodeInfo != null) {
                                codeInfo = smsCodeInfo;
                            }
                            mHandler.post(mRefreshCheck);
                        }

                        @Override
                        public void failure(RestError error) {
                            closeProgress();
                            ToastUtils.show(error.msg);
                        }
                    }));
                } else {
                    addJob(NetmonitorManager.sendEmailCode(finalPhone, new RestNewCallBack<com.one.education.beans.SmsCodeInfo>() {
                        @Override
                        public void success(com.one.education.beans.SmsCodeInfo smsCodeInfo) {
                            closeProgress();
                            if (smsCodeInfo != null) {
                                codeInfo = smsCodeInfo;
                            }
                            mHandler.post(mRefreshCheck);

                        }

                        @Override
                        public void failure(RestError error) {
                            closeProgress();
                            ToastUtils.show(error.msg);
                        }
                    }));
                }

                //获取验证码
//                ThreadPoolProxyFactory.getLoginScheduleThreadProxy().schedule(() -> {
//                    final SmsCodeInfo smsCodeInfo = HttpsServiceFactory.sendSmsCode(finalPhone);
//                    final int status = smsCodeInfo.getStatus();
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            closeProgress();
//                            if (status == ResponseResult.ResponseCode.SUCCESS) {
//                                //获取验证码成功
//                                mCheckSum = smsCodeInfo.getChecksum();
//                                mTime = smsCodeInfo.getTime();
//                            } else {
//                                ToastUtils.ShowToastLonStRegisterStep2Activity.this, ResponseResult.getString(status));
//                            }
//                        }
//                    });
//                });

                showProgress("");
            } else if (id == R.id.next_tv) {

                if (mAcceptPrivacyCb.isChecked()) {
                    if (mType == 0) {
                        account = mAccountEdt.getText().toString().trim();
                        account = mPhoneTv.getText().toString().trim() + account;
                        if (TextUtils.isEmpty(account)) {
                            Toast.makeText(mCtx, getText(R.string.forget_phone_hint), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!Utilts.isMobileNO(account)) {
                            ToastUtils.ShowToastLong(mCtx, getString(R.string.please_input_correct_phone));
                            return;
                        }

                    } else {
                        account = email_edit.getText().toString().trim();
                        if (TextUtils.isEmpty(account)) {
                            Toast.makeText(mCtx, getText(R.string.account_email_null), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!Utilts.isEmail(account)) {
                            ToastUtils.ShowToastLong(mCtx, getString(R.string.please_input_correct_email));
                            return;
                        }
                    }

                    smsCode = mVerifyEdt.getText().toString();
                    if (TextUtils.isEmpty(smsCode)) {
                        Toast.makeText(mCtx, getText(R.string.verify_no_null), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (codeInfo == null) {
                        Toast.makeText(mCtx, getText(R.string.verify_no_null), Toast.LENGTH_LONG).show();
                        return;
                    }

                    pwd = mPasswordEdt.getText().toString();
                    pwd2 = mSecondPasswordEdt.getText().toString();
                    if (checkPassword()) {
                        //走注册
                        //获取验证码
                        showProgress("");
                        requestVerifyAccountExist(account);
//                    ThreadPoolProxyFactory.getLoginScheduleThreadProxy().schedule(() -> {
//                        final RegisterInfo registerInfo = HttpsServiceFactory.register(account, smsCode, String.valueOf(mTime), mCheckSum, pwd, pwd2, mAcceptPrivacyCb.isChecked() ? 1 : 0);
//                        final int status = registerInfo.getStatus();
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                closeProgress();
//                                if (status == ResponseResult.ResponseCode.SUCCESS) {
//                                    ToastUtils.showToastFree(RegisterStep2Activity.this, R.layout.register_success_tip_layout);
//                                } else {
//                                    ToastUtils.ShowToastLong(RegisterStep2Activity.this, registerInfo.getDescript());
//                                }
//                            }
//                        });
//                    });
                    }
                } else {
                    com.one.education.commons.ToastUtils.showToastShort(getString(R.string.accept_the_terms));
                }
            } else if (v.getId() == R.id.country_phone_code) {
                startActivityForResult(SelectCountryCodeActivity.newIntent(mCtx), 1000);
            } else if (v.getId() == R.id.accept_account_privacy) {
                startActivity(AcceptAccountPrivacyActivity.newIntent(mCtx));
            }
        }
    };

    private boolean checkPassword() {
//        if (!Utilts.isPassword(mPasswordEdt.getText().toString())) {
//            Toast.makeText(RegisterStep2Activity.this, getText(R.string.register_password_tip2), Toast.LENGTH_LONG).show();
//            return false;
//        }

        if (TextUtils.equals(mPasswordEdt.getText(), mSecondPasswordEdt.getText())) {
            return true;
        }

        Toast.makeText(mCtx, getText(R.string.two_input_no_sample), Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 & resultCode == RESULT_OK) {
            String photoCode = data.getStringExtra("country_phone_code");
            StringBuilder stringBuilder = new StringBuilder();
            int index = -1;
            for (char code : photoCode.toCharArray()) {
                index++;
                if (code == '0') {
                    continue;
                }

                stringBuilder.append(photoCode.substring(index));
                break;
            }

            if (stringBuilder.toString().length() == 0) {
                stringBuilder.append("0");
            }

            mPhoneTv.setText("+" + stringBuilder.toString());
        } else if (requestCode == 10001 & resultCode == 93) {
            Intent intent = new Intent(RegisterStep2Activity.this, MainActivity.class);
            intent.putExtra("student_reg", "register");
            startActivity(intent);
            setResult(95);
            finish();
        }
    }

    private RadioGroup.OnCheckedChangeListener mOnCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_phone:
                    mType = 0;
                    codeInfo = null;
                    phoneOrEmail(true);
                    break;
                case R.id.rb_email:
                    mType = 1;
                    codeInfo = null;
                    phoneOrEmail(false);
                    break;
            }
        }
    };

    //隐藏与展示
    private void phoneOrEmail(boolean flag) {

        if (flag) {
            phone_layout.setVisibility(View.VISIBLE);
            email_layout.setVisibility(View.GONE);
        } else {
            phone_layout.setVisibility(View.GONE);
            email_layout.setVisibility(View.VISIBLE);
        }

        mAccountEdt.setText("");
        mPasswordEdt.setText("");
        mSecondPasswordEdt.setText("");
        mVerifyEdt.setText("");
        email_edit.setText("");
        mHandler.removeCallbacks(mRefreshCheck);
        getVerifyTv.setEnabled(true);
        getVerifyTv.setText(R.string.get_verify_code);


    }

    //判断手机号或者邮箱是否存在
    private void requestVerifyAccountExist(String account) {
        if (mType == 0) {
            addJob(NetmonitorManager.verifyExist(account, new RestNewCallBack<SmsCodeInfo>() {
                @Override
                public void success(SmsCodeInfo smsCodeInfo) {
                    closeProgress();
                    if (smsCodeInfo.isSuccess()) {
                        if (smsCodeInfo != null) {
                            if (smsCodeInfo.isExist()) {
                                if (!TextUtils.isEmpty(account)) {
                                    ToastUtils.show(getString(R.string.mobile_already_registered));
                                }
                            } else { //可注册
                                requestVerify(account);
                            }
                        }
                    } else {
                        closeProgress();
                        ToastUtils.show(getString(R.string.registration_failed));
                    }
                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.ShowToastShort(mCtx, error.msg);
                }
            }));
        } else {
            addJob(NetmonitorManager.verifyExistEmail(account, new RestNewCallBack<SmsCodeInfo>() {
                @Override
                public void success(SmsCodeInfo smsCodeInfo) {
                    closeProgress();
                    if (smsCodeInfo.isSuccess()) {
                        if (smsCodeInfo != null) {
                            if (smsCodeInfo.isExist()) {
                                if (!TextUtils.isEmpty(account)) {
                                    ToastUtils.show(getString(R.string.email_already_registered));
                                }
                            } else { //可注册
                                requestVerify(account);
                            }
                        }
                    } else {
                        closeProgress();
                        ToastUtils.show(getString(R.string.registration_failed));
                    }
                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.ShowToastShort(mCtx, error.msg);
                }
            }));
        }
    }

    //开始验证用户输入的验证码是否正确
    private void requestVerify(String account) {
        if (mType == 0) {
            addJob(NetmonitorManager.verifySmsCode(account, smsCode, codeInfo == null ? "" : codeInfo.getTime() + "", codeInfo == null ? "" : codeInfo.getChecksum(), new RestNewCallBack<SmsCodeInfo>() {
                @Override
                public void success(SmsCodeInfo smsCodeInfo) {
                    Intent intent = new Intent(RegisterStep2Activity.this, StudentsImproveInformation.class);

                    StudentBeanInfo studentBeanInfo = new StudentBeanInfo();
                    studentBeanInfo.setAccount(account);
                    studentBeanInfo.setCheckNum(codeInfo.getChecksum());
                    studentBeanInfo.setTime(codeInfo.getTime() + "");
                    studentBeanInfo.setSmsCode(smsCode);
                    studentBeanInfo.setPwd(pwd);
                    studentBeanInfo.setPwd2(pwd2);
                    studentBeanInfo.setmType(mType);

                    intent.putExtra("register_student", studentBeanInfo);
                    startActivityForResult(intent, 10001);

                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.show(error.msg);
                }
            }));
        } else {
            addJob(NetmonitorManager.verifyEmailCode(account, smsCode, codeInfo == null ? "" : codeInfo.getTime() + "", codeInfo == null ? "" : codeInfo.getChecksum(), new RestNewCallBack<SmsCodeInfo>() {
                @Override
                public void success(SmsCodeInfo smsCodeInfo) {
                    closeProgress();
                    Intent intent = new Intent(RegisterStep2Activity.this, StudentsImproveInformation.class);
                    StudentBeanInfo studentBeanInfo = new StudentBeanInfo();
                    studentBeanInfo.setAccount(account);
                    studentBeanInfo.setCheckNum(codeInfo.getChecksum());
                    studentBeanInfo.setTime(codeInfo.getTime() + "");
                    studentBeanInfo.setSmsCode(smsCode);
                    studentBeanInfo.setPwd(pwd);
                    studentBeanInfo.setPwd2(pwd2);
                    studentBeanInfo.setmType(mType);

                    intent.putExtra("register_student", studentBeanInfo);
                    startActivityForResult(intent,10001);
                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.show(error.msg);
                }
            }));
        }
    }


    @MainThread
    public void onEventMainThread(String s) {
        if (!TextUtils.isEmpty(s)) {
            if (s.equals("register_success")) {
//                dialogTo();
                SystemInterface.getInstance().login(account, pwd, mLoginCallBack);
                showProgress("");
            }
        }
    }


    private final LoginService.ILoginCallBack mLoginCallBack = (userLoginInfo) -> runOnUiThread(() -> {
        if (isFinishing()) {
            return;
        }

        closeProgress();
        if (userLoginInfo.getUserType() == 20) {//老师
            ToastUtils.ShowToastShort(this, getString(R.string.only_student));
            return;
        }
        if (userLoginInfo.getStatus() == ResponseResult.ResponseCode.SUCCESS) {
            Intent intent = new Intent(RegisterStep2Activity.this, MainActivity.class);
            intent.putExtra("student_reg", "register");
            startActivity(intent);
            finish();
        } else {
            ToastUtils.ShowToastShort(RegisterStep2Activity.this, userLoginInfo.getDescript());
        }
    });

}
