package com.newtonacademic.newtontutors.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:52
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TeacherBean extends BaseBean implements Serializable {

    private int totalCount;

    private List<TeacherList> data;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<TeacherList> getData() {
        return data;
    }

    public void setData(List<TeacherList> data) {
        this.data = data;
    }

    public static class TeacherList implements Serializable {

        private long studentId;
        private long teacherId;
        private String teacherName;
        private String teacherPhotoUrl;
        private int education;
        private String languageList;
        private int teachingExperience;
        private String courseId;
        private String courseName;
        private int sex;
        private long birthday;
        private float commentStar;
        private int isFollow;

        public long getStudentId() {
            return studentId;
        }

        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }

        public long getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(long teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherPhotoUrl() {
            return teacherPhotoUrl;
        }

        public void setTeacherPhotoUrl(String teacherPhotoUrl) {
            this.teacherPhotoUrl = teacherPhotoUrl;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public String getLanguageList() {
            return languageList;
        }

        public void setLanguageList(String languageList) {
            this.languageList = languageList;
        }

        public int getTeachingExperience() {
            return teachingExperience;
        }

        public void setTeachingExperience(int teachingExperience) {
            this.teachingExperience = teachingExperience;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public float getCommentStar() {
            return commentStar;
        }

        public void setCommentStar(float commentStar) {
            this.commentStar = commentStar;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }
    }

}
