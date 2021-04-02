package com.newtonacademic.newtontutors.classschedule;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.activities.IntentEx;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.language.SpUtil;
import com.newtonacademic.newtontutors.retrofit.HttpsServiceFactory;
import com.newtonacademic.newtontutors.retrofit.model.CommentRsp;
import com.newtonacademic.newtontutors.retrofit.model.GetBaseProfile;
import com.newtonacademic.newtontutors.retrofit.model.GetStudentStudyCourseList;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;
import com.newtonacademic.newtontutors.utils.ImageLoader;
import com.newtonacademic.newtontutors.utils.TimeUtils;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;
import com.newtonacademic.newtontutors.widget.MyEditText;
import com.newtonacademic.mylibrary.ConstantGlobal;

import java.util.Locale;

import static com.newtonacademic.newtontutors.utils.TimeUtils.DEFAULT_TIME_FORMA2;

/**
 * @author laiyongyang
 * @date 2020-05-20
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class CourseEvaluationActivity extends BaseActivity {
    public static IntentEx newIntentEx(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse) {
        IntentEx intentEx = new IntentEx(context, CourseEvaluationActivity.class);
        intentEx.putExtraEx(INTENT_DATA, studentStudyCourse);
        return intentEx;
    }

    private static final String INTENT_DATA = "StudentStudyCourse";
    private ImageView mIconIv;
    private TextView mNameTv;
    private TextView mClassAgeTv;
    private RatingBar mTeacherBar;
    private TextView mDataWeekTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mClassTimeTv;
    private RatingBar mAchievingLearningObjectivesBar;
    private RatingBar mAcademicExpertiseBar;
    private RatingBar mClarityOfTeachingBar;
    private RatingBar mApproachabilityRapportBar;
    private RatingBar mInspirationAndMotivationBar;
    private MyEditText mEditText;
    private ScrollView mScrollView;
    private GetStudentStudyCourseList.StudentStudyCourse mStudentStudyCourse;
    private GetBaseProfile.TeacherBaseInfo mTeacherBaseInfo;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_evaluation);
        findViewById(R.id.back_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.finish_evaluqtion).setOnClickListener(mOnClickListener);
        mStudentStudyCourse = getIntentEx().getExtraEx(INTENT_DATA);

        mIconIv = findViewById(R.id.icon_iv);
        mNameTv = findViewById(R.id.teacher_name_tv);
        mClassAgeTv = findViewById(R.id.class_age_tv);
        mTeacherBar = findViewById(R.id.score_rating_bar);
        mDataWeekTv = findViewById(R.id.date_week_tv);
        mStartTimeTv = findViewById(R.id.start_time_tv);
        mEndTimeTv = findViewById(R.id.end_time_tv);
        mClassTimeTv = findViewById(R.id.class_tv);
        mAchievingLearningObjectivesBar = findViewById(R.id.achieving_learning_objectives_bar);
        mAcademicExpertiseBar = findViewById(R.id.academic_expertise_bar);
        mClarityOfTeachingBar = findViewById(R.id.clarity_of_teaching_bar);
        mApproachabilityRapportBar = findViewById(R.id.approachability_rapport);
        ;
        mInspirationAndMotivationBar = findViewById(R.id.inspiration_and_motivation_bar);
        mEditText = findViewById(R.id.evalution_edt);
        mEditText.setOnClickListener(mOnClickListener);
        mScrollView = findViewById(R.id.scrollView);
        showProgress();
        loadData();
    }

    private void loadData() {
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

                        updateview();
                    }
                });
            }
        });
    }

    private void updateview() {
        ImageLoader.loadAdImage(mStudentStudyCourse.getTeacherUserPicUrl(), mIconIv);
        mNameTv.setText(mStudentStudyCourse.getTeacherName());
        if (mTeacherBaseInfo.getSex() == 1) {
            mNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.male, 0);
        } else if (mTeacherBaseInfo.getSex() == 2) {
            mNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.female, 0);
        }

        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("en")) {
                if (mTeacherBaseInfo.getTeachingExperience() == 0 || mTeacherBaseInfo.getTeachingExperience() == 1) {

//                        th_age.setText(getString(R.string.teaching_experience_single, item.getTeachingExperience()));
                    mClassAgeTv.setText("Teaching: " + mTeacherBaseInfo.getTeachingExperience() + " year");
                } else {
                    mClassAgeTv.setText(getString(R.string.teaching_experience, mTeacherBaseInfo.getTeachingExperience()));
                }
            } else {
                mClassAgeTv.setText(getString(R.string.teaching_experience, mTeacherBaseInfo.getTeachingExperience()));
            }
        }

//        mClassAgeTv.setText(getString(R.string.class_age_format, mTeacherBaseInfo.getTeachingExperience()));
        mTeacherBar.setRating(Utilts.ratingJS(mTeacherBaseInfo.getCommentStar()));

        String startTime = TimeUtils.GetTime(mStudentStudyCourse.getBeginTime() * 1000, DEFAULT_TIME_FORMA2);
        String endTime = TimeUtils.GetTime(mStudentStudyCourse.getEndTime() * 1000, DEFAULT_TIME_FORMA2);
        String[] startTimeArray = startTime.split(" ");
        String[] endTimeArray = endTime.split(" ");

        mDataWeekTv.setText(String.format(Locale.getDefault(), "%s %s", startTimeArray[0], TimeUtils.getWeek(getActivity(), mStudentStudyCourse.getBeginTime() * 1000)));
        mStartTimeTv.setText(getString(R.string.start_time_format, startTimeArray.length == 2 ? startTimeArray[1] : ""));
        mEndTimeTv.setText(getString(R.string.end_time_format, endTimeArray.length == 2 ? endTimeArray[1] : ""));

        mClassTimeTv.setText(String.valueOf(mStudentStudyCourse.getCourseDuration() / 60));
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_iv) {
                finish();
            } else if (v.getId() == R.id.evalution_edt) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //将ScrollView滚动到底
                        mScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 100);
            } else if (v.getId() == R.id.finish_evaluqtion) {
                ThreadPoolProxyFactory.getNormalThreadProxy().execute(new Runnable() {
                    @Override
                    public void run() {
                        int id = mStudentStudyCourse.getId();
                        String orderCode = mStudentStudyCourse.getOrderCode();

                        int courseId = mStudentStudyCourse.getCourseId();
                        int teacherId = mStudentStudyCourse.getTeacherId();
                        int commentStar1 = (int) mAchievingLearningObjectivesBar.getRating();
                        int commentStar2 = (int) mAcademicExpertiseBar.getRating();
                        int commentStar3 = (int) mClarityOfTeachingBar.getRating();
                        int commentStar4 = (int) mApproachabilityRapportBar.getRating();
                        int commentStar5 = (int) mInspirationAndMotivationBar.getRating();
                        String content = mEditText.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            ToastUtils.ShowToastLong(CourseEvaluationActivity.this, getString(R.string.enter_your_commons));
                            return;
                        }

                        if (commentStar1 == 0 || commentStar2 == 0 || commentStar3 == 0 || commentStar4 == 0 || commentStar5 == 0) {
                            ToastUtils.ShowToastLong(CourseEvaluationActivity.this, getString(R.string.give_your_Tutor));
                            return;
                        }

                        final CommentRsp commentRsp = HttpsServiceFactory.coursecommentCreate(id, orderCode, courseId,
                                teacherId, commentStar1, commentStar2, commentStar3, commentStar4,
                                commentStar5, content);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinishing()) {
                                    return;
                                }

                                if (commentRsp.getStatus() == 1) {
                                    ToastUtils.ShowToastLong(CourseEvaluationActivity.this, getString(R.string.comment_success));
                                    finish();
                                } else {
                                    ToastUtils.ShowToastLong(CourseEvaluationActivity.this, commentRsp.getDescript());
                                }
                            }
                        });
                    }
                });
            }
        }
    };
}
