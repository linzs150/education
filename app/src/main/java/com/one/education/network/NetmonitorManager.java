package com.one.education.network;


import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.one.education.beans.AmountResponse;
import com.one.education.beans.BaseBean;
import com.one.education.beans.ChangeApplyBean;
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
import com.one.education.beans.ProfileRequest;
import com.one.education.beans.ProfileResponse;
import com.one.education.beans.QuestRequest;
import com.one.education.beans.QuestionResponse;
import com.one.education.beans.RechargeSetupResponse;
import com.one.education.beans.SmsCodeInfo;
import com.one.education.beans.StudentBeanInfo;
import com.one.education.beans.SubjectResponse;
import com.one.education.beans.TeacherBean;
import com.one.education.beans.TeacherProfileResponse;
import com.one.education.beans.UploadPicUResponse;
import com.one.education.beans.VersionUpdateRequest;
import com.one.education.beans.VersionUpdateResponse;
import com.one.education.commons.AppUtils;
import com.one.education.commons.SharedPreferencesUtils;

import retrofit.Call;

/**
 * 服務器接口
 */

public class NetmonitorManager {


    //版本升级
    public static Call checkVersionUpdate(Context mct, RestNewCallBack<VersionUpdateResponse> callBack) {
        VersionUpdateRequest request = new VersionUpdateRequest();
        request.setPackageName(AppUtils.getPackageName(mct));
        request.setVersionCode(AppUtils.getVersionCode(mct));
        request.setTerminal(1);
        Call call = ApiClient.getApiService().checkVersionUpdate(AppUtils.getPackageName(mct), AppUtils.getVersionCode(mct), 1);
        call.enqueue(callBack);

        return call;
    }

    //问题列表

    public static Call getFaqList(QuestRequest request, RestNewCallBack<QuestionResponse> callBack) {
        Call call = ApiClient.getApiService().getFaqList(request.getPageNO(), request.getPageSize(), request.getTotalCount());
        call.enqueue(callBack);

        return call;
    }

    //问题列表

    public static Call getArticleList(QuestRequest request, RestNewCallBack<QuestionResponse> callBack) {
        Call call = ApiClient.getApiService().getArticleList(request.getPageNO(), request.getPageSize(), request.getCategoryId());
        call.enqueue(callBack);

        return call;
    }

    //退出
    public static Call logout(RestNewCallBack<BaseBean> callBack) {
        Call call = ApiClient.getApiService().logout();
        call.enqueue(callBack);
        return call;
    }

    //获取学生基本信息
    public static Call getBaseProfile(RestNewCallBack<ProfileResponse> callBack) {

        ProfileRequest request = new ProfileRequest();
        request.setUid(SharedPreferencesUtils.getInstance().getInt("uid", 0) + "");
        Call call = ApiClient.getApiService().getBaseProfile(SharedPreferencesUtils.getInstance().getInt("uid", 0) + "");
        call.enqueue(callBack);
        return call;

    }

    public static Call getAccountLogin(String account, String pwd, RestNewCallBack<LoginResponse> callBack) {
        //        LoginRequest request = new LoginRequest();
        //        request.setAccount(account);
        //        request.setPwd(pwd);
        Call call = ApiClient.getApiService().login(account, pwd);
        call.enqueue(callBack);
        return call;
    }

    public static Call getAmount(RestNewCallBack<AmountResponse> callBack) {
        Call call = ApiClient.getApiService().getAmount();
        call.enqueue(callBack);
        return call;
    }

    public static Call getCourseList(RestNewCallBack<CourseResponse> callBack) {
        Call call = ApiClient.getApiService().getCourseList();
        call.enqueue(callBack);
        return call;
    }

    public static Call getSubject(String courseId, RestNewCallBack<SubjectResponse> callBack) {
        Call call = ApiClient.getApiService().getSubject(courseId);
        call.enqueue(callBack);
        return call;
    }

    //关注老师
    public static Call followTeacher(RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().getFollowTeacher(SharedPreferencesUtils.getInstance().getInt("uid", 0) + "");
        call.enqueue(callBack);
        return call;
    }

    //取消关注老师
    public static Call CancelFollowTeacher(RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().getCancelFollow(SharedPreferencesUtils.getInstance().getInt("uid", 0) + "");
        call.enqueue(callBack);
        return call;
    }

