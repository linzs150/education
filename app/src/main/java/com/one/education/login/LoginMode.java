package com.one.education.login;

import com.one.education.user.UserInfo;

/**
 * 登录模式<br/>
 */
abstract class LoginMode {
    /**
     * 获取登录信息
     *
     * @return the login result
     */
    abstract UserLoginInfo login();

    /**
     * 登录成功
     *
     * @param userInfo 用户信息
     */
    void onLoginSuccess(UserInfo userInfo) {
    }

    /**
     * 获取账号类型
     *
     * @return the user type
     */
    abstract int getUserType();
}
