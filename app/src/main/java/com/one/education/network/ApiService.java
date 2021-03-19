package com.one.education.network;

import com.netease.nim.uikit.common.util.C;
import com.one.education.beans.AmountResponse;
import com.one.education.beans.BaseBean;
import com.one.education.beans.CourseResponse;
import com.one.education.beans.ExistPayPwdResponse;
import com.one.education.beans.GetFaqResponse;
import com.one.education.beans.LoginResponse;
import com.one.education.beans.NotifyPlanResponse;
import com.one.education.beans.OrderCreateResponse;
import com.one.education.beans.OrderListResponse;
import com.one.education.beans.OrderQueryResponse;
import com.one.education.beans.PayOrderResponse;
import com.one.education.beans.PerfectMyProfileResponse;
import com.one.education.beans.ProfileResponse;
import com.one.education.beans.QuestionResponse;
import com.one.education.beans.RechargeSetupResponse;
import com.one.education.beans.SmsCodeInfo;
import com.one.education.beans.SubjectResponse;
import com.one.education.beans.TeacherBean;
import com.one.education.beans.TeacherProfileResponse;
import com.one.education.beans.VersionUpdateResponse;
import com.one.education.retrofit.model.CreateOrderRsp;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * 服务器接口类
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("assets/app/checkVersionUpdate")
    Call<VersionUpdateResponse> checkVersionUpdate(@Field("packageName") String packageName,
                                                   @Field("versionCode") int versionCode,
                                                   @Field("terminal") int terminal);

    @FormUrlEncoded
    @POST("cms/faq/getFaqList")
    Call<QuestionResponse> getFaqList(@Field("pageNO") int pageNO,
                                      @Field("pageSize") int pageSize,
                                      @Field("totalCount") int totalCount);

    @FormUrlEncoded
    @POST("cms/article/getArticleList")
    Call<QuestionResponse> getArticleList(@Field("pageNO") int pageNO,
                                          @Field("pageSize") int pageSize,
                                          @Field("categoryId") int categoryId);


    @POST("user/account/logout")
    Call<BaseBean> logout();

    @FormUrlEncoded
    @POST("edu/student/getBaseProfile")
    Call<ProfileResponse> getBaseProfile(@Field("studentId") String uid);

    @FormUrlEncoded
    @POST("edu/student/followTeacher")
    Call<BaseBean> getFollowTeacher(@Field("teacherId") String uid);

    @FormUrlEncoded
    @POST("edu/student/cancelFollow")
    Call<BaseBean> getCancelFollow(@Field("teacherId") String uid);
    //    @POST("user/account/login")
    //    Call<LoginResponse> accountLogin(@Body LoginRequest request);

    @FormUrlEncoded
    @POST("user/account/login")
    Call<LoginResponse> login(@Field("account") String cellphone,
                              @Field("pwd") String pwd);

    @POST("user/profile/getAmount")
    Call<AmountResponse> getAmount();

    @POST("edu/course/getCourseList")
    Call<CourseResponse> getCourseList();

    @FormUrlEncoded
    @POST("edu/course/getSubjectList")
    Call<SubjectResponse> getSubject(@Field("courseId") String courseId);

    @FormUrlEncoded
    @POST("edu/student/perfectMyProfile")
    Call<BaseBean> perfectMyProfile(@Field("studiedSubjects") String subject, @Field("lastName") String lastName, @Field("firstName") String firstName, @Field("birthday") String birthday
            , @Field("sex") int sex, @Field("studentIdCard") String studentIdCard, @Field("mobileNO") String mobileNO, @Field("email") String email
            , @Field("wechat") String wechat, @Field("skype") String skype, @Field("school") String school, @Field("schoolYear") String schoolYear
            , @Field("englishSpel") String englishSpokenLevel, @Field("course") String course, @Field("targetUniversitys") String targetUniversitys, @Field("targetColleges") String targetColleges
            , @Field("userName") String userName
            , @Field("userPhotoUrl") String userPhotoUrl, @Field("studentId") int studentId, @Field("courseName") String courseName, @Field("regIp") String regIp);


    @FormUrlEncoded
    @POST("user/account/modifyPwd")
    Call<BaseBean> accountModifyPwd(@Field("oldPwd") String oldPwd, @Field("newPwd") String newPwd, @Field("newPwd2") String surePwd);

    @FormUrlEncoded
    @POST("user/account/setOrModifyPayPwd")
    Call<BaseBean> setOrModifyPayPwd(@Field("oldPwd") String oldPwd, @Field("newPwd") String newPwd, @Field("newPwd2") String surePwd);


    @POST("user/account/verifyExistPayPwd")
    Call<ExistPayPwdResponse> verifyExistPayPwd();


    @POST("edu/course/rechargeSetupList")
    Call<RechargeSetupResponse> rechargeSetupList();


    @FormUrlEncoded
    @POST("edu/teacher/getBaseProfile")
    Call<TeacherProfileResponse> getTeacherBaseProfile(@Field("teacherId") long teacherId);

    @FormUrlEncoded
    @POST("edu/student/getMyTeacherList")
    Call<TeacherBean> getMyTeacherList(@Field("studentId") String studentId);


    @POST("edu/student/getMyFollowTeacherList")
    Call<TeacherBean> getMyFollowTeacherList();

    @FormUrlEncoded
    @POST("upload/uploadPic")
    Call<TeacherBean> uploadPic(@Field("uploadFile") String uploadFile, @Field("fn") String fn,
                                @Field("dw") int dw, @Field("dh") int dh);

    @POST("edu/student/getMyProfile")
    Call<PerfectMyProfileResponse> getMyProfile();

    @POST("edu/recharge/getSetupList")
    Call<RechargeSetupResponse> getSetupList();

    @FormUrlEncoded
    @POST("edu/recharge/order/create")
    Call<OrderCreateResponse> orderCreate(@Field("channel") String channel, @Field("id") long id);

    //    @FormUrlEncoded
