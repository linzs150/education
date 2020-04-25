package com.one.education.education;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.activities.BaseActivity;
import com.one.education.fragments.HomeFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    FragmentManager manager;
    private Fragment[] mFragments;
    private Fragment mContent;
    private FrameLayout container;
    private FrameLayout fl_show;

    private LinearLayout home_layout;
    private ImageView home_image;
    private TextView home_text;

    private LinearLayout st_layout;
    private ImageView st_image;
    private TextView st_text;

    private LinearLayout plan_layout;
    private ImageView plan_image;
    private TextView plan_text;

    private LinearLayout my_layout;
    private ImageView my_image;
    private TextView my_text;
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initData();
        setListener();
    }

    private void initView() {


        home_layout = findViewById(R.id.home_layout);
        home_image = findViewById(R.id.home_image);
        home_text = findViewById(R.id.home_text);

        st_layout = findViewById(R.id.st_layout);
        st_image = findViewById(R.id.st_image);
        st_text = findViewById(R.id.st_text);

        plan_layout = findViewById(R.id.plan_layout);
        plan_image = findViewById(R.id.plan_image);
        plan_text = findViewById(R.id.plan_text);

        my_layout = findViewById(R.id.my_layout);
        my_image = findViewById(R.id.my_image);
        my_text = findViewById(R.id.my_text);

        home_layout.setOnClickListener(this);
        st_layout.setOnClickListener(this);
        plan_layout.setOnClickListener(this);
        my_layout.setOnClickListener(this);

    }

    private void initFragment() {
        mFragments = new Fragment[4];
        mFragments[0] = new HomeFragment();
        mFragments[1] = new HomeFragment();
        mFragments[2] = new HomeFragment();
        mFragments[3] = new HomeFragment();

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_show, mFragments[0]);
        transaction.commitAllowingStateLoss();
        mContent = mFragments[0];
        setSelected(0);


    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                setSelected(0);
                break;
            case R.id.st_layout:
                setSelected(1);
                break;
            case R.id.plan_layout:
                setSelected(2);
                break;
            case R.id.my_layout:
                setSelected(3);
                break;

        }
        switchContent(mContent, mFragments[position]);
    }

    private void setListener() {

    }

    private void setSelected(int index) {
//        StatusBarUtil.setStatusBarColor(getActivity(), Color.TRANSPARENT);
        position = index;
        switch (index) {
            case 0:
                home_image.setSelected(true);
                st_image.setSelected(false);
                plan_image.setSelected(false);
                my_image.setSelected(false);
                home_text.setSelected(true);
                st_text.setSelected(false);
                plan_text.setSelected(false);
                my_text.setSelected(false);
                break;
            case 1:
                home_image.setSelected(false);
                st_image.setSelected(true);
                plan_image.setSelected(false);
                my_image.setSelected(false);
                home_text.setSelected(false);
                st_text.setSelected(true);
                plan_text.setSelected(false);
                my_text.setSelected(false);
                break;
            case 2:
                home_image.setSelected(false);
                st_image.setSelected(false);
                plan_image.setSelected(true);
                my_image.setSelected(false);
                home_text.setSelected(false);
                st_text.setSelected(false);
                plan_text.setSelected(true);
                my_text.setSelected(false);
                break;
            case 3:
                home_image.setSelected(false);
                st_image.setSelected(false);
                plan_image.setSelected(false);
                my_image.setSelected(true);
                home_text.setSelected(false);
                st_text.setSelected(false);
                plan_text.setSelected(false);
                my_text.setSelected(true);
                break;
        }
    }

    private void switchContent(Fragment from, Fragment to) {

        if (from != to) {
            mContent = to;
            FragmentTransaction transaction = manager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fl_show, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }

    }
}
