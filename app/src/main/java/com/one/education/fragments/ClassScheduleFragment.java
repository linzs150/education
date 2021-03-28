package com.one.education.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import uikit.api.NimUIKit;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.BaseFragment;
import com.one.education.activities.TeacherDetail1Activity;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.ViewHolder;
import com.one.education.beans.BaseBean;
import com.one.education.beans.OrderQueryResponse;
import com.one.education.classappointment.ClassAppointmentActivity;
import com.one.education.classappointment.OrderConfirmActivity;
import com.one.education.classappointment.TeacherSearchActivity;
import com.one.education.classschedule.CourseEvaluationActivity;
import com.one.education.classschedule.CoursewareActivity;
import com.one.education.commons.SharedPreferencesUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.dialogs.DialogCourseCancel;
import com.one.education.education.MainActivity;
import com.one.education.education.R;
import com.one.education.language.MultiLanguageUtil;
import com.one.education.language.SpUtil;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.user.UserInstance;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.TimeUtils;
import com.one.education.widget.ClassStartDialog;
import com.one.education.widget.smartrefresh.layout.SmartRefreshLayout;
import com.one.education.widget.smartrefresh.layout.api.RefreshLayout;
import com.one.education.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.one.education.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;
import com.one.education.widget.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.one.mylibrary.ConstantGlobal;
import com.one.mylibrary.TaughtSubjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import static com.one.education.utils.TimeUtils.DEFAULT_TIME_FORMA2;

/**
 * @author laiyongyang
 * @date 2020-05-14
 * @desc 课程表
 * @email fzhlaiyy@intretech.com
 */
