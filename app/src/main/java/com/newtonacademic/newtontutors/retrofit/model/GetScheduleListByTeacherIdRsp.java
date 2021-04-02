package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-06-21
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetScheduleListByTeacherIdRsp {
    private int status;
    private String descript;
    private List<ScheduleListByTeacherIdInfo> data;

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

    public List<ScheduleListByTeacherIdInfo> getData() {
        return data;
    }

    public class ScheduleListByTeacherIdInfo {
        private int scheduleId;
        private String timeZone;
        // 程时间类型
        //1:本月
        //2：本季度
        //3：本年
        //4：自定义
        private int timeIntervalType;
        //日期
        private long timeIntervalBegin;
        //日期
        private long timeIntervalEnd;
        private long createTime;
        private List<Entries> entries;

        public int getScheduleId() {
            return scheduleId;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public int getTimeIntervalType() {
            return timeIntervalType;
        }

        public long getTimeIntervalBegin() {
            return timeIntervalBegin;
        }

        public long getTimeIntervalEnd() {
            return timeIntervalEnd;
        }

        public long getCreateTime() {
            return createTime;
        }

        public List<Entries> getEntries() {
            return entries;
        }

        public class Entries {
            private int entryId;
            //            1：可预约
//            2：不可预约
            private int scheduleEntryType;
            //8:00->8 * 60 * 60
            private long timeBegin;
            private long timeEnd;
            private long scheduleDate;
            private String weekDays;
            private long scheduleId;
            private int weekIndex;
            private long createTime;

            public long getScheduleId() {
                return scheduleId;
            }

            public void setScheduleId(long scheduleId) {
                this.scheduleId = scheduleId;
            }

            public int getWeekIndex() {
                return weekIndex;
            }

            public void setWeekIndex(int weekIndex) {
                this.weekIndex = weekIndex;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getEntryId() {
                return entryId;
            }

            public int getEntryType() {
                return scheduleEntryType;
            }

            public long getBeginTime() {
                return timeBegin;
            }

            public long getEndTime() {
                return timeEnd;
            }

            public long getScheduleDate() {
                return scheduleDate;
            }

            public String getWeekDays() {
                return weekDays;
            }
        }
    }
}
