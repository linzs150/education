package com.one.education.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.common.media.imagepicker.loader.GlideImageLoader;
import com.one.education.EducationAppliction;
import com.one.education.activities.BaseFragment;
import com.one.education.activities.TeacherDetail1Activity;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.MultiTypeDelegate;
import com.one.education.adapter.OnItemClickListener;
import com.one.education.adapter.ViewHolder;
import com.one.education.beans.TeacherBean;
import com.one.education.classappointment.TeacherSearchActivity;
import com.one.education.commons.AppUtils;
import com.one.education.display.DisplayImageOptionsCreator;
import com.one.education.display.MyImageLoader;
import com.one.education.education.EducationUtils;
import com.one.education.education.MainActivity;
import com.one.education.education.R;
import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetTeacherListRsp;
import com.one.education.retrofit.model.Subject;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.FileUri;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.Utilts;
import com.one.education.widget.ProgressDialog;
import com.one.education.widget.smartrefresh.layout.SmartRefreshLayout;
import com.one.education.widget.smartrefresh.layout.api.RefreshLayout;
import com.one.education.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.one.education.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;
import com.one.education.widget.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author laiyongyang
 * @date 2020-05-02
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ClassAppointment extends BaseFragment implements OnRefreshLoadMoreListener {

    private static final String TAG = "ClassAppointment";
    private static final int HANDLER_FRESH_FINISH = 0x1001;
    private static final int HANDLER_LOAD_FINISH = 0x1002;
    private View mView;
    private TextView mNoLimitTv;
    private TextView mALevelTv;
    private TextView mIbTv;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ProgressDialog mProgressDialog;
    private MyAdapter mMyAdapter;
    private int mCourseId = 0;
    private Map<Integer, List<GetTeacherListRsp.UserTeacherInfo>> mDatas = new HashMap<>();
    private boolean mIsRefresh;
    private boolean mIsLoadMore;
    private Context mCtx;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_FRESH_FINISH) {
                mSmartRefreshLayout.finishRefresh();
                mIsRefresh = false;
                int status = msg.arg1;
                int course = msg.arg2;
                if (status == 1) {
                    List<GetTeacherListRsp.UserTeacherInfo> dataList = (List<GetTeacherListRsp.UserTeacherInfo>) msg.obj;
                    if (dataList == null) {
                        return;
                    }


                    List<GetTeacherListRsp.UserTeacherInfo> userTeacherInfos = mDatas.get(course);
                    if (userTeacherInfos == null) {
                        userTeacherInfos = new ArrayList<>();
                    } else {
                        userTeacherInfos.clear();
                    }

                    userTeacherInfos.addAll(dataList);
                    mDatas.put(mCourseId, userTeacherInfos);
                    updateView();
                }

                if (course == mCourseId) {
                    mProgressDialog.hide();
                }
            } else if (msg.what == HANDLER_LOAD_FINISH) {
                mSmartRefreshLayout.finishLoadMore();
                mIsLoadMore = false;
                int status = msg.arg1;
                int course = msg.arg2;
                if (status == 1) {
                    List<GetTeacherListRsp.UserTeacherInfo> dataList = (List<GetTeacherListRsp.UserTeacherInfo>) msg.obj;
                    if (dataList == null) {
                        return;
                    }

                    List<GetTeacherListRsp.UserTeacherInfo> teacherDatas = mDatas.get(course);
                    if (teacherDatas == null) {
                        teacherDatas = new ArrayList<>();
                    }

                    teacherDatas.addAll(dataList);
                    mDatas.put(mCourseId, teacherDatas);
                    updateView();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.class_appointment_fragment, container, false);
        mCtx = getActivity();
        mView.findViewById(R.id.search_tv).setOnClickListener(mOnClickListener);
        mProgressDialog = mView.findViewById(R.id.progress_dialog);

        RecyclerView recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        recyclerView.setHasFixedSize(true);
        mMyAdapter = new MyAdapter();
        mMyAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(mMyAdapter);

        mSmartRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(mCtx)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColorId(R.color.color_999999));

        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(mCtx));

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
        mNoLimitTv = mView.findViewById(R.id.no_limit_tv);
        mNoLimitTv.setOnClickListener(mOnClickListener);
        mALevelTv = mView.findViewById(R.id.a_level_tv);
        mALevelTv.setOnClickListener(mOnClickListener);
        mIbTv = mView.findViewById(R.id.ib_tv);
        mIbTv.setOnClickListener(mOnClickListener);
        mIsRefresh = true;
        loadData(1, 0, mCourseId);
        updateSelectCourseView(0);
        return mView;
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    private void updateView() {
        List<GetTeacherListRsp.UserTeacherInfo> teacherDataList = mDatas.get(mCourseId);
        mMyAdapter.setDataList(teacherDataList);
        mMyAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.no_limit_tv) {
                updateSelectCourseView(0);
                List<GetTeacherListRsp.UserTeacherInfo> userTeacherInfos = mDatas.get(mCourseId);
                if (userTeacherInfos == null || userTeacherInfos.isEmpty()) {
                    mProgressDialog.show();
                }

                mIsRefresh = true;
                loadData(1, 0, mCourseId);
            } else if (v.getId() == R.id.a_level_tv) {
                updateSelectCourseView(1);
                List<GetTeacherListRsp.UserTeacherInfo> userTeacherInfos = mDatas.get(mCourseId);
                if (userTeacherInfos == null || userTeacherInfos.isEmpty()) {
                    mProgressDialog.show();
                }

                mIsRefresh = true;
                loadData(1, 0, mCourseId);
            } else if (v.getId() == R.id.ib_tv) {
                updateSelectCourseView(2);
                List<GetTeacherListRsp.UserTeacherInfo> userTeacherInfos = mDatas.get(mCourseId);
                if (userTeacherInfos == null || userTeacherInfos.isEmpty()) {
                    mProgressDialog.show();
                }

                mIsRefresh = true;
                loadData(1, 0, mCourseId);
            } else if (R.id.search_tv == v.getId()) {
                startActivity(TeacherSearchActivity.newIntent(mCtx));
            }
        }
    };

    private OnItemClickListener<GetTeacherListRsp.UserTeacherInfo> mOnItemClickListener = (adapter, view, position) -> {
        GetTeacherListRsp.UserTeacherInfo userTeacherInfo = adapter.getItem(position);
        TeacherBean.TeacherList teacherList = new TeacherBean.TeacherList();
        teacherList.setCommentStar(userTeacherInfo.getCommentStar());
        teacherList.setCourseId(userTeacherInfo.getCourse());
        teacherList.setCourseName(userTeacherInfo.getCourseName());
        teacherList.setEducation(userTeacherInfo.getEducation());
        teacherList.setIsFollow(userTeacherInfo.getIsFollow());
        teacherList.setLanguageList(userTeacherInfo.getLanguageList());
        teacherList.setTeacherId(userTeacherInfo.getTeacherId());
        teacherList.setTeacherName(userTeacherInfo.getTeacherName());
        teacherList.setTeacherPhotoUrl(userTeacherInfo.getTeacherPhotoUrl());
        teacherList.setTeachingExperience(userTeacherInfo.getTeachingExperience());

        MainActivity mainActivity = (MainActivity) mCtx;
        mainActivity.startActivity(TeacherDetail1Activity.newIntent(mCtx, teacherList.getTeacherId()));
    };

    private void updateSelectCourseView(int courseId) {
        mCourseId = courseId;
        mNoLimitTv.setTextColor(0 == mCourseId ? getResources().getColor(R.color.color_a9c4b1) : getResources().getColor(R.color.color_999999));
        mALevelTv.setTextColor(1 == mCourseId ? getResources().getColor(R.color.color_a9c4b1) : getResources().getColor(R.color.color_999999));
        mIbTv.setTextColor(2 == mCourseId ? getResources().getColor(R.color.color_a9c4b1) : getResources().getColor(R.color.color_999999));
        updateView();
    }

    private void loadData(int pageNO, int totalCount, int courseId) {
        if (mIsRefresh) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new RefreshGetTeacherListTask(pageNO, totalCount, courseId));
        } else if (mIsLoadMore) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new LoadGetTeacherListTask(pageNO, totalCount, courseId));
        }
    }

    private class RefreshGetTeacherListTask implements Runnable {
        private int pageNO;
        private int totalCount;
        private int courseId;

        public RefreshGetTeacherListTask(int pageNO, int totalCount, int courseId) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
            this.courseId = courseId;
        }

        @Override
        public void run() {
            GetTeacherListRsp getTeacherListRsp = HttpsServiceFactory.getTeacherList(pageNO, 50, totalCount, courseId, -1);
            int status = getTeacherListRsp.getStatus();
            String desc = getTeacherListRsp.getDescript();
            Log.i(TAG, "RefreshGetTeacherListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_FRESH_FINISH;
            message.arg1 = status;
            message.arg2 = courseId;
            message.obj = getTeacherListRsp.getDatas();
            mHandler.sendMessage(message);
        }
    }

    private class LoadGetTeacherListTask implements Runnable {
        private int pageNO;
        private int totalCount;
        private int courseId;

        public LoadGetTeacherListTask(int pageNO, int totalCount, int courseId) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
            this.courseId = courseId;
        }

        @Override
        public void run() {
            GetTeacherListRsp getTeacherListRsp = HttpsServiceFactory.getTeacherList(pageNO, 50, totalCount, courseId, -1);
            int status = getTeacherListRsp.getStatus();
            String desc = getTeacherListRsp.getDescript();
            Log.i(TAG, "LoadGetTeacherListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_LOAD_FINISH;
            message.arg1 = status;
            message.arg2 = courseId;
            message.obj = getTeacherListRsp.getDatas();
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        loadData(1, 0, mCourseId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mIsLoadMore = true;
        List<GetTeacherListRsp.UserTeacherInfo> userTeacherInfos = mDatas.get(mCourseId);
        if (userTeacherInfos == null) {
            loadData(1, 0, mCourseId);
        } else {
            if (userTeacherInfos.size() % 50 > 0) {
                //没有更多数据可加载
                mSmartRefreshLayout.finishLoadMore();
            } else {
                loadData(userTeacherInfos.size() / 50 + 1, userTeacherInfos.size(), mCourseId);
            }
        }
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<GetTeacherListRsp.UserTeacherInfo> {
        public MyAdapter() {
            setMultiTypeDelegate(data -> R.layout.class_appointment_fragment_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, GetTeacherListRsp.UserTeacherInfo item, int position) {
            ImageView icon = holder.getView(R.id.icon);
            TextView nameTv = holder.getView(R.id.teacher_name_tv);
            TextView schoolTv = holder.getView(R.id.school_tv);
            TextView degreeTv = holder.getView(R.id.degree_tv);
            TextView classTv = holder.getView(R.id.class_tv);
            TextView classAgeTv = holder.getView(R.id.class_age_tv);
            TextView levelTv = holder.getView(R.id.level_tv);
            RatingBar score_rating_bar = holder.getView(R.id.score_rating_bar);
            nameTv.setText(item.getTeacherName());
            schoolTv.setText(getString(R.string.graduated_school_format, getNewString(item.getUniversitys())));
            degreeTv.setText(getString(R.string.degree_format, getNewString(item.getColleges())));
            String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
            String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
            if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            } else {
                if (spLanguage.equals("en")) {
                    if (item.getTeachingExperience() == 0 || item.getTeachingExperience() == 1) {

//                        th_age.setText(getString(R.string.teaching_experience_single, item.getTeachingExperience()));
                        classAgeTv.setText("Teaching: " + item.getTeachingExperience() + " year");
                    } else {
                        classAgeTv.setText(getString(R.string.teaching_experience, item.getTeachingExperience()));
                    }
                } else {
                    classAgeTv.setText(getString(R.string.teaching_experience, item.getTeachingExperience()));
                }
            }
//            classAgeTv.setText(getString(R.string.class_age_format, item.getTeachingExperience()));
            score_rating_bar.setRating(Utilts.ratingJS(item.getCommentStar()));
            levelTv.setText(AppUtils.getTeacherLevelText(mContext, item.getTeacherLevel()));
            List<Subject> subjects = Utilts.removeSubjectList(item.getSubjects());
            StringBuilder str = new StringBuilder();
            if (subjects != null) {
                int size = subjects.size();
                int index = 0;
                for (Subject subject : subjects) {
                    str.append(subject.getSubjectName());
                    if (index != size - 1) {
                        str.append(", ");
                    }

                    index++;
                }
            }

            classTv.setText(getString(R.string.class_format, str.toString()));
            ImageLoader.loadAdImage(item.getTeacherPhotoUrl(), icon);
        }
    }

    private String getNewString(String str) {
        StringBuffer buffer = new StringBuffer();

        String[] strings = str.split(",");
        int index = 0;
        int size = strings.length;
        for (String s : strings) {
            buffer.append(s);
            if (index != size - 1) {
                buffer.append(", ");
            }

            index++;
        }


        return buffer.toString();
    }
}