package com.newtonacademic.newtontutors.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.beans.BaseBean;
import com.newtonacademic.newtontutors.beans.ExistPayPwdResponse;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/2 21:23
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class ModifyPasswordActivity extends BaseActivity {

    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private EditText et_current_psd;
    private EditText et_new_psd;
    private EditText et_new_again_psd;
    private Button btn_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_activity);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        et_current_psd = findViewById(R.id.et_current_psd);
        et_new_psd = findViewById(R.id.et_new_psd);
        et_new_again_psd = findViewById(R.id.et_new_again_psd);
        btn_commit = findViewById(R.id.btn_commit);
        tvTitle.setText(getString(R.string.payment_password));
    }

    private void initData() {

    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassword()) {
                    verifyExistPayPwd();

                }
            }
        });
    }

    //修改密码
    private void modifyPsd(String old, String newPsd, String surePsd) {
        addJob(NetmonitorManager.setOrModifyPayPwd(old, newPsd, surePsd, new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                if (baseBean.isSuccess()) {
                    ToastUtils.showToastShort(getString(R.string.change_payment_success));
                    finish();
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private void verifyExistPayPwd() {
        addJob(NetmonitorManager.verifyExistPayPwd(new RestNewCallBack<ExistPayPwdResponse>() {
            @Override
            public void success(ExistPayPwdResponse existPayPwdResponse) {
                if (existPayPwdResponse.isSuccess()) {
                    String strCurrentPsd = et_current_psd.getText().toString().trim();
                    if (existPayPwdResponse.isExistPayPwd()) {

                        if (TextUtils.isEmpty(strCurrentPsd)) {
                            ToastUtils.showToastShort(getString(R.string.existing_password));
                        } else {
                            //                            modifyPsd(et_current_psd.getText().toString().trim(), et_new_psd.getText().toString().trim(), et_new_again_psd.getText().toString().trim());
                        }
                    } else {
                        if (TextUtils.isEmpty(strCurrentPsd)) {

                        } else {
                            ToastUtils.showToastShort(getString(R.string.leave_blank));
                            return;
                        }
                    }

                    modifyPsd(et_current_psd.getText().toString().trim(), et_new_psd.getText().toString().trim(), et_new_again_psd.getText().toString().trim());

                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    //密碼確認
    private boolean checkPassword() {
        String strCurrentPsd = et_current_psd.getText().toString().trim();
        String strNewFirst = et_new_psd.getText().toString().trim();
        String strNewSecond = et_new_again_psd.getText().toString().trim();
        //        if (strCurrentPsd.length() != 6) {
        //            ToastUtils.showToastShort("请输入6位支付密码");
        //            return false;
        //        }
        if (TextUtils.isEmpty(strNewSecond)) {
            ToastUtils.showToastShort(getString(R.string.enter_new_password));
            return false;
        }
        if (!strNewFirst.equals(strNewSecond)) {
            ToastUtils.showToastShort(getString(R.string.two_input_no_sample));
            return false;
        }

        return true;
    }
}
