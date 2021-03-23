package com.one.education.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.api.NimUIKit;
import com.one.education.EducationAppliction;
import com.one.education.adapters.IndentityAdapter;
import com.one.education.adapters.VideoAdapter;
import com.one.education.beans.BaseBean;
import com.one.mylibrary.TaughtSubjects;
import com.one.education.beans.TeacherProfileItem;
import com.one.education.beans.TeacherProfileResponse;
import com.one.education.classappointment.ClassAppointmentActivity;
import com.one.education.commons.SharedPreferencesUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.commons.Utils;
import com.one.education.education.R;
import com.one.education.language.SpUtil;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.utils.AppTipsUtils;
import com.one.education.utils.EventBusUtils;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.Utilts;
import com.one.education.widget.CourseLinearLayout;
import com.one.education.widget.ExpericenceLinearLayout;
import com.one.mylibrary.ConstantGlobal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/12 20:54
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TeacherDetail1Activity extends BaseActivity {
//    public static Intent newIntent(Context context, TeacherBean.TeacherList teacherList) {
//        Intent intent = new Intent(context, TeacherDetail1Activity.class);
//        intent.putExtra(INTENT_DATA, teacherList);
//        return intent;
//    }

    public static Intent newIntent(Context context, long teacherId) {
        Intent intent = new Intent(context, TeacherDetail1Activity.class);
        intent.putExtra(INTENT_DATA, teacherId);
        return intent;
    }

    private static final String INTENT_DATA = "techerId";
    private TextView tvTitle;
    private RelativeLayout leftBtnLayout;
    private CircleImageView head;
    private TextView name;
    private ImageView sex;
    private ImageView image_info;
    private ImageView image_care;
    private TextView th_age;
    private RatingBar mRatingBar;
    private TextView location;
    private TextView date;
    private LinearLayout course_layout;
    private RadioButton detail_radio;
    private RadioButton identity_radio;
    private RadioButton video_radio;
    private LinearLayout detail_layout;
    private TextView tv_yes;
    private TextView tv_mamdarin;
    private TextView tv_universities;
    private TextView tv_college;
    private TextView tv_level;
    private LinearLayout subject_layout; //科目
    private LinearLayout experience_layout;//经验
    private LinearLayout identity_layout;
    private RecyclerView detail_recyclerview;
    private LinearLayout video_layout;
    private RecyclerView video_recyclerview;
    private RadioGroup detail_radiogroup;
    //    private List<VideoBean> videoBeanList = new ArrayList<>();
//    private CircleImageView circleImageView;
    private VideoAdapter videoAdapter;
    private RecyclerView identity_recyclerview;
    private IndentityAdapter indentityAdapter;
    private LinearLayout course_th_layout;
    //    private TeacherBean.TeacherList teacherList;
    private long teacherId = 0;
    private TextView score;
    private TextView tv_capacity;
    private Boolean mIsFollow = false;
    private long teachId = 0l;
    private float mCoursePrice = 0;
    private TextView tv_file_content;
    private TextView tv_information_content;
    private LinearLayout taugh_layout;
    private TextView tv_course_item;
    private TextView type_id;
    private LinearLayout speak_layout;
    private String state;
    private TeacherProfileItem profileItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_detail1_activity);
        Intent intent = getIntent();
        teacherId = intent.getLongExtra(INTENT_DATA, 0l);
