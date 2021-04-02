package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.MainActivity;
import com.newtonacademic.newtontutors.utils.Utilts;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Copyright (c) 2015, Intretech All rights reserved.
 *
 * @author zhuangjianwei
 * @version v1.0
 * @Description
 * @email fzhzjw@intretech.com
 * @date 2016年06月06日
 */
public class ActivityMgr {
    private static final String TAG = "AppActivityMgr";
    private static final String INTENT_TAG = "IntentTag";

    private static ActivityMgr sInstance = new ActivityMgr();

    private ActivityMgr() {
    }

    public static ActivityMgr get() {
        return sInstance;
    }

    private ArrayList<AppCompatActivity> mAllActivity = new ArrayList<>();
    private final HashMap<String, IntentEx> mCacheIntentEx = new HashMap<>();

    /**
     * AppActivity onCreate
     */
    public void onCreate(AppCompatActivity activity) {
        mAllActivity.add(activity);
        printActivityState(activity, "onCreate");
    }

    public void onStart(AppCompatActivity activity) {
        printActivityState(activity, "onStart");
    }

    public void onResume(AppCompatActivity activity) {
        printActivityState(activity, "onResume");
    }

    public void onPause(AppCompatActivity activity) {
        printActivityState(activity, "onPause");
    }

    public void onStop(AppCompatActivity activity) {
        printActivityState(activity, "onStop");
    }

    private void printActivityState(AppCompatActivity activity, String state) {
        Log.i("AppActivityMgr", "Activity:" + state + " class:" + activity.getClass() + " size:" + mAllActivity.size());
    }

    public void onDestroy(AppCompatActivity activity) {
        mAllActivity.remove(activity);
        printActivityState(activity, "onDestroy");
        removeCacheIntent(activity.getIntent());
    }

    /**
     * 获取当前Activity数量
     */
    public int getCurActivityCount() {
        return mAllActivity.size();
    }

    private void forceFinishAll() {
        Log.i("AppActivityMgr", "forceFinishAll : size = " + mAllActivity.size());
        for (int i = mAllActivity.size() - 1; i >= 0; i--) {
            mAllActivity.get(i).finish();
        }
    }

    /**
     * 关闭其他的页面并返回登录页面<br/>
     * 该接口需要在主线程调用
     *
     * @param info 提示消息
     */
    public void backToLoginActivity(final String info) {
        forceFinishAll();
        Context context = EducationAppliction.getInstance();
//        Intent intent = LoginActivity.newIntent(context);
        Intent intent = new Intent(context, BootActivity.class);
        intent.putExtra("event", info);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到课程表
     */
    public void backToMainActivityClassScheduleFragment() {
        for (int i = mAllActivity.size() - 1; i >= 0; i--) {
            AppCompatActivity appCompatActivity = mAllActivity.get(i);
            if (appCompatActivity instanceof MainActivity) {
                ((MainActivity) appCompatActivity).changeFragment(2);
                break;
            }

            appCompatActivity.finish();
        }
    }



    /**
     * 获取栈顶的Activity
     *
     * @return Activity
     */
    public AppCompatActivity getCurActivity() {
        if (mAllActivity.isEmpty()) {
            return null;
        }

        return mAllActivity.get(mAllActivity.size() - 1);
    }


    public interface IActivityListener {
        void onCreate(AppCompatActivity activity, int remainderActivity);

        void onDestroy(AppCompatActivity activity, int remainderActivity);
    }


    //----------------------------- IntentEx -------------------------------------------------------------------------------------------//
    void cacheIntent(IntentEx intent) {
        String tag = Utilts.randomUUIDRemoveMinusSign();
        intent.putExtra(INTENT_TAG, tag);
        mCacheIntentEx.put(tag, intent);
    }

    void removeCacheIntent(Intent intentEx) {
        String intentTag = intentEx.getStringExtra(INTENT_TAG);
        if (TextUtils.isEmpty(intentTag)) {
            return;
        }

        mCacheIntentEx.remove(intentTag);
    }

    IntentEx getCacheIntent(Intent intent) {
        if (null == intent) {
            return null;
        }

        String intentTag = intent.getStringExtra(INTENT_TAG);
        if (TextUtils.isEmpty(intentTag)) {
            return null;
        }
        return mCacheIntentEx.get(intentTag);
    }

    //----------------------------- IntentEx end----------------------------------------------------------------------------------------//
}
