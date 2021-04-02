package com.newtonacademic.newtontutors.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.commons.SharedPreferencesUtils;
import com.newtonacademic.newtontutors.db.DBManager;
import com.newtonacademic.newtontutors.db.IDMLResultListener;
import com.newtonacademic.newtontutors.db.bean.DUser;
import com.newtonacademic.newtontutors.retrofit.HttpsServiceFactory;
import com.newtonacademic.newtontutors.retrofit.ResponseResult;
import com.newtonacademic.newtontutors.user.UserInfo;


/**
 * @author lyy
 * @Description 账号登录模式
 */
public class UserLoginMode extends LoginMode {
    private static final String TAG = "UserLoginMode";
    /**
     * 登录账号
     */
    private String mUserCellphone;
    /**
     * 登录密码
     */
    private String mUserPassword;

    /**
     * 登录账号类型
     */
    private int mUserType;

    /**
     * Instantiates a new User login mode.
     *
     * @param cellphone the cellphone
     * @param password  the password
     */
    public UserLoginMode(String cellphone, String password) {
        this.mUserCellphone = cellphone;
        this.mUserPassword = password;
    }

    @NonNull
    @Override
    int getUserType() {
        return mUserType;
    }

    @Override
    UserLoginInfo login() {
        if (!TextUtils.isEmpty(mUserPassword)) {
            Log.i(TAG, "getLoginInfo : password login.");
            return getLoginInfoByPasswordLogin();
        }


        Log.i(TAG, "getLoginInfo : failed.");
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
        userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
        return userLoginInfo;
    }

    /**
     * 密码登录
     *
     * @return 登录结果
     */
    private UserLoginInfo getLoginInfoByPasswordLogin() {
        return HttpsServiceFactory.login(mUserCellphone, mUserPassword);
    }

    @Override
    protected void onLoginSuccess(final UserInfo userInfo) {
        if (null == userInfo) {
            Log.e(TAG, "onLoginSuccess : userInfo is null.");
            return;
        }

        IDMLResultListener<DUser> listener = result -> {
            DUser dUser = null == result ? new DUser() : result;
            dUser.setUserId(userInfo.getUid());
            dUser.setUserType(userInfo.getUserType());
            dUser.setAccount(userInfo.getAccount());
            dUser.setUserName(userInfo.getUserName());
            dUser.setUserNick(userInfo.getUserNick());
            dUser.setSex(userInfo.getSex());
            dUser.setIconResource(userInfo.getUserPhoto());
            dUser.setCellphone(mUserCellphone);
            dUser.setToken(userInfo.getToken());
            dUser.setLastLoginTime(String.valueOf(SystemClock.elapsedRealtime()));
            DBManager.getInstance().getUserDao().save(dUser, null);

            String info = JSONObject.toJSONString(userInfo);
            SharedPreferencesUtils.getInstance().putString("userInfo",info);
            SharedPreferencesUtils.getInstance().putInt("uid",userInfo.getUid());
            SharedPreferencesUtils.getInstance().putString(Constants.TOKEN,userInfo.getToken());
        };

        DBManager.getInstance().getUserDao().getUserByUserId(userInfo.getUid(), listener);
        SharedPreferences sharedPreferences = EducationAppliction.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCES_BOOT_CONFIG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SHARED_PREFERENCES_BOOT_CONFIG_AUTO_LOGIN, true);
        editor.apply();
        super.onLoginSuccess(userInfo);
    }
}