//        if (teacherList != null) {
//            teacherId = teacherList.getTeacherId();
//        }

        initView();
        initData();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.tutor_profile));
    }

    private void initData() {
        head = findViewById(R.id.head);
        name = findViewById(R.id.name);
        subject_layout = findViewById(R.id.subject_layout);
        experience_layout = findViewById(R.id.experience_layout);
        detail_radiogroup = findViewById(R.id.detail_radiogroup);
        video_layout = findViewById(R.id.video_layout);
        video_recyclerview = findViewById(R.id.video_recyclerview);
//        circleImageView = findViewById(R.id.imge_video);
        identity_recyclerview = findViewById(R.id.identity_recyclerview);
        identity_layout = findViewById(R.id.identity_layout);
        video_layout = findViewById(R.id.video_layout);
        detail_layout = findViewById(R.id.detail_layout);
        course_th_layout = findViewById(R.id.course_th_layout);
        score = findViewById(R.id.score);
        tv_capacity = findViewById(R.id.tv_capacity);
        sex = findViewById(R.id.sex);
        image_care = findViewById(R.id.image_care);
        th_age = findViewById(R.id.th_age);
        speak_layout = findViewById(R.id.speak_layout);

        tv_course_item = findViewById(R.id.tv_course_item);

        mRatingBar = findViewById(R.id.mRatingBar);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        video_radio = findViewById(R.id.video_radio);
        detail_layout = findViewById(R.id.detail_layout);
        tv_yes = findViewById(R.id.tv_yes);
        tv_mamdarin = findViewById(R.id.tv_mamdarin);
        tv_universities = findViewById(R.id.tv_universities);
        tv_college = findViewById(R.id.tv_college);
        tv_level = findViewById(R.id.tv_level);
        tv_file_content = findViewById(R.id.tv_file_content);
        tv_information_content = findViewById(R.id.tv_infomation_content);
        image_info = findViewById(R.id.image_info);
        taugh_layout = findViewById(R.id.taugh_layout);
        type_id = findViewById(R.id.type_id);

        state = SharedPreferencesUtils.getInstance().getString("state", "");
        findViewById(R.id.confirm_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (profileItem != null) {
                    if (!TextUtils.isEmpty(state) && state.equals("1")) {
                        startActivityForResult(ClassAppointmentActivity.newIntent(TeacherDetail1Activity.this, profileItem, (int) mCoursePrice), 1001);
                    } else {
                        ToastUtils.showToastShort(getString(R.string.been_approved));
                        return;
                    }
                }
            }
        });

        initDetail();
        initIdentity();
        initVideo();
        courseZhiShi();
        teacherDetail();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1003) {
            EventBusUtils.post("pay_success");
            finish();
        }
    }

    CourseLinearLayout courseLinearLayout;

    private void teacherDetail() {
        if (AppTipsUtils.checkNetworkState(TeacherDetail1Activity.this)) {
            addJob(NetmonitorManager.teacherGetBaseProfile(teacherId, new RestNewCallBack<TeacherProfileResponse>() {
                @Override
                public void success(TeacherProfileResponse teacherProfileResponse) {
                    closeProgress();
                    if (teacherProfileResponse != null) {
                        if (teacherProfileResponse.isSuccess()) {

                            updateTeacherInfo(teacherProfileResponse.getData());
                        }
                    }
                }

                @Override
                public void failure(RestError error) {
                    ToastUtils.showToastShort(error.msg);
                    closeProgress();
                }
            }));
        }

        showProgress("");
    }

    private void getCourseName(String course) {
        String[] courseName = course.split(",");
        updateTaugh(courseName);
    }

    private void updateTaugh(String[] subject) {
        taugh_layout.removeAllViews();
        for (int i = 0; i < subject.length; i++) {
            if (!TextUtils.isEmpty(subject[i])) {
                View view = LayoutInflater.from(this).inflate(R.layout.taugh_teacher_item, null);
                TextView tv = view.findViewById(R.id.tv_level);
                tv.setText(subject[i]);
                taugh_layout.addView(view);
            }
        }
    }


    private void updateTeacherInfo(TeacherProfileItem item) {
        if (item != null) {
            profileItem = item;
            mCoursePrice = item.getCoursePrice();
            name.setText(TextUtils.isEmpty(item.getTeacherName()) ? "" : item.getTeacherName());
            if (!TextUtils.isEmpty(item.getTeacherName()) && item.getTeacherName().length() >= 16) {
                name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else {
                name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            }
            if (item.getSex() == 1) {
                sex.setImageResource(R.drawable.boy_sex);
            } else if (item.getSex() == 2) {
                sex.setImageResource(R.drawable.sex_female);
            }

            if (item.getIsFollow() == 1) {
                image_care.setImageResource(R.drawable.care_follow);
                mIsFollow = true;
            } else {
                image_care.setImageResource(R.drawable.care_icon);
                mIsFollow = false;
            }
            teachId = item.getTeacherId();

            String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
            String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
            if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            } else {
                if (spLanguage.equals("en")) {
                    if (item.getTeachingExperience() == 0 || item.getTeachingExperience() == 1) {

//                        th_age.setText(getString(R.string.teaching_experience_single, item.getTeachingExperience()));
                        th_age.setText("Teaching: " + item.getTeachingExperience() + " year");
                    } else {
                        th_age.setText(getString(R.string.teaching_experience, item.getTeachingExperience()));
                    }
                } else {
                    th_age.setText(getString(R.string.teaching_experience, item.getTeachingExperience()));
                }
            }
            score.setText(item.getCommentStar() + "");
            mRatingBar.setRating(Utilts.ratingJS(item.getCommentStar()));
            date.setText(Utilts.dateToInt(item.getBirthday()));
            location.setText(TextUtils.isEmpty(item.getProvince()) ? "" : item.getProvince() + (TextUtils.isEmpty(item.getCity()) ? "" : item.getCity()));
            if (TextUtils.isEmpty(item.getUserPicUrl())) {

            } else {
                ImageLoader.loadAdImage(item.getUserPicUrl(), head);
            }

            if (item.getTeacherLevel() == 0 || item.getTeacherLevel() == 1) {
                type_id.setText(getString(R.string.teacher_level1));
            } else if (item.getTeacherLevel() == 2) {
                type_id.setText(getString(R.string.teacher_level2));
            } else if (item.getTeacherLevel() == 3) {
                type_id.setText(getString(R.string.teacher_level3));
            } else {
                type_id.setText(getString(R.string.teacher_level1));
            }

            if (item.getCanSpeakChinese() == 1 || item.getCanSpeakChinese() == 2 || item.getCanSpeakChinese() == 3) {
                tv_yes.setText(getString(R.string.china_yes));
                speak_layout.setVisibility(View.VISIBLE);
                tv_mamdarin.setText(Utilts.speakChinese(this, item.getCanSpeakChinese()));
            } else {
                tv_yes.setText(getString(R.string.china_no));
                speak_layout.setVisibility(View.GONE);
            }

            tv_information_content.setText(item.getAchievements());
            tv_file_content.setText(item.getIntroduction());

            tv_universities.setText(TextUtils.isEmpty(item.getUniversitys()) ? "" : Utils.spliteData(item.getUniversitys(), ","));
            tv_college.setText(TextUtils.isEmpty(item.getColleges()) ? "" : Utils.spliteData(item.getColleges(), ","));
            tv_capacity.setText(TextUtils.isEmpty(item.getPresentCapacity()) ? "" : item.getPresentCapacity());
            tv_level.setText(TextUtils.isEmpty(item.getCourseName()) ? "" : item.getCourseName());
            if (item.getTaughtSubjects() != null && item.getTaughtSubjects().size() > 0) {

                Collections.sort(item.getTaughtSubjects(), new Comparator<TaughtSubjects>() {
                    @Override
                    public int compare(TaughtSubjects o1, TaughtSubjects o2) {
                        return o1.getCourseName().compareTo(o2.getCourseName());
                    }
                });

                tv_course_item.setText(courseData(item.getTaughtSubjects()));
//                courseData(item.getTaughtSubjects());

            }

            if (TextUtils.isEmpty(item.getCourseName())) {
                taugh_layout.setVisibility(View.GONE);
            } else {
                taugh_layout.setVisibility(View.VISIBLE);
                getCourseName(item.getCourseName());
            }

            if (item.getIdentityProof() != null && item.getIdentityProof().size() > 0) {

                List<String> tempString = new ArrayList<>();
                for (String proof : item.getIdentityProof()) {
                    if (!TextUtils.isEmpty(proof)) {
                        tempString.add(proof.substring(0, proof.indexOf("?t=")));
                    }
                }

                if (tempString != null && tempString.size() > 0) {
                    indentityAdapter.updateTeacherInfo(tempString);
                }
            }
            ExpriseData(item);
            if (!TextUtils.isEmpty(item.getIntroductoryVideoUrl())) {
                indentyString.clear();
                indentyString.add(item.getIntroductoryVideoUrl());
                videoAdapter.updateTeacherInfo(indentyString);

            }
//            videoImage(item.getIntroductoryVideoUrl());
            if (!TextUtils.isEmpty(state) && state.equals("1")) {
                ((TextView) findViewById(R.id.confirm_tv)).setText(getString(R.string.book_of_lessons, (int) item.getCoursePrice() + ""));
            } else {
                ((TextView) findViewById(R.id.confirm_tv)).setText(getString(R.string.book_lessons));
            }
        }
    }

    //获取taugh的科目
    private List<String> getTaughList(List<TaughtSubjects> subjects) {

        List<String> tmp = new ArrayList<>();

        for (TaughtSubjects subjects1 : subjects) {
            if (tmp.contains(subjects1.getCourseName())) {

            } else {

                tmp.add((TextUtils.isEmpty(subjects1.getMark())) ? subjects1.getCourseName() : (subjects1.getCourseName() + ":" + subjects1.getMark()));
            }
        }

        return tmp;

    }

    private void cancelFollow(final boolean follow) {

        if (follow) {
            addJob(NetmonitorManager.getCancelFollow(teacherId + "", new RestNewCallBack<BaseBean>() {
                @Override
                public void success(BaseBean baseBean) {
                    if (baseBean.isSuccess()) {
                        mIsFollow = !follow;
                        image_care.setImageResource(R.drawable.care_icon);
                        setResult(200);
                    }

                }

                @Override
                public void failure(RestError error) {

                }
            }));
        } else {
            addJob(NetmonitorManager.getFollowTeacher(teacherId + "", new RestNewCallBack<BaseBean>() {
                @Override
                public void success(BaseBean baseBean) {
                    if (baseBean.isSuccess()) {
                        mIsFollow = !follow;
                        image_care.setImageResource(R.drawable.care_follow);
                    }

                }

                @Override
                public void failure(RestError error) {

                }
            }));
        }

    }

    //详情
    private void initDetail() {
        pos(0);
    }

    private String courseData(List<TaughtSubjects> items) {


        List<TaughtSubjects> subjects = Utilts.removeList(items);

        StringBuffer buffer = new StringBuffer();
        for (TaughtSubjects item : subjects) {
            buffer.append((TextUtils.isEmpty(item.getMark())) ? item.getSubjectName() : (item.getSubjectName() + ": " + item.getMark())).append("\n");
        }

        return buffer.toString();

//        subject_layout.removeAllViews();
//        if (courseLinearLayout == null) {
//            courseLinearLayout = new CourseLinearLayout(this);
//        }
//        courseLinearLayout.updateCourseData(items);
//        subject_layout.addView(courseLinearLayout);


    }

    private ExpericenceLinearLayout expericenceLinearLayout;

    private void ExpriseData(TeacherProfileItem item) {
        experience_layout.removeAllViews();
        if (expericenceLinearLayout == null) {
            expericenceLinearLayout = new ExpericenceLinearLayout(this);
        }


        experience_layout.addView(expericenceLinearLayout);
    }

    private void pos(int position) {
        switch (position) {
            case 0:
                detail_layout.setVisibility(View.VISIBLE);
                identity_layout.setVisibility(View.GONE);
                video_layout.setVisibility(View.GONE);
                break;
            case 1:

                detail_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.VISIBLE);
                video_layout.setVisibility(View.GONE);

                break;
            case 2:
                detail_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.GONE);
                video_layout.setVisibility(View.VISIBLE);
                break;
        }

    }

    private List<String> indentyString = new ArrayList<>();

    //資質證明
    private void initIdentity() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        identity_recyclerview.setLayoutManager(manager);
        indentityAdapter = new IndentityAdapter(this, indentyString);
        identity_recyclerview.setAdapter(indentityAdapter);
        indentityAdapter.setSearchListener(new IndentityAdapter.SearchListener() {
            @Override
            public void click(int position) {
                if (indentyString != null && indentyString.size() > 0) {
                    callToVideo(indentyString.get(position));
                }
            }
        });
    }

    //視頻
    private void initVideo() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        video_recyclerview.setLayoutManager(manager);
        videoAdapter = new VideoAdapter(this, indentyString);
        video_recyclerview.setAdapter(videoAdapter);
        videoAdapter.setSearchListener(new VideoAdapter.SearchListener() {
            @Override
            public void click(int position) {
                if (indentyString != null && indentyString.size() > 0) {
                    if (!TextUtils.isEmpty(indentyString.get(position))) {
//                        callToVideo(indentyString.get(position));
                        Intent intent = new Intent(TeacherDetail1Activity.this, VideoActivity.class);
                        intent.putExtra("videoUrl", indentyString.get(position));
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void callToVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        image_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileItem != null) {

                    if (!TextUtils.isEmpty(state) && state.equals("1")) {
                        teachMessage();
                    } else {
                        ToastUtils.showToastShort(getString(R.string.been_approved));
                    }
                }
            }
        });

        detail_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.detail_radio:
                        pos(0);
                        break;
                    case R.id.identity_radio:
                        pos(1);
                        break;
                    case R.id.video_radio:
                        pos(2);
                        break;
                }
            }
        });


        image_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFollow(mIsFollow);
            }
        });


    }

    //跳转聊天界面
    private void teachMessage() {

        NimUIKit.startP2PSession(getActivity(), profileItem.getTeacherId() + "");

    }

    //课程
    private void courseZhiShi() {
        course_th_layout.removeAllViews();
        for (int i = 0; i < 6; i++) {
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.course_teacher_item, null);
            course_th_layout.addView(view);
        }


    }

    //获取视频封面
    private void videoImage(String url) {

        videoUrl = url;
        uiHandler.post(playRunnable);
//        circleImageView.setImageBitmap(bitmap);
    }

    Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
//                circleImageView.setImageBitmap(bitmap);
            }
        }
    };
    ;

    Runnable playRunnable = new Runnable() {

        @Override
        public void run() {
            Bitmap bitmap = Utilts.createVideoThumbnail(videoUrl, MediaStore.Images.Thumbnails.MINI_KIND);
            Message message = new Message();
            message.obj = bitmap;
            message.what = 1;
            uiHandler.sendMessage(message);
//            circleImageView.setImageBitmap(bitmap);
        }
    };

    private String videoUrl;

    Bitmap mBitmap;


}