    //修改密码
    public static Call accountModifyPwd(String old, String newPwd, String surePwd, RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().accountModifyPwd(old, newPwd, surePwd);
        call.enqueue(callBack);
        return call;

    }

    //修改或设置支付密码
    public static Call setOrModifyPayPwd(String old, String newPwd, String surePwd, RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().setOrModifyPayPwd(old, newPwd, surePwd);
        call.enqueue(callBack);
        return call;

    }

    //检测是否已经设置支付密码

    public static Call verifyExistPayPwd(RestNewCallBack<ExistPayPwdResponse> callBack) {
        Call call = ApiClient.getApiService().verifyExistPayPwd();
        call.enqueue(callBack);
        return call;
    }

    //预存金额

    public static Call rechargeSetupList(RestNewCallBack<RechargeSetupResponse> callBack) {
        Call call = ApiClient.getApiService().rechargeSetupList();
        call.enqueue(callBack);
        return call;
    }

    //老师基本信息

    public static Call teacherGetBaseProfile(long teachId, RestNewCallBack<TeacherProfileResponse> callBack) {

        Call call = ApiClient.getApiService().getTeacherBaseProfile(teachId);
        call.enqueue(callBack);
        return call;
    }

    //获取我的老师列表

    public static Call getMyTeacherList(RestNewCallBack<TeacherBean> callBack) {

        Call call = ApiClient.getApiService().getMyTeacherList(SharedPreferencesUtils.getInstance().getInt("uid", 0) + "");
        call.enqueue(callBack);
        return call;

    }

    //获取我关注的老师列表

    public static Call getMyFollowTeacherList(RestNewCallBack<TeacherBean> callBack) {
        Call call = ApiClient.getApiService().getMyFollowTeacherList();
        call.enqueue(callBack);
        return call;
    }


    //图片上传

    public static Call uploadPic(String file, String edu, int dw, int dh, RestNewCallBack<UploadPicUResponse> callBack) {
        Call call = ApiClient.getApiService().uploadPic(file, edu, dw, dh);
        call.enqueue(callBack);
        return call;
    }

    //
    public static Call getMyProfile(RestNewCallBack<PerfectMyProfileResponse> callBack) {
        Call call = ApiClient.getApiService().getMyProfile();
        call.enqueue(callBack);
        return call;
    }

    //取消
    public static Call getCancelFollow(String teachId, RestNewCallBack<BaseBean> callBack) {
        Call call = ApiClient.getApiService().getCancelFollow(teachId);
        call.enqueue(callBack);
        return call;

    }

    public static Call getFollowTeacher(String teachId, RestNewCallBack<BaseBean> callBack) {
        Call call = ApiClient.getApiService().getFollowTeacher(teachId);
        call.enqueue(callBack);
        return call;
    }

    //获取充值档位清
    public static Call getSetupList(RestNewCallBack<RechargeSetupResponse> callBack) {
        Call call = ApiClient.getApiService().getSetupList();
        call.enqueue(callBack);
        return call;
    }

    //充值下单
    public static Call orderCreate(String channel, long id, RestNewCallBack<OrderCreateResponse> callBack) {

        Call call = ApiClient.getApiService().orderCreate(channel, id);
        call.enqueue(callBack);
        return call;

    }

    //int orderState, int refundState, int pageNO,

    public static Call getOrderList(RestNewCallBack<OrderListResponse> callBack) {

        Call call = ApiClient.getApiService().getOrderList();
        call.enqueue(callBack);
        return call;

    }

    public static Call courseOrderCreate(String teacherId, String payChannel, String products, RestNewCallBack<OrderCreateResponse> createResponseRestNewCallBack) {

        Call call = ApiClient.getApiService().orderCreate(teacherId, payChannel, products);
        call.enqueue(createResponseRestNewCallBack);
        return call;
    }


    public static Call payOrder(String pwd, OrderCreateResponse.OrderCreate orderCreate, RestNewCallBack<PayOrderResponse> callBack) {

        Call call = ApiClient.getApiService().payOrder(orderCreate.getPayChannel(), orderCreate.getAmount(), orderCreate.getCurrency(), orderCreate.getSubject()
                , orderCreate.getBody(), orderCreate.getOrderCode(), orderCreate.getTimeExpire(), orderCreate.getClientIp(), orderCreate.getProductCount(), orderCreate.getExtra(), orderCreate.getSign(), pwd);
        call.enqueue(callBack);
        return call;
    }

