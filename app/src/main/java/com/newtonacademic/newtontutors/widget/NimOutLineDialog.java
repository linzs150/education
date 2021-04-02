package com.newtonacademic.newtontutors.widget;

import android.content.Context;

import com.newtonacademic.newtontutors.R;

public class NimOutLineDialog extends CenterDialog {
    public NimOutLineDialog(Context context) {
        super(context, R.layout.nim_out_line_dialog);
        setCanceledOnTouchOutside(false);
        findViewById(R.id.let_ok).setOnClickListener(v -> dismiss());
    }
}
