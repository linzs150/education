package com.newtonacademic.newtontutors.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.mylibrary.TaughtSubjects;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.language.SpUtil;
import com.newtonacademic.newtontutors.retrofit.model.Subject;
import com.newtonacademic.mylibrary.ConstantGlobal;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class Utilts {

    /**
     * 判断是否是手机号码
     *
     * @param mobiles 手机号码
     * @return bool
     */
    public static boolean isMobileNO(String mobiles) {
        if (null == mobiles) {
            return false;
        }

//        Pattern p = Pattern.compile("^1\\d{10}$");
        Pattern p = Pattern.compile("^(\\+\\d{2}\\-?)?(\\d{2,3}\\-?)?([1-9][0-9]{8,10})$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 必须以数字加上字母进行组合
     *
     * @param password 密码
     * @return bool
     */
    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 工具函数：合并文件夹路径和文件相对路径为一个文件路径
     *
     * @param parent_folder 文件夹路径
     * @param relative_file 文件相对路径
     * @return 文件完整路径
     */
    public static String createFilePath(String parent_folder, String relative_file) {
        if (parent_folder.endsWith(File.separator)) {
            return parent_folder + relative_file;
        }

        return parent_folder + File.separatorChar + relative_file;
    }

    /**
     * 工具函数：合并文件夹路径和文件相对路径为一个文件完整路径
     *
     * @param parent_folder 文件夹路径
     * @param relative_file 文件相对路径
     * @return 文件完整路径
     */
    public static String createFilePath(File parent_folder, String relative_file) {
        return createFilePath(parent_folder.getAbsolutePath(), relative_file);
    }

    public static String GetMD5String(String text) {
        return GetMD5String(text.getBytes());
    }

    public static String GetMD5String(byte[] bytes) {
        MessageDigest messageDisest;
        try {
            messageDisest = MessageDigest.getInstance("MD5");
            messageDisest.update(bytes);
            return BytesToHex(messageDisest.digest());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private static String BytesToHex(byte[] buffer) {
        return BytesToHex(buffer, false);
    }

    private static String BytesToHex(byte[] buffer, boolean space) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuffer stringbuffer = new StringBuffer(2 * buffer.length);
        int k = buffer.length;
        for (int l = 0; l < k; l++) {
            byte bt = buffer[l];
            char c0 = hexDigits[(bt & 0xf0) >> 4];
            char c1 = hexDigits[bt & 0xf];
            stringbuffer.append(c0);
            stringbuffer.append(c1);

            if (space && l != k - 1) {
                stringbuffer.append(" ");
            }
        }
        return stringbuffer.toString();
    }

    public static boolean createDirectory(String folder_path) {
        if ("/".contentEquals(folder_path)) {
            return true;
        }

        File folder = new File(folder_path);
        if (folder.exists()) {
            return true;
        }

        if (createDirectory(folder.getParent())) {
            try {
                return folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param file 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFile(File file) {
        if (null == file) {
            return false;
        }

        if (!file.exists()) {
            return true;
        } else if (file.isDirectory()) {
            // 为目录时调用删除目录方法
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return file.delete();
            }

            //遍历删除文件夹下的所有文件(包括子目录)
            for (File file1 : files) {
                if (file1.isFile()) {
                    //删除子文件
                    if (!file1.delete()) {
                        return false;
                    }
                } else if (!deleteFile(file1)) {
                    //删除子目录
                    return false;
                }
            }
        }

        return file.delete();
    }

    /**
     * 获取UUID，移除减号
     *
     * @return
     */
    public static String randomUUIDRemoveMinusSign() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static float GetDimension(Context context, int resource_id) {
        return context.getResources().getDimension(resource_id);
    }

    public static int getIntDimension(Context context, int resourceId) {
        return (int) GetDimension(context, resourceId);
    }

    public static void setTextSize(Context context, TextView tvText, int resource_id) {
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(resource_id));
    }

    public static void setTextBold(TextView tvText) {
        tvText.getPaint().setFakeBoldText(true);
    }

    /**
     * 获取设备唯一标识（MANUFACTURER_MODEL_SERIAL）
     *
     * @return 组合的唯一标识
     */
    public static String getUniqueIdentification() {
        String manufacturer = android.os.Build.MANUFACTURER;
        manufacturer = manufacturer.replaceAll(" ", "");
        String model = android.os.Build.MODEL;
        model = model.replaceAll(" ", "");
        String serialNum = android.os.Build.SERIAL;
        return String.format(Locale.getDefault(), "%s_%s_%s", manufacturer, model, serialNum);
    }

    //星级计算
    public static int ratingJS(float rate) {
        return (int) (rate + 0.99f);
    }

    public static String dateToInt(long time) {

        String str = time + "";
        String strE = "";

        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("en")) {
//                if (spCountry.equals("CN")) {
//                } else if (spCountry.equals("HK")) {
//                }

                if (str.length() >= 8) {
                    strE += str.substring(6, 8) + "/";
                    strE += str.substring(4, 6) + "/";
                    strE += str.substring(0, 4);
                } else if (str.length() >= 6) {
                    strE += str.substring(4, 6) + "/";
                    strE += str.substring(0, 4);
                } else if (str.length() >= 4) {
                    strE += str.substring(0, 4);
                }

            } else if (spLanguage.equals("zh")) {
                if (str.length() >= 4) {
                    strE += str.substring(0, 4) + "-";
                }
                if (str.length() >= 6) {
                    strE += str.substring(4, 6) + "-";
                }
                if (str.length() >= 8) {
                    strE += str.substring(6, 8);
                }
            }
        }

//        if (EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("UK") ||
//                EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("US")) {
//
//        } else {
//
//        }
        return strE;
////        Calendar c = Calendar.getInstance();
////        long millions = new Long(time).longValue() * 1000;
////        c.setTimeInMillis(millions);
////        System.out.println("" + c.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        String dateString = sdf.format(time);
//        Date date = null;
//        try {
//
//             date = sdf.parse(time+"");
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        String dateString = sdf.format(c.getTime());
//        return sdf.format(date);
    }

    public static String dateToInt2(long time) {
        Calendar c = Calendar.getInstance();
        long millions = new Long(time).longValue() * 1000;
        c.setTimeInMillis(millions);
//        c.setTime(new Date(time));
        System.out.println("" + c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(c.getTime());
        return dateString;
    }

    public static String dateToLong(long time) {
        Calendar c = Calendar.getInstance();
        long millions = new Long(time).longValue() * 1000;
        c.setTimeInMillis(millions);
        System.out.println("" + c.getTime());
        SimpleDateFormat sdf = null;


        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("zh")) {

                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            } else if (spLanguage.equals("en")) {
                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            }
        }

//
//        if (EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("UK") ||
//                EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("US")) {
//
//        } else {
//
//        }
        String dateString = sdf.format(c.getTime());
        return dateString;
    }

    //年级
    public static String schoolYear(Context context, int year) {
        String school = "";
        switch (year) {
            case Constants.PRIMARY1:
                school = EducationAppliction.getContext().getString(R.string.primary1);
                break;
            case Constants.PRIMARY2:
                school = EducationAppliction.getContext().getString(R.string.primary2);
                break;
            case Constants.PRIMARY3:
                school = EducationAppliction.getContext().getString(R.string.primary3);
                break;
            case Constants.PRIMARY4:
                school = EducationAppliction.getContext().getString(R.string.primary4);
                break;
            case Constants.PRIMARY5:
                school = context.getString(R.string.primary5);
                break;
            case Constants.PRIMARY6:
                school = context.getString(R.string.primary6);
                break;
            case Constants.JUNIORMIDDLE1:
                school = context.getString(R.string.juniormiddle1);
                break;
            case Constants.JUNIORMIDDLE2:
                school = context.getString(R.string.juniormiddle2);
                break;
            case Constants.JUNIORMIDDLE3:
                school = context.getString(R.string.juniormiddle3);
                break;
            case Constants.HIGH1:
                school = context.getString(R.string.high1);
                break;
            case Constants.HIGH2:
                school = context.getString(R.string.high2);
                break;
            case Constants.HIGH3:
                school = context.getString(R.string.high3);
                break;
            default:
                school = "";
                break;

        }
        return school;

    }


    //年级
    public static int schoolYearToInt(String year) {
        int school = 0;
        switch (year) {
            case "小学一年级":
                school = Constants.PRIMARY1;
                break;
            case "小学二年级":
                school = Constants.PRIMARY2;
                break;
            case "小学三年级":
                school = Constants.PRIMARY3;
                break;
            case "小学四年级":
                school = Constants.PRIMARY4;
                break;
            case "小学五年级":
                school = Constants.PRIMARY5;
                break;
            case "小学六年级":
                school = Constants.PRIMARY6;
                break;
            case "初中一年级":
                school = Constants.JUNIORMIDDLE1;
                break;
            case "初中二年级":
                school = Constants.JUNIORMIDDLE2;
                break;
            case "初中三年级":
                school = Constants.JUNIORMIDDLE3;
                break;
            case "高中一年级":
                school = Constants.HIGH1;
                break;
            case "高中二年级":
                school = Constants.HIGH2;
                break;
            case "高中三年级":
                school = Constants.HIGH3;
                break;
            default:
                school = 0;
                break;

        }
        return school;

    }


    //获取网络视频的第一帧图片
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    public static String getTime(Date date) {

        SimpleDateFormat format = null;

        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("en")) {
                format = new SimpleDateFormat("dd/MM/yyyy");
            } else {
                format = new SimpleDateFormat("yyyy-MM-dd");//
            }
        }

        return format.format(date);

    }

    public static String getJson(Context context, String json) {

        StringBuilder builder = new StringBuilder();
        InputStream is = null;
        try {
            is = context.getAssets().open(json);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String next = "";
            while (null != (next = bufferedReader.readLine())) {
                builder.append(next);
            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.delete(0, builder.length());
        }

        return builder.toString().trim();
    }

//
//    public static String readWord(Context context, String file) {
//
//        FileInputStream in = null;
//        try {
//            String text = null;
//            try {
//                in = new FileInputStream(new File(file));
//                WordExtractor extractor = null;
//                //创建WordExtractor
//                extractor = new WordExtractor();
//                //进行提取对doc文件
//                text = extractor.extractText(in);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return text;
//        }


    /**
     * 正则表达式 判断邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //准时
    public static String notifyPlan(Context mCtx, int plan) {
        switch (plan) {
            case 1:
                return mCtx.getString(R.string.on_time);
            case 2:
                return mCtx.getString(R.string.mins_prior);
            case 3:
                return mCtx.getString(R.string.hour_prior);
        }
        return "";
    }

    public static String speakChinese(Context mCtx, int type) {
        String speak = "";
        switch (type) {
            case 1:
                speak = mCtx.getString(R.string.mandarin);
                break;
            case 2:
                speak = mCtx.getString(R.string.cantonese);
                break;
            case 3:
                speak = mCtx.getString(R.string.mandarin_cantonese);
                break;
        }

        return speak;
    }

    //去除重复
    public static List<TaughtSubjects> removeList(List<TaughtSubjects> userList) {

        Set<TaughtSubjects> s = new TreeSet<TaughtSubjects>(new Comparator<TaughtSubjects>() {

            @Override
            public int compare(TaughtSubjects o1, TaughtSubjects o2) {

                String tau1 = (TextUtils.isEmpty(o1.getMark())) ? o1.getSubjectName().trim() : (o1.getSubjectName().trim() + ": " + o1.getMark().trim());
                String tau2 = (TextUtils.isEmpty(o2.getMark())) ? o2.getSubjectName().trim() : (o2.getSubjectName().trim() + ": " + o2.getMark().trim());
                return tau1.compareTo(tau2);
            }
        });
        s.addAll(userList);
        return new ArrayList<TaughtSubjects>(s);
    }


    //去除重复
    public static List<Subject> removeSubjectList(List<Subject> userList) {
        Set<Subject> s = null;
        if (userList != null && userList.size() > 0) {
            s = new TreeSet<Subject>(new Comparator<Subject>() {

                @Override
                public int compare(Subject o1, Subject o2) {

                    String tau1 = (TextUtils.isEmpty(o1.getMark())) ? o1.getSubjectName().trim() : (o1.getSubjectName().trim() + ": " + o1.getMark().trim());
                    String tau2 = (TextUtils.isEmpty(o2.getMark())) ? o2.getSubjectName().trim() : (o2.getSubjectName().trim() + ": " + o2.getMark().trim());
                    return tau1.compareTo(tau2);
                }
            });
            s.addAll(userList);
            return new ArrayList<>(s);
        }
        return null;
    }

    //审核展示
    public static String getAuditStatus(Context context, int type) {
        switch (type) {
            case Constants.PENDING:
                return context.getString(R.string.pedding);
//            case Constants.APPROVED:
//                return EducationAppliction.getInstance().getString(R.string.approved);
            case Constants.REJECTION:
                return context.getString(R.string.rejection);
            default:
                return context.getString(R.string.rejection);
        }

    }

}
