package com.newtonacademic.newtontutors.login;

import com.google.gson.annotations.SerializedName;

public class ImLoginInfo {
    @SerializedName("status")
    private int status;
    @SerializedName("descript")
    private String descript;
    @SerializedName("data")
    private TokenInfo data;

    public String getAccountId() {
        if (data == null) {
            return "";
        }
        return data.getAccountId();
    }

    public String getToken3rd() {
        if (data == null) {
            return "";
        }
        return data.getToken3rd();
    }

    public int getStatus() {
        return status;
    }

    public String getDescript() {
        return descript;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    private class TokenInfo {
        @SerializedName("token3rd")
        private String token3rd;
        @SerializedName("accountId")
        private String accountId;
        @SerializedName("uid")
        private String uid;
        @SerializedName("lastModifyTime")
        private String lastModifyTime;
        @SerializedName("createTime")
        private String createTime;

        public String getToken3rd() {
            return token3rd;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getUid() {
            return uid;
        }

        public String getLastModifyTime() {
            return lastModifyTime;
        }

        public String getCreateTime() {
            return createTime;
        }
    }
}
