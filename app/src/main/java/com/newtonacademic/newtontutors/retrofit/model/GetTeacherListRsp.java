package com.newtonacademic.newtontutors.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-05-04
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetTeacherListRsp {
    @SerializedName("status")
    private int status;
    @SerializedName("descript")
    private String descript;
    @SerializedName("data")
    private List<UserTeacherInfo> datas;

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

    public List<UserTeacherInfo> getDatas() {
        return datas;
    }

    public class UserTeacherInfo {
        @SerializedName("id")
        private int id;
        @SerializedName("uid")
        private int userId;
        @SerializedName("teacherId")
        private int teacherId;
        @SerializedName("teacherName")
        private String teacherName;
        @SerializedName("teacherPhotoUrl")
        private String teacherPhotoUrl;
        @SerializedName("universitys")
        private String universitys;
        @SerializedName("colleges")
        private String colleges;
        @SerializedName("education")
        private int education;
        @SerializedName("languageList")
        private String languageList;
        @SerializedName("teachingExperience")
        private int teachingExperience;
        @SerializedName("isFollow")
        private int isFollow;
        @SerializedName("commentStar")
        private float commentStar;
        @SerializedName("teacherLevel")
        private int teacherLevel;
        @SerializedName("taughtSubjects")
        private List<Subject> subjects;
        @SerializedName("course")
        private String course;
        @SerializedName("courseName")
        private String courseName;

        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
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

        public String getUniversitys() {
            return universitys;
        }

        public String getColleges() {
            return colleges;
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

        public int getIsFollow() {
            return isFollow;
        }

        public float getCommentStar() {
            return commentStar;
        }

        public int getTeacherLevel() {
            return teacherLevel;
        }

        public List<Subject> getSubjects() {
            return subjects;
        }

        public String getCourse() {
            return course;
        }

        public String getCourseName() {
            return courseName;
        }
    }
}
