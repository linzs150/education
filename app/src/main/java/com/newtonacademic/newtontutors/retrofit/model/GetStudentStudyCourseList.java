package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-05-29
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetStudentStudyCourseList {
    private int status;
    private String descript;
    private int totalCount;
    private List<StudentStudyCourse> data;

    public enum State {
        DEFAULT(-1, "EDU_ORDER_COURSE_STATE_ENUM_DEFAULT"), //默认

        WAITING_PAY(0, "EDU_ORDER_COURSE_STATE_ENUM_WAITING_PAY"), //待支付

        BOOKING(1, "EDU_ORDER_COURSE_STATE_ENUM_BOOKING"), //已预约

        COMPLETED(2, "EDU_ORDER_COURSE_STATE_ENUM_COMPLETED"), //已上课

        APPLY_DELAY(3, "EDU_ORDER_COURSE_STATE_ENUM_APPLY_DELAY"), //已申请改签

        DELAYED(4, "EDU_ORDER_COURSE_STATE_ENUM_DELAYED"),//已预约

        ABOUT_TO_START(5, "EDU_ORDER_COURSE_STATE_ENUM_ABOUT_TO_START"), //即将开始

        IN_CLASS(6, "EDU_ORDER_COURSE_STATE_ENUM_IN_CLASS"),//上课中

        INVITE_APPLY_CHECKING(7, "EDU_ORDER_COURSE_STATE_ENUM_INVITE_APPLY_CHECKING"),//邀约待确认

        OVER_TIME(8, "EDU_ORDER_COURSE_STATE_ENUM_OVER_TIME"),//过期

        CANCELLED(9, "EDU_ORDER_COURSE_STATE_ENUM_CANCELLED"),//取消
        NOTEVALUATED(20, "EDU_ORDER_COURSE_STATE_ENUM_NOTEVALUATED");//已结束未评论

        private int key;
        private String desc;

        State(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public int getKey() {
            return key;
        }
    }


    /**
     * "advancedPrice": 800.00,
     * "advancedRate": 0.00,
     * "beginTime": 1590565239,
     * "commentStar": 0.00,
     * "commentStar1": 0.00,
     * "commentStar2": 0.00,
     * "commentStar3": 0.00,
     * "commentStar4": 0.00,
     * "commentStar5": 0.00,
     * "courseAlias": "ALevel",
     * "courseCount": 2,
     * "courseDuration": 60,
     * "courseId": 1,
     * "courseName": "ALevel",
     * "coursePrice": 500.00,
     * "createTime": 1590565012,
     * "endTime": 1590572439,
     * "generalPrice": 500.00,
     * "generalRate": 0.00,
     * "id": 22,
     * "isComment": 0,
     * "lastModifyTime": 0,
     * "orderCode": "EDU2020052715365209452",
     * "orderId": 13,
     * "refundState": "0",
     * "state": 1,
     * "stateName": "上课中",
     * "studentId": 16,
     * "studentUserPicUrl": "",
     * "subjectId": 0,
     * "superAdvancedPrice": 1000.00,
     * "superAdvancedRate": 0.00,
     * "teacherId": 24,
     * "teacherLevel": 0,
     * "teacherName": "firstName3 lastName3",
     * "teacherShareAmount": 0.00,
     * "teacherShareRate": 0.00,
     * "teacherUserPicUrl": "http://121.idaguo.com/upload/edu/2020/05/27/20200527204923108.png",
     * "userName": "3tte 工"
     */
    public class StudentStudyCourse {
        private int id;
        private int studentId;
        private int courseId;
        private String courseName;
        private int courseCount;
        private int courseLevel;
        private String orderCode;
        private int courseDuration;
        private long beginTime;
        private long endTime;
        private long createTime;
        private int teacherId;
        private String teacherName;
        private String teacherUserPicUrl;
        private String stateName;
        //1.已预约
        //2.已完结
        //3、申请延期
        //4、延期
        //5、即将开课：返回结果
        //6、上课中
        private int state;
        private int orderId;
        private int coursePrice;
        private int isComment;
        private int changeApplyState;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(int courseCount) {
            this.courseCount = courseCount;
        }

        public int getCourseLevel() {
            return courseLevel;
        }

        public void setCourseLevel(int courseLevel) {
            this.courseLevel = courseLevel;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getCourseDuration() {
            return courseDuration;
        }

        public void setCourseDuration(int courseDuration) {
            this.courseDuration = courseDuration;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherUserPicUrl() {
            return teacherUserPicUrl;
        }

        public void setTeacherUserPicUrl(String teacherUserPicUrl) {
            this.teacherUserPicUrl = teacherUserPicUrl;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getCoursePrice() {
            return coursePrice;
        }

        public int getIsComment() {
            return isComment;
        }

        public int getChangeApplyState() {
            return changeApplyState;
        }
    }

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

    public List<StudentStudyCourse> getData() {
        return data;
    }

    public void setData(List<StudentStudyCourse> data) {
        this.data = data;
    }
}
