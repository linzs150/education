package com.one.education.db.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author lyy
 * @Description 登录账号信息
 */
@Entity(nameInDb = "t_User")
public class DUser{
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_TOKEN = "token";
    private static final String COLUMN_USER_TYPE = "user_type";
    private static final String COLUMN_USER = "user_id";
    private static final String COLUMN_ACCOUNT = "account";
    private static final String COLUMN_CELLPHONE = "cellphone";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_NICK = "user_nick";
    private static final String COLUMN_SEX = "sex";
    private static final String COLUMN_ICON_RESOURCE = "icon_resource";
    private static final String COLUMN_LAST_LOGIN_TIME = "last_login_time";

    @Id(autoincrement = true)
    @Property(nameInDb = COLUMN_ID)
    private Long id;
    @Property(nameInDb = COLUMN_USER_TOKEN)
    private String token;
    @Property(nameInDb = COLUMN_USER_TYPE)
    private int userType;
    @Property(nameInDb = COLUMN_USER)
    private long userId;
    @Property(nameInDb = COLUMN_ACCOUNT)
    private String account;
    @Property(nameInDb = COLUMN_USER_NAME)
    private String userName;
    @Property(nameInDb = COLUMN_USER_NICK)
    private String userNick;
    @Property(nameInDb = COLUMN_SEX)
    private int sex;
    @Property(nameInDb = COLUMN_ICON_RESOURCE)
    private String iconResource;
    @Property(nameInDb = COLUMN_CELLPHONE)
    private String cellphone;
    @Property(nameInDb = COLUMN_LAST_LOGIN_TIME)
    private String lastLoginTime;
    @Generated(hash = 1939628332)
    public DUser(Long id, String token, int userType, long userId, String account,
            String userName, String userNick, int sex, String iconResource,
            String cellphone, String lastLoginTime) {
        this.id = id;
        this.token = token;
        this.userType = userType;
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.userNick = userNick;
        this.sex = sex;
        this.iconResource = iconResource;
        this.cellphone = cellphone;
        this.lastLoginTime = lastLoginTime;
    }
    @Generated(hash = 2086853187)
    public DUser() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserType() {
        return this.userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserNick() {
        return this.userNick;
    }
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getIconResource() {
        return this.iconResource;
    }
    public void setIconResource(String iconResource) {
        this.iconResource = iconResource;
    }
    public String getCellphone() {
        return this.cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    public String getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}