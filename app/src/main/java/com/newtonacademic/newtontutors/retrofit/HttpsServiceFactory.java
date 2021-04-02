package com.newtonacademic.newtontutors.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.commons.AppUtils;
import com.newtonacademic.newtontutors.BuildConfig;
import com.newtonacademic.newtontutors.login.ImLoginInfo;
import com.newtonacademic.newtontutors.login.LogoutInfo;
import com.newtonacademic.newtontutors.login.UserLoginInfo;
import com.newtonacademic.newtontutors.retrofit.model.CommentRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetAppointRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetArticleListRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetArticleRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetBaseProfile;
import com.newtonacademic.newtontutors.retrofit.model.GetBySpaceRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetCountryList;
import com.newtonacademic.newtontutors.retrofit.model.GetCoursewareList;
import com.newtonacademic.newtontutors.retrofit.model.GetMyFollowTeacherList;
import com.newtonacademic.newtontutors.retrofit.model.GetMyProfileRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetScheduleListByTeacherIdRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetStudentStudyCourseList;
import com.newtonacademic.newtontutors.retrofit.model.GetTeacherListRsp;
import com.newtonacademic.newtontutors.retrofit.model.RegisterInfo;
import com.newtonacademic.newtontutors.retrofit.model.SmsCodeInfo;
import com.newtonacademic.newtontutors.user.UserInstance;
import com.newtonacademic.mylibrary.ConstantGlobal;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lyy
 * @Description Https服务工厂
 * Copyright (C) 2018 Intretech Inc. All Rights Reserved.
 * Created by lyy on 2018/9/6.
 */
public class HttpsServiceFactory {
    private static final String TAG = "HttpsServiceFactory";