public class ClassScheduleFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private static final String TAG = "ClassScheduleFragment";
    private static final int HANDLER_FRESH_FINISH = 0x1001;
    private static final int HANDLER_LOAD_FINISH = 0x1002;
    private View mView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyAdapter mMyAdapter;
    private List<GetStudentStudyCourseList.StudentStudyCourse> mDatas = new ArrayList<>();
    private boolean mIsRefresh = true;
    private boolean mIsLoadMore;
    private Context mCtx;
    private DialogCourseCancel courseCancelDialog;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_FRESH_FINISH) {
                mSmartRefreshLayout.finishRefresh();
                mIsRefresh = false;
                int status = msg.arg1;
                List<GetStudentStudyCourseList.StudentStudyCourse> dataList = (List<GetStudentStudyCourseList.StudentStudyCourse>) msg.obj;
                if (dataList == null) {
                    return;
                }

                List<GetStudentStudyCourseList.StudentStudyCourse> temps = new ArrayList<>();
                for (GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse : dataList) {
                    if (studentStudyCourse.getStudentId() != UserInstance.getInstance().getUserId()) {
                        continue;
                    }

                    temps.add(studentStudyCourse);
                }

                if (status == 1) {
                    mDatas.clear();
                    mDatas.addAll(temps);
                    updateView();
                }
            } else if (msg.what == HANDLER_LOAD_FINISH) {
                mSmartRefreshLayout.finishLoadMore();
                mIsLoadMore = false;
                int status = msg.arg1;
                List<GetStudentStudyCourseList.StudentStudyCourse> dataList = (List<GetStudentStudyCourseList.StudentStudyCourse>) msg.obj;
                if (dataList == null) {
                    return;
                }

                List<GetStudentStudyCourseList.StudentStudyCourse> temps = new ArrayList<>();
                for (GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse : dataList) {
                    if (studentStudyCourse.getStudentId() != UserInstance.getInstance().getUserId()) {
                        continue;
                    }

                    temps.add(studentStudyCourse);
                }
                if (status == 1) {
                    mDatas.addAll(temps);
                    updateView();
                }
            }
        }
    };

    private String studentState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCtx = getActivity();
        initLaungue();
        mView = inflater.inflate(R.layout.class_schedule_fragment, container, false);
        mView.findViewById(R.id.search_tv).setOnClickListener(mOnClickListener);

        studentState = SharedPreferencesUtils.getInstance().getString("state", "");

        RecyclerView recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        recyclerView.setHasFixedSize(true);
        mMyAdapter = new MyAdapter();
        recyclerView.setAdapter(mMyAdapter);

        mMyAdapter.setCourseListener(new CourseInteface() {
            @Override
            public void teachCourse(long id) {
                courseCancel(id);
            }
        });

        mSmartRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(mCtx));
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(mCtx)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColorId(R.color.color_999999));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
        updateSelectCourseView();
        return mView;
    }

    private void initLaungue() {
        changeLanguage();
    }


    private void changeLanguage() {
        String spLanguage = SpUtil.getString(mCtx, ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(mCtx, ConstantGlobal.LOCALE_COUNTRY);

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            // 如果有一个不同
            if (!MultiLanguageUtil.isSameWithSetting(mCtx)) {
                Locale locale = new Locale(spLanguage, spCountry);
                MultiLanguageUtil.changeAppLanguage(mCtx, locale, false);
            }
        } else {
            Locale locale = Locale.getDefault();
            MultiLanguageUtil.saveLanguageSetting(mCtx, locale);
            MultiLanguageUtil.changeAppLanguage(mCtx, locale, true);
        }
    }

    //取消课程
    private void courseCancel(long id) {

        addJob(NetmonitorManager.courseCancel(id, new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                if (baseBean.isSuccess()) {
                    mIsRefresh = true;
                    loadData(1, 0);
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));

    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsRefresh = true;
        loadData(1, 0);
    }

    private void updateView() {
        mMyAdapter.setDataList(mDatas);
        mMyAdapter.notifyDataSetChanged();
    }


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.search_tv == v.getId()) {
                startActivity(TeacherSearchActivity.newIntent(mCtx));
            }
        }
    };

    private void updateSelectCourseView() {
        mMyAdapter.setDataList(mDatas);
        mMyAdapter.notifyDataSetChanged();
    }

    private void loadData(int pageNO, int totalCount) {
        if (mIsRefresh) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new RefreshGetStudentStudyCourseListTask(pageNO, totalCount));
        } else if (mIsLoadMore) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new LoadGetStudentStudyCourseListTask(pageNO, totalCount));
        }
    }

    private class RefreshGetStudentStudyCourseListTask implements Runnable {
        private int pageNO;
        private int totalCount;

        public RefreshGetStudentStudyCourseListTask(int pageNO, int totalCount) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
        }

        @Override
        public void run() {
            GetStudentStudyCourseList getStudentStudyCourseList = HttpsServiceFactory.getStudentStudyCourseList(UserInstance.getInstance().getUserId(), pageNO, 50, totalCount);
            int status = getStudentStudyCourseList.getStatus();
            String desc = getStudentStudyCourseList.getDescript();
            Log.i(TAG, "RefreshGetStudentStudyCourseListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_FRESH_FINISH;
            message.arg1 = status;
            message.obj = getStudentStudyCourseList.getData();
            mHandler.sendMessage(message);
        }
    }

    private class LoadGetStudentStudyCourseListTask implements Runnable {
        private int pageNO;
        private int totalCount;

        public LoadGetStudentStudyCourseListTask(int pageNO, int totalCount) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
        }

        @Override
        public void run() {
            GetStudentStudyCourseList getStudentStudyCourseList = HttpsServiceFactory.getStudentStudyCourseList(UserInstance.getInstance().getUserId(), pageNO, 50, totalCount);
            int status = getStudentStudyCourseList.getStatus();
            String desc = getStudentStudyCourseList.getDescript();
            Log.i(TAG, "LoadGetStudentStudyCourseListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_LOAD_FINISH;
            message.arg1 = status;
            message.obj = getStudentStudyCourseList.getData();
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        loadData(1, 0);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mIsLoadMore = true;
        if (mDatas.size() % 50 > 0) {
            //没有更多数据可加载
            mSmartRefreshLayout.finishLoadMore();
        } else {
            loadData(mDatas.size() / 50 + 1, mDatas.size());
        }
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<GetStudentStudyCourseList.StudentStudyCourse> {
        public MyAdapter() {
            setMultiTypeDelegate(data -> R.layout.class_schedule_fragment_item_layout);
        }


        private void setCourseListener(CourseInteface listener) {
            this.listener = listener;
        }

        private CourseInteface listener;


        @Override
        public void bindViewHolder(ViewHolder holder, GetStudentStudyCourseList.StudentStudyCourse item, int position) {
            ImageView icon = holder.getView(R.id.icon);
            TextView nameTv = holder.getView(R.id.name_tv);
            TextView dateWeekTv = holder.getView(R.id.date_week_tv);
            TextView startTimeTv = holder.getView(R.id.start_time_tv);
            TextView endTimeTv = holder.getView(R.id.end_time_tv);
            TextView classTv = holder.getView(R.id.class_tv);
            TextView coursewareTv = holder.getView(R.id.courseware_tv);
            TextView statusTv = holder.getView(R.id.status_tv);
            nameTv.setText(getString(R.string.teacher) + item.getTeacherName());
            RelativeLayout all_layout = holder.getView(R.id.all_layout);

            String startTime = TimeUtils.GetTime(item.getBeginTime() * 1000, DEFAULT_TIME_FORMA2);
            String endTime = TimeUtils.GetTime(item.getEndTime() * 1000, DEFAULT_TIME_FORMA2);
            String[] startTimeArray = startTime.split(" ");
            String[] endTimeArray = endTime.split(" ");
            int state = item.getState();
            all_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (state == GetStudentStudyCourseList.State.WAITING_PAY.getKey() || state == GetStudentStudyCourseList.State.BOOKING.getKey() ||
                            state == GetStudentStudyCourseList.State.APPLY_DELAY.getKey() || state == GetStudentStudyCourseList.State.DELAYED.getKey() ||
                            state == GetStudentStudyCourseList.State.ABOUT_TO_START.getKey() || state == GetStudentStudyCourseList.State.INVITE_APPLY_CHECKING.getKey()) {

                        if (courseCancelDialog == null) {
                            courseCancelDialog = new DialogCourseCancel(mCtx);
                        }
                        courseCancelDialog.setSave(item.getId());
                        courseCancelDialog.show();
                        courseCancelDialog.setStudentListener(new DialogCourseCancel.StudentInterface() {
                            @Override
                            public void save(long ee) {
                                courseCancelDialog.dismiss();
                                if (listener != null) {
                                    listener.teachCourse(ee);
                                }
                            }
                        });

                        courseCancelDialog.setCancel(v1 -> courseCancelDialog.dismiss());
                    } else {
                    }
                }
            });

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(TeacherDetail1Activity.newIntent(mCtx, item.getTeacherId()));
                }
            });

            dateWeekTv.setText(String.format(Locale.getDefault(), "%s %s", startTimeArray[0], TimeUtils.getWeek(mCtx, item.getBeginTime() * 1000)));
            startTimeTv.setText(getString(R.string.start_time_format, startTimeArray.length == 2 ? startTimeArray[1] : ""));
            endTimeTv.setText(getString(R.string.end_time_format, endTimeArray.length == 2 ? endTimeArray[1] : ""));
            classTv.setText(String.valueOf(item.getCourseDuration() / 60));
            statusTv.setText(item.getStateName());
            TextView evaluationTv = holder.getView(R.id.evaluation_tv);
            evaluationTv.setVisibility(View.VISIBLE);
            evaluationTv.setVisibility(View.VISIBLE);
            coursewareTv.setVisibility(View.VISIBLE);


            if (state == GetStudentStudyCourseList.State.WAITING_PAY.getKey()) {
                //待支付
                statusTv.setBackgroundResource(R.drawable.class_schedule_item_comment_bg);
                statusTv.setText(mContext.getString(R.string.wait_pay));
                evaluationTv.setText(mContext.getString(R.string.pay));
                evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.pay, 0, 0, 0);
            } else if (state == GetStudentStudyCourseList.State.BOOKING.getKey()) {
                //已预约
                statusTv.setBackgroundResource(R.drawable.class_schedule_item_booking_bg);
                statusTv.setText(mContext.getString(R.string.reserved));
                evaluationTv.setText(mContext.getString(R.string.change));
                evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_delay, 0, 0, 0);

            } else if (state == GetStudentStudyCourseList.State.COMPLETED.getKey()) {

                if (item.getIsComment() == 0) {
                    //待评论
                    statusTv.setBackgroundResource(R.drawable.class_schedule_item_comment_bg);
                    statusTv.setText(mContext.getString(R.string.wait_comment));
                    evaluationTv.setText(mContext.getString(R.string.evaluation));
                    evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.evaluation, 0, 0, 0);
                } else {
                    //已完成
                    statusTv.setBackgroundResource(R.drawable.class_schedule_item_delay_bg);
                    statusTv.setText(mContext.getString(R.string.finished));
                    evaluationTv.setVisibility(View.INVISIBLE);
                }

            } else if (state == GetStudentStudyCourseList.State.APPLY_DELAY.getKey()) {

                statusTv.setBackgroundResource(R.drawable.class_schedule_item_had_change);
                statusTv.setText(mContext.getString(R.string.change_had));
                evaluationTv.setText(mContext.getString(R.string.change));
                evaluationTv.setVisibility(View.INVISIBLE);
                evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_delay, 0, 0, 0);

            } else if (state == GetStudentStudyCourseList.State.DELAYED.getKey()) {

            } else if (state == GetStudentStudyCourseList.State.ABOUT_TO_START.getKey()) {

                statusTv.setText(mContext.getString(R.string.start_soon));
                if (item.getChangeApplyState() == 0) {
                    //已申请改签
                    statusTv.setBackgroundResource(R.drawable.class_schedule_item_had_change);
                    statusTv.setText(mContext.getString(R.string.change_had));
                    evaluationTv.setVisibility(View.INVISIBLE);
                } else {
                    //即将开始
                    evaluationTv.setVisibility(View.VISIBLE);
                    if (item.getBeginTime() - SystemClock.currentThreadTimeMillis() > 10 * 60 * 1000) {
                        //距离上课超过10分钟
                        statusTv.setBackgroundResource(R.drawable.class_schedule_item_delay_bg);
                        evaluationTv.setText(mContext.getString(R.string.change));
                        evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_delay, 0, 0, 0);
                    } else {
                        statusTv.setBackgroundResource(R.drawable.class_schedule_item_prepare_start_bg);
                        evaluationTv.setText(mContext.getString(R.string.start_class));
                        evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_prepare_start, 0, 0, 0);
                    }
                }
            } else if (state == GetStudentStudyCourseList.State.IN_CLASS.getKey()) {
                statusTv.setText(mContext.getString(R.string.start_soon));
                if (item.getChangeApplyState() == 0) {
                    //已申请改签
                    statusTv.setBackgroundResource(R.drawable.class_schedule_item_had_change);
                    statusTv.setText(mContext.getString(R.string.change_had));
                    evaluationTv.setVisibility(View.INVISIBLE);
                } else {
                    //即将开始
                    evaluationTv.setVisibility(View.VISIBLE);
                    if (item.getBeginTime() - SystemClock.currentThreadTimeMillis() > 10 * 60 * 1000) {
                        //距离上课超过10分钟
                        statusTv.setBackgroundResource(R.drawable.class_schedule_item_delay_bg);
                        evaluationTv.setText(mContext.getString(R.string.change));
                        evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_delay, 0, 0, 0);
                    } else {
                        statusTv.setBackgroundResource(R.drawable.class_schedule_item_prepare_start_bg);
                        evaluationTv.setText(mContext.getString(R.string.start_class));
                        evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_prepare_start, 0, 0, 0);
                    }
                }
            } else if (state == GetStudentStudyCourseList.State.INVITE_APPLY_CHECKING.getKey()) {
                //邀请待确认
                statusTv.setBackgroundResource(R.drawable.class_schedule_item_invite_checking_bg);
                statusTv.setText(mContext.getResources().getString(R.string.invite_apply_checking));
                evaluationTv.setText(mContext.getString(R.string.invite_apply));
                evaluationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.class_schedule_item_delay, 0, 0, 0);
            } else if (state == GetStudentStudyCourseList.State.OVER_TIME.getKey()) {
                //过期
                statusTv.setBackgroundResource(R.drawable.class_schedule_item_expire_bg);
                statusTv.setText(mContext.getString(R.string.expired));
                evaluationTv.setVisibility(View.INVISIBLE);
                coursewareTv.setVisibility(View.INVISIBLE);
            } else if (state == GetStudentStudyCourseList.State.CANCELLED.getKey()) {
                statusTv.setBackgroundResource(R.drawable.class_schedule_item_expire_bg);
                statusTv.setText(mContext.getString(R.string.canceles));
                evaluationTv.setVisibility(View.INVISIBLE);
                coursewareTv.setVisibility(View.INVISIBLE);
            } else if (state == GetStudentStudyCourseList.State.DEFAULT.getKey()) {

            }

            ImageLoader.loadAdImage(item.getTeacherUserPicUrl(), icon);
            coursewareTv.setTag(item);
            coursewareTv.setOnClickListener(v -> (
                    (MainActivity) mCtx).startActivity(CoursewareActivity.newIntentEx(mCtx, (GetStudentStudyCourseList.StudentStudyCourse) coursewareTv.getTag())));

            evaluationTv.setTag(item);
            evaluationTv.setOnClickListener(v -> {
                GetStudentStudyCourseList.StudentStudyCourse temp = (GetStudentStudyCourseList.StudentStudyCourse) evaluationTv.getTag();
                if (temp.getState() == GetStudentStudyCourseList.State.COMPLETED.getKey() && temp.getIsComment() == 0) {
                    ((MainActivity) mCtx).startActivity(CourseEvaluationActivity.newIntentEx(mCtx, (GetStudentStudyCourseList.StudentStudyCourse) coursewareTv.getTag()));
                } else if (temp.getState() == GetStudentStudyCourseList.State.BOOKING.getKey()
                        || temp.getState() == GetStudentStudyCourseList.State.APPLY_DELAY.getKey()) {

                    ((MainActivity) mCtx).startActivity(ClassAppointmentActivity.newIntent(mCtx, (GetStudentStudyCourseList.StudentStudyCourse) coursewareTv.getTag(), 1));
                } else if (temp.getState() == GetStudentStudyCourseList.State.ABOUT_TO_START.getKey()) {
                    GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse = (GetStudentStudyCourseList.StudentStudyCourse) coursewareTv.getTag();
                    if (TextUtils.equals(evaluationTv.getText(), mContext.getString(R.string.change))) {
                        jumpClassAppoint(studentStudyCourse);
                    } else {
                        if (studentStudyCourse.getBeginTime() - SystemClock.currentThreadTimeMillis() < 10 * 60 * 1000) {
                            ClassStartDialog classStartDialog = new ClassStartDialog(mCtx);
                            classStartDialog.show();
                        }
                    }
                } else if (temp.getState() == GetStudentStudyCourseList.State.INVITE_APPLY_CHECKING.getKey()
                        || temp.getState() == GetStudentStudyCourseList.State.WAITING_PAY.getKey()) {
                    ((BaseActivity) mCtx).showProgress("");
                    GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse = (GetStudentStudyCourseList.StudentStudyCourse) coursewareTv.getTag();
                    courseQuery(studentStudyCourse.getOrderCode());
                }
            });

            TextView contactTv = holder.getView(R.id.contact_tv);
            contactTv.setTag(item);
            contactTv.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(studentState) && studentState.equals("1")) {
                    NimUIKit.startP2PSession(mCtx, String.valueOf(item.getTeacherId()));
                } else {
                    ToastUtils.showToastShort(getString(R.string.been_approved));
                }
            });
        }
    }

    private void jumpClassAppoint(GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse) {
        mCtx.startActivity(ClassAppointmentActivity.newIntent(mCtx, studentStudyCourse, 1));
    }

    private void courseQuery(String orderCode) {
        addJob(NetmonitorManager.courseOrderQuery(orderCode, new RestNewCallBack<OrderQueryResponse>() {
            @Override
            public void success(OrderQueryResponse orderCreateResponse) {
                ((BaseActivity) mCtx).closeProgress();
                OrderQueryResponse.Certificates certificates = orderCreateResponse.getCertificates();
                if (certificates != null && !TextUtils.isEmpty(certificates.getOrderCode())) {
                    Vector<Long> selectTimes = new Vector<>();
                    float coursePrice = orderCreateResponse.getCertificates().getAmount();
                    TaughtSubjects taughtSubjects = new TaughtSubjects();
                    for (OrderQueryResponse.Data.CourseList courseList : orderCreateResponse.getData().getCourseList()) {
                        if (courseList.getOrderCode().equals(orderCode)) {
                            taughtSubjects.setCourseName(courseList.getCourseName());
                            taughtSubjects.setCourseId(courseList.getCourseId());
                            taughtSubjects.setCreateTime(courseList.getCreateTime());
                            taughtSubjects.setId(courseList.getId());
                            taughtSubjects.setTeacherId(courseList.getTeacherId());
                            taughtSubjects.setSubjectId(courseList.getSubjectId());
                            taughtSubjects.setSubjectName(courseList.getSubjectName());

                            selectTimes.add(courseList.getBeginTime() * 1000);
                        }
                    }

                    long teachId = orderCreateResponse.getData().getTeacherId();
                    startActivity(OrderConfirmActivity.newPayOrderIntent(mCtx, teachId, coursePrice, selectTimes, taughtSubjects, orderCreateResponse));
                } else {
                    ((BaseActivity) mCtx).closeProgress();
                    ToastUtils.showToastShort(mCtx.getString(R.string.order_expired));
                }
            }

            @Override
            public void failure(RestError error) {
                ((BaseActivity) mCtx).closeProgress();
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    private class SelectTime {
        long time1;
        long time2;
        long time3;
        long time4;
        long time5;
    }

    public interface CourseInteface {
        void teachCourse(long id);
    }

}