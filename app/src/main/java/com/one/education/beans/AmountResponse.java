package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/3 20:56
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class AmountResponse extends BaseBean {

    private long uid;
    private long balance;
    private long gold;
    private long point;
    private long lottery;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getLottery() {
        return lottery;
    }

    public void setLottery(long lottery) {
        this.lottery = lottery;
    }
}
