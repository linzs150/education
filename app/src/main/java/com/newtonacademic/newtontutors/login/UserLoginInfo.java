package com.newtonacademic.newtontutors.login;

import com.google.gson.annotations.SerializedName;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class UserLoginInfo {
    @SerializedName("status")
    private int status;
    @SerializedName("descript")
    private String descript;
    @SerializedName("token")
    private String token;
    /**
     * 用户类型
     * 10：学生
     * 20：教师
     */
    @SerializedName("userType")
    private int userType;
    @SerializedName("uid")
    private int uid;
    @SerializedName("account")
    private String account;
    @SerializedName("mobileNO")
    private String mobileNO;
    @SerializedName("userName")
    private String userName;
    @SerializedName("userNick")
    private String userNick;
    @SerializedName("sex_female")
    private int sex;
    @SerializedName("userPhoto")
    private String userPhoto;
    @SerializedName("lastLoginTime")
    private String lastLoginTime;

    public static int STUDENT = 10;
    public static int TEACHER = 20;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
