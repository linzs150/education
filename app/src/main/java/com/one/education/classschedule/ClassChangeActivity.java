package com.one.education.classschedule;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.IntentEx;
import com.one.education.classappointment.ClassAppointmentChangeActivity;
import com.one.education.display.DisplayImageOptionsCreator;
import com.one.education.display.MyImageLoader;
import com.one.education.education.R;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetBaseProfile;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.FileUri;
import com.one.education.utils.TimeUtils;
import com.one.education.utils.Utilts;

import java.util.Locale;

import static com.one.education.utils.TimeUtils.DEFAULT_TIME_FORMA2;

/**
 * @author laiyongyang
 * @date 2020-06-03
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ClassChangeActivity extends BaseActivity {

    public static IntentEx newIntentEx(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse) {
        IntentEx intentEx = new IntentEx(context, ClassChangeActivity.class);
        intentEx.putExtraEx(INTENT_DATA, studentStudyCourse);
        return intentEx;
    }

    private static final String INTENT_DATA = "studentStudyCourse";
    private GetStudentStudyCourseList.StudentStudyCourse mStudentStudyCourse;
    private GetBaseProfile.TeacherBaseInfo mTeacherBaseInfo;
    private ImageView mIconIv;
    private TextView mNameTv;
    private TextView mClassAgeTv;
    private TextView mScoreTv;
    private RatingBar mTeacherBar;
    private TextView mLocationTv;
    private TextView mDataWeekTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mClassTimeTv;
    private TextView mPriceTv;
    private TextView mCourseNameTv;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_change);
        mStudentStudyCourse = getIntentEx().getExtraEx(INTENT_DATA);
        findViewById(R.id.back_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.need_change_tv).setOnClickListener(mOnClickListener);
        mIconIv = findViewById(R.id.icon_iv);
        mNameTv = findViewById(R.id.teacher_name_tv);
        mClassAgeTv = findViewById(R.id.class_age_tv);
        mScoreTv = findViewById(R.id.score_tv);
        mTeacherBar = findViewById(R.id.score_rating_bar);
        mLocationTv = findViewById(R.id.class_location_tv);
        mDataWeekTv = findViewById(R.id.date_week_tv);
        mStartTimeTv = findViewById(R.id.start_time_tv);
        mEndTimeTv = findViewById(R.id.end_time_tv);
        mClassTimeTv = findViewById(R.id.class_tv);
        mCourseNameTv = findViewById(R.id.subject_tv);
        mPriceTv = findViewById(R.id.price_tv);
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                GetBaseProfile getBaseProfile = HttpsServiceFactory.getBaseProfile(mStudentStudyCourse.getTeacherId());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isFinishing()) {
                            return;
                        }

                        closeProgress();
                        mTeacherBaseInfo = getBaseProfile.getData();
                        if (mTeacherBaseInfo == null) {
                            return;
                        }

                        updateView();
                    }
                });
            }
        });

        showProgress();
    }

    private void updateView() {
        MyImageLoader.getInstance().displayImage(FileUri.newIconUri(mStudentStudyCourse.getTeacherUserPicUrl()), mIconIv, DisplayImageOptionsCreator.getDefaultUserDisplayImageOptions());
        mNameTv.setText(mStudentStudyCourse.getTeacherName());
        if (mTeacherBaseInfo.getSex() == 1) {
            mNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.male, 0);
        } else if (mTeacherBaseInfo.getSex() == 2) {
            mNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.female, 0);
        }

        mCourseNameTv.setText(mStudentStudyCourse.getCourseName());
        mPriceTv.setText(getString(R.string.money_symbol) + mStudentStudyCourse.getCoursePrice());

        mClassAgeTv.setText(getString(R.string.class_age_format, mTeacherBaseInfo.getTeachingExperience()));
        mScoreTv.setText(String.valueOf(mTeacherBaseInfo.getCommentStar()));
//        mTeacherBar.setRating(mTeacherBaseInfo.getCommentStar() / 2);
        mTeacherBar.setRating(Utilts.ratingJS(mTeacherBaseInfo.getCommentStar()));
        mLocationTv.setText(mTeacherBaseInfo.getProvince() + " " + mTeacherBaseInfo.getCity());

        String startTime = TimeUtils.GetTime(mStudentStudyCourse.getBeginTime() * 1000, DEFAULT_TIME_FORMA2);
        String endTime = TimeUtils.GetTime(mStudentStudyCourse.getEndTime() * 1000, DEFAULT_TIME_FORMA2);
        String[] startTimeArray = startTime.split(" ");
        String[] endTimeArray = endTime.split(" ");

        mDataWeekTv.setText(String.format(Locale.getDefault(), "%s %s", startTimeArray[0], TimeUtils.getWeek(getActivity(), mStudentStudyCourse.getBeginTime())));
        mStartTimeTv.setText(getString(R.string.start_time_format, startTimeArray.length == 2 ? startTimeArray[1] : ""));
        mEndTimeTv.setText(getString(R.string.end_time_format, endTimeArray.length == 2 ? endTimeArray[1] : ""));
        mClassTimeTv.setText(String.valueOf(mStudentStudyCourse.getCourseDuration() / 60));
        findViewById(R.id.change_tip_layout).setVisibility(checkAllowChange() ? View.GONE : View.VISIBLE);
        findViewById(R.id.need_change_tv).setVisibility(checkAllowChange() ? View.VISIBLE : View.GONE);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_iv) {
                finish();
            } else if (v.getId() == R.id.need_change_tv) {
                startActivity(ClassAppointmentChangeActivity.newIntent(ClassChangeActivity.this, mStudentStudyCourse));
            }
        }
    };

    /**
     * 检查是否允许改签
     * @return
     */
    private boolean checkAllowChange() {
        long startTime = mStudentStudyCourse.getBeginTime() * 1000;
        long curTime = SystemClock.elapsedRealtime();
        return startTime - curTime >= 3 * 24 * 60 * 60 * 1000;
    }
}
