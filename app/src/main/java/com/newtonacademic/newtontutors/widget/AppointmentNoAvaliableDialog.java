package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.widget.TextView;

import com.newtonacademic.newtontutors.R;

public class AppointmentNoAvaliableDialog extends CenterDialog {
    private TextView mMessageTv;
    public AppointmentNoAvaliableDialog(Context context) {
        super(context, R.layout.appointment_no_aviable_dialog);
        mMessageTv = findViewById(R.id.message_tv);
        setCanceledOnTouchOutside(false);
        findViewById(R.id.let_ok).setOnClickListener(v -> dismiss());
    }

    public void setMessageTv(String message) {
        mMessageTv.setText(message);
    }
}
