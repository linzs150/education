package com.newtonacademic.newtontutors.beans;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/4 12:20
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CourseResponse extends BaseBean {

    private List<Course> data;

    public List<Course> getData() {
        return data;
    }

    public void setData(List<Course> data) {
        this.data = data;
    }

    public static class Course {

        private long courseId;
        private String courseName;
        private String courseAlias;
        private long courseDuration;
        private long generalPrice;
        private long advancedPrice;
        private long superAdvancedPrice;

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseAlias() {
            return courseAlias;
        }

        public void setCourseAlias(String courseAlias) {
            this.courseAlias = courseAlias;
        }

        public long getCourseDuration() {
            return courseDuration;
        }

        public void setCourseDuration(long courseDuration) {
            this.courseDuration = courseDuration;
        }

        public long getGeneralPrice() {
            return generalPrice;
        }

        public void setGeneralPrice(long generalPrice) {
            this.generalPrice = generalPrice;
        }

        public long getAdvancedPrice() {
            return advancedPrice;
        }

        public void setAdvancedPrice(long advancedPrice) {
            this.advancedPrice = advancedPrice;
        }

        public long getSuperAdvancedPrice() {
            return superAdvancedPrice;
        }

        public void setSuperAdvancedPrice(long superAdvancedPrice) {
            this.superAdvancedPrice = superAdvancedPrice;
        }
    }
}
