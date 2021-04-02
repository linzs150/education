package com.newtonacademic.newtontutors.utils;

import android.text.TextUtils;
import android.util.Log;

import com.newtonacademic.newtontutors.api.ICallback;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.db.bean.DUser;
import com.newtonacademic.newtontutors.user.UserInstance;

public abstract class FileUri {
    private static final String TAG = "FileUri";

    public static FileUri newIconUri(String iconResource) {
        if (TextUtils.isEmpty(iconResource)) {
            return null;
        }

        long userId = UserInstance.getInstance().getUserId();
        if (0 == userId) {
            Log.e(TAG, "newIconUri(String iconResource) : UserId is invalid.");
            return null;
        }

        return new SingleFileUri(iconResource, Constants.createIconPath(userId, iconResource));
    }

    /**
     * 课件地址
     */
    public static String newCoursewareUri(int courseId, String fileName) {
        long userId = UserInstance.getInstance().getUserId();
        return Constants.createCoursewarePath(userId, courseId, fileName);
    }

    /**
     * 获取用户头像
     *
     * @param user 用户信息
     */
    public static FileUri newUserIconUri(UserInstance user) {
        if (null == user) {
            return null;
        }

        return newUserIconUri(user.getUserIcon());
    }

    /**
     * 获取用户头像
     *
     * @param user 用户信息
     */
    public static FileUri newUserIconUri(DUser user) {
        if (null == user) {
            return null;
        }

        //登录用户选择user用户
        long userId = user.getUserId();
        String iconResource = user.getIconResource();
        if (TextUtils.isEmpty(iconResource)) {
            return null;
        }

        return new SingleFileUri(iconResource, Constants.createIconPath(userId, iconResource));
    }

    /**
     * 获取联系人头像
     *
     * @param url 头像连接
     */
    public static FileUri newUserIconUri(String url) {
        return newIconUri(url);
    }


    /**
     * 获取本地资源路径
     */
    public abstract String getLocalPath();

    /**
     * 加载资源
     */
    public abstract void loadResource(ICallback<Boolean> callback);

}