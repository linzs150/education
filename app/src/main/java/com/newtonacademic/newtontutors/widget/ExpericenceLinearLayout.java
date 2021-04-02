package com.newtonacademic.newtontutors.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.R;


/**
 * Created by lzs on 2018/2/23.
 */

public class ExpericenceLinearLayout extends LinearLayout {

    private Context mCtx;
    private LinearLayout ll_block_content;


    public ExpericenceLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context ctx) {

        this.mCtx = ctx;
        LayoutInflater.from(ctx).inflate(R.layout.setting_enter_my_block, this);
        initView();

    }

    private void initView() {
        ll_block_content = findViewById(R.id.ll_block_content);
        //        for (int i = 0; i < 3; i++) {
        //            View view = LayoutInflater.from(mCtx).inflate(R.layout.setting_enter_my_block_item, null);
        //            TextView tv_expericence = view.findViewById(R.id.tv_expericence);
        //            TextView tv_year = view.findViewById(R.id.tv_year);
        //            ll_block_content.setOrientation(VERTICAL);
        //            ll_block_content.addView(view);
        //        }
    }

    private void updateExpericence() {
        ll_block_content.removeAllViews();
        //        for (SubjectResponse.Subject subject : subjects) {
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.setting_enter_my_block_item, null);
            TextView tv_expericence = view.findViewById(R.id.tv_expericence);
            TextView tv_year = view.findViewById(R.id.tv_year);
            ll_block_content.setOrientation(VERTICAL);
            ll_block_content.addView(view);
        }
    }

}
