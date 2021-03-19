package com.one.education.beans;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/30 18:11
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class QuestionResponse extends BaseBean {

    private int totalCount;
    private List<Question> data;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public static class Question {

        private long id;
        private long categoryId;
        private String title;
        private String summary;
        private String keywords;
        private String tags;
        private String author;
        private String picUrl;
        private int isRecommend;
        private int isHot;
        private long readCount;
        private long shareCount;
        private long hitCount;
        private String createTime;


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(long categoryId) {
            this.categoryId = categoryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public long getReadCount() {
            return readCount;
        }

        public void setReadCount(long readCount) {
            this.readCount = readCount;
        }

        public long getShareCount() {
            return shareCount;
        }

        public void setShareCount(long shareCount) {
            this.shareCount = shareCount;
        }

        public long getHitCount() {
            return hitCount;
        }

        public void setHitCount(long hitCount) {
            this.hitCount = hitCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
