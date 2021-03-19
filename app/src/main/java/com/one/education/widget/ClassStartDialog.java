
package com.one.education.widget;

import android.content.Context;

import com.one.education.education.R;


/**
 * 开始上课对话框
 */
public class ClassStartDialog extends CenterDialog {
    public ClassStartDialog(Context context) {
        super(context, R.layout.dialog_one_button);
        setCanceledOnTouchOutside(true);
    }
}
