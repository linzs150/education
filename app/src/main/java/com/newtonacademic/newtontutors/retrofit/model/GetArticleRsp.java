package com.newtonacademic.newtontutors.retrofit.model;

/**
 * @author laiyongyang
 * @date 2020-07-03
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetArticleRsp {
    private int status;
    private String descript;
    private ArticleList data;

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

    public ArticleList getData() {
        return data;
    }

    public void setData(ArticleList data) {
        this.data = data;
    }

    public class ArticleList{
        private int id;
        private int categoryId;
        private String title;
        private String summary;
        private String keywords;
        private String tags;
        private String author;
        private String picUrl;
        private String content;
        private int isRecommend;
        private int isHot;
        private int readCount;
        private int shareCount;
        private int hitCount;
        private long createTime;

        public int getId() {
            return id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getTags() {
            return tags;
        }

        public String getAuthor() {
            return author;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public String getContent() {
            return content;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public int getIsHot() {
            return isHot;
        }

        public int getReadCount() {
            return readCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public int getHitCount() {
            return hitCount;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}
