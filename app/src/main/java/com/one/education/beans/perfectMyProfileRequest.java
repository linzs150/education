package com.one.education.beans;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/20 13:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class perfectMyProfileRequest {

    private String lastName;
    private String firstName;
    private String birthday;
    private int sex;//1：男 2：女 0：其他
    private String studentIdCard;
    private String mobileNO;
    private String email;
    private String wechat;//""
    private String skype;
    private String school;
    private String schoolYear;
    private String englishSpokenLevel;
    private String course;
    private String targetUniversitys;
    private String targetColleges;
    private List<SubjectResponse.Subject> studiedSubjects;
    private String studiedSubjectLevels;//Standard Level:1    Higher Level:2
    private String userName;
    private int studentId;
    private String regIp;
    private String userPhotoUrl;
    private String courseName;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(String studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }


    public String getEnglishSpokenLevel() {
        return englishSpokenLevel;
    }

    public void setEnglishSpokenLevel(String englishSpokenLevel) {
        this.englishSpokenLevel = englishSpokenLevel;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTargetUniversitys() {
        return targetUniversitys;
    }

    public void setTargetUniversitys(String targetUniversitys) {
        this.targetUniversitys = targetUniversitys;
    }

    public String getTargetColleges() {
        return targetColleges;
    }

    public void setTargetColleges(String targetColleges) {
        this.targetColleges = targetColleges;
    }

    public List<SubjectResponse.Subject> getStudiedSubjects() {
        return studiedSubjects;
    }

    public void setStudiedSubjects(List<SubjectResponse.Subject> studiedSubjects) {
        this.studiedSubjects = studiedSubjects;
    }

    public String getStudiedSubjectLevels() {
        return studiedSubjectLevels;
    }

    public void setStudiedSubjectLevels(String studiedSubjectLevels) {
        this.studiedSubjectLevels = studiedSubjectLevels;
    }
}
