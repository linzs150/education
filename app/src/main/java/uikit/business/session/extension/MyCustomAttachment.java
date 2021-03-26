package uikit.business.session.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class MyCustomAttachment extends CustomAttachment {
    private String content;

    public MyCustomAttachment() {
        super(CustomAttachmentType.CUSTOM);
    }

    @Override
    protected void parseData(JSONObject data) {
        content = data.toJSONString();
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = null;
        try {
            data = JSONObject.parseObject(content);
        } catch (Exception e) {

        }
        return data;
    }


    /**
     * //    {"type":7,"data":{"orderCode":"EDU2021031401123913430",
     * "beginTime":"1615854600",
     * "endTime":"1615858200",
     * "courseId":"1",
     * "coursePrice":"100",
     * "courseCount":1,
     * "teacherId":"55",
     * "courseName":"A-Level",
     * "orderAmount":"100"}}
     *
     * @return
     */
    public Data getContent() {
        try {
            return JSON.parseObject(content, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = -9183358730590345792L;
        @JSONField(name = "orderCode")
        private String orderCode;
        @JSONField(name = "beginTime")
        private String beginTime;
        @JSONField(name = "endTime")
        private String endTime;
        @JSONField(name = "courseId")
        private String courseId;
        @JSONField(name = "coursePrice")
        private String coursePrice;
        @JSONField(name = "courseCount")
        private String courseCount;
        @JSONField(name = "teacherId")
        private String teacherId;
        @JSONField(name = "courseName")
        private String courseName;
        @JSONField(name = "orderAmount")
        private String orderAmount;

        public Data() {
        }

        public Data(String orderCode, String beginTime, String endTime, String courseId, String coursePrice, String courseCount, String teacherId, String courseName, String orderAmount) {
            this.orderCode = orderCode;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.courseId = courseId;
            this.coursePrice = coursePrice;
            this.courseCount = courseCount;
            this.teacherId = teacherId;
            this.courseName = courseName;
            this.orderAmount = orderAmount;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCoursePrice() {
            return coursePrice;
        }

        public void setCoursePrice(String coursePrice) {
            this.coursePrice = coursePrice;
        }

        public String getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(String courseCount) {
            this.courseCount = courseCount;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }
    }
}
