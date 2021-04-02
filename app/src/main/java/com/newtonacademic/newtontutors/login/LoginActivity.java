package com.newtonacademic.newtontutors.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.api.SystemInterface;
import com.newtonacademic.newtontutors.MainActivity;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.register.RegisterStep2Activity;
import com.newtonacademic.newtontutors.retrofit.ResponseResult;
import com.newtonacademic.newtontutors.utils.AppTipsUtils;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;
import com.newtonacademic.newtontutors.widget.NimOutLineDialog;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class LoginActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private EditText mEdtLogin;
    private EditText mPwdLogin;
    private TextView mPhoneCodeTv;
    private LinearLayout phone_layout;
    private LinearLayout email_layout;
    private RadioButton rb_phone;
    private RadioButton rb_email;
    private EditText email_edit;
    private int mType = 0; //0 手机 1邮箱
    private RadioGroup rg_choice;
    private String account = "";
    private String pwd = "";
    private NimOutLineDialog mNimOutLineDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtLogin = findViewById(R.id.phone_edit);
        mPwdLogin = findViewById(R.id.password_edit);
        mPhoneCodeTv = findViewById(R.id.country_phone_code);
        phone_layout = findViewById(R.id.phone_layout);
        email_layout = findViewById(R.id.email_layout);
        email_edit = findViewById(R.id.email_edit);
        rb_phone = findViewById(R.id.rb_phone);
        rb_email = findViewById(R.id.rb_email);
        rg_choice = findViewById(R.id.rg_choice);
        rg_choice.setOnCheckedChangeListener(mOnCheckListener);
        mPhoneCodeTv.setOnClickListener(mOnClickListener);
        findViewById(R.id.register_btn).setOnClickListener(mOnClickListener);
        findViewById(R.id.login_btn).setOnClickListener(mOnClickListener);
        findViewById(R.id.forget_pwd).setOnClickListener(mOnClickListener);
        findViewById(R.id.back_btn).setOnClickListener(mOnClickListener);
        mPwdLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEdtLogin.setSelection(mEdtLogin.getText().length());
        mPwdLogin.setSelection(mPwdLogin.getText().length());
        Intent intent = getIntent();
        if (intent != null) {
            String info = intent.getStringExtra("event");
            if (info != null && info.equals("im_out_line")) {
                showImOutLineDialog();
            }
        }

    }

    private RadioGroup.OnCheckedChangeListener mOnCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
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

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_btn) {
                finish();
            } else if (v.getId() == R.id.register_btn) {
                startActivityForResult(RegisterStep2Activity.newIntent(LoginActivity.this), 3001);
            } else if (v.getId() == R.id.login_btn) {
                if (!AppTipsUtils.checkNetworkState(LoginActivity.this)) {
                    return;
                }
                pwd = mPwdLogin.getText().toString();
                if (mType == 0) {
                    account = mEdtLogin.getText().toString();
                    account = mPhoneCodeTv.getText().toString().trim() + account;
                    if (TextUtils.isEmpty(account)) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.account_pwd_null));
                        return;
                    }
                    if (TextUtils.isEmpty(pwd) || pwd.length() < 8 || pwd.length() > 20) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.login_pwd_null));
                        return;
                    }

                    if (!Utilts.isMobileNO(account)) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.please_input_correct_phone));
                        return;
                    }
                } else if (mType == 1) {
                    account = email_edit.getText().toString();
                    if (TextUtils.isEmpty(account)) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.account_pwd_null));
                        return;
                    }

                    if (TextUtils.isEmpty(pwd)) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.login_pwd_null));
                        return;
                    }

                    if (!Utilts.isEmail(account)) {
                        ToastUtils.ShowToastLong(LoginActivity.this, getString(R.string.please_input_correct_email));
                        return;
                    }
                }

//                SystemInterface.getInstance().login(mType == 0 ? mPhoneCodeTv.getText().toString().trim() + account : account, pwd, mLoginCallBack);
                SystemInterface.getInstance().login(account, pwd, mLoginCallBack);
                showProgress("");

            } else if (v.getId() == R.id.forget_pwd) {
                startActivity(UserLoginPwdModifyActivity.newIntent(LoginActivity.this));
            } else if (v.getId() == R.id.country_phone_code) {
                startActivityForResult(SelectCountryCodeActivity.newIntent(LoginActivity.this),
                        1000);
            }
        }
    };

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            setResult(95);
            finish();
        } else {
            com.newtonacademic.newtontutors.commons.ToastUtils.showToastShort(LoginActivity.this, userLoginInfo.getDescript());
        }
    });

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
        } else if (requestCode == 3001 && resultCode == 95) {
            setResult(95);
            finish();
        }
    }

    private void showImOutLineDialog() {
        if (mNimOutLineDialog != null && mNimOutLineDialog.isShowing()) {
            return;
        }

        mNimOutLineDialog = new NimOutLineDialog(LoginActivity.this);
        mNimOutLineDialog.show();

    }
}
