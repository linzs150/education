package com.one.education.beans;

import java.io.Serializable;
import java.util.List;

public class OrderQueryResponse extends BaseBean implements Serializable {

    private Certificates certificates;
    private Data data;

    public Certificates getCertificates() {
        return certificates;
    }

    public void setCertificates(Certificates certificates) {
        this.certificates = certificates;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable{

        private List<CourseList> courseList;
        private int finalPrice;
        private String orderCode;
        private int orderPrice;
        private int teacherLevel;
        private int cannelTime;
        private int deliverTime;
        private String orderMark;
        private String currency;
        private String cannelReason;
        private int isDistribution;
        private String userMark;
        private int orderState;
        private long refundTime;
        private String studentName;
        private int inviteApplyState;
        private int orderType;
        private String inviteApplyReason;
        private long confirmTime;
        private int teacherShareAmount;
        private int goodsCount;
        private String teacherShareRate;
        private int payState;
        private int refundState;
        private long studentId;
        private long createTime;
        private String orderName;
        private long receivingTime;
        private String teacherName;
        private long orderId;
        private String cashBack;
        private String outOrderCode;
        private String payChannel;
        private int payTime;
        private long teacherId;
        private int isDel;
        private String clientIp;
        private int inviteApplyTime;
        private String teacherMark;
        private String inviteApplyRejectReason;

        public List<CourseList> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseList> courseList) {
            this.courseList = courseList;
        }

        public int getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(int finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(int orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getTeacherLevel() {
            return teacherLevel;
        }

        public void setTeacherLevel(int teacherLevel) {
            this.teacherLevel = teacherLevel;
        }

        public int getCannelTime() {
            return cannelTime;
        }

        public void setCannelTime(int cannelTime) {
            this.cannelTime = cannelTime;
        }

        public int getDeliverTime() {
            return deliverTime;
        }

        public void setDeliverTime(int deliverTime) {
            this.deliverTime = deliverTime;
        }

        public String getOrderMark() {
            return orderMark;
        }

        public void setOrderMark(String orderMark) {
            this.orderMark = orderMark;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCannelReason() {
            return cannelReason;
        }

        public void setCannelReason(String cannelReason) {
            this.cannelReason = cannelReason;
        }

        public int getIsDistribution() {
            return isDistribution;
        }

        public void setIsDistribution(int isDistribution) {
            this.isDistribution = isDistribution;
        }

        public String getUserMark() {
            return userMark;
        }

        public void setUserMark(String userMark) {
            this.userMark = userMark;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public long getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(long refundTime) {
            this.refundTime = refundTime;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getInviteApplyState() {
            return inviteApplyState;
        }

        public void setInviteApplyState(int inviteApplyState) {
            this.inviteApplyState = inviteApplyState;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getInviteApplyReason() {
            return inviteApplyReason;
        }

        public void setInviteApplyReason(String inviteApplyReason) {
            this.inviteApplyReason = inviteApplyReason;
        }

        public long getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(long confirmTime) {
            this.confirmTime = confirmTime;
        }

        public int getTeacherShareAmount() {
            return teacherShareAmount;
        }

        public void setTeacherShareAmount(int teacherShareAmount) {
            this.teacherShareAmount = teacherShareAmount;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getTeacherShareRate() {
            return teacherShareRate;
        }

        public void setTeacherShareRate(String teacherShareRate) {
            this.teacherShareRate = teacherShareRate;
        }

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public int getRefundState() {
            return refundState;
        }

        public void setRefundState(int refundState) {
            this.refundState = refundState;
        }

        public long getStudentId() {
            return studentId;
        }

        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public long getReceivingTime() {
            return receivingTime;
        }

        public void setReceivingTime(long receivingTime) {
            this.receivingTime = receivingTime;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getCashBack() {
            return cashBack;
        }

        public void setCashBack(String cashBack) {
            this.cashBack = cashBack;
        }

        public String getOutOrderCode() {
            return outOrderCode;
        }

        public void setOutOrderCode(String outOrderCode) {
            this.outOrderCode = outOrderCode;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public int getPayTime() {
            return payTime;
        }

        public void setPayTime(int payTime) {
            this.payTime = payTime;
        }

        public long getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(long teacherId) {
            this.teacherId = teacherId;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public int getInviteApplyTime() {
            return inviteApplyTime;
        }

        public void setInviteApplyTime(int inviteApplyTime) {
            this.inviteApplyTime = inviteApplyTime;
        }

        public String getTeacherMark() {
            return teacherMark;
        }

        public void setTeacherMark(String teacherMark) {
            this.teacherMark = teacherMark;
        }

        public String getInviteApplyRejectReason() {
            return inviteApplyRejectReason;
        }

        public void setInviteApplyRejectReason(String inviteApplyRejectReason) {
            this.inviteApplyRejectReason = inviteApplyRejectReason;
        }

        public static class CourseList implements Serializable{

            private long courseId;
            private long endTime;
            private int commentStar2;
            private int commentStar5;
            private String studentUserPicUrl;
            private String courseName;
            private int changeApplyState;
            private int generalPrice;
            private int commentStar;
            private int state;
            private long orderId;
            private long subjectId;
            private int coursePrice;
            private long courseDuration;
            private int isComment;
            private String courseAlias;
            private int superAdvancedRate;
            private int commentStar4;
            private String subjectName;
            private int inviteApplyState;
            private int courseCount;
            private int commentStar1;
            private int orderState;
            private int refundState;
            private long teacherId;
            private String teacherUserPicUrl;
            private long id;
            private int teacherShareAmount;
            private int teacherShareRate;
            private int lastModifyTime;
            private int superAdvancedPrice;
            private int advancedPrice;
            private int teacherLevel;
            private long beginTime;
            private String orderCode;
            private int payState;
            private long studentId;
            private long createTime;
            private String userName;
            private int generalRate;
            private String teacherName;
            private int advancedRate;
            private int commentStar3;


            public long getCourseId() {
                return courseId;
            }

            public void setCourseId(long courseId) {
                this.courseId = courseId;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getCommentStar2() {
                return commentStar2;
            }

            public void setCommentStar2(int commentStar2) {
                this.commentStar2 = commentStar2;
            }

            public int getCommentStar5() {
                return commentStar5;
            }

            public void setCommentStar5(int commentStar5) {
                this.commentStar5 = commentStar5;
            }

            public String getStudentUserPicUrl() {
                return studentUserPicUrl;
            }

            public void setStudentUserPicUrl(String studentUserPicUrl) {
                this.studentUserPicUrl = studentUserPicUrl;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public int getChangeApplyState() {
                return changeApplyState;
            }

            public void setChangeApplyState(int changeApplyState) {
                this.changeApplyState = changeApplyState;
            }

            public int getGeneralPrice() {
                return generalPrice;
            }

            public void setGeneralPrice(int generalPrice) {
                this.generalPrice = generalPrice;
            }

            public int getCommentStar() {
                return commentStar;
            }

            public void setCommentStar(int commentStar) {
                this.commentStar = commentStar;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public long getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(long subjectId) {
                this.subjectId = subjectId;
            }

            public int getCoursePrice() {
                return coursePrice;
            }

            public void setCoursePrice(int coursePrice) {
                this.coursePrice = coursePrice;
            }

            public long getCourseDuration() {
                return courseDuration;
            }

            public void setCourseDuration(long courseDuration) {
                this.courseDuration = courseDuration;
            }

            public int getIsComment() {
                return isComment;
            }

            public void setIsComment(int isComment) {
                this.isComment = isComment;
            }

            public String getCourseAlias() {
                return courseAlias;
            }

            public void setCourseAlias(String courseAlias) {
                this.courseAlias = courseAlias;
            }

            public int getSuperAdvancedRate() {
                return superAdvancedRate;
            }

            public void setSuperAdvancedRate(int superAdvancedRate) {
                this.superAdvancedRate = superAdvancedRate;
            }

            public int getCommentStar4() {
                return commentStar4;
            }

            public void setCommentStar4(int commentStar4) {
                this.commentStar4 = commentStar4;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }

            public int getInviteApplyState() {
                return inviteApplyState;
            }

            public void setInviteApplyState(int inviteApplyState) {
                this.inviteApplyState = inviteApplyState;
            }

            public int getCourseCount() {
                return courseCount;
            }

            public void setCourseCount(int courseCount) {
                this.courseCount = courseCount;
            }

            public int getCommentStar1() {
                return commentStar1;
            }

            public void setCommentStar1(int commentStar1) {
                this.commentStar1 = commentStar1;
            }

            public int getOrderState() {
                return orderState;
            }

            public void setOrderState(int orderState) {
                this.orderState = orderState;
            }

            public int getRefundState() {
                return refundState;
            }

            public void setRefundState(int refundState) {
                this.refundState = refundState;
            }

            public long getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(long teacherId) {
                this.teacherId = teacherId;
            }

            public String getTeacherUserPicUrl() {
                return teacherUserPicUrl;
            }

            public void setTeacherUserPicUrl(String teacherUserPicUrl) {
                this.teacherUserPicUrl = teacherUserPicUrl;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getTeacherShareAmount() {
                return teacherShareAmount;
            }

            public void setTeacherShareAmount(int teacherShareAmount) {
                this.teacherShareAmount = teacherShareAmount;
            }

            public int getTeacherShareRate() {
                return teacherShareRate;
            }

            public void setTeacherShareRate(int teacherShareRate) {
                this.teacherShareRate = teacherShareRate;
            }

            public int getLastModifyTime() {
                return lastModifyTime;
            }

            public void setLastModifyTime(int lastModifyTime) {
                this.lastModifyTime = lastModifyTime;
            }

            public int getSuperAdvancedPrice() {
                return superAdvancedPrice;
            }

            public void setSuperAdvancedPrice(int superAdvancedPrice) {
                this.superAdvancedPrice = superAdvancedPrice;
            }

            public int getAdvancedPrice() {
                return advancedPrice;
            }

            public void setAdvancedPrice(int advancedPrice) {
                this.advancedPrice = advancedPrice;
            }

            public int getTeacherLevel() {
                return teacherLevel;
            }

            public void setTeacherLevel(int teacherLevel) {
                this.teacherLevel = teacherLevel;
            }

            public long getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(long beginTime) {
                this.beginTime = beginTime;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public int getPayState() {
                return payState;
            }

            public void setPayState(int payState) {
                this.payState = payState;
            }

            public long getStudentId() {
                return studentId;
            }

            public void setStudentId(long studentId) {
                this.studentId = studentId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getGeneralRate() {
                return generalRate;
            }

            public void setGeneralRate(int generalRate) {
                this.generalRate = generalRate;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public int getAdvancedRate() {
                return advancedRate;
            }

            public void setAdvancedRate(int advancedRate) {
                this.advancedRate = advancedRate;
            }

            public int getCommentStar3() {
                return commentStar3;
            }

            public void setCommentStar3(int commentStar3) {
                this.commentStar3 = commentStar3;
            }
        }

    }


    public static class Certificates implements Serializable{
        private String sign;
        private int productCount;
        private long timeExpire;
        private String body;
        private String currency;
        private int amount;
        private String extra;
        private String orderCode;
        private String clientIp;
        private String payChannel;
        private String subject;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public long getTimeExpire() {
            return timeExpire;
        }

        public void setTimeExpire(long timeExpire) {
            this.timeExpire = timeExpire;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }


}
