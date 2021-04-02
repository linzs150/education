package com.newtonacademic.newtontutors.login.oauth;

/**
 * Copyright (c) 2016, Intretech All rights reserved.
 *
 * @author laipengtao
 * @version v1.0
 * @Description
 * @date 2016年7月7日 18:48:22
 */
public class OauthImp {
//    private ServerUser.YqLinkedAccountInfo mUserInfo;
//    private IOauthResult mResultListener;
//    private Platform mPlatform;
//    private boolean mIsCheckAuth;
//
//    /**
//     * Instantiates a new Oauth imp.
//     *
//     * @param userInfo    the user info
//     * @param oauthResult the oauth result
//     */
//    public OauthImp(ServerUser.YqLinkedAccountInfo userInfo, IOauthResult oauthResult) {
//        this.mUserInfo = userInfo;
//        this.mResultListener = oauthResult;
//        this.mIsCheckAuth = true;
//        mPlatform = ShareSDK.getPlatform(unifiedTypeToString(getUserInfo().getLinkedAccountType()));
//    }
//
//    /**
//     * Instantiates a new Oauth imp.
//     *
//     * @param userInfo    the user info
//     * @param oauthResult the oauth result
//     * @param isCheckAuth 是否检查授权
//     */
//    public OauthImp(ServerUser.YqLinkedAccountInfo userInfo, boolean isCheckAuth, IOauthResult oauthResult) {
//        this.mUserInfo = userInfo;
//        this.mResultListener = oauthResult;
//        this.mIsCheckAuth = isCheckAuth;
//        mPlatform = ShareSDK.getPlatform(unifiedTypeToString(getUserInfo().getLinkedAccountType()));
//    }
//
//    /**
//     * Gets user info.
//     *
//     * @return the user info
//     */
//    public ServerUser.YqLinkedAccountInfo getUserInfo() {
//        return mUserInfo;
//    }
//
//    /**
//     * Oauth.
//     * @Description 选择是否移除第三方数据库信息
//     */
//    public void oauth() {
//        if (!mIsCheckAuth) {
//            if (null != mResultListener) {
//                mResultListener.onComplete(getOauthData(mPlatform));
//            }
//
//            return;
//        }
//
//        try {
//            if (isValid()) {
//                return;
//            }
//
//            mPlatform.removeAccount(false);
//            //检测客户端是否安装
//            if (!mPlatform.isClientValid()) {
//                if (null != mResultListener) {
//                    mResultListener.onError(OauthErrorCode.ClientUnInstall);
//                    return;
//                }
//            }
//
//            PlatformActionListener listener = new PlatformActionListener() {
//                @Override
//                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                    if (null != mResultListener) {
//                        mResultListener.onComplete(getOauthData(platform));
//                    }
//                }
//
//                @Override
//                public void onError(Platform platform, int code, Throwable throwable) {
//                    mPlatform.removeAccount(false);
//                    if (null != mResultListener) {
//                        mResultListener.onError(OauthErrorCode.UnknowError);
//                    }
//                }
//
//                @Override
//                public void onCancel(Platform platform, int i) {
//                    if (null != mResultListener) {
//                        mResultListener.onCancel();
//                    }
//                }
//            };
//
//            mPlatform.setPlatformActionListener(listener);
//            mPlatform.showUser(null);
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (null != mResultListener) {
//            mResultListener.onError(OauthErrorCode.UnknowError);
//        }
//    }
//
//    /**
//     * Is valid boolean.
//     *
//     * @return the boolean
//     */
//    public boolean isValid() {
//        mPlatform.getDb().putUserId(null == mUserInfo ? null : mUserInfo.getAuthenticateId());
//        mPlatform.getDb().putToken(null == mUserInfo ? null : mUserInfo.getAuthenticateCode());
//        if (mPlatform.isAuthValid()) {
//            if (null != mResultListener) {
//                mResultListener.onComplete(getOauthData(mPlatform));
//            }
//
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     *  统一认证类型转字符串
//     */
//    public static String unifiedTypeToString(ServerUser.YqLinkedAccountType accountType) {
//        switch (accountType) {
//            case unknown_linked_account:
//                return "";
//            case qq:
//                return SmartApplication.getContext().getString(R.string.ssdk_share_qq_name);
//            case wechat:
//                return SmartApplication.getContext().getString(R.string.ssdk_share_wechat_name);
//            case sina_weibo:
//                return SmartApplication.getContext().getString(R.string.ssdk_share_sinaweibo_name);
//            default:
//                return null;
//        }
//    }
//
//    private OauthBean getOauthData(Platform platform) {
//        OauthBean oauthBean = new OauthBean();
//        oauthBean.setAuthType(platform.getDb().getPlatformNname());
//        oauthBean.setAuthId(platform.getDb().getUserId());
//        oauthBean.setAuthToken(platform.getDb().getToken());
//        oauthBean.setAuthName(platform.getDb().getUserName());
//        oauthBean.setAuthIcon(platform.getDb().getUserIcon());
//        return oauthBean;
//    }
}
