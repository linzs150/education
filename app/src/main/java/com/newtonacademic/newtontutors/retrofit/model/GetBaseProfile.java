package com.newtonacademic.newtontutors.retrofit.model;

/**
 * @author laiyongyang
 * @date 2020-06-04
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetBaseProfile {
    /*"data":{
        "teacherId":15,
                "teacherName":"一 王",
                "userPicUrl":"http://121.idaguo.com/upload/edu/2020/05/24/20200524011107253.png",
                "birthday":1577721600,
                "sex_female":1,
                "state":0,
                "stateName":"待审核",
                "country":null,
                "province":null,
                "city":null,
                "course":0,
                "courseName":"",
                "coursePrice":0,
                "teacherLevel":0,
                "identityProof":[
        "http://121.idaguo.com/upload/edu/2020/05/06/20200506115433358.png",
                "http://121.idaguo.com/upload/edu/2020/05/06/20200506115439007.png",
                "http://121.idaguo.com/upload/edu/2020/05/06/20200506115444538.png",
                "http://121.idaguo.com/upload/edu/2020/05/06/20200506115500296.png"
        ],
        "universitys":"Cambridge",
                "achievements":"大家都积极的角度讲",
                "introduction":"Dead fad",
                "introductoryVideoUrl":"",
                "education":0,
                "languageList":"",
                "teachingExperience":34,
                "commentStar":0,
                "isFollow":0,
                "isRecommend":0
    },
            "descript":"成功",
            "status":1*/
    private int status;
    private String descript;
    private TeacherBaseInfo data;

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

    public TeacherBaseInfo getData() {
        return data;
    }

    public void setData(TeacherBaseInfo data) {
        this.data = data;
    }

    public class TeacherBaseInfo {
        private int id;
        private int uid;
        private int teacherId;
        private String teacherName;
        private String teacherPhotoUrl;
        private String userPicUrl;
        private int education;
        private String languageList;
        private int teachingExperience;
        private float commentStar;
        private int isFollow;
        private int state;
        private String stateName;
        private String country;
        private String province;
        private String city;
        private int sex;//1男2女
        private long birthday;
        private int coursePrice;

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeacherPhotoUrl() {
            return teacherPhotoUrl;
        }

        public String getUserPicUrl() {
            return userPicUrl;
        }

        public int getEducation() {
            return education;
        }

        public String getLanguageList() {
            return languageList;
        }

        public int getTeachingExperience() {
            return teachingExperience;
        }

        public float getCommentStar() {
            return commentStar;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public int getState() {
            return state;
        }

        public String getStateName() {
            return stateName;
        }

        public String getCountry() {
            return country;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public int getSex() {
            return sex;
        }

        public long getBirthday() {
            return birthday;
        }

        public int getCoursePrice() {
            return coursePrice;
        }
    }
}
