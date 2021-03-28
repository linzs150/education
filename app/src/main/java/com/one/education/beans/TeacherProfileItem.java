package com.one.education.beans;

import com.one.mylibrary.TaughtSubjects;

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/21 11:38
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TeacherProfileItem implements Serializable {

    private long teacherId;
    private String teacherName;
    private String userPicUrl;
    private long birthday;
    private int sex;
    private int state;
    private String stateName;
    private String country;
    private String province;
    private String city;
    private String course;
    private String courseName;
    private float coursePrice;
    private String presentCapacity;
    private String colleges;
    private int canSpeakChinese;
    private List<TaughtSubjects> taughtSubjects;
    private int teacherLevel;
    private List<String> identityProof;
    private String universitys;
    private String achievements;
    private String introduction;
    private String introductoryVideoUrl;
    private int education;
    private String languageList;
    private int teachingExperience;
    private float commentStar;
    private int isFollow;
    private int isRecommend;
    private String timeZone;




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

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(float coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getPresentCapacity() {
        return presentCapacity;
    }

    public void setPresentCapacity(String presentCapacity) {
        this.presentCapacity = presentCapacity;
    }

    public String getColleges() {
        return colleges;
    }

    public void setColleges(String colleges) {
        this.colleges = colleges;
    }

    public int getCanSpeakChinese() {
        return canSpeakChinese;
    }

    public void setCanSpeakChinese(int canSpeakChinese) {
        this.canSpeakChinese = canSpeakChinese;
    }

    public List<TaughtSubjects> getTaughtSubjects() {
        return taughtSubjects;
    }

    public void setTaughtSubjects(List<TaughtSubjects> taughtSubjects) {
        this.taughtSubjects = taughtSubjects;
    }

    public int getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(int teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public List<String> getIdentityProof() {
        return identityProof;
    }

    public void setIdentityProof(List<String> identityProof) {
        this.identityProof = identityProof;
    }

    public String getUniversitys() {
        return universitys;
    }

    public void setUniversitys(String universitys) {
        this.universitys = universitys;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroductoryVideoUrl() {
        return introductoryVideoUrl;
    }

    public void setIntroductoryVideoUrl(String introductoryVideoUrl) {
        this.introductoryVideoUrl = introductoryVideoUrl;
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

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
