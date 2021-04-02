package com.newtonacademic.newtontutors.beans;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/22 19:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class OrderListResponse extends BaseBean {

    private List<OrderList> data;

    public List<OrderList> getData() {
        return data;
    }

    public void setData(List<OrderList> data) {
        this.data = data;
    }

    public static class OrderList {

        //{"id": 2, "serialCode": "RECHARGE2020062106522315267",
        // "uid": 9,"account": "ad0426b750424d3c8e70fb5cd7d698af","userName": "",channel": "pay_pal",
        // "amount": 500,"bonusAmount": 100,"finalAmount": 600,"createTime": 1592693543,"payTime": 1592695255,
        // "cancelTime": 0,"completeTime": 0,"orderState": 1,"refundState": 0,"isUserDel": 0}

        private long id;
        private String serialCode;
        private long uid;
        private String account;
        private String userName;
        private String channel;
        private long amount;
        private long bonusAmount;
        private long finalAmount;
        private long createTime;
        private long payTime;
        private int cancelTime;
        private int completeTime;
        private int orderState;
        private int refundState;
        private int isUserDel;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSerialCode() {
            return serialCode;
        }

        public void setSerialCode(String serialCode) {
            this.serialCode = serialCode;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getBonusAmount() {
            return bonusAmount;
        }

        public void setBonusAmount(long bonusAmount) {
            this.bonusAmount = bonusAmount;
        }

        public long getFinalAmount() {
            return finalAmount;
        }

        public void setFinalAmount(long finalAmount) {
            this.finalAmount = finalAmount;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public int getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(int cancelTime) {
            this.cancelTime = cancelTime;
        }

        public int getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(int completeTime) {
            this.completeTime = completeTime;
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

        public int getIsUserDel() {
            return isUserDel;
        }

        public void setIsUserDel(int isUserDel) {
            this.isUserDel = isUserDel;
        }
    }
}
