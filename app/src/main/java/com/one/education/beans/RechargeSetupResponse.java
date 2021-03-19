package com.one.education.beans;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/20 10:49
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class RechargeSetupResponse extends BaseBean {

    private List<RechargeSetup> data;

    public List<RechargeSetup> getData() {
        return data;
    }

    public void setData(List<RechargeSetup> data) {
        this.data = data;
    }

    public static class RechargeSetup {
        private long amount;
        private long give;
        private int sort;
        private String description;
        private boolean isSelect;
        private long id;
        private String name;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getGive() {
            return give;
        }

        public void setGive(long give) {
            this.give = give;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
