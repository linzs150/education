package com.one.education.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author lyy
 * FragmentActivityEx扩展类
 */
public class FragmentActivityEx extends AppCompatActivity {
    private static final String TAG = "FragmentActivityEx";
    private static final int ACTIVITY_STATE_NONE = 0;
    private static final int ACTIVITY_STATE_ON_CREATE = 1;
    private static final int ACTIVITY_STATE_ON_START = 2;
    private static final int ACTIVITY_STATE_ON_RESUME = 3;
    private static final int ACTIVITY_STATE_ON_PAUSE = 4;
    private static final int ACTIVITY_STATE_ON_STOP = 5;
    private static final int ACTIVITY_STATE_ON_DESTROY = 6;

    private int mActivityState = ACTIVITY_STATE_NONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMgr.get().onCreate(this);
        mActivityState = ACTIVITY_STATE_ON_CREATE;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityMgr.get().onStart(this);
        mActivityState = ACTIVITY_STATE_ON_START;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityMgr.get().onResume(this);
        mActivityState = ACTIVITY_STATE_ON_RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityMgr.get().onPause(this);
        mActivityState = ACTIVITY_STATE_ON_PAUSE;
    }

    protected boolean isResume() {
        return ACTIVITY_STATE_ON_RESUME == mActivityState;
    }

    protected boolean isStop() {
        return ACTIVITY_STATE_ON_STOP == mActivityState;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityMgr.get().onStop(this);
        mActivityState = ACTIVITY_STATE_ON_STOP;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityMgr.get().onDestroy(this);
        mActivityState = ACTIVITY_STATE_ON_DESTROY;
    }

    public boolean hasDestroyed() {
        return ACTIVITY_STATE_ON_DESTROY == mActivityState;
    }

    @Override
    public void startActivity(Intent intent) {
        if (intent instanceof IntentEx) {
            startActivity((IntentEx) intent);
            return;
        }

        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent instanceof IntentEx) {
            startActivityForResult((IntentEx) intent, requestCode);
            return;
        }

        super.startActivityForResult(intent, requestCode);
    }

    public void startActivity(IntentEx intent) {
        try {
            ActivityMgr.get().cacheIntent(intent);
            super.startActivity(intent);
        } catch (Exception e) {
            ActivityMgr.get().removeCacheIntent(intent);
            throw e;
        }
    }

    public void startActivityForResult(IntentEx intent, int requestCode) {
        try {
            ActivityMgr.get().cacheIntent(intent);
            super.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            ActivityMgr.get().removeCacheIntent(intent);
            throw e;
        }
    }

    public IntentEx getIntentEx() {
        return ActivityMgr.get().getCacheIntent(getIntent());
    }
}
