package com.newtonacademic.newtontutors.db.dao;

import com.newtonacademic.newtontutors.db.IDMLResultListener;
import com.newtonacademic.newtontutors.db.bean.DUserSearch;
import com.newtonacademic.newtontutors.db.bean.DUserSearchDao;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-05-25
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class UserSearchDao {

    private DUserSearchDao mDUserSearchDao;

    public UserSearchDao(DUserSearchDao userSearchDao) {
        this.mDUserSearchDao = userSearchDao;
    }

    /**
     * 获取用户信息ByUserId
     *
     * @param userId   用户Id
     * @param listener 回调
     */
    public void getUserSearchByUserId(final long userId, final IDMLResultListener<List<DUserSearch>> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<DUserSearch> dUserSearches = mDUserSearchDao.queryBuilder().where(DUserSearchDao.Properties.UserId.eq(userId)).limit(10).orderDesc(DUserSearchDao.Properties.Id).build().list();
                if (listener != null) {
                    listener.onResult(dUserSearches);
                }
            }
        });
    }


    /**
     * 保存用户信息数据搜索信息
     *
     * @param dUserSearch    用户搜索信息
     * @param listener 回调
     */
    public void save(final DUserSearch dUserSearch, final IDMLResultListener<Long> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<DUserSearch> dUserSearches = mDUserSearchDao.queryBuilder().where(DUserSearchDao.Properties.Message.eq(dUserSearch.getMessage())).list();
                for (DUserSearch userSearch : dUserSearches) {
                    mDUserSearchDao.delete(userSearch);
                }

                mDUserSearchDao.insert(dUserSearch);
                if (null != listener) {
                    listener.onResult(dUserSearch.getId());
                }
            }
        });
    }

    /**
     * 删除搜索记录
     *
     * @param userId    用户Id
     * @param listener 回调
     */
    public void delete(final int userId, final IDMLResultListener<Boolean> listener) {
        ThreadPoolProxyFactory.getDbThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<DUserSearch> userSearches = mDUserSearchDao.queryBuilder().where(DUserSearchDao.Properties.UserId.eq(userId)).list();
                for (DUserSearch dUserSearch : userSearches) {
                    mDUserSearchDao.delete(dUserSearch);
                }

                if (null != listener) {
                    listener.onResult(true);
                }
            }
        });
    }

}
