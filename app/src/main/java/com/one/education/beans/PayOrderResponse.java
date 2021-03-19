package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/7/4 15:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class PayOrderResponse extends BaseBean {

    private PayOrder data;

    public PayOrder getData() {
        return data;
    }

    public void setData(PayOrder data) {
        this.data = data;
    }

    public static class PayOrder {
        private long amount;
        private String orderCode;
        private String payState;
        private String approveUrl;

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public String getApproveUrl() {
            return approveUrl;
        }

        public void setApproveUrl(String approveUrl) {
            this.approveUrl = approveUrl;
        }
    }
}