    private static IHttpsRequest newBuildRetrofitService() {
        //http拦截器。
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        });

        //日志显示级别
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
            loggingInterceptor.setLevel(level);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier()).addInterceptor(loggingInterceptor).addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder();

                    builder.header("Authorization", "TOKEN " + UserInstance.getInstance().getToken());
                    //Head设置  请求过滤
                    builder.header("X-User-Agent", String.format("{%s/%s}{%s/%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}", "android",
                            AppUtils.getSystemVersion(), AppUtils.getVersionName(EducationAppliction.getInstance().getApplicationContext()),
                            AppUtils.getVersionCode(EducationAppliction.getInstance().getApplicationContext()) + "",
                            AppUtils.getTelImei(EducationAppliction.getInstance().getApplicationContext()),
                            AppUtils.getTelImei(EducationAppliction.getInstance().getApplicationContext()),
                            AppUtils.getMacAddress(EducationAppliction.getInstance().getApplicationContext()),
                            AppUtils.getTelModel(), "CPU",
                            AppUtils.getUUID(EducationAppliction.getInstance().getApplicationContext()), "1"));

                    builder.method(original.method(), original.body());
                    return chain.proceed(builder.build());
                }).build();

        //要访问的主机地址，注意以 /（斜线） 结束，不然可能会抛出异常
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantGlobal.Net.URL + File.separator)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();


        return retrofit.create(IHttpsRequest.class);
    }

    /**
     * 获取验证码
     *
     * @param cellphone 手机号
     * @return SmsCodeInfo 请求响应
     */
    @NonNull
    public static SmsCodeInfo sendSmsCode(String cellphone) {
        Call<SmsCodeInfo> call = newBuildRetrofitService().sendSmsCode(cellphone);
        Log.i(TAG, call.request().url().toString());
        Response<SmsCodeInfo> response;
        try {
            response = call.execute();
            Log.i(TAG, "sendSmsCode message = " + response.toString());
            SmsCodeInfo smsCodeInfo = response.body();
            if (smsCodeInfo == null) {
                smsCodeInfo = new SmsCodeInfo();
                smsCodeInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                smsCodeInfo.setDescript("验证码获取失败");
            }

            return smsCodeInfo;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            SmsCodeInfo smsCodeInfo = new SmsCodeInfo();
            smsCodeInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
            smsCodeInfo.setDescript("验证码获取失败");
            return smsCodeInfo;
        }
    }

    /**
     * 手机号注册
     *
     * @param cellphone 手机号
     * @return SmsCodeInfo 请求响应
     */
    @NonNull
    public static RegisterInfo register(String cellphone,
                                        String smsCode,
                                        String time,
                                        String checksum,
                                        String pwd,
                                        String pwd2,
                                        int isSign) {
        Call<RegisterInfo> call = newBuildRetrofitService().register(cellphone,
                smsCode, time, checksum, 10, pwd, pwd2, isSign);
        Log.i(TAG, call.request().url().toString());
        Response<RegisterInfo> response;
        try {
            response = call.execute();
            Log.i(TAG, "register message = " + response.toString());
            RegisterInfo registerInfo = response.body();
            if (registerInfo == null) {
                registerInfo = new RegisterInfo();
                registerInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                registerInfo.setDescript("注册失败");
            }

            return registerInfo;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            RegisterInfo registerInfo = new RegisterInfo();
            registerInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
            registerInfo.setDescript("注册失败");
            return registerInfo;
        }
    }

    /**
     * 手机号+密码登录
     *
     * @param cellphone 手机号
     * @param pwd       密码
     * @return SmsCodeInfo 请求响应
     */
    @NonNull
    public static UserLoginInfo login(String cellphone,
                                      String pwd) {
        Call<UserLoginInfo> call = newBuildRetrofitService().login(cellphone, pwd);
        Log.i(TAG, call.request().url().toString());
        Response<UserLoginInfo> response;
        try {
            response = call.execute();
            Log.i(TAG, "login message = " + response.toString());
            UserLoginInfo userLoginInfo = response.body();
            if (userLoginInfo == null) {
                userLoginInfo = new UserLoginInfo();
                userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
            }

            return userLoginInfo;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
            userLoginInfo.setDescript(ResponseResult.getString(ResponseResult.ResponseCode.FAILURE));
            return userLoginInfo;
        }
    }

    /**
     * 获取Token
     *
     * @return ImLoginInfo 请求响应
     */
    public static ImLoginInfo getImLoginInfo() {
        Call<ImLoginInfo> call = newBuildRetrofitService().getAccessToken();
        Log.i(TAG, call.request().url().toString());
        Response<ImLoginInfo> response;
        try {
            response = call.execute();
            Log.i(TAG, "login message = " + response.toString());
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        ImLoginInfo imLoginInfo = new ImLoginInfo();
        imLoginInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
        imLoginInfo.setDescript("登录失败");
        return imLoginInfo;
    }

    /**
     * 退出登录
     *
     * @return SmsCodeInfo 请求响应
     */
    @NonNull
    public static LogoutInfo logout(String token) {
        Call<LogoutInfo> call = newBuildRetrofitService().logout();

        Log.i(TAG, call.request().url().toString());
        Response<LogoutInfo> response;
        try {
            response = call.execute();
            Log.i(TAG, "logout message = " + response.toString());
            LogoutInfo logoutInfo = response.body();
            if (logoutInfo == null) {
                logoutInfo = new LogoutInfo();
                logoutInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
                logoutInfo.setDescript("登出失败");
            }

            return logoutInfo;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            LogoutInfo logoutInfo = new LogoutInfo();
            logoutInfo.setStatus(ResponseResult.ResponseCode.FAILURE);
            logoutInfo.setDescript("登出失败");
            return logoutInfo;
        }
    }

    /**
     * 手机号注册
     *
     * @param cellphone 手机号
     * @return SmsCodeInfo 请求响应
     */
    @NonNull
    public static CommentRsp modifyPwdByMobile(String cellphone,
                                               String smsCode,
                                               long time,
                                               String checksum,
                                               String pwd,
                                               String pwd2) {
        Call<CommentRsp> call = newBuildRetrofitService().modifyPwdByMobile(cellphone,
                smsCode, time, checksum, pwd, pwd2);
        Log.i(TAG, call.request().url().toString());
        Response<CommentRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "register message = " + response.toString());
            CommentRsp commentRsp = response.body();
            if (commentRsp == null) {
                commentRsp = new CommentRsp();
                commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                commentRsp.setDescript("重置失败");
            }

            return commentRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            CommentRsp commentRsp = new CommentRsp();
            commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            commentRsp.setDescript("重置失败");
            return commentRsp;
        }
    }


    /**
     * 获取推荐老师的列表(约课)
     *
     * @param pageNO      页码
     * @param pageSize    页数
     * @param totalCount  总记录数
     * @param courseId    课程（0：不限，1：ALevel，2：IB ）
     * @param isRecommend 1：推荐，0：包括推荐与不推荐
     * @return GetTeacherListRsp
     */
    @NonNull
    public static GetTeacherListRsp getTeacherList(int pageNO, int pageSize, int totalCount, int courseId, int isRecommend) {
        Call<GetTeacherListRsp> call = newBuildRetrofitService().getTeacherList(pageNO, pageSize, totalCount, courseId, isRecommend);
        Log.i(TAG, call.request().url().toString());
        Response<GetTeacherListRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "getTeacherList message = " + response.toString());
            GetTeacherListRsp getTeacherListRsp = response.body();
            if (getTeacherListRsp == null) {
                getTeacherListRsp = new GetTeacherListRsp();
                getTeacherListRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getTeacherListRsp.setDescript("");
            }

            return getTeacherListRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetTeacherListRsp getTeacherListRsp = new GetTeacherListRsp();
            getTeacherListRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getTeacherListRsp.setDescript("");
            return getTeacherListRsp;
        }
    }

    /**
     * 获取课程列表
     *
     * @param studentId
     * @param pageNO
     * @param pageSize
     * @param totalCount
     * @return
     */
    public static GetStudentStudyCourseList getStudentStudyCourseList(int studentId, int pageNO, int pageSize, int totalCount) {
        return getStudentStudyCourseList(studentId, 0, pageNO, pageSize, totalCount);
    }

    /**
     * 获取课程列表
     *
     * @param studentId
     * @param pageNO
     * @param pageSize
     * @param totalCount
     * @return
     */
    public static GetStudentStudyCourseList getStudentStudyCourseList(int studentId, int state, int pageNO, int pageSize, int totalCount) {
        Call<GetStudentStudyCourseList> call;
        if (state == 0) {
            call = newBuildRetrofitService().getStudentStudyCourseList(studentId, pageNO, pageSize, totalCount);
        } else {
            call = newBuildRetrofitService().getStudentStudyCourseList(studentId, state, pageNO, pageSize, totalCount);
        }

        Log.i(TAG, call.request().url().toString());
        Response<GetStudentStudyCourseList> response;
        try {
            response = call.execute();
            Log.i(TAG, "getStudentStudyCourseList message = " + response.toString());
            GetStudentStudyCourseList getStudentStudyCourseList = response.body();
            if (getStudentStudyCourseList == null) {
                getStudentStudyCourseList = new GetStudentStudyCourseList();
                getStudentStudyCourseList.setStatus(ResponseResult.ResponseCode.FAILURE);
                getStudentStudyCourseList.setDescript("");
            }

            return getStudentStudyCourseList;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetStudentStudyCourseList getStudentStudyCourseList = new GetStudentStudyCourseList();
            getStudentStudyCourseList.setStatus(ResponseResult.ResponseCode.FAILURE);
            getStudentStudyCourseList.setDescript("");
            return getStudentStudyCourseList;
        }
    }

    /**
     * 获取课件列表
     *
     * @param studentId
     * @param pageNO
     * @param pageSize
     * @param totalCount
     * @return
     */
    public static GetCoursewareList getCoursewareList(int pageNO, int pageSize, int totalCount,
                                                      int studentId, int teacherId, int orderCourseId,
                                                      String checkSum) {
        Call<GetCoursewareList> call = newBuildRetrofitService().getCoursewareList(pageNO, pageSize, totalCount,
                studentId, teacherId, orderCourseId, checkSum);
        Log.i(TAG, call.request().url().toString());
        Response<GetCoursewareList> response;
        try {
            response = call.execute();
            Log.i(TAG, "getCoursewareList message = " + response.toString());
            GetCoursewareList getCoursewareList = response.body();
            if (getCoursewareList == null) {
                getCoursewareList = new GetCoursewareList();
                getCoursewareList.setStatus(ResponseResult.ResponseCode.FAILURE);
                getCoursewareList.setDescript("");
            }

            return getCoursewareList;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetCoursewareList getCoursewareList = new GetCoursewareList();
            getCoursewareList.setStatus(ResponseResult.ResponseCode.FAILURE);
            getCoursewareList.setDescript("");
            return getCoursewareList;
        }
    }

    /**
     * 课程评价
     *
     * @param id
     * @param orderCode
     * @param courseId
     * @param teacherId
     * @param commentStar1
     * @param commentStar2
     * @param commentStar3
     * @param commentStar4
     * @param commentStar5
     * @param comment
     * @return
     */
    public static CommentRsp coursecommentCreate(int id,
                                                 String orderCode,
                                                 int courseId,
                                                 int teacherId,
                                                 int commentStar1,
                                                 int commentStar2,
                                                 int commentStar3,
                                                 int commentStar4,
                                                 int commentStar5,
                                                 String comment) {
        Call<CommentRsp> call = newBuildRetrofitService().coursecommentCreate(id, orderCode,
                courseId, teacherId, commentStar1, commentStar2, commentStar3, commentStar4, commentStar5,
                comment);
        Log.i(TAG, call.request().url().toString());
        Response<CommentRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "CommentRsp message = " + response.toString());
            CommentRsp commentRsp = response.body();
            if (commentRsp == null) {
                commentRsp = new CommentRsp();
                commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                commentRsp.setDescript("");
            }

            return commentRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            CommentRsp commentRsp = new CommentRsp();
            commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            commentRsp.setDescript("");
            return commentRsp;
        }
    }

    /**
     * 获取教师详细信息
     *
     * @param teacherId
     * @return
     */
    public static GetMyProfileRsp getMyProfile(int teacherId) {
        Call<GetMyProfileRsp> call = newBuildRetrofitService().getMyProfile(teacherId);
        Log.i(TAG, call.request().url().toString());
        Response<GetMyProfileRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetMyProfileRsp message = " + response.toString());
            GetMyProfileRsp getMyProfileRsp = response.body();
            if (getMyProfileRsp == null) {
                getMyProfileRsp = new GetMyProfileRsp();
                getMyProfileRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getMyProfileRsp.setDescript("");
            }

            return getMyProfileRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetMyProfileRsp getMyProfileRsp = new GetMyProfileRsp();
            getMyProfileRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getMyProfileRsp.setDescript("");
            return getMyProfileRsp;
        }
    }

    /**
     * 获取教师基础信息
     *
     * @param teacherId
     * @return
     */
    public static GetBaseProfile getBaseProfile(int teacherId) {
        Call<GetBaseProfile> call = newBuildRetrofitService().getBaseProfile(teacherId);
        Log.i(TAG, call.request().url().toString());
        Response<GetBaseProfile> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetMyProfileRsp message = " + response.toString());
            GetBaseProfile getBaseProfile = response.body();
            if (getBaseProfile == null) {
                getBaseProfile = new GetBaseProfile();
                getBaseProfile.setStatus(ResponseResult.ResponseCode.FAILURE);
                getBaseProfile.setDescript("");
            }

            return getBaseProfile;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetBaseProfile getBaseProfile = new GetBaseProfile();
            getBaseProfile.setStatus(ResponseResult.ResponseCode.FAILURE);
            getBaseProfile.setDescript("");
            return getBaseProfile;
        }
    }

    /**
     * 获取关注列表
     *
     * @return
     */
    public static GetMyFollowTeacherList getMyFollowTeacherList() {
        Call<GetMyFollowTeacherList> call = newBuildRetrofitService().getMyFollowTeacherList();
        Log.i(TAG, call.request().url().toString());
        Response<GetMyFollowTeacherList> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetMyFollowTeacherList message = " + response.toString());
            GetMyFollowTeacherList getMyFollowTeacherList = response.body();
            if (getMyFollowTeacherList == null) {
                getMyFollowTeacherList = new GetMyFollowTeacherList();
                getMyFollowTeacherList.setStatus(ResponseResult.ResponseCode.FAILURE);
                getMyFollowTeacherList.setDescript("");
            }

            return getMyFollowTeacherList;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetMyFollowTeacherList getMyFollowTeacherList = new GetMyFollowTeacherList();
            getMyFollowTeacherList.setStatus(ResponseResult.ResponseCode.FAILURE);
            getMyFollowTeacherList.setDescript("");
            return getMyFollowTeacherList;
        }
    }

    /**
     * 关注/取消关注
     *
     * @return
     */
    public static CommentRsp followTeacher(int teacherId, boolean follow) {
        Call<CommentRsp> call;
        if (follow) {
            call = newBuildRetrofitService().followTeacher(teacherId);
        } else {
            call = newBuildRetrofitService().cancelFollow(teacherId);
        }

        Log.i(TAG, call.request().url().toString());
        Response<CommentRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "followTeacher message = " + response.toString());
            CommentRsp commentRsp = response.body();
            if (commentRsp == null) {
                commentRsp = new CommentRsp();
                commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                commentRsp.setDescript("");
            }

            return commentRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            CommentRsp commentRsp = new CommentRsp();
            commentRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            commentRsp.setDescript("");
            return commentRsp;
        }
    }

    /**
     * 获取老师排课列表
     *
     * @return
     */
    public static GetScheduleListByTeacherIdRsp getScheduleListByTeacherId(long teacherId) {
        Call<GetScheduleListByTeacherIdRsp> call = newBuildRetrofitService().getScheduleListByTeacherId(teacherId);
        Log.i(TAG, call.request().url().toString());
        Response<GetScheduleListByTeacherIdRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetScheduleListByTeacherIdRsp message = " + response.toString());
            GetScheduleListByTeacherIdRsp getScheduleListByTeacherIdRsp = response.body();
            if (getScheduleListByTeacherIdRsp == null) {
                getScheduleListByTeacherIdRsp = new GetScheduleListByTeacherIdRsp();
                getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getScheduleListByTeacherIdRsp.setDescript("");
            }

            return getScheduleListByTeacherIdRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetScheduleListByTeacherIdRsp getScheduleListByTeacherIdRsp = new GetScheduleListByTeacherIdRsp();
            getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getScheduleListByTeacherIdRsp.setDescript("");
            return getScheduleListByTeacherIdRsp;
        }
    }


    /**
     * 获取老师已经被预约的排课列表
     *
     * @return
     */
    public static GetAppointRsp getTeacherAppointedTimeList(long teacherId) {
        Call<GetAppointRsp> call = newBuildRetrofitService().getTeacherAppointedTimeList(teacherId,
                1, 500);
        Log.i(TAG, call.request().url().toString());
        Response<GetAppointRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetScheduleListByTeacherIdRsp message = " + response.toString());
            GetAppointRsp getScheduleListByTeacherIdRsp = response.body();
            if (getScheduleListByTeacherIdRsp == null) {
                getScheduleListByTeacherIdRsp = new GetAppointRsp();
                getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getScheduleListByTeacherIdRsp.setDescript("");
            }

            return getScheduleListByTeacherIdRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetAppointRsp getScheduleListByTeacherIdRsp = new GetAppointRsp();
            getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getScheduleListByTeacherIdRsp.setDescript("");
            return getScheduleListByTeacherIdRsp;
        }
    }

    /**
     * 获取学生已经预约的排课列表
     *
     * @return
     */
    public static GetAppointRsp getMyAppointedTimeList() {
        Call<GetAppointRsp> call = newBuildRetrofitService().getMyAppointedTimeList(1,500);
        Log.i(TAG, call.request().url().toString());
        Response<GetAppointRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetScheduleListByTeacherIdRsp message = " + response.toString());
            GetAppointRsp getScheduleListByTeacherIdRsp = response.body();
            if (getScheduleListByTeacherIdRsp == null) {
                getScheduleListByTeacherIdRsp = new GetAppointRsp();
                getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getScheduleListByTeacherIdRsp.setDescript("");
            }

            return getScheduleListByTeacherIdRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetAppointRsp getScheduleListByTeacherIdRsp = new GetAppointRsp();
            getScheduleListByTeacherIdRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getScheduleListByTeacherIdRsp.setDescript("");
            return getScheduleListByTeacherIdRsp;
        }
    }

    /**
     * 获取广告信息
     *
     * @return
     */
    public static GetBySpaceRsp getBySpace(String code) {
        Call<GetBySpaceRsp> call = newBuildRetrofitService().getBySpace(code);
        Log.i(TAG, call.request().url().toString());
        Response<GetBySpaceRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetBySpaceRsp message = " + response.toString());
            GetBySpaceRsp getBySpaceRsp = response.body();
            if (getBySpaceRsp == null) {
                getBySpaceRsp = new GetBySpaceRsp();
                getBySpaceRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getBySpaceRsp.setDescript("");
            }

            return getBySpaceRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetBySpaceRsp getBySpaceRsp = new GetBySpaceRsp();
            getBySpaceRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getBySpaceRsp.setDescript("");
            return getBySpaceRsp;
        }
    }

    /**
     * 获取外教风采
     *
     * @return
     */
    public static GetArticleListRsp getArticleList() {
        Call<GetArticleListRsp> call = newBuildRetrofitService().getArticleList(27, 1);
        Log.i(TAG, call.request().url().toString());
        Response<GetArticleListRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetArticleList message = " + response.toString());
            GetArticleListRsp getArticleListRsp = response.body();
            if (getArticleListRsp == null) {
                getArticleListRsp = new GetArticleListRsp();
                getArticleListRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getArticleListRsp.setDescript("");
            }

            return getArticleListRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetArticleListRsp getArticleListRsp = new GetArticleListRsp();
            getArticleListRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getArticleListRsp.setDescript("");
            return getArticleListRsp;
        }
    }

    /**
     * 获取外教风采详情
     *
     * @return
     */
    public static GetArticleRsp getArticle(int id) {
        Call<GetArticleRsp> call = newBuildRetrofitService().getArticle(id);
        Log.i(TAG, call.request().url().toString());
        Response<GetArticleRsp> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetArticleRsp message = " + response.toString());
            GetArticleRsp getArticleRsp = response.body();
            if (getArticleRsp == null) {
                getArticleRsp = new GetArticleRsp();
                getArticleRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
                getArticleRsp.setDescript("");
            }

            return getArticleRsp;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetArticleRsp getArticleRsp = new GetArticleRsp();
            getArticleRsp.setStatus(ResponseResult.ResponseCode.FAILURE);
            getArticleRsp.setDescript("");
            return getArticleRsp;
        }
    }

    /**
     * 获取国家列表
     *
     * @return
     */
    public static GetCountryList getCountryList() {
        Call<GetCountryList> call = newBuildRetrofitService().getCountryList();
        Log.i(TAG, call.request().url().toString());
        Response<GetCountryList> response;
        try {
            response = call.execute();
            Log.i(TAG, "GetCountryList message = " + response.toString());
            GetCountryList getCountryList = response.body();
            if (getCountryList == null) {
                getCountryList = new GetCountryList();
                getCountryList.setStatus(ResponseResult.ResponseCode.FAILURE);
                getCountryList.setDescript("");
            }

            return getCountryList;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            GetCountryList getCountryList = new GetCountryList();
            getCountryList.setStatus(ResponseResult.ResponseCode.FAILURE);
            getCountryList.setDescript("");
            return getCountryList;
        }
    }

    /**
     * 获取国家列表
     * @return
     */
//    public static GetCountryList createOrder() {
//        Call<GetCountryList> call = newBuildRetrofitService().createOrderRsp();
//        Log.i(TAG, call.request().url().toString());
//        Response<GetCountryList> response;
//        try {
//            response = call.execute();
//            Log.i(TAG, "GetCountryList message = " + response.toString());
//            GetCountryList getCountryList = response.body();
//            if (getCountryList == null) {
//                getCountryList = new GetCountryList();
//                getCountryList.setStatus(ResponseResult.ResponseCode.FAILURE);
//                getCountryList.setDescript("");
//            }
//
//            return getCountryList;
//        } catch (IOException e) {
//            Log.e(TAG, e.toString());
//            GetCountryList getCountryList = new GetCountryList();
//            getCountryList.setStatus(ResponseResult.ResponseCode.FAILURE);
//            getCountryList.setDescript("");
//            return getCountryList;
//        }
//    }

}
