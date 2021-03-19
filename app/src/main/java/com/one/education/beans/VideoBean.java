package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/16 21:23
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class VideoBean {

    private String videoImage;
    private String videoUrl;
    private String content;
    private String second_content;

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSecond_content() {
        return second_content;
    }

    public void setSecond_content(String second_content) {
        this.second_content = second_content;
    }
}
