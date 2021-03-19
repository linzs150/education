package com.one.education.beans;

import java.io.Serializable;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/20 11:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TeacherProfileResponse extends BaseBean implements Serializable {

    private TeacherProfileItem data;

    public TeacherProfileItem getData() {
        return data;
    }

    public void setData(TeacherProfileItem data) {
        this.data = data;
    }

    //    private long id;
    //    private long uid;
    //    private long teacherId;
    //    private String teacherName;
    //    private String teacherPhotoUrl;
    //    private String userPicUrl;
    //    private int education;//1：高中 2：学士3：硕士 4：博士 5：博士后 6：博导
    //    private String languageList;
    //    private String teachingExperience;
    //    private String commentStar;
    //    private int isFollow;
    //    private int state; //1:删除 0：待审核，1:审核通过，2，被拒绝，3锁定
    //    private String stateName;
    //
    //    public long getId() {
    //        return id;
    //    }
    //
    //    public void setId(long id) {
    //        this.id = id;
    //    }
    //
    //    public long getUid() {
    //        return uid;
    //    }
    //
    //    public void setUid(long uid) {
    //        this.uid = uid;
    //    }
    //
    //    public long getTeacherId() {
    //        return teacherId;
    //    }
    //
    //    public void setTeacherId(long teacherId) {
    //        this.teacherId = teacherId;
    //    }
    //
    //    public String getTeacherName() {
    //        return teacherName;
    //    }
    //
    //    public void setTeacherName(String teacherName) {
    //        this.teacherName = teacherName;
    //    }
    //
    //    public String getTeacherPhotoUrl() {
    //        return teacherPhotoUrl;
    //    }
    //
    //    public void setTeacherPhotoUrl(String teacherPhotoUrl) {
    //        this.teacherPhotoUrl = teacherPhotoUrl;
    //    }
    //
    //    public String getUserPicUrl() {
    //        return userPicUrl;
    //    }
    //
    //    public void setUserPicUrl(String userPicUrl) {
    //        this.userPicUrl = userPicUrl;
    //    }
    //
    //    public int getEducation() {
    //        return education;
    //    }
    //
    //    public void setEducation(int education) {
    //        this.education = education;
    //    }
    //
    //    public String getLanguageList() {
    //        return languageList;
    //    }
    //
    //    public void setLanguageList(String languageList) {
    //        this.languageList = languageList;
    //    }
    //
    //    public String getTeachingExperience() {
    //        return teachingExperience;
    //    }
    //
    //    public void setTeachingExperience(String teachingExperience) {
    //        this.teachingExperience = teachingExperience;
    //    }
    //
    //    public String getCommentStar() {
    //        return commentStar;
    //    }
    //
    //    public void setCommentStar(String commentStar) {
    //        this.commentStar = commentStar;
    //    }
    //
    //    public int getIsFollow() {
    //        return isFollow;
    //    }
    //
    //    public void setIsFollow(int isFollow) {
    //        this.isFollow = isFollow;
    //    }
    //
    //    public int getState() {
    //        return state;
    //    }
    //
    //    public void setState(int state) {
    //        this.state = state;
    //    }
    //
    //    public String getStateName() {
    //        return stateName;
    //    }
    //
    //    public void setStateName(String stateName) {
    //        this.stateName = stateName;
    //    }
}
