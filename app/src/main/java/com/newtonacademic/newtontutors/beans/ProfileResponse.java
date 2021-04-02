package com.newtonacademic.newtontutors.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/3 15:28
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class ProfileResponse extends BaseBean {

    //    private Profle data;
    //
    //    public  Profle getData() {
    //        return data;
    //    }
    //
    //    public void setData(Profle data) {
    //        this.data = data;
    //    }
    //
    //    public static class Profle{
    private long uid;
    private String lastName;
    private String firstName;
    private int sex;
    private String userPicUrl;
    private int age;
    private long courseId;
    private String courseName;
    private int schoolYear;
    private String targetUniversitys;
    private int completedCount;
    private int reserveCount;
    private long course;
    private int englishSpokenLevel;
    private String school;
    private long studentId;
    private String targetColleges;
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        this.course = course;
    }

    public int getEnglishSpokenLevel() {
        return englishSpokenLevel;
    }

    public void setEnglishSpokenLevel(int englishSpokenLevel) {
        this.englishSpokenLevel = englishSpokenLevel;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getTargetColleges() {
        return targetColleges;
    }

    public void setTargetColleges(String targetColleges) {
        this.targetColleges = targetColleges;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTargetUniversitys() {
        return targetUniversitys;
    }

    public void setTargetUniversitys(String targetUniversitys) {
        this.targetUniversitys = targetUniversitys;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(int reserveCount) {
        this.reserveCount = reserveCount;
    }
    //    }

}
