package com.newtonacademic.newtontutors.classschedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.widget.SuperFileView2;

import java.io.File;

/**
 * @author laiyongyang
 * @date 2020-07-06
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class CoursewareReadViewActivity extends BaseActivity{
    public static Intent newIntent(Context context, String filePath) {
        Intent intent = new Intent(context, CoursewareReadViewActivity.class);
        intent.putExtra("filePath", filePath);
        return intent;
    }

    private SuperFileView2 mSuperFileView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseware_read_view);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String filePath = getIntent().getStringExtra("filePath");
        mSuperFileView2 =  findViewById(R.id.content_layout);
        mSuperFileView2.displayFile(new File(filePath));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuperFileView2.onStopDisplay();
    }
}
