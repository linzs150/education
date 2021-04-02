package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-06-18
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetMyFollowTeacherList {
    private int status;
    private String descript;
    private int totalCount;
    private List<MyFollowTeacherList> data;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MyFollowTeacherList> getData() {
        return data;
    }

    public class MyFollowTeacherList{
        private int id;
        private int teacherId;
        private String teacherName;
        private String teacherPhotoUrl;
        private int education;
        private String languageList;
        private int teachingExperience;

        public int getId() {
            return id;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeacherPhotoUrl() {
            return teacherPhotoUrl;
        }

        public int getEducation() {
            return education;
        }

        public String getLanguageList() {
            return languageList;
        }

        public int getTeachingExperience() {
            return teachingExperience;
        }
    }

}
