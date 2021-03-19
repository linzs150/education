package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/22 19:38
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class OrderCreateResponse extends BaseBean {

    private OrderCreate data;

    public OrderCreate getData() {
        return data;
    }

    public void setData(OrderCreate data) {
        this.data = data;
    }

    public static class OrderCreate {
        private long amount;
        private String body;
        private String clientIp;
        private String currency;
        private String extra;
        private String orderCode;
        private String payChannel;
        private int productCount;
        private String sign;
        private String subject;
        private long timeExpire;


        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
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

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public long getTimeExpire() {
            return timeExpire;
        }

        public void setTimeExpire(long timeExpire) {
            this.timeExpire = timeExpire;
        }
    }
}
