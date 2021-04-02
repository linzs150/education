package com.newtonacademic.newtontutors.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author laiyongyang
 * @date 2020-05-25
 * @desc
 * @email fzhlaiyy@intretech.com
 */
@Entity(nameInDb = "t_UserSearch")
public class DUserSearch {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_MESSAGE = "message";

    @Id(autoincrement = true)
    @Property(nameInDb = COLUMN_ID)
    private Long id;
    @Property(nameInDb = COLUMN_USER_ID)
    private int userId;
    @Property(nameInDb = COLUMN_MESSAGE)
    private String message;
    @Generated(hash = 462789475)
    public DUserSearch(Long id, int userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
    }
    @Generated(hash = 1522768291)
    public DUserSearch() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
