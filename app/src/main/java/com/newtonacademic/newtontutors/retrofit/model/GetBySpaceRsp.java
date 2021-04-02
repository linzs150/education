package com.newtonacademic.newtontutors.retrofit.model;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-06-25
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetBySpaceRsp {
    private int status;
    private String descript;
    private Content data;

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

    public Content getData() {
        return data;
    }

    public void setData(Content data) {
        this.data = data;
    }

    public class Content {
        private String spaceCode;
        private int picHeight;
        private int picWeight;
        private int limitCount;
        // 1：APP 2：WAP 3：WEB  4：小程序
        private int terminalType;
        //显示模式(1:轮播 2：信息流 3：banner 4：启屏 5：激励视频)
        private int displayMode;
        //广告位状态 0：待上线 1：上线  2：被下线
        private int spaceState;
        private List<Advertising> contents;

        public String getSpaceCode() {
            return spaceCode;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public int getPicWeight() {
            return picWeight;
        }

        public int getLimitCount() {
            return limitCount;
        }

        public int getTerminalType() {
            return terminalType;
        }

        public int getDisplayMode() {
            return displayMode;
        }

        public int getSpaceState() {
            return spaceState;
        }

        public List<Advertising> getContents() {
            return contents;
        }

        public class Advertising extends SimpleBannerInfo {
            private int id;
            private int spaceId;
            private String title;
            private String advertisingWords;
            private String imgUrl;
            private String linkUrl;
            private long startTime;
            private long expiredTime;
            private long versionBegin;
            private long versionEnd;
            private String channel;
            private int state;

            @Override
            public String getXBannerUrl() {
                return imgUrl;
            }

            public int getId() {
                return id;
            }

            public int getSpaceId() {
                return spaceId;
            }

            public String getTitle() {
                return title;
            }

            public String getAdvertisingWords() {
                return advertisingWords;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public long getStartTime() {
                return startTime;
            }

            public long getExpiredTime() {
                return expiredTime;
            }

            public long getVersionBegin() {
                return versionBegin;
            }

            public long getVersionEnd() {
                return versionEnd;
            }

            public String getChannel() {
                return channel;
            }

            public int getState() {
                return state;
            }
        }
    }
}
