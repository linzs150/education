package com.one.education.beans;

import java.io.Serializable;

public class SmsCodeInfo extends BaseBean implements Serializable {

    private long time;
    private String checksum;
    private long smsCode;
    private boolean isExist;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public long getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(long smsCode) {
        this.smsCode = smsCode;
    }
}
