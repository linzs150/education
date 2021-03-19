package com.one.education.login;

import com.one.education.login.oauth.OauthBean;
import com.one.education.user.UserInfo;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public interface LoginService {
    /**
     * 短信验证登录
     * 该接口只使用在登录前
     *
     * @param callBack the call back
     */
    void login(ILoginCallBack callBack);

    /**
     * 第三方登录，登出走{@link #logout(ILogoutCallBack)}
     *
     * @param callBack the call back
     */
    void login(IOAuthLoginCallBack callBack);

    /**
     * 登出
     *
     * @param logoutCallBack the logout call back
     */
    void logout(ILogoutCallBack logoutCallBack);

    /**
     * 获取服务器时间
     *
     * @return the server time
     */
    long getServerTime();

    /**
     * 注册登录服务器监听器
     *
     * @param listener the listener
     */
    void regLoginListener(ILoginListener listener);

    /**
     * 注销登录服务器监听器
     *
     * @param listener the listener
     */
    void unregLoginListener(ILoginListener listener);

    /**
     * 登录账户监听器
     * 包括服务器登录和Ali的账户登录
     */
    interface ILoginListener {
        /**
         * 登录
         * @param user the user
         */
        void onLogin(UserInfo user);

        /**
         * 登出
         */
        void onLogout();
    }


    /**
     * The interface Login call back.
     */
    interface ILoginCallBack {
        /**
         * On login.
         *
         * @param userLoginInfo
         */
        void onLogin(UserLoginInfo userLoginInfo);
    }

    /**
     * The interface Logout call back.
     */
    interface ILogoutCallBack {
        /**
         * On logout.
         */
        void onLogout(int loginResponseCode, String message);
    }

    /**
     * 第三方登录, 登出走{@link ILogoutCallBack}
     * The interface Login call back.
     */
    interface IOAuthLoginCallBack {
        /**
         * On login.
         *
         * @param loginResultCode the ResponseResult.ResponseCode
         * @param message         the message
         * @param oauthBean       授权信息
         * @param userLinkPhone   用户关联的手机号
         */
        void onLogin(int loginResultCode, String message, OauthBean oauthBean, String userLinkPhone);
    }
}
