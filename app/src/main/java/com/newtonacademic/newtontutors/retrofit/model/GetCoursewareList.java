package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-05-31
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetCoursewareList {

    private int status;
    private String descript;
    private int totalCount;
    private List<CoursewareInfo> data;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CoursewareInfo> getData() {
        return data;
    }

    public void setData(List<CoursewareInfo> data) {
        this.data = data;
    }

    //    "coursewareId":14,
//            "orderCourseId":179,
//            "courseId":1,
//            "studentId":16,
//            "teacherId":15,
//            "coursewareName":"IOS_SDK开发规范.docx",
//            "coursewareRemark":"",
//            "coursewareUrl":"http://121.idaguo.com/upload/edu/2020/06/30/20200630223059764.docx",
//            "uploadUserId":15,
//            "uploadUser":"chai ted",
//            "uploadTime":1593527459,
//            "isDel":0
    public class CoursewareInfo {
        private int coursewareId;
        private int orderCourseId;
        private int courseId;
        private int studentId;
        private int teacherId;
        private String coursewareName;
        private String coursewareRemark;
        private String coursewareUrl;
        private int uploadUserId;
        private String uploadUser;
        private long uploadTime;
        private int isDel;

        public int getCoursewareId() {
            return coursewareId;
        }

        public int getOrderCourseId() {
            return orderCourseId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getStudentId() {
            return studentId;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public String getCoursewareName() {
            return coursewareName;
        }

        public String getCoursewareRemark() {
            return coursewareRemark;
        }

        public String getCoursewareUrl() {
            return coursewareUrl;
        }

        public int getUploadUserId() {
            return uploadUserId;
        }

        public String getUploadUser() {
            return uploadUser;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public int getIsDel() {
            return isDel;
        }
    }

}
