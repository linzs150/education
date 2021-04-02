package com.newtonacademic.newtontutors.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lyy
 * @Description 数据库升级帮助类
 */
public final class GreenDaoUpgradeHelper {

    private static String TAG = "GreenDaoUpgradeHelper";
    /**
     * DB中约束为NOT NULL的数据类型
     */
    private static List<Class> sNotNullClasses = Arrays.asList(boolean.class, byte.class, char.class,
            short.class, int.class, long.class, float.class, double.class,
            Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class);

    /**
     * 更改表结构，将旧表数据导入新表
     */
    public static void upgradeTablesScheme(SQLiteDatabase database, List<Class<? extends AbstractDao<?, ?>>> daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            handleUpgradeTableScheme(database, daoClass);
        }

        dropTempTables(database, daoClasses);
    }

    /**
     * 删除临时表
     */
    private static void dropTempTables(SQLiteDatabase database, List<Class<? extends AbstractDao<?, ?>>> daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            dropTempTable(database, daoClass);
        }
    }

    /**
     * 执行表结构变更
     */
    private static void handleUpgradeTableScheme(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>> daoCls) {
        StandardDatabase standardDatabase = new StandardDatabase(db);
        DaoConfig daoConfig = new DaoConfig(standardDatabase, daoCls);
        String tableName = daoConfig.tablename;
        if (!checkTable(db, tableName)) {
            Log.i(TAG, "is new table name = " + tableName);
            createTable(daoCls, standardDatabase, false);
            return;
        }

        String tempTableName = tableName + "_TEMP";
        //重命名表
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + tempTableName);
        //创建新表
        createTable(daoCls, standardDatabase, true);

        List<String> oldColumns = getColumns(standardDatabase, tempTableName);
        List<String> shareColumns = new ArrayList<>();
        List<String> newNotNullColumns = new ArrayList<>();
        for (Property property : daoConfig.properties) {
            if (oldColumns.contains(property.columnName)) {
                shareColumns.add(property.columnName);
            } else if (checkNotNull(property)) {
                newNotNullColumns.add(property.columnName);
            }
        }

        //将旧表数据导入新表
        String insertSQL = "INSERT INTO " + tableName + "("
                + TextUtils.join(",", shareColumns);
        if (!newNotNullColumns.isEmpty()) {
            insertSQL += ",";
            insertSQL += TextUtils.join(",", newNotNullColumns);
        }

        insertSQL += ") SELECT ";
        insertSQL += TextUtils.join(",", shareColumns);

        String notNullStr = "";
        for (String column : newNotNullColumns) {
            notNullStr += ",0 AS " + column;
        }
        insertSQL += notNullStr;
        insertSQL += " FROM " + tempTableName;

        Log.i(TAG, "insertSQL = " + insertSQL);
        db.execSQL(insertSQL);
    }

    /**
     * 是否是非空列
     */
    private static boolean checkNotNull(Property property) {
        return sNotNullClasses.contains(property.type);
    }

    /**
     * 检测table是否存在
     *
     * @param db        数据库
     * @param tableName 表名
     */
    private static Boolean checkTable(SQLiteDatabase db, String tableName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='").append(tableName).append("'");
        Cursor c = db.rawQuery(query.toString(), null);
        if (c.moveToNext()) {
            int count = c.getInt(0);
            return count > 0;
        }
        return false;
    }

    /**
     * 删除旧表
     */
    private static void dropTempTable(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>> daoCls) {
        StandardDatabase standardDatabase = new StandardDatabase(db);
        DaoConfig daoConfig = new DaoConfig(standardDatabase, daoCls);
        String tableName = daoConfig.tablename;
        String tempTableName = tableName + "_TEMP";
        if (checkTable(db, tempTableName)) {
            //删除旧表
            db.execSQL("DROP TABLE " + tempTableName);
        }
    }

    /**
     * 获取数据库所有列名称
     */
    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                Collections.addAll(columns, cursor.getColumnNames());
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columns;
    }

    /**
     * 创建表
     */
    private static void createTable(Class<? extends AbstractDao<?, ?>> daoCls, Database db, boolean b) {
        reflectMethod(daoCls, "createTable", db, b);
    }

    /**
     * 反射createTable或dropTable方法
     */
    private static void reflectMethod(@NonNull Class<? extends AbstractDao<?, ?>> daoCls,
                                      String methodName, Database db, boolean isExists) {
        try {
            Method method = daoCls.getDeclaredMethod(methodName, Database.class, boolean.class);
            method.invoke(null, db, isExists);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}