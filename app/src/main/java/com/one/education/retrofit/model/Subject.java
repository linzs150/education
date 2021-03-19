package com.one.education.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("id")
    private int id;
    @SerializedName("subjectId")
    private int subjectId;
    @SerializedName("subjectName")
    private String subjectName;
    @SerializedName("teacherId")
    private int teacherId;
    @SerializedName("courseId")
    private int courseId;
    @SerializedName("courseName")
    private String courseName;
    @SerializedName("subjectLevel")
    private int subjectLevel;
    @SerializedName("mark")
    private String mark;

    public int getId() {
        return id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getSubjectLevel() {
        return subjectLevel;
    }

    public String getMark() {
        return mark;
    }
}
