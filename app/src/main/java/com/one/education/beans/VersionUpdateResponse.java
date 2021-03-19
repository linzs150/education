package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/24 23:19
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class VersionUpdateResponse extends BaseBean {

    private int hasNewVersion;
    private String fileUrl;
    private String newVersionName;
    private int newVersionCode;
    private String newVersionDescript;
    private long fileSize;

    public int getHasNewVersion() {
        return hasNewVersion;
    }

    public void setHasNewVersion(int hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getNewVersionName() {
        return newVersionName;
    }

    public void setNewVersionName(String newVersionName) {
        this.newVersionName = newVersionName;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    public String getNewVersionDescript() {
        return newVersionDescript;
    }

    public void setNewVersionDescript(String newVersionDescript) {
        this.newVersionDescript = newVersionDescript;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