    public static Call getFag(long id, RestNewCallBack<GetFaqResponse> callBack) {

        Call call = ApiClient.getApiService().getFag(id);
        call.enqueue(callBack);
        return call;
    }

    public static Call getArticle(long id, RestNewCallBack<GetFaqResponse> callBack) {

        Call call = ApiClient.getApiService().getArticle(id);
        call.enqueue(callBack);
        return call;
    }

    //提醒
    public static Call notifyPlanModify(int plan, RestNewCallBack<BaseBean> callBack) {
        Call call = ApiClient.getApiService().notifyPlanModify(plan);
        call.enqueue(callBack);
        return call;
    }

    public static Call getNotifyPlan(RestNewCallBack<NotifyPlanResponse> callBack) {
        Call call = ApiClient.getApiService().getNotifyPlan();
        call.enqueue(callBack);

        return call;
    }

    //获取手机验证码

    public static Call sendSmsCode(String phone, RestNewCallBack<SmsCodeInfo> callBack) {

        Call call = ApiClient.getApiService().sendSmsCode(phone);
        call.enqueue(callBack);

        return call;
    }

    //获取邮箱验证码

    public static Call sendEmailCode(String email, RestNewCallBack<SmsCodeInfo> callBack) {

        Call call = ApiClient.getApiService().sendEmailCode(email);
        call.enqueue(callBack);

        return call;
    }

    //验证手机账号账号是否存在
    public static Call verifyExist(String phone, RestNewCallBack<SmsCodeInfo> callBack) {

        Call call = ApiClient.getApiService().verifyExist(phone);
        call.enqueue(callBack);
        return call;

    }

    //验证邮箱账号是否存在
    public static Call verifyExistEmail(String email, RestNewCallBack<SmsCodeInfo> callBack) {

        Call call = ApiClient.getApiService().verifyExistEmail(email);
        call.enqueue(callBack);
        return call;

    }

    //验证手机短信验证码
    public static Call verifySmsCode(String account, String smsCode, String time, String checksum, RestNewCallBack<SmsCodeInfo> callBack) {

        Call call = ApiClient.getApiService().verifySmsCode(account, smsCode, time, checksum);
        call.enqueue(callBack);
        return call;
    }

    //验证邮箱短信验证码
    public static Call verifyEmailCode(String account, String smsCode, String time, String checksum, RestNewCallBack<SmsCodeInfo> callBack) {
        Call call = ApiClient.getApiService().verifyEmailCode(account, smsCode, time, checksum);
        call.enqueue(callBack);
        return call;
    }


    //完善学生资料

    public static Call perfectMyProfile(PerfectMyProfileResponse perfectMyProfile, RestNewCallBack<BaseBean> callBack) {
        String string = "";
        if (perfectMyProfile != null) {
            StringBuffer target = new StringBuffer();


            if (perfectMyProfile.getStudiedSubjects() != null && perfectMyProfile.getStudiedSubjects().size() > 0) {
                for (SubjectResponse.Subject subject : perfectMyProfile.getStudiedSubjects()) {
                    target.append(subject.getSubjectId()).append(",");
                }

                if (!TextUtils.isEmpty(target.toString())) {
                    string = target.toString().substring(0, target.toString().length() - 1);
                }
            }

            string = JSONArray.toJSONString(perfectMyProfile.getStudiedSubjects());
        }
        Call call = ApiClient.getApiService().perfectMyProfile(string, perfectMyProfile.getLastName()
                , perfectMyProfile.getFirstName(), perfectMyProfile.getBirthday(), perfectMyProfile.getSex(), perfectMyProfile.getStudentIdCard(),
                perfectMyProfile.getMobileNO(), perfectMyProfile.getEmail(), perfectMyProfile.getWechat(), perfectMyProfile.getSkype(), perfectMyProfile.getSchool()
                , perfectMyProfile.getSchoolYear(), perfectMyProfile.getEnglishSpokenLevel(), perfectMyProfile.getCourse(), perfectMyProfile.getTargetUniversitys(),
                perfectMyProfile.getTargetColleges(), perfectMyProfile.getUserName(), perfectMyProfile.getUserPhotoUrl(), perfectMyProfile.getStudentId(), perfectMyProfile.getCourseName(), perfectMyProfile.getRegIp()
        );
        call.enqueue(callBack);
        return call;
    }