//@Field("orderState") int orderState, @Field("refundState") int refundState, @Field("pageNO") int pageNO
    @POST("edu/recharge/order/geOrderList")
    Call<OrderListResponse> getOrderList();

    @FormUrlEncoded
    @POST("pay/order/payOrder")
    Call<PayOrderResponse> payOrder(@Field("payChannel") String payChannel, @Field("amount") long amount, @Field("currency") String currency,
                                    @Field("subject") String subject, @Field("body") String body, @Field("orderCode") String orderCode,
                                    @Field("timeExpire") long timeExpire, @Field("clientIp") String clientIp, @Field("productCount") int productCount,
                                    @Field("extra") String extra, @Field("sign") String sign, @Field("payPwd") String payPwd);


    @FormUrlEncoded
    @POST("cms/faq/getFaq")
    Call<GetFaqResponse> getFag(@Field("id") long id);

    @FormUrlEncoded
    @POST("cms/article/getArticle")
    Call<GetFaqResponse> getArticle(@Field("id") long id);

    //设置提醒时间
    @FormUrlEncoded
    @POST("edu/im/notifyPlan/modify")
    Call<BaseBean> notifyPlanModify(@Field("plan") int plan);

    //获取提醒时间设置
    @POST("edu/im/getNotifyPlan")
    Call<NotifyPlanResponse> getNotifyPlan();

    //发送邮箱验证码
    @FormUrlEncoded
    @POST("user/account/sendSmsCode")
    Call<com.one.education.beans.SmsCodeInfo> sendSmsCode(@Field("mobileNO") String mobileNO);

    //发送邮箱验证码
    @FormUrlEncoded
    @POST("user/account/emailCode/send")
    Call<SmsCodeInfo> sendEmailCode(@Field("email") String email);

    //验证手机账号账号是否存在
    @FormUrlEncoded
    @POST("user/account/verifyExist")
    Call<SmsCodeInfo> verifyExist(@Field("mobileNO") String mobileNO);

    //验证邮箱账号是否存在
    @FormUrlEncoded
    @POST("user/account/verifyExist")
    Call<SmsCodeInfo> verifyExistEmail(@Field("email") String email);

    // 验证手机短信验证码

    @FormUrlEncoded
    @POST("user/account/verifySmsCode")
    Call<SmsCodeInfo> verifySmsCode(@Field("mobileNO") String cellphone,
                                    @Field("smsCode") String smsCode,
                                    @Field("time") String time,
                                    @Field("checksum") String checksum);

    // 验证邮箱短信验证码

    @FormUrlEncoded
    @POST("user/account//verifyEmailCode")
    Call<SmsCodeInfo> verifyEmailCode(@Field("email") String email,
                                      @Field("smsCode") String smsCode,
                                      @Field("time") String time,
                                      @Field("checksum") String checksum);

    @FormUrlEncoded
    @POST("edu/student/regStudentByMobile")
    Call<BaseBean> regStudentByMobile(@Field("linkMobileNO") String linkMobileNO, @Field("linkEmail") String linkEmail,
                                      @Field("mobileNO") String mobileNO, @Field("smsCode") String smsCode, @Field("time") String time,
                                      @Field("checksum") String checksum, @Field("userType") int userType,
                                      @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("studiedSubjects") String subject, @Field("lastName") String lastName, @Field("firstName") String firstName, @Field("birthday") String birthday
            , @Field("sex") int sex, @Field("studentIdCard") String studentIdCard, @Field("email") String email
            , @Field("wechat") String wechat, @Field("skype") String skype, @Field("school") String school, @Field("schoolYear") String schoolYear
            , @Field("englishSpel") String englishSpokenLevel, @Field("course") String course, @Field("targetUniversitys") String targetUniversitys, @Field("targetColleges") String targetColleges
            , @Field("userName") String userName
            , @Field("userPhotoUrl") String userPhotoUrl, @Field("studentId") int studentId, @Field("courseName") String courseName, @Field("regIp") String regIp);


    @FormUrlEncoded
    @POST("edu/student/regStudentByEmail")
    Call<BaseBean> regStudentByEmail(@Field("linkMobileNO") String linkMobileNO, @Field("linkEmail") String linkEmail,
                                     @Field("email") String email, @Field("smsCode") String smsCode, @Field("time") String time,
                                     @Field("checksum") String checksum, @Field("userType") int userType,
                                     @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("studiedSubjects") String subject, @Field("lastName") String lastName, @Field("firstName") String firstName, @Field("birthday") String birthday
            , @Field("sex") int sex, @Field("studentIdCard") String studentIdCard, @Field("mobileNO") String mobileNO
            , @Field("wechat") String wechat, @Field("skype") String skype, @Field("school") String school, @Field("schoolYear") String schoolYear
            , @Field("englishSpel") String englishSpokenLevel, @Field("course") String course, @Field("targetUniversitys") String targetUniversitys, @Field("targetColleges") String targetColleges
            , @Field("userName") String userName
            , @Field("userPhotoUrl") String userPhotoUrl, @Field("studentId") int studentId, @Field("courseName") String courseName, @Field("regIp") String regIp);


    @FormUrlEncoded
    @POST("edu/course/order/create")
    Call<OrderCreateResponse> orderCreate(@Field("teacherId") String teacherId, @Field("payChannel") String payChannel, @Field("products") String products);

    //支付
    @FormUrlEncoded
    @POST("pay/order/payOrder")
    Call<CreateOrderRsp> payOrder(@Field("payChannel") String payChannel, @Field("orderCode") String orderCode,
                                  @Field("payPwd") String payPwd, @Field("amount") String amount, @Field("currency") String currency,
                                  @Field("subject") String subject, @Field("body") String body,
                                  @Field("timeExpire") String timeExpire, @Field("clientIp") String clientIp,
                                  @Field("extra") String extra, @Field("sign") String sign, @Field("productCount") String productCount);

    @FormUrlEncoded
    @POST("edu/course/order/course/cancel")
    Call<BaseBean> courseCancel(@Field("orderCourseId") long orderCourseId);

    @FormUrlEncoded
    @POST("edu/course/order/query")
    Call<OrderQueryResponse> orderQuery(@Field("orderCode") String orderCode);

    @FormUrlEncoded
    @POST("edu/course/order/changeApply")
    Call<BaseBean> orderChangeApply(@Field("studentId") long studentId, @Field("teacherId") long teacherId,
                                    @Field("orderCourseId") long orderCourseId,
                                    @Field("applyBeginTime") long applyBeginTime, @Field("applyEndTime") long applyEndTime,
                                    @Field("appyReason") String appyReason);


}
