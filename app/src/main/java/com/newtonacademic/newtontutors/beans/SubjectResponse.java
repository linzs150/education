package com.newtonacademic.newtontutors.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/4 16:04
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class SubjectResponse extends BaseBean implements Serializable {

    private List<Subject> data;

    public List<Subject> getData() {
        return data;
    }

    public void setData(List<Subject> data) {
        this.data = data;
    }

    public static class
    Subject implements Serializable {

        private long subjectId;
        private String subjectName;
        private long courseId;
        private int isNeedLevel;
        private int isNeedMark;
        private int subjectLevel;
        private long id;
        private String markHoldPlace;
        private long studentId;
        private long createTime;
        private String mark;

        public String getMarkHoldPlace() {
            return markHoldPlace;
        }

        public void setMarkHoldPlace(String markHoldPlace) {
            this.markHoldPlace = markHoldPlace;
        }

        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getStudentId() {
            return studentId;
        }

        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public int getSubjectLevel() {
            return subjectLevel;
        }

        public void setSubjectLevel(int subjectLevel) {
            this.subjectLevel = subjectLevel;
        }

        public int getIsNeedLevel() {
            return isNeedLevel;
        }

        public void setIsNeedLevel(int isNeedLevel) {
            this.isNeedLevel = isNeedLevel;
        }

        public int getIsNeedMark() {
            return isNeedMark;
        }

        public void setIsNeedMark(int isNeedMark) {
            this.isNeedMark = isNeedMark;
        }

        public long getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(long subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }

    }
}
