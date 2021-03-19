package com.one.education.login;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.one.education.EducationAppliction;
import com.one.education.com.netease.nim.DemoCache;
import com.one.education.com.netease.nim.Preferences;
import com.one.education.commons.Constants;
import com.one.education.commons.SharedPreferencesUtils;
import com.one.education.education.R;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.ResponseResult;
import com.one.education.service.ConnectBroadcastReceiver;
import com.one.education.thread.LoginScheduleThreadProxy;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.user.UserInfo;
import com.one.education.user.UserInstance;

import java.util.ArrayList;
import java.util.TimerTask;


/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class LoginServiceImpl implements LoginService {
    private static String TAG = "LoginServiceImpl";
    /**
     * 登录侦听器
     */
    private final ArrayList<ILoginListener> mLoginListeners = new ArrayList<>();
    private LoginScheduleThreadProxy mLoginScheduleThreadProxy;
    private Handler mHandler;
    /**
     * 由于第一次注册广播时会触发广播的回调，会导致重连，做个标记为赖过滤第一次广播
     * 是否是第一次接收网络变化的广播
     */
    private static boolean sFirstReceiverNetworkChange = true;
    /**
     * 已经登录过
     */
    private boolean mLogin = false;
    /**
     * 已经登录成功过
     */
    private boolean mHasLoggedIn = false;
    /**
     * 登录模式
     */
    private LoginMode mLoginMode = null;

    /**
     * 登录的用户信息
     */
    private UserInfo mUserInfo;

    /**
     * Instantiates a new Login service imp.
     */
    public LoginServiceImpl() {
        mHandler = new Handler(Looper.getMainLooper());
        mLoginScheduleThreadProxy = ThreadPoolProxyFactory.getLoginScheduleThreadProxy();
        ConnectBroadcastReceiver.regListener(mNetworkChangeListener);
    }

    /**
     * Destroy.
     */
    public void destroy() {
        ConnectBroadcastReceiver.unregListener(mNetworkChangeListener);
        Log.i(TAG, "uninitialize");
    }

    /**
     * Sets login mode.
     *
     * @param loginMode the login mode
     */
    public void setLoginMode(LoginMode loginMode) {
        mLoginMode = loginMode;
    }

    /**
     * 用户已经登录
     */
    private boolean userHasLogin() {
        //重登条件
        //1.有登录
        //2.有登录成功过
        return mLogin && mHasLoggedIn;
    }

    @Override
    public void login(ILoginCallBack callBack) {
        mHasLoggedIn = false;
        mLogin = true;
        mLoginScheduleThreadProxy.schedule(new LoginTask(callBack));
    }

    @Override
    public void login(IOAuthLoginCallBack callBack) {
        mHasLoggedIn = false;
        mLogin = true;
        mLoginScheduleThreadProxy.schedule(new OAuthLoginTask(callBack));
    }

    @Override
    public void logout(ILogoutCallBack logoutCallBack) {
        Log.i(TAG, "logout.");
        mHasLoggedIn = false;
        mLogin = false;
        mLoginScheduleThreadProxy.schedule(new LogoutTask(logoutCallBack));
    }

    @Override
    public long getServerTime() {
        return 0;
    }

    @Override
    public void regLoginListener(ILoginListener listener) {
        synchronized (mLoginListeners) {
            mLoginListeners.add(listener);
        }
    }

    @Override
    public void unregLoginListener(ILoginListener listener) {
        synchronized (mLoginListeners) {
            mLoginListeners.remove(listener);
        }
    }

    /**
     * 普通登录任务
     */
    private class LoginTask implements Runnable {
        private final ILoginCallBack mCallBack;

        /**
         * Instantiates a new Login task.
         *
         * @param callBack the call back
         */
        LoginTask(ILoginCallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void run() {
            taskRun(mCallBack);
        }
    }

    /**
     * 授权登录任务
     */
    private class OAuthLoginTask implements Runnable {
        private final IOAuthLoginCallBack mCallBack;

        OAuthLoginTask(IOAuthLoginCallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void run() {
            taskRun(mCallBack);
        }
    }

    /**
     * 登录任务
     *
     * @param callback 回调对象
     */
    private void taskRun(final Object callback) {
        UserLoginInfo userLoginInfo = mLoginMode.login();
        if (userLoginInfo.getStatus() == ResponseResult.ResponseCode.SUCCESS) {
            UserInfo userInfo = new UserInfo();
            userInfo.setToken(userLoginInfo.getToken());
            userInfo.setAccount(userLoginInfo.getAccount());
            userInfo.setMobileNO(userLoginInfo.getMobileNO());
            userInfo.setLastLoginTime(userLoginInfo.getLastLoginTime());
            userInfo.setSex(userLoginInfo.getSex());
            userInfo.setUid(userLoginInfo.getUid());
            userInfo.setUserName(userLoginInfo.getUserName());
            userInfo.setUserNick(userLoginInfo.getUserNick());
            userInfo.setUserPhoto(userLoginInfo.getUserPhoto());
            userInfo.setUserType(userLoginInfo.getUserType());
            UserInstance.getInstance().initialize(userInfo);//去除登出
            doLogin(userLoginInfo, callback);
            return;
        }

        if (userLoginInfo.getUserType() == UserLoginInfo.TEACHER) {
            userLoginInfo.setDescript(EducationAppliction.getContext().getString(R.string.only_login_student));
        }

        if (callback instanceof ILoginCallBack) {
            ((ILoginCallBack) callback).onLogin(userLoginInfo);
        }
    }

    public void doLogin(final UserLoginInfo loginInfo, final Object callback) {
        ImLoginInfo imLoginInfo = HttpsServiceFactory.getImLoginInfo();
        if (imLoginInfo.getStatus() == ResponseResult.ResponseCode.FAILURE) {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
            userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
            if (callback instanceof ILoginCallBack) {
                ((ILoginCallBack) callback).onLogin(userLoginInfo);
            }

            return;
        }

        LoginInfo info = new LoginInfo(imLoginInfo.getAccountId(), imLoginInfo.getToken3rd(),
                EducationAppliction.getContext().getString(R.string.nim_key), 0);
        RequestCallback<LoginInfo> requestCallback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        DemoCache.setAccount(param.getAccount());
                        Preferences.saveUserAccount(param.getAccount());
                        Preferences.saveUserToken(param.getToken());
                        onLoginSuccess(UserInstance.getInstance().getUserInfo());
                        String info = JSONObject.toJSONString(loginInfo);
                        SharedPreferencesUtils.getInstance().putString("userInfo", info);
                        SharedPreferencesUtils.getInstance().putInt("uid", loginInfo.getUid());
                        SharedPreferencesUtils.getInstance().putString(Constants.TOKEN, loginInfo.getToken());
                        if (callback instanceof ILoginCallBack) {
                            ((ILoginCallBack) callback).onLogin(loginInfo);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        UserLoginInfo userLoginInfo = new UserLoginInfo();
                        userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                        userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
                        if (callback instanceof ILoginCallBack) {
                            ((ILoginCallBack) callback).onLogin(userLoginInfo);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // your code
                        Log.e(TAG, "loginImp exception : " + exception.getMessage());
                        UserLoginInfo userLoginInfo = new UserLoginInfo();
                        userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                        userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
                        if (callback instanceof ILoginCallBack) {
                            ((ILoginCallBack) callback).onLogin(userLoginInfo);
                        }
                    }

                };

        //执行手动登录
        NIMClient.getService(AuthService.class).login(info).setCallback(requestCallback);
    }

    /**
     * 登陆成功
     *
     * @param userInfo 用户信息
     */
    private void onLoginSuccess(UserInfo userInfo) {
        mUserInfo = userInfo;
        LoginMode loginMode = mLoginMode;
        if (null == loginMode) {
            Log.e(TAG, "loginImp : loginMode is null.");
            return;
        }

        loginMode.onLoginSuccess(userInfo);
        synchronized (mLoginListeners) {
            for (ILoginListener listener : mLoginListeners) {
                listener.onLogin(userInfo);
            }
        }
    }

    /**
     * 账号登出任务
     */
    private class LogoutTask extends TimerTask {
        private final ILogoutCallBack mLogoutCallBack;

        private LogoutTask(ILogoutCallBack logoutCallBack) {
            mLogoutCallBack = logoutCallBack;
        }

        @Override
        public void run() {
            UserInfo userInfo = mUserInfo;
            mUserInfo = null;
            boolean logoutSuccess;
            if (userInfo == null) {
                logoutSuccess = true;
                if (null != mLogoutCallBack) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mLogoutCallBack.onLogout(ResponseResult.ResponseCode.SUCCESS,
                                    ResponseResult.getString(ResponseResult.ResponseCode.SUCCESS));
                        }
                    });
                }
            } else {
                final LogoutInfo logoutInfo = HttpsServiceFactory.logout(userInfo.getToken());
                if (null != mLogoutCallBack) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mLogoutCallBack.onLogout(logoutInfo.getStatus(), logoutInfo.getDescript());
                        }
                    });
                }

                logoutSuccess = logoutInfo.getStatus() == ResponseResult.ResponseCode.SUCCESS;
            }

            if (logoutSuccess) {
                UserInstance.getInstance().uninitialize();
                synchronized (mLoginListeners) {
                    for (ILoginListener listener : mLoginListeners) {
                        listener.onLogout();
                    }
                }
            }
        }
    }


    /**
     * 本地网络广播监听器
     */
    private ConnectBroadcastReceiver.INetworkChangeListener mNetworkChangeListener = new ConnectBroadcastReceiver.INetworkChangeListener() {
        @Override
        public void onChange(boolean connected) {
            Log.i(TAG, "INetworkChangeListener.onChange[connected = " + connected);
            if (sFirstReceiverNetworkChange) {
                sFirstReceiverNetworkChange = false;
                return;
            }

            //1：网络已经连接上，2.用户已经登录
            //注：用户已经登录后出现网络连接上，说明之前有断开，需要进行重连
            //当connected为false时，表示没有可用网络，不需要进行重连
            if (connected && userHasLogin()) {
                //重新连接
            }
        }
    };
}
