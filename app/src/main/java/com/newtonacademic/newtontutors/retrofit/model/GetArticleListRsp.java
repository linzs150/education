package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-07-03
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetArticleListRsp {
    private int status;
    private String descript;
    private List<ArticleList> data;

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

    public List<ArticleList> getData() {
        return data;
    }

    public void setData(List<ArticleList> data) {
        this.data = data;
    }

    public class ArticleList{
        private String author;
        private int categoryId;
        private long createTime;
        private int hitCount;
        private int id;
        private int isHot;
        private int isRecommend;
        private String keywords;
        private String picUrl;
        private int readCount;
        private int shareCount;
        private String summary;
        private String tags;
        private String title;

        public String getAuthor() {
            return author;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public int getHitCount() {
            return hitCount;
        }

        public int getId() {
            return id;
        }

        public int getIsHot() {
            return isHot;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public int getReadCount() {
            return readCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public String getSummary() {
            return summary;
        }

        public String getTags() {
            return tags;
        }

        public String getTitle() {
            return title;
        }
    }
}
