package com.one.education.beans;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/20 0:23
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class ExistPayPwdResponse extends BaseBean {

    private boolean isExistPayPwd;

    public boolean isExistPayPwd() {
        return isExistPayPwd;
    }

    public void setExistPayPwd(boolean existPayPwd) {
        isExistPayPwd = existPayPwd;
    }
}