    //手机注册
    public static Call registerStudent(StudentBeanInfo info, PerfectMyProfileResponse perfectMyProfileResponse, RestNewCallBack<BaseBean> callBack) {
        Call call = null;

        String string = "";
        if (perfectMyProfileResponse != null) {
            StringBuffer target = new StringBuffer();


            if (perfectMyProfileResponse.getStudiedSubjects() != null && perfectMyProfileResponse.getStudiedSubjects().size() > 0) {
                for (SubjectResponse.Subject subject : perfectMyProfileResponse.getStudiedSubjects()) {
                    target.append(subject.getSubjectId()).append(",");
                }

                if (!TextUtils.isEmpty(target.toString())) {
                    string = target.toString().substring(0, target.toString().length() - 1);
                }
            }

            string = JSONArray.toJSONString(perfectMyProfileResponse.getStudiedSubjects());
        }

        if (info.getmType() == 0) {
            call = ApiClient.getApiService().regStudentByMobile(perfectMyProfileResponse.getMobileNO(), perfectMyProfileResponse.getEmail(), info.getAccount(), info
                            .getSmsCode(), info.getTime(), info.getCheckNum(), 10, info.getPwd(), info.getPwd2(), string, perfectMyProfileResponse.getLastName(), perfectMyProfileResponse.getFirstName(),
                    perfectMyProfileResponse.getBirthday(), perfectMyProfileResponse.getSex(), perfectMyProfileResponse.getStudentIdCard(),
                    perfectMyProfileResponse.getEmail(), perfectMyProfileResponse.getWechat(), perfectMyProfileResponse.getSkype(), perfectMyProfileResponse.getSchool()
                    , perfectMyProfileResponse.getSchoolYear(), perfectMyProfileResponse.getEnglishSpokenLevel(), perfectMyProfileResponse.getCourse(), perfectMyProfileResponse.getTargetUniversitys(),
                    perfectMyProfileResponse.getTargetColleges(), perfectMyProfileResponse.getUserName(), perfectMyProfileResponse.getUserPhoto(), perfectMyProfileResponse.getStudentId(), perfectMyProfileResponse.getCourseName(), perfectMyProfileResponse.getRegIp()
            );
        } else {
            call = ApiClient.getApiService().regStudentByEmail(perfectMyProfileResponse.getMobileNO(), perfectMyProfileResponse.getEmail(), info.getAccount(), info
                            .getSmsCode(), info.getTime(), info.getCheckNum(), 10, info.getPwd(), info.getPwd2(), string, perfectMyProfileResponse.getLastName(), perfectMyProfileResponse.getFirstName(),
                    perfectMyProfileResponse.getBirthday(), perfectMyProfileResponse.getSex(), perfectMyProfileResponse.getStudentIdCard(),
                    perfectMyProfileResponse.getMobileNO(), perfectMyProfileResponse.getWechat(), perfectMyProfileResponse.getSkype(), perfectMyProfileResponse.getSchool()
                    , perfectMyProfileResponse.getSchoolYear(), perfectMyProfileResponse.getEnglishSpokenLevel(), perfectMyProfileResponse.getCourse(), perfectMyProfileResponse.getTargetUniversitys(),
                    perfectMyProfileResponse.getTargetColleges(), perfectMyProfileResponse.getUserName(), perfectMyProfileResponse.getUserPhoto(), perfectMyProfileResponse.getStudentId(), perfectMyProfileResponse.getCourseName(), perfectMyProfileResponse.getRegIp());
        }

        call.enqueue(callBack);
        return call;

    }

    //订单取消
    public static Call courseCancel(long orderCourseId, RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().courseCancel(orderCourseId);
        call.enqueue(callBack);
        return call;

    }

    //查询订单是否有效
    public static Call courseOrderQuery(String orderCode, RestNewCallBack<OrderQueryResponse> orderCreateResponseRestNewCallBack) {

        Call call = ApiClient.getApiService().orderQuery(orderCode);
        call.enqueue(orderCreateResponseRestNewCallBack);
        return call;

    }

    //课程改签申请
    public static Call orderChangeApply(ChangeApplyBean bean, RestNewCallBack<BaseBean> callBack) {

        Call call = ApiClient.getApiService().orderChangeApply(bean.getStudentId(), bean.getTeacherId(), bean.getOrderCourseId()
                , bean.getApplyBeginTime(), bean.getApplyEndTime(), bean.getAppyReason());
        call.enqueue(callBack);
        return call;

    }

}
