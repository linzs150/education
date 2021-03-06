package com.newtonacademic.newtontutors.classappointment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.activities.IntentEx;
import com.newtonacademic.newtontutors.adapter.BaseRecyclerViewAdapter;
import com.newtonacademic.newtontutors.adapter.ViewHolder;
import com.newtonacademic.newtontutors.beans.BaseBean;
import com.newtonacademic.newtontutors.beans.ChangeApplyBean;
import com.newtonacademic.newtontutors.beans.TeacherProfileItem;
import com.newtonacademic.newtontutors.beans.TeacherProfileResponse;
import com.newtonacademic.newtontutors.commons.AppUtils;
import com.newtonacademic.newtontutors.commons.Utils;
import com.newtonacademic.newtontutors.dialogs.BookedDialog1;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.retrofit.HttpsServiceFactory;
import com.newtonacademic.newtontutors.retrofit.model.GetAppointRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetScheduleListByTeacherIdRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetStudentStudyCourseList;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;
import com.newtonacademic.newtontutors.utils.AppTipsUtils;
import com.newtonacademic.newtontutors.utils.ImageLoader;
import com.newtonacademic.newtontutors.utils.TimeUtils;
import com.newtonacademic.newtontutors.utils.toast.DateUtilts;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;
import com.newtonacademic.newtontutors.widget.AppointmentNoAvaliableDialog;
import com.newtonacademic.mylibrary.TaughtSubjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author laiyongyang
 * @date 2020-05-16
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ClassAppointmentActivity extends BaseActivity {
    public static IntentEx newIntent(Context context, TeacherProfileItem teacherBaseInfo, int coursePrice) {
        IntentEx intentEx = new IntentEx(context, ClassAppointmentActivity.class);
        intentEx.putExtraEx(INTENT_DATA, teacherBaseInfo);
        intentEx.putExtraEx(INTENT_PRICE, coursePrice);
        return intentEx;
    }

    public static IntentEx newIntent(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse, int schedule) {
        IntentEx intentEx = new IntentEx(context, ClassAppointmentActivity.class);
        intentEx.putExtraEx("student_study_course", studentStudyCourse);
        intentEx.putExtraEx(INTENT_PRICE, studentStudyCourse.getCoursePrice());
        intentEx.putExtraEx("schedule", schedule);
        return intentEx;
    }

    private static final String TAG = "ClassAppointment";
    private static final String INTENT_DATA = "data";
    private static final String INTENT_PRICE = "coursePrice";
    private TeacherProfileItem mTeacherBaseInfo;
    private TextView mTvSelectCount;
    private TextView mTvMoney;
    private ScrollerLinearLayout mContentLayout = null;
    private int mCoursePrice;

    /**
     * ?????????????????????
     */
    private GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse;

    /**
     * ????????????????????????
     */
    private List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo> mScheduleListByTeacherIdInfo;

    /**
     * ?????????????????????
     */
    private List<GetAppointRsp.AppointList> mMyAppointedTimeList;

    /**
     * ???????????????????????????
     */
    private List<GetAppointRsp.AppointList> mTeacherAppointedTimeList;
    /**
     * ???????????????/?????????
     */
    private final List<Pair<SelectTime, Boolean>> mSelectAppointments = new ArrayList<>();
    private final List<Pair<SelectTime, Boolean>> mCacheSelectAppointments = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView nameTv;
    private ImageView icon;
    private int mReschedule = 0; //1??????
    private final List<ViewItem> mViewItem = new ArrayList<>();
    private int mItemWidthSize = 0;

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
        mContentLayout = findViewById(R.id.content);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels - Utils.GetDimension(this, R.dimen.dp_15);
        mItemWidthSize = screenWidth / 3;

        initView();
        updateView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadData() {
        if (!AppTipsUtils.checkNetworkState(ClassAppointmentActivity.this)) {
            return;
        }

        showProgress("");
        if (mReschedule == 1) {
            addJob(NetmonitorManager.teacherGetBaseProfile(studentStudyCourse.getTeacherId(), new RestNewCallBack<TeacherProfileResponse>() {
                @Override
                public void success(TeacherProfileResponse teacherProfileResponse) {
                    if (isFinishing()) {
                        return;
                    }

                    closeProgress();
                    if (teacherProfileResponse != null) {
                        if (teacherProfileResponse.isSuccess()) {
                            mTeacherBaseInfo = teacherProfileResponse.getData();
                            refreshViewItem();
                        }
                    }
                }

                @Override
                public void failure(RestError error) {
                    com.newtonacademic.newtontutors.commons.ToastUtils.showToastShort(error.msg);
                    closeProgress();
                }
            }));
        }

        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetScheduleListByTeacherIdRsp response = HttpsServiceFactory.getScheduleListByTeacherId(mReschedule == 1 ? studentStudyCourse.getTeacherId() : mTeacherBaseInfo.getTeacherId());
            mHandler.post(() -> {
                if (isFinishing()) {
                    return;
                }

                closeProgress();
                int status = response.getStatus();
                if (status == 1) {
                    mScheduleListByTeacherIdInfo = response.getData();
                    //??????????????????????????????
                    if (!avaliableTime()) {
                        if (mReschedule == 1) {
                            showImOutLineDialog(studentStudyCourse.getTeacherName());
                        } else {
                            showImOutLineDialog(mTeacherBaseInfo.getTeacherName());
                        }
                    }

                    refreshViewItem();
                } else {
                    ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                }
            });
        });

        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetAppointRsp response = HttpsServiceFactory.getTeacherAppointedTimeList(mReschedule == 1 ? studentStudyCourse.getTeacherId() : mTeacherBaseInfo.getTeacherId());
            mHandler.post(() -> {
                if (isFinishing()) {
                    return;
                }

                closeProgress();
                int status = response.getStatus();
                if (status == 1) {
                    mTeacherAppointedTimeList = response.getData();
                    if (mTeacherAppointedTimeList == null) {
                        return;
                    }

                    for (GetAppointRsp.AppointList appointList : mTeacherAppointedTimeList) {
                        if (mReschedule == 1 && studentStudyCourse.getBeginTime() == appointList.getBeginTime()) {
                            continue;
                        }

                        SelectTime selectTime = new SelectTime();
                        selectTime.time1 = appointList.getBeginTime() * 1000;
                        selectTime.time2 = selectTime.time1 + 15 * 60 * 1000;
                        selectTime.time3 = selectTime.time2 + 15 * 60 * 1000;
                        selectTime.time4 = selectTime.time3 + 15 * 60 * 1000;
                        selectTime.time5 = selectTime.time4 + 15 * 60 * 1000;
                        mCacheSelectAppointments.add(new Pair<>(selectTime, false));
                    }

                    refreshViewItem();
                } else {
                    ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                }
            });
        });

        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetAppointRsp response = HttpsServiceFactory.getMyAppointedTimeList();
            mHandler.post(() -> {
                if (isFinishing()) {
                    return;
                }

                closeProgress();
                int status = response.getStatus();
                if (status == 1) {
                    mMyAppointedTimeList = response.getData();
                    if (mMyAppointedTimeList == null) {
                        return;
                    }

                    //?????????????????? ?????????????????????
                    for (GetAppointRsp.AppointList appointList : mMyAppointedTimeList) {
                        if (mReschedule == 1 && studentStudyCourse.getBeginTime() == appointList.getBeginTime()) {
                            continue;
                        }

                        SelectTime selectTime = new SelectTime();
                        selectTime.time1 = appointList.getBeginTime() * 1000;
                        selectTime.time2 = selectTime.time1 + 15 * 60 * 1000;
                        selectTime.time3 = selectTime.time2 + 15 * 60 * 1000;
                        selectTime.time4 = selectTime.time3 + 15 * 60 * 1000;
                        selectTime.time5 = selectTime.time4 + 15 * 60 * 1000;
                        mCacheSelectAppointments.add(new Pair<>(selectTime, false));
                    }

                    refreshViewItem();
                } else {
                    ToastUtils.ShowToastLong(ClassAppointmentActivity.this, response.getDescript());
                }
            });
        });
    }

    private void refreshViewItem() {
        if (mScheduleListByTeacherIdInfo == null || mTeacherBaseInfo == null) {
            return;
        }

        ThreadPoolProxyFactory.getNormalThreadProxy().execute(new GetClassTimeTask(mViewItem));
    }

    private class GetClassTimeTask implements Runnable{
        private final List<ViewItem> viewItems;

        public GetClassTimeTask(List<ViewItem> viewItems) {
            this.viewItems = viewItems;
        }

        @Override
        public void run() {
           List<Pair<ViewItem, List<TimeItme>>> listPair = new ArrayList<>();
            for (ViewItem viewItem : viewItems) {
                List<TimeItme>  timeItmes = getClassTime(viewItem.date);
                listPair.add(new Pair<>(viewItem, timeItmes));
            }

            mHandler.post(() -> {
                if (isFinishing()) {
                    return;
                }

                mSelectAppointments.addAll(mCacheSelectAppointments);
                mCacheSelectAppointments.clear();
                for (Pair<ViewItem, List<TimeItme>> pair : listPair) {
                    pair.first.adapter.setDataList(pair.second);
                    pair.first.adapter.notifyDataSetChanged();
                }
            });

        }
    }

    private void initView() {
        List<Long> dates = DateUtilts.getPeroidTime(30);
        int index = 0;
        int itemId = 0;
        for (long date : dates) {
            View view = LayoutInflater.from(this).inflate(R.layout.class_appointment_item_layout, null);
            TextView weekTv = view.findViewById(R.id.week_tv);
            TextView dateTv = view.findViewById(R.id.date_tv);
            if (index == 0) {
                weekTv.setText(getResources().getString(R.string.today));
            } else {
                weekTv.setText(DateUtilts.getWeek(this, date));
            }

            view.findViewById(R.id.line_layout).setVisibility(index == mViewItem.size() - 1 ? View.GONE : View.VISIBLE);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mItemWidthSize, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);

            String time = TimeUtils.formatDate(new Date(date), TimeUtils.DEFAULT_TIME_FORMA3);
            String[] dateArray = time.split("-");
            dateTv.setText(String.format(Locale.getDefault(), "%s-%s", dateArray[1], dateArray[2]));

            RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(ClassAppointmentActivity.this));
            ContentAdapter contentAdapter = new ContentAdapter();
            contentAdapter.setDataList(getClassTime(date));
            recyclerView.setAdapter(contentAdapter);
            recyclerView.scrollToPosition(32);

            ViewItem viewItem = new ViewItem();
            viewItem.id = itemId++;
            viewItem.view = view;
            viewItem.date = date;
            viewItem.adapter = contentAdapter;
            mContentLayout.addView(view);
            mViewItem.add(viewItem);
            index++;
        }
    }

    private class ViewItem {
        private int id;
        private View view;
        private ContentAdapter adapter;
        private long date;
    }


    private boolean avaliableTime() {
        boolean avaliableTime = false;
        data:
        for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo data : mScheduleListByTeacherIdInfo) {
            List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries> list = data.getEntries();
            if (list == null) {
                continue;
            }

            for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries entries : list) {
                if (entries.getEntryType() == 1) {
                    avaliableTime = true;
                    break data;
                }
            }
        }


        return avaliableTime;
    }

    private BookedDialog1 bookedDialog;

    //??????????????????
    private void choiceCourseName(List<TaughtSubjects> taughtSubjects) {
        if (bookedDialog == null) {
            bookedDialog = new BookedDialog1(ClassAppointmentActivity.this);
        }
        bookedDialog.setSubjectName(taughtSubjects);
        bookedDialog.show();
        bookedDialog.setListener(book -> {
            bookedDialog.dismiss();
            order(book);

        });
    }

    //??????
    private void order(TaughtSubjects book) {
        Vector<Long> tempList = new Vector<>();
        for (Pair<SelectTime, Boolean> selectTime : mSelectAppointments) {
            if (selectTime.second) {
                tempList.add(selectTime.first.time1);
            }
        }

        startActivityForResult(OrderConfirmActivity.newIntent(ClassAppointmentActivity.this, mTeacherBaseInfo, mCoursePrice * tempList.size(), tempList, book), 1002);
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
        int price = 0;
        int size = 0;
        for (Pair<SelectTime, Boolean> selectTimeBooleanPair : mSelectAppointments) {
            if (!selectTimeBooleanPair.second) {
                continue;
            }

            price += mCoursePrice;
            size++;
        }

        mTvSelectCount.setText(String.format(Locale.getDefault(), "%d %s", size, getString(R.string.lessons)));
        mTvMoney.setText(String.format(Locale.getDefault(), "%s%s", getString(R.string.money_symbol), price));
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_iv) {
                finish();
            } else if (v.getId() == R.id.order) {
                //??????????????????????????????
                if (!avaliableTime()) {
                    if (mReschedule == 1) {
                        showImOutLineDialog(studentStudyCourse.getTeacherName());
                    } else {
                        showImOutLineDialog(mTeacherBaseInfo.getTeacherName());
                    }

                    return;
                }

                boolean hasSelectTime = false;
                for (Pair<SelectTime, Boolean> selectTimeBooleanPair : mSelectAppointments) {
                    if (selectTimeBooleanPair.second) {
                        hasSelectTime = true;
                        break;
                    }
                }

                if (!hasSelectTime) {
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
                        for (Pair<SelectTime, Boolean> selectTimeBooleanPair : mSelectAppointments) {
                            if (selectTimeBooleanPair.second) {
                                applyBean.setApplyBeginTime(selectTimeBooleanPair.first.time1 / 1000);
                                applyBean.setApplyEndTime((selectTimeBooleanPair.first.time1 + 60 * 60 * 1000) / 1000);
                                changeApply(applyBean);
                                break;
                            }
                        }
                    }
                } else {
                    if (mTeacherBaseInfo != null) {
                        List<TaughtSubjects> taughtSubjects = getmentSubjects(mTeacherBaseInfo);
                        if (taughtSubjects.size() > 0) {
                            if (taughtSubjects.size() > 1) { //???????????????????????????
                                choiceCourseName(taughtSubjects);
                            } else {
                                order(taughtSubjects.get(0));
                            }
                        }
                    }
                }
            } else if (v.getId() == R.id.left_icon_iv) {
                if (AppUtils.isFastDoubleClick(300)) {
                    return;
                }

                if (mToPosition <= 0) {
                    return;
                }

                mToPosition = mToPosition - 1;
                mContentLayout.doScrollTo(-mItemWidthSize, 300);
            } else if (v.getId() == R.id.right_icon_iv) {
                if (AppUtils.isFastDoubleClick(300)) {
                    return;
                }

                if (mToPosition >= 27) {
                    return;
                }

                mToPosition = mToPosition + 1;
                mContentLayout.doScrollTo(mItemWidthSize, 300);
            }
        }
    };


    //??????
    private void changeApply(ChangeApplyBean applyBean) {
        showProgress();
        addJob(NetmonitorManager.orderChangeApply(applyBean, new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                closeProgress();
                com.newtonacademic.newtontutors.commons.ToastUtils.showToastShort(baseBean.getDescript());
                finish();

            }

            @Override
            public void failure(RestError error) {
                closeProgress();
                com.newtonacademic.newtontutors.commons.ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    //????????????????????????id????????????
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
        for (int i = 0; i < courseId.size(); i++) {
            TaughtSubjects taughtSubjects1 = new TaughtSubjects();
            taughtSubjects1.setCourseId(courseId.get(i));
            taughtSubjects1.setCourseName(courseName[i]);
            taughtSubjects.add(taughtSubjects1);
        }

        return taughtSubjects;

    }

    /**
     * ?????????????????????
     */
    private int mToPosition = 0;

    /**
     * @param time ?????????0???
     * @return
     */
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

    private static class TimeItme {
        public long date;
        //????????????????????????
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
            textView.setTextColor(hasValid(item) ? ContextCompat.getColor(ClassAppointmentActivity.this, R.color.color_activity_blue_bg)
                    : ContextCompat.getColor(ClassAppointmentActivity.this, R.color.color_bfbebe));

            textView.setEnabled(item.isvalid);
            textView.setBackgroundResource(0);
            for (Pair<SelectTime, Boolean> selectTime1 : mSelectAppointments) {
                if (selectTime1.second) {
                    if (selectTime1.first.time1 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_green_bg1);
                    } else if (selectTime1.first.time5 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_green_bg3);
                    } else if (selectTime1.first.time2 == item.date
                            || selectTime1.first.time3 == item.date
                            || selectTime1.first.time4 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_green_bg2);
                    }
                } else {
                    if (selectTime1.first.time1 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_gray_bg1);
                    } else if (selectTime1.first.time5 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_gray_bg3);
                    } else if (selectTime1.first.time2 == item.date
                            || selectTime1.first.time3 == item.date
                            || selectTime1.first.time4 == item.date) {
                        textView.setBackgroundResource(R.drawable.class_appointment_time_item_layout_gray_bg2);
                    }
                }
            }

            textView.setTag(item);
            textView.setOnClickListener(v -> {
                TimeItme clickTimeItem = (TimeItme) v.getTag();
                //??????????????????????????????????????????????????????????????????????????????
                if (!mSelectAppointments.isEmpty()) {
                    for (Pair<SelectTime, Boolean> selectTime : mSelectAppointments) {
                        if (selectTime.first.time1 == clickTimeItem.date && selectTime.second) {
                            mSelectAppointments.remove(selectTime);
                            notifyDataSetChanged();
                            updateView();
                            return;
                        }
                    }
                }

                Pair<SelectTime, Boolean> clickTime = null;
                List<TimeItme> timeItmeList = getDataList();
                int curIndex = 0;
                for (TimeItme timeItme : timeItmeList) {
                    if (clickTimeItem.date == timeItme.date) {
                        //??????????????????5?????????????????????????????????
                        if (curIndex > timeItmeList.size() - 5) {
                            return;
                        }

                        //????????????5??????????????????????????????????????????
                        List<TimeItme> subList = timeItmeList.subList(curIndex, curIndex + 5);
                        for (TimeItme itme : subList) {
                            if (!itme.isvalid) {
                                return;
                            }
                        }

                        //????????????5??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        if (!mSelectAppointments.isEmpty()) {
                            for (TimeItme timeItme1 : subList) {
                                for (Pair<SelectTime, Boolean> selectTime : mSelectAppointments) {
                                    if ((selectTime.first.time1 == timeItme1.date
                                            || selectTime.first.time2 == timeItme1.date
                                            || selectTime.first.time3 == timeItme1.date
                                            || selectTime.first.time4 == timeItme1.date
                                            || selectTime.first.time5 == timeItme1.date)) {
                                        if (!selectTime.second) {
                                            return;
                                        }

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
                        clickTime = new Pair<>(selectTime, true);
                        mSelectAppointments.add(clickTime);
                    }

                    curIndex++;
                }

                //??????????????????
                if (mReschedule == 1) {
                    for (Pair<SelectTime, Boolean> selectTime : new ArrayList<>(mSelectAppointments)) {
                        if (!selectTime.second) {
                            continue;
                        }

                        mSelectAppointments.remove(selectTime);
                    }

                    if (clickTime != null) {
                        mSelectAppointments.add(clickTime);
                    }
                }

                for (ViewItem viewItem : mViewItem) {
                    viewItem.adapter.notifyDataSetChanged();
                }

                updateView();
            });
        }

        private boolean hasValid(TimeItme item) {
            if (!item.isvalid) {
                return false;
            }

            for (Pair<SelectTime, Boolean> selectTime1 : mSelectAppointments) {
                if ((selectTime1.first.time1 == item.date
                        || selectTime1.first.time2 == item.date
                        || selectTime1.first.time3 == item.date
                        || selectTime1.first.time4 == item.date
                        || selectTime1.first.time5 == item.date) && !selectTime1.second) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * @param start ?????????0???
     * @param time  00???00???-23???45???
     * @return
     */
    private boolean checkValid(long start, long time) { //start ??????0???
        if (mScheduleListByTeacherIdInfo == null || mTeacherBaseInfo == null) {
            return false;
        }

        if (time <= System.currentTimeMillis()) {
            return false;
        }

        //??????
        String teacherTimeZone = mTeacherBaseInfo.getTimeZone();
        TimeZone curTimeZone = TimeZone.getDefault();

        //????????????????????????0??????????????????????????????
        Date zeroStartTimeInterval = new Date(start);
        Calendar zeroStartTimeIntervalCalendar = Calendar.getInstance();
        zeroStartTimeIntervalCalendar.setTime(zeroStartTimeInterval);
        int zeroStartTimeIntervaWek = zeroStartTimeIntervalCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        zeroStartTimeIntervaWek = zeroStartTimeIntervaWek == 0 ? 7 : zeroStartTimeIntervaWek;
        //???????????????
        for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo scheduleListByTeacherIdInfo : mScheduleListByTeacherIdInfo) {
            String startTimeStr = TimeUtils.GetDefaultTime(scheduleListByTeacherIdInfo.getTimeIntervalBegin() * 1000);
            String endTimeStr = TimeUtils.GetDefaultTime(scheduleListByTeacherIdInfo.getTimeIntervalEnd() * 1000);
            long startTime = convert(startTimeStr, teacherTimeZone, curTimeZone.getID());
            long endTime = convert(endTimeStr, teacherTimeZone, curTimeZone.getID());
            //????????????????????????????????????????????????0???
            if (zeroStartTimeInterval.getTime() <= startTime || zeroStartTimeInterval.getTime() >= endTime) {
                //????????????????????????????????????????????????
                return false;
            }

            List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries> entries = scheduleListByTeacherIdInfo.getEntries();
            for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries entrie : entries) {
                //????????????
                if (entrie.getEntryType() != 1) {
                    continue;
                }

                String[] weekArray = entrie.getWeekDays().split(",");
                if (weekArray.length == 0) {
                    return false;
                }

                //????????????????????????
                long beginTime2 = zeroStartTimeInterval.getTime() + (entrie.getBeginTime() * 1000);
                long endTime2 = zeroStartTimeInterval.getTime() + (entrie.getEndTime() * 1000);
                String date1 = TimeUtils.GetDefaultTime(beginTime2);
                String date2 = TimeUtils.GetDefaultTime(endTime2);
                long startTimeIntervalLong = convert(date1, teacherTimeZone, curTimeZone.getID());
                long endTimeIntervalLong = convert(date2, teacherTimeZone, curTimeZone.getID());

                String startTimeIntervalStr = TimeUtils.GetDefaultTime(startTimeIntervalLong);
                String endTimeIntervalStr = TimeUtils.GetDefaultTime(endTimeIntervalLong);
                if (TextUtils.isEmpty(startTimeIntervalStr) || TextUtils.isEmpty(endTimeIntervalStr)) {
                    return false;
                }

                //??????????????????
                long offset = TimeUtils.GetTimestamp(TimeUtils.GetDefaultTime(beginTime2), TimeUtils.DEFAULT_TIME_FORMA3)
                        - TimeUtils.GetTimestamp(startTimeIntervalStr, TimeUtils.DEFAULT_TIME_FORMA3);
                if (Math.abs(offset) >= 24 * 60 * 60 * 1000) {
                    //????????????????????????????????????
                    String startHourDate = startTimeIntervalStr.split(" ")[1];
                    String endHourDate = endTimeIntervalStr.split(" ")[1];
                    String[] startHourArray = startHourDate.split(":");
                    String[] endHourArray = endHourDate.split(":");
                    startTimeIntervalLong = zeroStartTimeInterval.getTime()
                            + (Integer.parseInt(startHourArray[0]) * 60 * 60
                            + Integer.parseInt(startHourArray[1]) * 60
                            + Integer.parseInt(startHourArray[2])) * 1000;
                    endTimeIntervalLong = zeroStartTimeInterval.getTime()
                            + (Integer.parseInt(endHourArray[0]) * 60 * 60
                            + Integer.parseInt(endHourArray[1]) * 60
                            + Integer.parseInt(endHourArray[2])) * 1000;

                    String[] weekArrayTemp = new String[weekArray.length];
                    if (offset > 0) {
                        //?????????????????????????????????????????????????????????
                        for (int i = 0; i < weekArray.length; i++) {
                            int intData = Integer.parseInt(weekArray[i]);
                            intData = intData - 1 <= 0 ? 7 : intData - 1;
                            weekArrayTemp[i] = String.valueOf(intData);
                        }

                        weekArray = weekArrayTemp;
                    } else if (offset < 0) {
                        //?????????????????????????????????????????????????????????
                        for (int i = 0; i < weekArray.length; i++) {
                            int intData = Integer.parseInt(weekArray[i]);
                            intData = intData + 1 > 7 ? 1 : intData + 1;
                            weekArrayTemp[i] = String.valueOf(intData);
                        }

                        weekArray = weekArrayTemp;
                    }
                }

                for (String weekStr : weekArray) {
                    if (Integer.parseInt(weekStr) == zeroStartTimeIntervaWek) {
                        return time >= startTimeIntervalLong && time <= endTimeIntervalLong;
                    }
                }
            }
        }

        return false;
    }

    /**
     * ????????????
     *
     * @param sourceDate       ?????????
     * @param sourceTimeZone   ?????????
     * @param targetTimeZoneId ????????????
     * @return
     */
    private long convert(String sourceDate, String sourceTimeZone, String targetTimeZoneId) {
        TimeZone timeZoneSH = TimeZone.getTimeZone(targetTimeZoneId);
        TimeZone timeZoneNY = TimeZone.getTimeZone(sourceTimeZone);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        inputFormat.setTimeZone(timeZoneNY);
        Date date;
        try {
            date = inputFormat.parse(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFormat.setTimeZone(timeZoneSH);
        return TimeUtils.GetTimestamp(outputFormat.format(date));
    }


    private AppointmentNoAvaliableDialog mAppointmentNoAvaliableDialog = null;

    private void showImOutLineDialog(String teacherName) {
        if (mAppointmentNoAvaliableDialog != null && mAppointmentNoAvaliableDialog.isShowing()) {
            return;
        }

        String message = getString(R.string.appointment_tip1);
        if (teacherName.equals("Dr James  Orr")) {
            message = getString(R.string.appointment_tip2);
        }

        mAppointmentNoAvaliableDialog = new AppointmentNoAvaliableDialog(ClassAppointmentActivity.this);
        mAppointmentNoAvaliableDialog.setMessageTv(message);
        mAppointmentNoAvaliableDialog.show();

    }
}
