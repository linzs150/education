package com.one.education.commons;

import android.os.Environment;

import com.one.education.utils.Utilts;

import java.util.Locale;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/22 23:26
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class Constants {

    public static final String TOKEN = "token";//会话id

    public static class Net {

        //        public static final String URL = "http://121.idaguo.com/api";//测试环境
        public static final String URL = "https://www.newtontutor.hk/api";//生产环境
    }

    public static final int WX_PAY = 1;//微信类型
    public static final int UNION_PAY = 2;//银联类型
    public static final int ALIBA_PAY = 3;//支付宝类型
    public static final int PAyPAL = 4;
    public static final String APP_ID = "";

    /**
     * 客户端自动登录参数配置
     */
    public static String SHARED_PREFERENCES_BOOT_CONFIG = "boot_config";
    public static String SHARED_PREFERENCES_BOOT_CONFIG_AUTO_LOGIN = "auto_login";

    public static final String EXT_FOLDER_PATH = Utilts.createFilePath(Environment.getExternalStorageDirectory(), "OneByOne/Education");
    public static final String EXT_FOLDER_CACHE_PATH = Utilts.createFilePath(EXT_FOLDER_PATH, "FileCache");
    private static final String EXT_FOLDER_ICON_PATH = Utilts.createFilePath(EXT_FOLDER_PATH, "User/%d/Icon");
    private static final String EXT_FOLDER_COURSE_PATH = Utilts.createFilePath(EXT_FOLDER_PATH, "User/%d/Course/%d");
    public static final String EXT_IMAGE_LOADER_CACHE = Utilts.createFilePath(EXT_FOLDER_PATH, "Images");

    static {
        Utilts.createDirectory(EXT_FOLDER_PATH);
        Utilts.createDirectory(EXT_FOLDER_CACHE_PATH);
    }

    public static String createIconPath(long curUserId, String url) {
        String userFolder = String.format(Locale.getDefault(), EXT_FOLDER_ICON_PATH, curUserId);
        return Utilts.createFilePath(userFolder, Utilts.GetMD5String(url));
    }

    public static String createCoursewarePath(long curUserId, int courseId, String filename) {
        String userFolder = String.format(Locale.getDefault(), EXT_FOLDER_COURSE_PATH, curUserId, courseId);
        return Utilts.createFilePath(userFolder, filename);
    }

    // MARK: 获取首页banner列表
    //英文：3bca2f9e9ecc472681471150800cecf6
    public static final String BANNER_CODE_EN = "3bca2f9e9ecc472681471150800cecf6";
    //中文：47d56103559d43018e229b14721084f7
    public static final String BANNER_CODE_CN = "47d56103559d43018e229b14721084f7";

    // MARK: 获取首页广告列表
    //英文：3bca2f9e9ecc472681471150800cecf6
    public static final String ADVERTISING_CODE_EN = "7433419011214129b0663c095063b348";
    //中文：47d56103559d43018e229b14721084f7
    public static final String ADVERTISING_CODE_CN = "f80b039161e1470e88cbd8adf9762264";


    //学生年级
    public static final int PRIMARY1 = 1;
    public static final int PRIMARY2 = 2;
    public static final int PRIMARY3 = 3;
    public static final int PRIMARY4 = 4;
    public static final int PRIMARY5 = 5;
    public static final int PRIMARY6 = 6;
    public static final int JUNIORMIDDLE1 = 7;
    public static final int JUNIORMIDDLE2 = 8;
    public static final int JUNIORMIDDLE3 = 9;
    public static final int HIGH1 = 10;
    public static final int HIGH2 = 11;
    public static final int HIGH3 = 12;

    //老师学历
    public static final int EDUCATION1 = 1;//高中
    public static final int EDUCATION2 = 2;//学士
    public static final int EDUCATION3 = 3;//硕士
    public static final int EDUCATION4 = 4;//博士
    public static final int EDUCATION5 = 5;//博士后
    public static final int EDUCATION6 = 6;//博导

    //英语口语水平
    public static final int NONE = 0;//无
    public static final int BASIC = 1;//普通
    public static final int INTERMEDIATE = 2;//中等
    public static final int FLUENT = 3;//很好

    public static final int DELETE = -1;//删除
    public static final int PENDING = 0;//待审核
    public static final int APPROVED = 1;//审核通过
    public static final int REJECTION = 2;//被拒绝
    public static final int LOCKED = 3;//锁定


    public static class ORDERSTATE {
        public static final int ORDERING = 0;
        public static final int ORDERED = 1;
        public static final int ORDER_REVE = 2;
        public static final int CANCEL = 3;
    }
}
