package com.newtonacademic.newtontutors.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.newtonacademic.newtontutors.beans.LoginResponse;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.commons.SharedPreferencesUtils;
import com.newtonacademic.newtontutors.R;

public class DialogPay {
    private Dialog dialog;
    private Context mContext;
    private TextView tv_name;
    private TextView tv_money;
    private CheckBox cb_wx;
    private CheckBox cb_union;
    private CheckBox cb_alib;
    private CheckBox cb_pay;
    private View view;
    private LoginResponse loginResponse;
    private TextView tv_cancel;
    private TextView tv_pay;

    public DialogPay(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
        dialog = new Dialog(context, R.style.PayCustomDialog);
        initView();
        initData();
    }

    private void initData() {
        String userInfo = SharedPreferencesUtils.getInstance().getString("userInfo", "");
        if (!TextUtils.isEmpty(userInfo)) {
            LoginResponse loginResponse = JSONObject.parseObject(userInfo, LoginResponse.class);
            if (loginResponse != null) {
                tv_name.setText((TextUtils.isEmpty(loginResponse.getUserName()) ? "" : loginResponse.getUserName()));
            }
        } else {
        }
    }

    private void initView() {
        tv_name = view.findViewById(R.id.tv_name);
        tv_money = view.findViewById(R.id.tv_money);
        cb_wx = view.findViewById(R.id.cb_wx);
        cb_union = view.findViewById(R.id.cb_union);
        cb_alib = view.findViewById(R.id.cb_ali);
        cb_pay = view.findViewById(R.id.cb_pay);
        tv_pay = view.findViewById(R.id.tv_pay);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        setCancelable(true);
        setCancelableOnTouchOutside(true);
    }

    public void clearPayType() {
        cb_wx.setChecked(false);
        cb_alib.setChecked(false);
        cb_union.setChecked(false);
        cb_pay.setChecked(false);

    }

    //设置状态
    public void setTypePay(final PayInterface payInterface) {
        cb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (payInterface != null) {
                        payInterface.payType(Constants.WX_PAY);
                    }
                }
            }
        });

        cb_union.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (payInterface != null) {
                        payInterface.payType(Constants.UNION_PAY);
                    }
                }
            }
        });

        cb_alib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (payInterface != null) {
                        payInterface.payType(Constants.ALIBA_PAY);
                    }
                }
            }
        });

        cb_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (payInterface != null) {
                        payInterface.payType(Constants.PAyPAL);
                    }
                }
            }
        });

//        tv_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setPrice(long price) {
        tv_money.setText("US$ " + price);
    }

    public interface PayInterface { //支付类型
        void payType(int type);
    }

    public void setTvPayListener(View.OnClickListener listener) {
        tv_pay.setOnClickListener(listener);
    }


    public void setCancelableOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (dialog != null)
            return dialog.isShowing();
        return false;
    }

    public void setCancelable(boolean flag) {
        dialog.setCancelable(flag);
    }


}
