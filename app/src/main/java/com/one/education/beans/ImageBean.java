package com.one.education.beans;

public class ImageBean {

    private String descript;
    private String imageName;
    private String origUrl;
    private String savedUrl;
    private int status;
    private String url;

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOrigUrl() {
        return origUrl;
    }

    public void setOrigUrl(String origUrl) {
        this.origUrl = origUrl;
    }

    public String getSavedUrl() {
        return savedUrl;
    }

    public void setSavedUrl(String savedUrl) {
        this.savedUrl = savedUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
