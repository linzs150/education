package com.one.education.retrofit.model;

import com.google.gson.annotations.SerializedName;
import com.one.education.beans.BaseBean;

import java.io.Serializable;

/**
 * SmsCodeInfo：短信验证码校验信息
 *
 * @author luoq
 * @version v1.0, 2019/4/11
 */
public class SmsCodeInfo implements Serializable {

    @SerializedName("status")
    private int status;
    @SerializedName("descript")
    private String descript;
    @SerializedName("time")
    private long time;
    @SerializedName("checksum")
    private String checksum;
    @SerializedName("smsCode")
    private long smsCode;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public void setSmsCode(long smsCode) {
        this.smsCode = smsCode;
    }

    public int getStatus() {
        return status;
    }

    public String getDescript() {
        return descript;
    }

    public long getTime() {
        return time;
    }

    public String getChecksum() {
        return checksum;
    }

    public long getSmsCode() {
        return smsCode;
    }
}
