package com.one.education.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.education.beans.AmountResponse;
import com.one.education.commons.SharedPreferencesUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.education.R;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 15:52
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class WalletActivity extends BaseActivity {

    private LinearLayout recharge_layout;
    private LinearLayout detail_layout;
    private LinearLayout setting_psd_layout;
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private TextView money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        initView();
        initData();
        setListener();
    }

    private void initView() {

        recharge_layout = findViewById(R.id.recharge_layout);
        detail_layout = findViewById(R.id.detail_layout);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        setting_psd_layout = findViewById(R.id.setting_psd_layout);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.my_wallet));
        money = findViewById(R.id.money);

    }

    private void initData() {
        getAccountMoney();
    }

    //总余额
    private void getAccountMoney() {
        addJob(NetmonitorManager.getAmount(new RestNewCallBack<AmountResponse>() {
            @Override
            public void success(AmountResponse amountResponse) {
                if (amountResponse.isSuccess()) {
                    if (amountResponse != null) {
                        updateMoney(amountResponse);
                    }
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private void updateMoney(AmountResponse amountResponse) {
        if (amountResponse != null) {
            money.setText(amountResponse.getBalance() + "");
        }
    }

    private void setListener() {


        recharge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String state = SharedPreferencesUtils.getInstance().getString("state", "");

                if (!TextUtils.isEmpty(state) && state.equals("1")) {

                    Intent intent = new Intent(WalletActivity.this, RechargeActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.showToastShort(getString(R.string.been_approved));
                }
            }
        });

        detail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this, TransactionDetailActivity.class);
                startActivity(intent);
            }
        });


        setting_psd_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);
            }
        });

        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
