package com.one.education.db.dao;

import com.one.education.db.IDMLResultListener;
import com.one.education.db.bean.DUser;
import com.one.education.db.bean.DUserDao;
import com.one.education.thread.ThreadPoolProxyFactory;

import java.util.List;

/**
 * MyUserDao类：登录账号信息数据库接口
 *
 * @author lyy
 * @version v1.0, 2019/3/5
 */
public class MyUserDao {

    private DUserDao mDUserDao;

    public MyUserDao(DUserDao mDUserDao) {
        this.mDUserDao = mDUserDao;
    }

    /**
     * 获取用户信息ByUserId
     *
     * @param userId   用户Id
     * @param listener 回调
     */
    public void getUserByUserId(final long userId, final IDMLResultListener<DUser> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onResult(getUserByUserId(userId));
                }
            }
        });
    }

    /**
     * 获取用户信息ByUserId
     *
     * @param userId   用户Id
     */
    public DUser getUserByUserId(final long userId) {
        List<DUser> users = mDUserDao.queryBuilder().where(DUserDao.Properties.UserId.eq(userId)).list();
        DUser user = null;
        if (users.size() > 0) {
            user = users.get(0);
        }

        return user;
    }

    /**
     * 获取最近的登录记录
     *
     * @param listener 回调
     */
    public void getLatestLoginRecord(final IDMLResultListener<DUser> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                DUser user = null;
                List<DUser> users = mDUserDao.queryBuilder().orderDesc(DUserDao.Properties.LastLoginTime).limit(1).build().list();
                if (null != users && users.size() > 0) {
                    user = users.get(0);
                }

                listener.onResult(user);
            }
        });
    }

    /**
     * 保存用户信息数据
     *
     * @param dUser    用户信息
     * @param listener 回调
     */
    public void save(final DUser dUser, final IDMLResultListener<Long> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                DUser user = mDUserDao.queryBuilder().where(DUserDao.Properties.UserId.eq(dUser.getUserId())).unique();
                if (user == null) {
                    mDUserDao.insert(dUser);
                } else {
                    dUser.setId(user.getId());
                    mDUserDao.update(dUser);
                }

                if (null != listener) {
                    listener.onResult(dUser.getId());
                }
            }
        });
    }

    /**
     * 更新最近用户头像
     *
     * @param userId       用户Id
     * @param iconResource 头像地址
     */
    public void updateUserIconResource(final long userId, final String iconResource) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                DUser user = mDUserDao.queryBuilder().where(DUserDao.Properties.UserId.eq(userId)).build().unique();
                if (user != null) {
                    user.setIconResource(iconResource);
                    mDUserDao.update(user);
                }
            }
        });
    }

    /**
     * 清除免密登陆token
     * @param userId 用户id
     */
    public void clearSessionToken(long userId) {
        DUser user = mDUserDao.queryBuilder().where(DUserDao.Properties.UserId.eq(userId)).unique();
        user.setToken(null);
        mDUserDao.update(user);
    }
}
