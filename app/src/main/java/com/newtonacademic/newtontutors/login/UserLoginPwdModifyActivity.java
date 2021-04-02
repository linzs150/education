package com.newtonacademic.newtontutors.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.retrofit.HttpsServiceFactory;
import com.newtonacademic.newtontutors.retrofit.ResponseResult;
import com.newtonacademic.newtontutors.retrofit.model.CommentRsp;
import com.newtonacademic.newtontutors.retrofit.model.SmsCodeInfo;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;

/**
 * @author laiyongyang
 * @date 2020-07-05
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class UserLoginPwdModifyActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, UserLoginPwdModifyActivity.class);
    }

    private EditText mAccountEdt;
    private EditText mVerifyEdt;
    private EditText mPasswordEdt;
    private EditText mSecondPasswordEdt;
    private TextView mPhoneCodeTv;
    private String mCheckSum = null;
    private long mTime = 0l;
    private int mType = 0; //0 手机 1邮箱
    private LinearLayout phone_layout;
    private LinearLayout email_layout;
    private EditText email_edit;

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_pwd_modify);
        findViewById(R.id.back_btn).setOnClickListener(mOnClickListener);
        mAccountEdt = findViewById(R.id.phone_edit);
        mVerifyEdt = findViewById(R.id.phone_code_edit);
        mPasswordEdt = findViewById(R.id.password_edit);
        mSecondPasswordEdt = findViewById(R.id.confirm_password_edit);
        mPhoneCodeTv = findViewById(R.id.country_phone_code);
        mPhoneCodeTv.setOnClickListener(mOnClickListener);
        phone_layout = findViewById(R.id.phone_layout);
        email_layout = findViewById(R.id.email_layout);
        email_edit = findViewById(R.id.email_edit);
        RadioGroup rg_choice = findViewById(R.id.rg_choice);
        rg_choice.setOnCheckedChangeListener(mOnCheckListener);
        TextView getVerifyTv = findViewById(R.id.get_verify_code_tv);
        getVerifyTv.setOnClickListener(mOnClickListener);
        findViewById(R.id.reset_pwd_btn).setOnClickListener(mOnClickListener);
    }

    private final RadioGroup.OnCheckedChangeListener mOnCheckListener = (group, checkedId) -> {
        switch (checkedId) {
            case R.id.rb_phone:
                mType = 0;
                phoneOrEmail(true);
                break;
            case R.id.rb_email:
                mType = 1;
                phoneOrEmail(false);
                break;
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

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.back_btn) {
                finish();
            } else if (id == R.id.get_verify_code_tv) {
                String account = "";
                if (mType == 0) {
                    String phone = mAccountEdt.getText().toString();
                    account = mAccountEdt.getText() + phone;
                    if (!Utilts.isMobileNO(account)) {
                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, getString(R.string.please_input_correct_phone));
                        return;
                    }

                } else if (mType == 1) {
                    account = email_edit.getText().toString();
                    if (!Utilts.isEmail(account)) {
                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, getString(R.string.please_input_correct_email));
                        return;
                    }
                }

                final String finalPhone = account;
                //获取验证码
                ThreadPoolProxyFactory.getLoginScheduleThreadProxy().schedule(() -> {
                    final SmsCodeInfo smsCodeInfo = HttpsServiceFactory.sendSmsCode(finalPhone);
                    final int status = smsCodeInfo.getStatus();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            closeProgress();
                            if (status == ResponseResult.ResponseCode.SUCCESS) {
                                //获取验证码成功
                                mCheckSum = smsCodeInfo.getChecksum();
                                mTime = smsCodeInfo.getTime();
                            } else {
                                ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, ResponseResult.getString(status));
                            }
                        }
                    });
                });

                showProgress("");
            } else if (id == R.id.reset_pwd_btn) {
                String account = "";
                if (mType == 0) {
                    String phone = mAccountEdt.getText().toString();
                    account = mPhoneCodeTv.getText().toString() + phone;
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(UserLoginPwdModifyActivity.this, getText(R.string.forget_phone_hint), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!Utilts.isMobileNO(account)) {
                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, getString(R.string.please_input_correct_phone));
                        return;
                    }

                } else {
                    account = email_edit.getText().toString();
                    if (TextUtils.isEmpty(account)) {
                        Toast.makeText(UserLoginPwdModifyActivity.this, getText(R.string.please_input_email_account), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!Utilts.isMobileNO(account)) {
                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, getString(R.string.please_input_correct_email));
                        return;
                    }
                }

                final String finalAccount = account;
                final String smsCode = mVerifyEdt.getText().toString();
                if (TextUtils.isEmpty(smsCode)) {
                    Toast.makeText(UserLoginPwdModifyActivity.this, getText(R.string.verify_no_null), Toast.LENGTH_LONG).show();
                    return;
                }

                final String pwd = mPasswordEdt.getText().toString();
                final String pwd2 = mSecondPasswordEdt.getText().toString();
                if (checkPassword()) {
                    //走注册
                    //获取验证码
                    showProgress("");
                    ThreadPoolProxyFactory.getLoginScheduleThreadProxy().schedule(new Runnable() {
                        @Override
                        public void run() {
                            final CommentRsp commentRsp = HttpsServiceFactory.modifyPwdByMobile(finalAccount, smsCode, mTime, mCheckSum, pwd, pwd2);
                            final int status = commentRsp.getStatus();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    closeProgress();
                                    if (status == ResponseResult.ResponseCode.SUCCESS) {
                                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, getString(R.string.password_reset_successful));
                                        finish();
                                    } else {
                                        ToastUtils.ShowToastLong(UserLoginPwdModifyActivity.this, commentRsp.getDescript());
                                    }
                                }
                            });
                        }
                    });
                }
            } else if (v.getId() == R.id.country_phone_code) {
                startActivityForResult(SelectCountryCodeActivity.newIntent(UserLoginPwdModifyActivity.this),
                        1000);
            }
        }
    };

    private boolean checkPassword() {
        if (!Utilts.isPassword(mPasswordEdt.getText().toString())) {
            Toast.makeText(UserLoginPwdModifyActivity.this, getText(R.string.register_password_tip2), Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.equals(mPasswordEdt.getText(), mSecondPasswordEdt.getText())) {
            return true;
        }

        Toast.makeText(UserLoginPwdModifyActivity.this, getText(R.string.two_input_no_sample), Toast.LENGTH_LONG).show();
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

            mPhoneCodeTv.setText("+" + stringBuilder.toString());
        }
    }
}