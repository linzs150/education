package com.newtonacademic.newtontutors.login.oauth;

/**
 * Copyright (c) 2016, Intretech All rights reserved.
 *
 * @author laipengtao
 * @version v1.0
 * @Description
 * @date 2016年7月7日 18:48:22
 */
public interface IOauthResult {
    /**
     * On complete.
     *
     * @param oauthData the oauth data
     */
     void onComplete(OauthBean oauthData);

    /**
     * On error.
     */
    void onError(OauthErrorCode code);

    /**
     * On cancel.
     */
    void onCancel();
}
