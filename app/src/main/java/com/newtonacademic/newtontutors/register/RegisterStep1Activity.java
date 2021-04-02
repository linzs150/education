package com.newtonacademic.newtontutors.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.R;

/**
 * @author laiyongyang
 * @date 2020-04-26
 * @desc 注册流程1
 * @email fzhlaiyy@intretech.com
 */
public class RegisterStep1Activity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterStep1Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);
        findViewById(R.id.student_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterStep2Activity.newIntent(RegisterStep1Activity.this));
                finish();
            }
        });

        findViewById(R.id.teacher_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterStep2Activity.newIntent(RegisterStep1Activity.this));
                finish();
            }
        });
    }
}
