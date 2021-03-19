package com.one.education.classappointment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.IntentEx;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.MultiTypeDelegate;
import com.one.education.adapter.ViewHolder;
import com.one.education.display.DisplayImageOptionsCreator;
import com.one.education.display.MyImageLoader;
import com.one.education.education.R;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetScheduleListByTeacherIdRsp;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.AppTipsUtils;
import com.one.education.utils.FileUri;
import com.one.education.utils.TimeUtils;
import com.one.education.utils.toast.DateUtilts;
import com.one.education.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author laiyongyang
 * @date 2020-05-16
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ClassAppointmentChangeActivity extends BaseActivity {
    public static IntentEx newIntent(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse) {
        IntentEx intentEx = new IntentEx(context, ClassAppointmentChangeActivity.class);
        intentEx.putExtraEx(INTENT_DATA, studentStudyCourse);
        return intentEx;
    }

    private static final String TAG = "ClassAppointment";
    private static final String INTENT_DATA = "data";
    private GetStudentStudyCourseList.StudentStudyCourse mStudentStudyCourse;
    private MyAdapter mMyAdapter;
    private List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo> mScheduleListByTeacherIdInfo;
    private RecyclerView mRecyclerView;
    private TextView mTvSelectCount;
    private TextView mTvMoney;
    private MyLinearLayoutManager mLinearLayoutManager;
    private final List<MyAdapterData> mDataList = new ArrayList<>();
    private final List<SelectTime> mSelectAppointments = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_appointment);
        findViewById(R.id.back_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.order).setOnClickListener(mOnClickListener);
        IntentEx intentEx = getIntentEx();
        mStudentStudyCourse = intentEx.getExtraEx(INTENT_DATA);
        SelectTime selectTime = new SelectTime();
        selectTime.time1 = mStudentStudyCourse.getBeginTime() * 1000;
        selectTime.time2 = mStudentStudyCourse.getBeginTime() * 1000 + 15 * 60 * 1000;
        selectTime.time3 = mStudentStudyCourse.getBeginTime() * 1000 + 2 * 15 * 60 * 1000;
        selectTime.time4 = mStudentStudyCourse.getBeginTime() * 1000 + 3 * 15 * 60 * 1000;
        selectTime.time5 = mStudentStudyCourse.getBeginTime() * 1000 + 4 * 15 * 60 * 1000;
        mSelectAppointments.add(selectTime);

        TextView nameTv = findViewById(R.id.name_tv);
        nameTv.setText(mStudentStudyCourse.getTeacherName());
        ImageView icon = findViewById(R.id.icon);
        mTvSelectCount = findViewById(R.id.had_select_course_tv);
        mTvMoney = findViewById(R.id.money_tv);

        MyImageLoader.getInstance().displayImage(FileUri.newUserIconUri(mStudentStudyCourse.getTeacherUserPicUrl()),
                icon, DisplayImageOptionsCreator.getDefaultUserDisplayImageOptions());

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
        mRecyclerView = findViewById(R.id.recycleview);
        mLinearLayoutManager = new MyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(this, MyDividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_item_decoration_white_shape));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMyAdapter);

        if (AppTipsUtils.checkNetworkState(this)) {
            showProgress("正在加载...");
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new Runnable() {
                @Override
                public void run() {
                    GetScheduleListByTeacherIdRsp response = HttpsServiceFactory.getScheduleListByTeacherId(mStudentStudyCourse.getTeacherId(), TimeZone.getDefault().getID());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isFinishing()) {
                                return;
                            }

                            closeProgress();
                            int status = response.getStatus();
                            if (status == 1) {
                                mScheduleListByTeacherIdInfo = response.getData();
                            } else {
                                ToastUtils.ShowToastLong(ClassAppointmentChangeActivity.this, response.getDescript());
                            }
                        }
                    });
                }
            });
        }

        updateView();
    }

    private void updateView() {
        mTvSelectCount.setText(String.format(Locale.getDefault(), "已选 %d节", mSelectAppointments.size()));
        mTvMoney.setText(String.format(Locale.getDefault(), "%s%s", getString(R.string.money_symbol), mSelectAppointments.size() * mStudentStudyCourse.getCoursePrice()));
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_iv) {
                finish();
            } else if (v.getId() == R.id.order) {
                if (mSelectAppointments.isEmpty()) {
                    ToastUtils.ShowToastLong(getActivity(), "您还没有选择预约时间");
                    return;
                }
                List<Long> tempList = new ArrayList<>();
                for (SelectTime selectTime : mSelectAppointments) {
                    tempList.add(selectTime.time1);
                }

                //申请改签

            } else if (v.getId() == R.id.left_icon_iv) {
                smoothMoveToPosition(mToPosition - 1);
            } else if (v.getId() == R.id.right_icon_iv) {
                smoothMoveToPosition(mToPosition + 1);
            }
        }
    };

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
            setMultiTypeDelegate(new MultiTypeDelegate<MyAdapterData>() {
                @Override
                public int getItemTypeLayoutRes(MyAdapterData data) {
                    return R.layout.class_appointment_item_layout;
                }
            });
        }

        @Override
        public void bindViewHolder(ViewHolder holder, MyAdapterData item, int position) {
            TextView weekTv = holder.getView(R.id.week_tv);
            TextView dateTv = holder.getView(R.id.date_tv);

            if (position == 0) {
                weekTv.setText(mContext.getString(R.string.today));
            } else {
                weekTv.setText(DateUtilts.getWeek(mContext, item.date));
            }

            String time = TimeUtils.GetTime(item.date, TimeUtils.DEFAULT_TIME_FORMA3);
            String[] dateArray = time.split("-");
            dateTv.setText(String.format(Locale.getDefault(), "%s-%s", dateArray[1], dateArray[2]));
            RecyclerView recyclerView = holder.getView(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(ClassAppointmentChangeActivity.this));
            recyclerView.setBackgroundColor(position % 2 == 0 ? getResources().getColor(R.color.color_ebebeb) :
                    getResources().getColor(R.color.color_8a8a8a));
            item.contentAdapter.setDataList(getClassTime(item.date));
            recyclerView.setAdapter(item.contentAdapter);
        }

        private List<TimeItme> getClassTime(long time) {
            List<TimeItme> dateList = new ArrayList<>();
            for (int i = 0; i < 24 * 4; i++) {
                TimeItme timeItme = new TimeItme();
                timeItme.date = time + i * 15 * 60 * 1000;
                timeItme.isvalid = checkValid(timeItme.date);
                dateList.add(timeItme);
            }

            return dateList;
        }
    }

    private class TimeItme {
        public long date;
        //是否是有效的时间
        boolean isvalid;
        boolean isSelect;
        boolean isSelectFirst;
        boolean isSelectLast;
    }

    private class SelectTime {
        long time1;
        long time2;
        long time3;
        long time4;
        long time5;
    }

    private class ContentAdapter extends BaseRecyclerViewAdapter<TimeItme> {
        public ContentAdapter() {
            setMultiTypeDelegate(new MultiTypeDelegate<TimeItme>() {
                @Override
                public int getItemTypeLayoutRes(TimeItme data) {
                    return R.layout.class_appointment_time_item_layout;
                }
            });
        }

        @Override
        public void bindViewHolder(ViewHolder holder, TimeItme item, int position) {
            TextView textView = holder.getView(R.id.time_tv);
            String date = TimeUtils.GetTime(item.date, TimeUtils.DEFAULT_TIME_FORMA2);
            textView.setText(date.split(" ")[1]);
            textView.setTextColor(item.isvalid ? ContextCompat.getColor(ClassAppointmentChangeActivity.this, R.color.black)
                    : ContextCompat.getColor(ClassAppointmentChangeActivity.this, R.color.color_bfbebe));

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
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });
        }
    }

    private boolean checkValid(long time) {
        if (mScheduleListByTeacherIdInfo == null || mScheduleListByTeacherIdInfo.isEmpty()) {
            return false;
        }

        for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo data : mScheduleListByTeacherIdInfo) {
            int timeIntervalType = data.getTimeIntervalType();
            long startTime = data.getTimeIntervalBegin();
            long endTime = data.getTimeIntervalEnd();
            if (time >= startTime && time <= endTime) {
                return true;
            }

            List<GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries> entries = data.getEntries();
            for (GetScheduleListByTeacherIdRsp.ScheduleListByTeacherIdInfo.Entries entrie : entries) {
                //不可预约
                if (entrie.getEntryType() != 1) {
                    continue;
                }

                long startTimeInterval = entrie.getBeginTime();
                long endTimeInterval = entrie.getEndTime();
                if (time >= startTimeInterval && time <= endTimeInterval) {
                    return true;
                }
            }
        }

        return false;
    }
}