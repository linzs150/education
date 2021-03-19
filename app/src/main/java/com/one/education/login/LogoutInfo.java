package com.one.education.login;

import com.google.gson.annotations.SerializedName;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class LogoutInfo {
    @SerializedName("status")
    private int status;
    @SerializedName("descript")
    private String descript;

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
}
