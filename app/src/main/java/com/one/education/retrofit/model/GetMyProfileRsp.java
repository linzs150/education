package com.one.education.retrofit.model;

/**
 * @author laiyongyang
 * @date 2020-06-04
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetMyProfileRsp {

    private int status;
    private String descript;
    private TeacherDetailInfo data;

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

    public TeacherDetailInfo getData() {
        return data;
    }

    public void setData(TeacherDetailInfo data) {
        this.data = data;
    }

    //    "achievements":"大家都积极的角度讲",
//            "anticipateTutoringDuration":0,
//            "birthday":0,
//            "canSpeakChinese":0,
//            "colleges":"All Souls College",
//            "course":"1",
//            "courseName":"ALevel",
//            "coursePrice":500,
//            "descript":"成功",
//            "education":0,
//            "email":"122501101@qq.com",
//            "firstName":"一2",
//            "identityProof":[
//
//            ],
//            "introduction":"",
//            "isProfessionalTutor":0,
//            "lastName":"王",
//            "mobileNo":"18650328280",
//            "presentCapacity":"Graduate",
//            "referredTime":0,
//            "sex":0,
//            "state":0,
//            "stateName":"WAIT_PENDING",
//            "status":1,
//            "taughtSubjects":[
//
//            ],
//            "teacherId":16,
//            "teacherLevel":0,
//            "teachingExperience":0,
//            "timeZone":"test_time_zone",
//            "universitys":"Oxford",
//            "userName":"3tte 工",
//            "userPhotoUrl":""
    public class TeacherDetailInfo {
        private int teacherId;
        private String firstName;
        private String lastName;
        private String birthday;
        private int sex;
        private String mobileNO;
        private String email;
        private String wechat;
        private String skype;
        private int canSpeakChinese;
        private String languageList;
        private int education;
        private String universitys;
        private String colleges;
        private String presentCapacity;
        private boolean isProfessionalTutor;
        private String course;
        private String taughtSubjects;
        private String anyOtherSubjects;
        private String identityProof;
        private int teachingExperience;
        private int tutorialHoursPerWeek;
        private String anticipateTutoringDuration;
        private int referredTime;
        private String timeZone;
        private String introductoryVideoUrl;
        private String achievements;
        private String introduction;
        private int state;
        private String stateName;
        private String userName;
        private String userPhotoUrl;
        private int coursePrice;

        public int getTeacherId() {
            return teacherId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getBirthday() {
            return birthday;
        }

        public int getSex() {
            return sex;
        }

        public String getMobileNO() {
            return mobileNO;
        }

        public String getEmail() {
            return email;
        }

        public String getWechat() {
            return wechat;
        }

        public String getSkype() {
            return skype;
        }

        public int getCanSpeakChinese() {
            return canSpeakChinese;
        }

        public String getLanguageList() {
            return languageList;
        }

        public int getEducation() {
            return education;
        }

        public String getUniversitys() {
            return universitys;
        }

        public String getColleges() {
            return colleges;
        }

        public String getPresentCapacity() {
            return presentCapacity;
        }

        public boolean isProfessionalTutor() {
            return isProfessionalTutor;
        }

        public String getCourse() {
            return course;
        }

        public String getTaughtSubjects() {
            return taughtSubjects;
        }

        public String getAnyOtherSubjects() {
            return anyOtherSubjects;
        }

        public String getIdentityProof() {
            return identityProof;
        }

        public int getTeachingExperience() {
            return teachingExperience;
        }

        public int getTutorialHoursPerWeek() {
            return tutorialHoursPerWeek;
        }

        public String getAnticipateTutoringDuration() {
            return anticipateTutoringDuration;
        }

        public int getReferredTime() {
            return referredTime;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getIntroductoryVideoUrl() {
            return introductoryVideoUrl;
        }

        public String getAchievements() {
            return achievements;
        }

        public String getIntroduction() {
            return introduction;
        }

        public int getState() {
            return state;
        }

        public String getStateName() {
            return stateName;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserPhotoUrl() {
            return userPhotoUrl;
        }

        public int getCoursePrice() {
            return coursePrice;
        }
    }
}
