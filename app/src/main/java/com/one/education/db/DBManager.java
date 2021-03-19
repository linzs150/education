package com.one.education.db;

import android.content.Context;

import com.one.education.db.bean.DaoMaster;
import com.one.education.db.bean.DaoSession;
import com.one.education.db.dao.MyUserDao;
import com.one.education.db.dao.UserSearchDao;

/**
 * DBManager类：数据库管理类
 *
 * @author lyy
 * @version v1.0, 2019/3/5
 */
public class DBManager {
    private static final String TAG = "DBManager";
    private static DBManager sInstance = new DBManager();
    /**
     * 默认数据库
     */
    private static final String DATABASE_NAME = "education";

    /**
     * 登录账号Dao
     */
    private MyUserDao mMyUserDao;

    private UserSearchDao mUserSearchDao;


    public static DBManager getInstance() {
        return sInstance;
    }

    public void initialize(Context context) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context, DATABASE_NAME, null);
        DaoMaster daoMaster = new DaoMaster(myOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mMyUserDao = new MyUserDao(daoSession.getDUserDao());
        mUserSearchDao = new UserSearchDao(daoSession.getDUserSearchDao());
    }

    /**
     * 登录账号信息数据库接口
     *
     * @return MyUserDao
     */
    public MyUserDao getUserDao() {
        return mMyUserDao;
    }

    /**
     * 搜索数据库接口
     *
     * @return UserSearchDao
     */
    public UserSearchDao getUserSearchDao() {
        return mUserSearchDao;
    }
}
