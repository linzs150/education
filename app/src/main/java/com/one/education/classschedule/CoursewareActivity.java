package com.one.education.classschedule;

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
import android.view.View;
import android.widget.TextView;

import com.one.education.activities.BaseActivity;
import com.one.education.activities.IntentEx;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.MultiTypeDelegate;
import com.one.education.adapter.OnItemClickListener;
import com.one.education.adapter.ViewHolder;
import com.one.education.commons.ToastUtils;
import com.one.education.download.Download;
import com.one.education.download.DownloadInfo;
import com.one.education.download.IDownloadListener;
import com.one.education.education.R;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetCoursewareList;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.FileUri;
import com.one.education.utils.TimeUtils;
import com.one.education.utils.Utilts;
import com.one.education.widget.MenuDialog;
import com.one.education.widget.MenuOption;
import com.one.education.widget.ProgressDialog;
import com.one.education.widget.smartrefresh.layout.SmartRefreshLayout;
import com.one.education.widget.smartrefresh.layout.api.RefreshLayout;
import com.one.education.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.one.education.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;
import com.one.education.widget.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.one.education.utils.TimeUtils.DEFAULT_TIME_FORMA2;

/**
 * @author laiyongyang
 * @date 2020-05-19
 * @desc 课件资料
 * @email fzhlaiyy@intretech.com
 */
