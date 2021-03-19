package com.one.education.user;

import android.util.Log;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class UserInstance {
    private static String TAG = "UserInstance";
    private static UserInstance sUserInstance = new UserInstance();
    private boolean mHasLogin = false;
    private UserInfo mUserInfo = null;

    public static UserInstance getInstance() {
        return sUserInstance;
    }

    /**
     * 账户登录
     *
     * @param info 账户信息
     */
    public void initialize(UserInfo info) {
        if (mHasLogin) {
            Log.e(TAG, "on login but previous no logout.");
            uninitialize();
        }

        mUserInfo = info;
        mHasLogin = true;
    }

    /**
     * 退出当前账户
     */
    public void uninitialize() {
        if (!mHasLogin) {
            return;
        }

        mHasLogin = false;
        mUserInfo = null;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public int getUserId() {
        return mUserInfo == null ? 0 : mUserInfo.getUid();
    }

    public String getToken() {
        return mUserInfo == null ? "" : mUserInfo.getToken();
    }

    public String getUserIcon() {
        return mUserInfo == null ? null : mUserInfo.getUserPhoto();
    }
}
