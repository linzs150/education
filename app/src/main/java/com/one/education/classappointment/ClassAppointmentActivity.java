package com.one.education.classappointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.one.education.activities.BaseActivity;
import com.one.education.activities.IntentEx;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.ViewHolder;
import com.one.education.beans.BaseBean;
import com.one.education.beans.ChangeApplyBean;
import com.one.education.beans.TaughtSubjects;
import com.one.education.beans.TeacherBean;
import com.one.education.beans.TeacherProfileItem;
import com.one.education.commons.LogUtils;
import com.one.education.commons.Utils;
import com.one.education.dialogs.BookedDialog1;
import com.one.education.education.R;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetAppointRsp;
import com.one.education.retrofit.model.GetScheduleListByTeacherIdRsp;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.AppTipsUtils;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.TimeUtils;
import com.one.education.utils.toast.DateUtilts;
import com.one.education.utils.toast.ToastUtils;
import com.one.education.widget.AutoAdjustRecylerView;
import com.one.education.widget.BookedDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author laiyongyang
 * @date 2020-05-16
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ClassAppointmentActivity extends BaseActivity {
    public static IntentEx newIntent(Context context, TeacherProfileItem teacherBaseInfo, float coursePrice) {
        IntentEx intentEx = new IntentEx(context, ClassAppointmentActivity.class);
        intentEx.putExtraEx(INTENT_DATA, teacherBaseInfo);
        intentEx.putExtraEx(INTENT_PRICE, coursePrice);
        return intentEx;
    }

    public static IntentEx newIntent(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse, int schedule) {
        IntentEx intentEx = new IntentEx(context, ClassAppointmentActivity.class);
        intentEx.putExtraEx("student_study_course", studentStudyCourse);
        intentEx.putExtraEx("schedule", schedule);
        return intentEx;
    }

    private static final String TAG = "ClassAppointment";
    private static final String INTENT_DATA = "data";
    private static final String INTENT_PRICE = "coursePrice";
    private TeacherProfileItem mTeacherBaseInfo;
    private TextView mTvSelectCount;
    private TextView mTvMoney;
    private AutoAdjustRecylerView mRecycleview;
    private MyLinearLayoutManager mLinearLayoutManager;
    private MyAdapter mMyAdapter;
    private final List<MyAdapterData> mDataList = new ArrayList<>();
    private float mCoursePrice;
    private GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse;

    private List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo> mScheduleListByTeacherIdInfo;
    private final List<SelectTime> mSelectAppointments = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    //    private BookedDialog bookedDialog;
    private TextView nameTv;
    private ImageView icon;
    private int mReschedule = 0; //1改簽


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_appointment);
        findViewById(R.id.back_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.order).setOnClickListener(mOnClickListener);
        IntentEx intentEx = getIntentEx();
        mTeacherBaseInfo = intentEx.getExtraEx(INTENT_DATA);
        mCoursePrice = intentEx.getExtraEx(INTENT_PRICE);
        studentStudyCourse = intentEx.getExtraEx("student_study_course");
        if (studentStudyCourse != null) {
            mReschedule = intentEx.getExtraEx("schedule");
        }
        nameTv = findViewById(R.id.name_tv);
        icon = findViewById(R.id.icon);
        mTvSelectCount = findViewById(R.id.had_select_course_tv);
        mTvMoney = findViewById(R.id.money_tv);

        if (mReschedule == 1) {
            if (studentStudyCourse != null) {
                nameTv.setText(studentStudyCourse.getTeacherName());
                ImageLoader.loadAdImage(studentStudyCourse.getTeacherUserPicUrl(), icon);
                mCoursePrice = studentStudyCourse.getCoursePrice();
            }
        } else {
            nameTv.setText(mTeacherBaseInfo.getTeacherName());
            ImageLoader.loadAdImage(mTeacherBaseInfo.getUserPicUrl(), icon);
        }


        findViewById(R.id.left_icon_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.right_icon_iv).setOnClickListener(mOnClickListener);
        List<Long> dates = DateUtilts.getPeroidTime(30);
        for (long data : dates) {
            MyAdapterData myAdapterData = new MyAdapterData();
            myAdapterData.contentAdapter = new ContentAdapter();
            myAdapterData.date = data;
            mDataList.add(myAdapterData);
        }

        mMyAdapter = new MyAdapter();
        mMyAdapter.setDataList(mDataList);
        mRecycleview = findViewById(R.id.recycleview);
        mLinearLayoutManager = new MyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager.setCanScrollHorizontally(true);
        mRecycleview.setLayoutManager(mLinearLayoutManager);
        mRecycleview.setAdapter(mMyAdapter);

        if (AppTipsUtils.checkNetworkState(ClassAppointmentActivity.this)) {
            showProgress("");
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
                GetScheduleListByTeacherIdRsp response = HttpsServiceFactory.getScheduleListByTeacherId(mReschedule == 1 ? studentStudyCourse.getTeacherId() : mTeacherBaseInfo.getTeacherId(), TimeZone.getDefault().getID());
                mHandler.post(() -> {
                    if (isFinishing()) {
                        return;
                    }

                    closeProgress();
                    int status = response.getStatus();
                    if (status == 1) {
                        mScheduleListByTeacherIdInfo = response.getData();
                        mMyAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                    }
                });
            });


            ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
                GetAppointRsp response = HttpsServiceFactory.getTeacherAppointedTimeList(mReschedule == 1 ? studentStudyCourse.getTeacherId() : mTeacherBaseInfo.getTeacherId(), TimeZone.getDefault().getID());
                mHandler.post(() -> {
                    if (isFinishing()) {
                        return;
                    }

                    closeProgress();
                    int status = response.getStatus();
                    if (status == 1) {
//                        mScheduleListByTeacherIdInfo = response.getData();
//                        mMyAdapter.notifyDataSetChanged();

                        LogUtils.e("lzs:teacher:", JSON.toJSONString(response));


                    } else {
                        ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                    }
                });
            });

            ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
                GetAppointRsp response = HttpsServiceFactory.getMyAppointedTimeList(TimeZone.getDefault().getID());
                mHandler.post(() -> {
                    if (isFinishing()) {
                        return;
                    }

                    closeProgress();
                    int status = response.getStatus();
                    if (status == 1) {
//                        mScheduleListByTeacherIdInfo = response.getData();
//                        mMyAdapter.notifyDataSetChanged();
                        LogUtils.e("lzs:my:", JSON.toJSONString(response));
                    } else {
                        ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                    }
                });
            });

        }

        updateView();
    }

    private BookedDialog1 bookedDialog;

    //弹窗选择课程
    private void choiceCourseName(List<TaughtSubjects> taughtSubjects) {
        if (bookedDialog == null) {
            bookedDialog = new BookedDialog1(ClassAppointmentActivity.this);
        }

        bookedDialog.setSubjectName(taughtSubjects);
        bookedDialog.show();
        bookedDialog.setListener(new BookedDialog1.Click() {
            @Override
            public void setBook(TaughtSubjects book) {
                bookedDialog.dismiss();
                order(book);

            }
        });

    }

    //下单
    private void order(TaughtSubjects book) {
        List<Long> tempList = new ArrayList<>();
        for (SelectTime selectTime : mSelectAppointments) {
            tempList.add(selectTime.time1);
        }

        startActivityForResult(OrderConfirmActivity.newIntent(ClassAppointmentActivity.this, mTeacherBaseInfo, mCoursePrice, tempList, book), 1002);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == 1004) {
            setResult(1003);
            finish();
        }
    }

    private void updateView() {
        if (mSelectAppointments.size() <= 1) {
            mTvSelectCount.setText(String.format(Locale.getDefault(), "%d %s", mSelectAppointments.size(), getString(R.string.lesson)));
        } else {
            mTvSelectCount.setText(String.format(Locale.getDefault(), "%d %s", mSelectAppointments.size(), getString(R.string.lessons)));
        }
        mTvMoney.setText(String.format(Locale.getDefault(), "%s%s", getString(R.string.money_symbol), mSelectAppointments.size() * (int) mCoursePrice));
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_iv) {
                finish();
            } else if (v.getId() == R.id.order) {
                if (mSelectAppointments.isEmpty()) {
                    ToastUtils.ShowToastLong(getActivity(), getString(R.string.no_select_appoint_time));
                    return;
                }

                if (mReschedule == 1) {

                    if (studentStudyCourse != null) {
                        ChangeApplyBean applyBean = new ChangeApplyBean();
                        applyBean.setStudentId(studentStudyCourse.getStudentId());
                        applyBean.setTeacherId(studentStudyCourse.getTeacherId());
                        applyBean.setOrderCourseId(studentStudyCourse.getId());
                        applyBean.setAppyReason("");
                        applyBean.setApplyBeginTime(mSelectAppointments.get(0).time1 / 1000);
                        applyBean.setApplyEndTime((mSelectAppointments.get(0).time1 + 60 * 60 * 1000) / 1000);
                        changeApply(applyBean);
                    }

                } else {
                    if (mTeacherBaseInfo != null) {

                        List<TaughtSubjects> taughtSubjects = getmentSubjects(mTeacherBaseInfo);
                        if (taughtSubjects != null && taughtSubjects.size() > 0) {
                            if (taughtSubjects.size() > 1) { //课程超过一个要弹窗
                                choiceCourseName(taughtSubjects);
                            } else if (taughtSubjects.size() == 1) {
                                order(taughtSubjects.get(0));
                            }
                        }
                    }
                }


            } else if (v.getId() == R.id.left_icon_iv) {
//                mLinearLayoutManager.setCanScrollHorizontally(true);
//                mRecycleview.setLayoutManager(mLinearLayoutManager);
//                if (mRecycleview.checkAutoAdjust(getActivity(), mToPosition, mToPosition - 1)) {
//                    mToPosition = mToPosition - 1;
//                }
//                mLinearLayoutManager.setCanScrollHorizontally(false);
                smoothMoveToPosition(mToPosition - 1);
            } else if (v.getId() == R.id.right_icon_iv) {
//                mLinearLayoutManager.setCanScrollHorizontally(true);
//                mRecycleview.setLayoutManager(mLinearLayoutManager);
//                if (mRecycleview.checkAutoAdjust(getActivity(), mToPosition, mToPosition + 1)) {
//                    mToPosition = mToPosition + 1;
//                }
//                mLinearLayoutManager.setCanScrollHorizontally(false);
                smoothMoveToPosition(mToPosition + 1);
            }
        }
    };

    //获取老师教学对应id和课程名
    private List<TaughtSubjects> getSubjects(TeacherBean.TeacherList teacherList) {
        List<TaughtSubjects> taughtSubjects = new ArrayList<>();
        String[] courseIdTemp = teacherList.getCourseId().split(",");
        List<Long> courseId = new ArrayList<>();
        for (String course : courseIdTemp) {
            if (!TextUtils.isEmpty(course)) {
                courseId.add(Long.valueOf(course));
            }
        }
        String[] courseName = teacherList.getCourseName().split(",");
        if (courseId != null && courseName != null) {
            for (int i = 0; i < courseId.size(); i++) {
                TaughtSubjects taughtSubjects1 = new TaughtSubjects();
                taughtSubjects1.setCourseId(courseId.get(i));
                taughtSubjects1.setCourseName(courseName[i]);
                taughtSubjects.add(taughtSubjects1);
            }
        }

        return taughtSubjects;

    }


    //改签
    private void changeApply(ChangeApplyBean applyBean) {
        showProgress();
        addJob(NetmonitorManager.orderChangeApply(applyBean, new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                closeProgress();
                com.one.education.commons.ToastUtils.showToastShort(baseBean.getDescript());
                finish();

            }

            @Override
            public void failure(RestError error) {
                closeProgress();
                com.one.education.commons.ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    //获取老师教学对应id和课程名
    private List<TaughtSubjects> getTmpSubjects(TeacherProfileItem teacherList) {
        List<TaughtSubjects> taughtSubjects = new ArrayList<>();
        String[] courseIdTemp = teacherList.getCourse().split(",");
        List<Long> courseId = new ArrayList<>();
        for (String course : courseIdTemp) {
            if (!TextUtils.isEmpty(course)) {
                courseId.add(Long.valueOf(course));
            }
        }
        String[] courseName = teacherList.getCourseName().split(",");
        if (courseId != null && courseName != null) {
            for (int i = 0; i < courseId.size(); i++) {
                TaughtSubjects taughtSubjects1 = new TaughtSubjects();
                taughtSubjects1.setCourseId(courseId.get(i));
                taughtSubjects1.setCourseName(courseName[i]);
                taughtSubjects.add(taughtSubjects1);
            }
        }

        return taughtSubjects;

    }

    //获取老师教学对应id和课程名
    private List<TaughtSubjects> getmentSubjects(TeacherProfileItem teacherList) {
        List<TaughtSubjects> taughtSubjects = new ArrayList<>();
        String[] courseIdTemp = teacherList.getCourse().split(",");
        List<Long> courseId = new ArrayList<>();
        for (String course : courseIdTemp) {
            if (!TextUtils.isEmpty(course)) {
                courseId.add(Long.valueOf(course));
            }
        }
        String[] courseName = teacherList.getCourseName().split(",");
        if (courseId != null && courseName != null) {
            for (int i = 0; i < courseId.size(); i++) {
                TaughtSubjects taughtSubjects1 = new TaughtSubjects();
                taughtSubjects1.setCourseId(courseId.get(i));
                taughtSubjects1.setCourseName(courseName[i]);
                taughtSubjects.add(taughtSubjects1);
            }
        }

        return taughtSubjects;

    }

    private class MyAdapterData {
        long date;
        ContentAdapter contentAdapter = null;
    }

    /**
     * 记录目标项位置
     */
    private int mToPosition = 0;


    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(final int position) {
        if (position < 0 || position >= 28) {
            return;
        }

        final TopSmoothScroller mScroller = new TopSmoothScroller(getActivity());
        mScroller.setTargetPosition(position);
        mLinearLayoutManager.startSmoothScroll(mScroller);
        mToPosition = position;
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<MyAdapterData> {
        public MyAdapter() {
            setMultiTypeDelegate(data -> R.layout.class_appointment_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, MyAdapterData item, int position) {
            TextView weekTv = holder.getView(R.id.week_tv);
            TextView dateTv = holder.getView(R.id.date_tv);

            if (position == 0) {
                weekTv.setText(mContext.getResources().getString(R.string.today));
            } else {
                weekTv.setText(DateUtilts.getWeek(mContext, item.date));
            }

            String time = TimeUtils.formatDate(new Date(item.date), TimeUtils.DEFAULT_TIME_FORMA3);
            String[] dateArray = time.split("-");
            dateTv.setText(String.format(Locale.getDefault(), "%s-%s", dateArray[1], dateArray[2]));
            RecyclerView recyclerView = holder.getView(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(ClassAppointmentActivity.this));
            recyclerView.setBackgroundColor(position % 2 == 0 ? getResources().getColor(R.color.white) :
                    getResources().getColor(R.color.white));
            item.contentAdapter.setDataList(getClassTime(item.date));
            recyclerView.setAdapter(item.contentAdapter);
        }

        private List<TimeItme> getClassTime(long time) {
            List<TimeItme> dateList = new ArrayList<>();
            for (int i = 0; i < 24 * 4; i++) {
                TimeItme timeItme = new TimeItme();
                timeItme.date = time + i * 15 * 60 * 1000;
                timeItme.isvalid = checkValid(time, timeItme.date);
                dateList.add(timeItme);
            }

            return dateList;
        }
    }

    private static class TimeItme {
        public long date;
        //是否是有效的时间
        boolean isvalid;
    }

    private static class SelectTime {
        long time1;
        long time2;
        long time3;
        long time4;
        long time5;
    }

    private class ContentAdapter extends BaseRecyclerViewAdapter<TimeItme> {
        public ContentAdapter() {
            setMultiTypeDelegate(data -> R.layout.class_appointment_time_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, TimeItme item, int position) {
            TextView textView = holder.getView(R.id.time_tv);
            String date = TimeUtils.GetTime(item.date, TimeUtils.DEFAULT_TIME_FORMA2);
            textView.setText(date.split(" ")[1]);
            textView.setTextColor(item.isvalid ? ContextCompat.getColor(ClassAppointmentActivity.this, R.color.color_activity_blue_bg)
                    : ContextCompat.getColor(ClassAppointmentActivity.this, R.color.color_bfbebe));

            textView.setBackgroundResource(0);
            for (SelectTime selectTime1 : mSelectAppointments) {
                if (selectTime1.time1 == item.date) {
                    textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_bg1);
                } else if (selectTime1.time5 == item.date) {
                    textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_bg3);
                } else if (selectTime1.time2 == item.date
                        || selectTime1.time3 == item.date
                        || selectTime1.time4 == item.date) {
                    textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_bg2);
                }
            }
            textView.setEnabled(item.isvalid);
            textView.setTag(item);
            textView.setOnClickListener(v -> {
                TimeItme clickTimeItem = (TimeItme) v.getTag();
                //如果点击的是之前已经选中过的第一个时间点，则取消选中
                if (!mSelectAppointments.isEmpty()) {
                    for (SelectTime selectTime : mSelectAppointments) {
                        if (selectTime.time1 == clickTimeItem.date) {
                            mSelectAppointments.remove(selectTime);
                            notifyDataSetChanged();
                            updateView();
                            return;
                        }
                    }
                }

                List<TimeItme> timeItmeList = getDataList();
                int curIndex = 0;
                for (TimeItme timeItme : timeItmeList) {
                    if (clickTimeItem.date == timeItme.date) {
                        //如果后面不足5个时间段，则不允许预约
                        if (curIndex > timeItmeList.size() - 5) {
                            return;
                        }

                        //如果超过5个，那么判断是否是有效的时间
                        List<TimeItme> subList = timeItmeList.subList(curIndex, curIndex + 5);
                        for (TimeItme itme : subList) {
                            if (!itme.isvalid) {
                                return;
                            }
                        }

                        //如果后面5个都是有效的，则判断是不是之前已经选择过了，如果选择过了，则需要把之前的取消
                        if (!mSelectAppointments.isEmpty()) {
                            for (TimeItme timeItme1 : subList) {
                                for (SelectTime selectTime : mSelectAppointments) {
                                    if (selectTime.time1 == timeItme1.date || selectTime.time2 == timeItme1.date
                                            || selectTime.time3 == timeItme1.date || selectTime.time4 == timeItme1.date
                                            || selectTime.time5 == timeItme1.date) {
                                        mSelectAppointments.remove(selectTime);
                                        break;
                                    }
                                }
                            }
                        }

                        SelectTime selectTime = new SelectTime();
                        selectTime.time1 = subList.get(0).date;
                        selectTime.time2 = subList.get(1).date;
                        selectTime.time3 = subList.get(2).date;
                        selectTime.time4 = subList.get(3).date;
                        selectTime.time5 = subList.get(4).date;
                        mSelectAppointments.add(selectTime);
                    }

                    curIndex++;
                }

                notifyDataSetChanged();
                updateView();
            });
        }
    }

    private boolean checkValid(long start, long time) { //start 一天0点
        if (mScheduleListByTeacherIdInfo == null || mScheduleListByTeacherIdInfo.isEmpty()) {
            return false;
        }

        for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo data : mScheduleListByTeacherIdInfo) {
            int timeIntervalType = data.getTimeIntervalType();
            long startTime = data.getTimeIntervalBegin() * 1000;
            long endTime = data.getTimeIntervalEnd() * 1000;
            if (time < startTime || time > endTime) {
//                continue;
                return false;
            }

            List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries> entries = data.getEntries();
            for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries entrie : entries) {
                //不可预约
                if (entrie.getEntryType() != 1) {
                    continue;
                }

//                long startTimeInterval = (data.getTimeIntervalBegin() + entrie.getBeginTime()) * 1000;
//                long endTimeInterval = (data.getTimeIntervalEnd() + entrie.getEndTime()) * 1000;
//                long startTimeInterval = entrie.getBeginTime() ;
//                long endTimeInterval = entrie.getEndTime();
                long startTimeInterval = start + entrie.getBeginTime() * 1000;
                long endTimeInterval = start + entrie.getEndTime() * 1000;
                //如果在设置的范围话内，在判断星期
//                if (time >= startTimeInterval && time <= endTimeInterval) {
                if (time >= startTimeInterval && time <= endTimeInterval) {
                    String[] weekArray = entrie.getWeekDays().split(",");
                    if (weekArray.length == 0) {
                        return false;
                    }

                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date(time));
                    int wek = c.get(Calendar.DAY_OF_WEEK);

                    for (String weekStr : weekArray) {
                        if (Integer.valueOf(weekStr) == (wek - 1)) {
                            boolean today = DateUtils.isToday(time);
                            if (today) {
                                if (time >= startTimeInterval && time <= System.currentTimeMillis()) { //去除当天超过上课的时间
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
//                    return true;
                    return false;
                }
            }
        }

        return false;
    }
}