public class CoursewareActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    public static IntentEx newIntentEx(Context context, GetStudentStudyCourseList.StudentStudyCourse studentStudyCourse) {
        IntentEx intentEx = new IntentEx(context, CoursewareActivity.class);
        intentEx.putExtraEx(INTENT_DATA, studentStudyCourse);
        return intentEx;
    }

    private static final String INTENT_DATA = "StudentStudyCourse";
    private static final String TAG = "CoursewareActivity";
    private static final int HANDLER_FRESH_FINISH = 0x1001;
    private static final int HANDLER_LOAD_FINISH = 0x1002;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ProgressDialog mProgressDialog;
    private MyAdapter mMyAdapter;
    private MenuDialog mMenuDialog;
    private GetStudentStudyCourseList.StudentStudyCourse mStudentStudyCourse;
    private final List<GetCoursewareList.CoursewareInfo> mCoursewareInfoList = new ArrayList<>();
    private boolean mIsRefresh = true;
    private boolean mIsLoadMore;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_FRESH_FINISH) {
                mSmartRefreshLayout.finishRefresh();
                mIsRefresh = false;
                int status = msg.arg1;
                List<GetCoursewareList.CoursewareInfo> dataList = (List<GetCoursewareList.CoursewareInfo>) msg.obj;
                if (dataList == null) {
                    return;
                }

                if (status == 1) {
                    mCoursewareInfoList.clear();
                    mCoursewareInfoList.addAll(dataList);
                    updateView();
                }

                mProgressDialog.hide();
            } else if (msg.what == HANDLER_LOAD_FINISH) {
                mSmartRefreshLayout.finishLoadMore();
                mIsLoadMore = false;
                int status = msg.arg1;
                List<GetCoursewareList.CoursewareInfo> dataList = (List<GetCoursewareList.CoursewareInfo>) msg.obj;
                if (dataList == null) {
                    return;
                }

                if (status == 1) {
                    mCoursewareInfoList.addAll(dataList);
                    updateView();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseware);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mStudentStudyCourse = getIntentEx().getExtraEx(INTENT_DATA);
        mProgressDialog = findViewById(R.id.progress_dialog);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mMyAdapter = new MyAdapter();
        mMyAdapter.setOnItemClickListener(mOnItemClickListener);

        mMyAdapter.setDataList(mCoursewareInfoList);
        recyclerView.setAdapter(mMyAdapter);
        mSmartRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity())
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColorId(R.color.color_999999));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
        loadData(1, 0);
        mProgressDialog.show();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        loadData(1, 0);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mIsLoadMore = true;
        if (mCoursewareInfoList.size() % 50 > 0) {
            //没有更多数据可加载
            mSmartRefreshLayout.finishLoadMore();
        } else {
            loadData(mCoursewareInfoList.size() / 50 + 1, mCoursewareInfoList.size());
        }
    }


    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void updateView() {
        mMyAdapter.setDataList(mCoursewareInfoList);
        mMyAdapter.notifyDataSetChanged();
    }

    private void loadData(int pageNO, int totalCount) {
        if (mIsRefresh) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new RefreshGetCoursewareListTask(pageNO, totalCount,
                    mStudentStudyCourse.getStudentId(), mStudentStudyCourse.getTeacherId(), mStudentStudyCourse.getId()));
        } else if (mIsLoadMore) {
            ThreadPoolProxyFactory.getNormalThreadProxy().execute(new LoadGetCoursewareListTask(pageNO, totalCount,
                    mStudentStudyCourse.getStudentId(), mStudentStudyCourse.getTeacherId(), mStudentStudyCourse.getId()));
        }
    }

    private class RefreshGetCoursewareListTask implements Runnable {
        private int pageNO;
        private int totalCount;
        private int studentId;
        private int teacherId;
        private int courseId;

        public RefreshGetCoursewareListTask(int pageNO, int totalCount, int studentId, int teacherId, int courseId) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
            this.studentId = studentId;
            this.teacherId = teacherId;
            this.courseId = courseId;
        }

        @Override
        public void run() {
            String checkSum = String.format(Locale.getDefault(), "%d%d%d", studentId, teacherId, SystemClock.elapsedRealtime());
            checkSum = Utilts.GetMD5String("123456");
            GetCoursewareList getCoursewareList = HttpsServiceFactory.getCoursewareList(pageNO, 50, totalCount,
                    studentId, teacherId, courseId, checkSum);
            int status = getCoursewareList.getStatus();
            String desc = getCoursewareList.getDescript();
            Log.i(TAG, "RefreshGetCoursewareListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_FRESH_FINISH;
            message.arg1 = status;
            message.obj = getCoursewareList.getData();
            mHandler.sendMessage(message);
        }
    }

    private class LoadGetCoursewareListTask implements Runnable {
        private int pageNO;
        private int totalCount;
        private int studentId;
        private int teacherId;
        private int courseId;

        public LoadGetCoursewareListTask(int pageNO, int totalCount, int studentId, int teacherId, int courseId) {
            this.pageNO = pageNO;
            this.totalCount = totalCount;
            this.studentId = studentId;
            this.teacherId = teacherId;
            this.courseId = courseId;
        }

        @Override
        public void run() {
            String checkSum = String.format(Locale.getDefault(), "%d%d%d", studentId, teacherId, SystemClock.elapsedRealtime());
            checkSum = Utilts.GetMD5String(checkSum);
            GetCoursewareList getCoursewareList = HttpsServiceFactory.getCoursewareList(pageNO, 50, totalCount,
                    studentId, teacherId, courseId, checkSum);
            int status = getCoursewareList.getStatus();
            String desc = getCoursewareList.getDescript();
            Log.i(TAG, "LoadGetCoursewareListTask status:" + status + " desc:" + desc);
            Message message = new Message();
            message.what = HANDLER_LOAD_FINISH;
            message.arg1 = status;
            message.obj = getCoursewareList.getData();
            mHandler.sendMessage(message);
        }
    }

    private OnItemClickListener<GetCoursewareList.CoursewareInfo> mOnItemClickListener = new OnItemClickListener<GetCoursewareList.CoursewareInfo>() {
        @Override
        public void onItemClick(BaseRecyclerViewAdapter<GetCoursewareList.CoursewareInfo> adapter, View view, int position) {
            showMenuDialog(adapter.getItem(position).getCourseId(), adapter.getItem(position).getCoursewareUrl());
        }
    };

    private class MyAdapter extends BaseRecyclerViewAdapter<GetCoursewareList.CoursewareInfo> {
        public MyAdapter() {
            setMultiTypeDelegate(new MultiTypeDelegate<GetCoursewareList.CoursewareInfo>() {
                @Override
                public int getItemTypeLayoutRes(GetCoursewareList.CoursewareInfo data) {
                    return R.layout.courseware_information_item_layout;
                }
            });
        }

        @Override
        public void bindViewHolder(ViewHolder holder, GetCoursewareList.CoursewareInfo item, int position) {
            TextView nameTv = holder.getView(R.id.name_tv);
            TextView uploadTimeNameTv = holder.getView(R.id.upload_time_tv);
            TextView uploadNameTv = holder.getView(R.id.upload_name_tv);
            nameTv.setText(item.getCoursewareName());
            uploadTimeNameTv.setText(getString(R.string.upload_time) + TimeUtils.GetTime(item.getUploadTime() * 1000, DEFAULT_TIME_FORMA2));
            uploadNameTv.setText(getString(R.string.uploaded_by) + item.getUploadUser());
        }
    }

    /**
     * 显示更多多画框
     */
    private void showMenuDialog(final int courseId, final String url) {
        if (null != mMenuDialog && mMenuDialog.isShowing()) {
            return;
        }

        MenuOption.MenuOptionBuilder builder = MenuOption.MenuOptionBuilder.newBuilder();
        builder.addOptionItem(getString(R.string.download_open));
        builder.addSecondaryItem(getString(R.string.cancel));
        MenuDialog.IOnSelectedMenuListener listener = new MenuDialog.IOnSelectedMenuListener() {
            @Override
            public boolean onSelectedMenu(int position, int key) {
                switch (position) {
                    case 0: {
                        if (TextUtils.isEmpty(url)) {
                            ToastUtils.showToastLong(CoursewareActivity.this, "下载失败，链接错误");
                            return false;
                        }

                        download(courseId, url);
                    }
                    break;
                    default:
                }
                return false;
            }
        };

        mMenuDialog = new MenuDialog(CoursewareActivity.this, builder.build());
        mMenuDialog.setMenuListener(listener);
        mMenuDialog.show();
    }

    private void download(final int courserId, final String url) {
        IDownloadListener listener = new IDownloadListener() {
            @Override
            public void onStart(DownloadInfo info) {
            }

            @Override
            public void onProgress(DownloadInfo info, int progress) {
            }

            @Override
            public void onFinish(final DownloadInfo info, Download.Result result) {
                if (Download.Result.SUCCESS == result) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isFinishing()) {
                                return;
                            }

                            String localPath = info.getFilePath();
                            if (TextUtils.isEmpty(localPath)) {
                                ToastUtils.showToastLong("下载失败，请重新点击下载");
                                return;
                            }

                            startActivity(CoursewareReadViewActivity.newIntent(CoursewareActivity.this, localPath));
                        }
                    });
                } else {
                    Log.e(TAG, "download failure result =" + result.getName());
                    ToastUtils.showToastLong("下载失败，请重新点击下载");
                }
            }
        };

        String fileName = parseName(url);
        Download.getInstance().download(new DownloadInfo(url, FileUri.newCoursewareUri(courserId, fileName)), listener);
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf(File.separator) + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }
}
