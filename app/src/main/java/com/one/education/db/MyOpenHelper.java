package com.one.education.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.one.education.db.bean.DUserDao;
import com.one.education.db.bean.DUserSearchDao;
import com.one.education.db.bean.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyy
 * @version v1.0, 2019/4/25
 * @Description
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    private final String TAG = "MyOpenHelper";
    private List<Class<? extends AbstractDao<?, ?>>> mDaClassList = new ArrayList<>();

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        mDaClassList.clear();
        mDaClassList.add(DUserDao.class);
        mDaClassList.add(DUserSearchDao.class);
    }

    @Override
    public void onCreate(Database db) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : mDaClassList) {
            if (daoClass.isAssignableFrom(DUserDao.class)) {
                DUserDao.createTable(db, false);
            } else if (daoClass.isAssignableFrom(DUserSearchDao.class)) {
                DUserSearchDao.createTable(db, false);
            } else {
                Log.i(TAG, "daoClass is no match name = " + daoClass.getSimpleName());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        GreenDaoUpgradeHelper.upgradeTablesScheme(db, mDaClassList);
    }
}
