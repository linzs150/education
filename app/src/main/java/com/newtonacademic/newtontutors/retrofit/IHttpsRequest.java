package com.newtonacademic.newtontutors.retrofit;

import com.newtonacademic.newtontutors.login.ImLoginInfo;
import com.newtonacademic.newtontutors.login.UserLoginInfo;
import com.newtonacademic.newtontutors.login.LogoutInfo;
import com.newtonacademic.newtontutors.retrofit.model.CommentRsp;
import com.newtonacademic.newtontutors.retrofit.model.CreateOrderRsp;
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

import retrofit2.Call;
import retrofit2.http.*;

/**
 * The interface Https request.
 *
 * @author lyy
 * @Description Http接口类
 */
public interface IHttpsRequest {

    /**
     * 获取验证码
     *
     * @param cellphone 手机号
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("user/account/sendSmsCode")
    Call<SmsCodeInfo> sendSmsCode(@Field("mobileNO") String cellphone);

    /**
     * 手机号注册
     *
     * @param cellphone 手机号
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("user/account/regUserByMobileNO")
    Call<RegisterInfo> register(@Field("mobileNO") String cellphone,
                                @Field("smsCode") String smsCode,
                                @Field("time") String time,
                                @Field("checksum") String checksum,
                                @Field("userType") int userType,
                                @Field("pwd") String pwd,
                                @Field("pwd2") String pwd2,
                                @Field("isSign") int isSign);

    /**
     * 账号密码/登录
     *
     * @param cellphone 手机号
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("user/account/login")
    Call<UserLoginInfo> login(@Field("account") String cellphone,
                              @Field("pwd") String pwd);

    /**
     * 获取Im账号
     *
     * @return HttpResponse 请求响应
     */
    @POST("user/yun163/getAccessToken")
    Call<ImLoginInfo> getAccessToken();

    /**
     * 登出
     *
     * @return HttpResponse 请求响应
     */
    @POST("user/account/logout")
    Call<LogoutInfo> logout();


    /**
     * 手机号注册
     *
     * @param cellphone 手机号
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("user/account/modifyPwdByMobile")
    Call<CommentRsp> modifyPwdByMobile(@Field("mobileNO") String cellphone,
                                       @Field("smsCode") String smsCode,
                                       @Field("time") long time,
                                       @Field("checksum") String checksum,
                                       @Field("newPwd") String pwd,
                                       @Field("newPwd2") String pwd2);


    /**
     * 获取推荐老师的列表
     *
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("edu/teacher/getTeacherList")
    Call<GetTeacherListRsp> getTeacherList(@Field("pageNO") int pageNO,
                                           @Field("pageSize") int pageSize,
                                           @Field("totalCount") int totalCount,
                                           @Field("courseId") int courseId,
                                           @Field("isRecommend") int isRecommend);

    /**
     * 获取学生所有课程表
     *
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("edu/course/getStudentStudyCourseList")
    Call<GetStudentStudyCourseList> getStudentStudyCourseList(@Field("studentId") int studentId,
                                                              @Field("pageNo") int pageNo,
                                                              @Field("pageSize") int pageSize,
                                                              @Field("totalCount") int totalCount);

    /**
     * 获取学生指定状态的课程表
     *
     * @return HttpResponse 请求响应
     */
    @FormUrlEncoded
    @POST("edu/course/getStudentStudyCourseList")
    Call<GetStudentStudyCourseList> getStudentStudyCourseList(@Field("studentId") int studentId,
                                                              @Field("state") int state,
                                                              @Field("pageNo") int pageNo,
                                                              @Field("pageSize") int pageSize,
                                                              @Field("totalCount") int totalCount);

    /**
     * 获取课件列表
     *
     * @param pageNO
     * @param pageSize
     * @param totalCount
     * @param studentId
     * @param teacherId
     * @param orderCourseId
     * @param checksum
     * @return
     */
    @FormUrlEncoded
    @POST("edu/course/getCoursewareList")
    Call<GetCoursewareList> getCoursewareList(@Field("pageNO") int pageNO,
                                              @Field("pageSize") int pageSize,
                                              @Field("totalCount") int totalCount,
                                              @Field("studentId") int studentId,
                                              @Field("teacherId") int teacherId,
                                              @Field("orderCourseId") int orderCourseId,
                                              @Field("checksum") String checksum);


    @FormUrlEncoded
    @POST("edu/course/comment/create")
    Call<CommentRsp> coursecommentCreate(@Field("id") int id,
                                         @Field("orderCode") String orderCode,
                                         @Field("courseId") int courseId,
                                         @Field("teacherId") int teacherId,
                                         @Field("commentStar1") int commentStar1,
                                         @Field("commentStar2") int commentStar2,
                                         @Field("commentStar3") int commentStar3,
                                         @Field("commentStar4") int commentStar4,
                                         @Field("commentStar5") int commentStar5,
                                         @Field("comment") String comment);


    @FormUrlEncoded
    @POST("edu/teacher/getMyProfile")
    Call<GetMyProfileRsp> getMyProfile(@Field("teacherId") int teacherId);

    @FormUrlEncoded
    @POST("edu/teacher/getBaseProfile")
    Call<GetBaseProfile> getBaseProfile(@Field("teacherId") int teacherId);

    @POST("edu/student/getMyFollowTeacherList")
    Call<GetMyFollowTeacherList> getMyFollowTeacherList();

    @FormUrlEncoded
    @POST("edu/student/followTeacher")
    Call<CommentRsp> followTeacher(@Field("teacherId") int teacherId);

    @FormUrlEncoded
    @POST("edu/student/cancelFollow")
    Call<CommentRsp> cancelFollow(@Field("teacherId") int teacherId);

    @FormUrlEncoded
    @POST("edu/teacher/schedule/getScheduleListByTeacherId")
    Call<GetScheduleListByTeacherIdRsp> getScheduleListByTeacherId(@Field("teacherId") long teacherId);

    @FormUrlEncoded
    @POST("edu/teacher/getTeacherAppointedTimeList")
    Call<GetAppointRsp> getTeacherAppointedTimeList(@Field("teacherId") long teacherId,
                                                    @Field("pageNO") int pageNO,
                                                    @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("edu/student/getMyAppointedTimeList")
    Call<GetAppointRsp> getMyAppointedTimeList(@Field("pageNO") int pageNO,
                                               @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("assets/advertising/getBySpace")
    Call<GetBySpaceRsp> getBySpace(@Field("code") String code);

    @FormUrlEncoded
    @POST("cms/article/getArticleList")
    Call<GetArticleListRsp> getArticleList(@Field("categoryId") int categoryId, @Field("isPic") int isPic);

    @FormUrlEncoded
    @POST("cms/article/getArticle")
    Call<GetArticleRsp> getArticle(@Field("id") int id);

    @POST("assets/dist/getCountryList")
    Call<GetCountryList> getCountryList();

    @POST("edu/course/order/create")
    Call<CreateOrderRsp> createOrderRsp();


}
