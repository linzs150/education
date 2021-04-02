package com.newtonacademic.newtontutors.login.oauth;

/**
 * Copyright (c) 2016, Intretech All rights reserved.
 *
 * @author laipengtao
 * @version v1.0
 * @Description
 * @date 2016年7月7日 18:48:22
 */
public class OauthBean {

    private String authType;
    private String authToken;
    private String authId;
    private String authName;
    private String authIcon;


    /**
     * Instantiates a new Oauth bean.
     */
    public OauthBean() {
    }

    /**
     * Gets auth type.
     *
     * @return the auth type
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * Sets auth type.
     *
     * @param authType the auth type
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets auth token.
     *
     * @param authToken the auth token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Gets auth id.
     *
     * @return the auth id
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Sets auth id.
     *
     * @param authId the auth id
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }

    /**
     * Gets auth name.
     *
     * @return the auth name
     */
    public String getAuthName() {
        return authName;
    }

    /**
     * Sets auth name.
     *
     * @param authName the auth name
     */
    public void setAuthName(String authName) {
        this.authName = authName;
    }

    /**
     * Gets auth icon.
     *
     * @return the auth icon
     */
    public String getAuthIcon() {
        return authIcon;
    }

    /**
     * Sets auth icon.
     *
     * @param authIcon the auth icon
     */
    public void setAuthIcon(String authIcon) {
        this.authIcon = authIcon;
    }

    @Override
    public String toString() {
        return "OauthBean{" +
                "authType='" + authType + '\'' +
                ", authToken='" + authToken + '\'' +
                ", authId='" + authId + '\'' +
                ", authName='" + authName + '\'' +
                ", authIcon='" + authIcon + '\'' +
                '}';
    }
}
