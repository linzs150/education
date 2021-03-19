package com.one.education.beans;

public class ChangeApplyBean {

    private long studentId;
    private long teacherId;
    private long orderCourseId;
    private long applyBeginTime;
    private long applyEndTime;
    private String appyReason;

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

    public long getOrderCourseId() {
        return orderCourseId;
    }

    public void setOrderCourseId(long orderCourseId) {
        this.orderCourseId = orderCourseId;
    }

    public long getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(long applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public long getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(long applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getAppyReason() {
        return appyReason;
    }

    public void setAppyReason(String appyReason) {
        this.appyReason = appyReason;
    }
}
