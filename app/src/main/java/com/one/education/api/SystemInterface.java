package com.one.education.api;

import android.util.Log;

import com.one.education.login.LoginService;
import com.one.education.login.LoginServiceImpl;
import com.one.education.login.UserLoginMode;
import com.one.education.retrofit.ResponseResult;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class SystemInterface {
    private final String TAG = "SystemInterface";
    private LoginServiceImpl mLoginService;
    private static SystemInterface sInstance = new SystemInterface();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SystemInterface getInstance() {
        return sInstance;
    }

    /**
     * 用户登录初始化
     *
     * @param userCode     用户账号(手机号或账号)
     * @param userPassword 用户密码
     * @param callback     the callback
     */
    public void login(final String userCode, final String userPassword, final LoginService.ILoginCallBack callback) {
        loginImp(userCode, userPassword, callback);
    }

    /**
     * 账号登录
     */
    private void loginImp(final String userCode, final String userPassword, final LoginService.ILoginCallBack callback) {
        if (null != mLoginService) {
            Log.w(TAG, "loginImp : uninitialize old loginService.");
            mLoginService.destroy();
        }

        mLoginService = new LoginServiceImpl();
        mLoginService.setLoginMode(new UserLoginMode(userCode, userPassword));
        mLoginService.login(callback);
    }

    /**
     * Logout.
     *
     * @param logoutCallBack the logout call back
     */
    public void logout(final LoginService.ILogoutCallBack logoutCallBack) {
        Log.i(TAG, "logout");
        if (null == mLoginService) {
            Log.i(TAG, "loginService is null.");
        }

        final LoginServiceImpl loginService = mLoginService;
        mLoginService = null;
        if (null != loginService) {
            loginService.logout(logoutCallBack);
        } else if (null != logoutCallBack) {
            logoutCallBack.onLogout(ResponseResult.ResponseCode.SUCCESS,
                    ResponseResult.getString(ResponseResult.ResponseCode.SUCCESS));
        }
    }
}
