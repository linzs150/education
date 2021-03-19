package com.one.education.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.one.education.education.R;

public class PaySelectDialog extends PushUpInDialog{
    private TextView mOneTv;
    private TextView mTwoTv;
    public PaySelectDialog(Context context, View.OnClickListener oneClickListener, View.OnClickListener twoClickListener) {
        super(context, R.layout.pay_select_dialog);
        setCanceledOnTouchOutside(true);
        mOneTv = findViewById(R.id.yu_e);
        mOneTv.setOnClickListener(v -> {
            oneClickListener.onClick(v);
            dismiss();
        });
        mTwoTv = findViewById(R.id.paypal);
        mTwoTv.setOnClickListener(v -> {
            twoClickListener.onClick(v);
            dismiss();
        });
    }

    public void select(int index) {
        if (index == 0) {
            mOneTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.yu_e, 0, R.drawable.accept_account_privacy_choosed, 0);
            mTwoTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.paypal, 0, R.drawable.accept_account_privacy_unchoosed, 0);
        } else if (index == 1) {
            mOneTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.yu_e, 0, R.drawable.accept_account_privacy_unchoosed, 0);
            mTwoTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.paypal, 0, R.drawable.accept_account_privacy_choosed, 0);
        }
    }
}
