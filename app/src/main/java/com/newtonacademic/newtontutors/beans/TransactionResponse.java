package com.newtonacademic.newtontutors.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/26 23:03
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TransactionResponse extends BaseBean{

    private Transaction data;
    private int amount;

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class Transaction{

        private String rechage;
        private String date;
        private String money;

        public String getRechage() {
            return rechage;
        }

        public void setRechage(String rechage) {
            this.rechage = rechage;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
