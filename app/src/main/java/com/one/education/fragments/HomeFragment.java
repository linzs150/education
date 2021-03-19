package com.one.education.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.one.education.EducationAppliction;
import com.one.education.activities.BaseFragment;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.OnItemClickListener;
import com.one.education.adapter.ViewHolder;
import com.one.education.classappointment.MyDividerItemDecoration;
import com.one.education.commons.Constants;
import com.one.education.commons.Utils;
import com.one.education.display.DisplayImageOptionsCreator;
import com.one.education.display.MyImageLoader;
import com.one.education.education.MainActivity;
import com.one.education.education.R;
import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetArticleListRsp;
import com.one.education.retrofit.model.GetBySpaceRsp;
import com.one.education.retrofit.model.GetStudentStudyCourseList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.user.UserInstance;
import com.one.education.utils.FileUri;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.TimeUtils;
import com.one.education.widget.smartrefresh.layout.SmartRefreshLayout;
import com.one.education.widget.smartrefresh.layout.api.RefreshLayout;
import com.one.education.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;
import com.one.education.widget.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.one.education.utils.TimeUtils.DEFAULT_TIME_FORMA2;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/23 23:10
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class HomeFragment extends BaseFragment implements OnRefreshListener {

    private static final int HANDLER_FRESH_FINISH = 1;
    private static final int HANDLER_LOAD_RECENT_CLASS = 2;
    private View mView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private XBanner mXBanner;
    private MyAdapter mMyAdapter;


    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_FRESH_FINISH) {
                mSmartRefreshLayout.finishRefresh();
            } else if (msg.what == HANDLER_LOAD_RECENT_CLASS) {
                List<GetStudentStudyCourseList.StudentStudyCourse> studentStudyCourses = (List<GetStudentStudyCourseList.StudentStudyCourse>) msg.obj;
                if (studentStudyCourses.isEmpty()) {
                    mView.findViewById(R.id.no_class_tip).setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.recent_class).setVisibility(View.INVISIBLE);
                } else {
                    GetStudentStudyCourseList.StudentStudyCourse item = studentStudyCourses.get(0);
                    mView.findViewById(R.id.no_class_tip).setVisibility(View.INVISIBLE);
                    mView.findViewById(R.id.recent_class).setVisibility(View.VISIBLE);
                    ImageView icon = mView.findViewById(R.id.icon);
                    TextView subjectTv = mView.findViewById(R.id.subject_tv);
                    TextView nameTv = mView.findViewById(R.id.name_tv);
                    TextView dateWeekTv = mView.findViewById(R.id.date_week_tv);
                    TextView startTimeTv = mView.findViewById(R.id.start_time_tv);
                    TextView endTimeTv = mView.findViewById(R.id.end_time_tv);
                    TextView classTv = mView.findViewById(R.id.class_tv);
                    subjectTv.setText(item.getCourseName());
                    nameTv.setText(String.format(Locale.getDefault(), "%s%s", mCtx.getString(R.string.teacher), item.getTeacherName()));

                    String startTime = TimeUtils.GetTime(item.getBeginTime() * 1000, DEFAULT_TIME_FORMA2);
                    String endTime = TimeUtils.GetTime(item.getEndTime() * 1000, DEFAULT_TIME_FORMA2);
                    String[] startTimeArray = startTime.split(" ");
                    String[] endTimeArray = endTime.split(" ");
                    dateWeekTv.setText(String.format(Locale.getDefault(), "%s %s", startTimeArray[0], TimeUtils.getWeek(mCtx, item.getBeginTime() * 1000)));
                    startTimeTv.setText(mCtx.getString(R.string.start_time_format, startTimeArray.length == 2 ? startTimeArray[1] : ""));
                    endTimeTv.setText(mCtx.getString(R.string.end_time_format, endTimeArray.length == 2 ? endTimeArray[1] : ""));
                    classTv.setText(String.valueOf(item.getCourseDuration() / 60));
                    ImageLoader.loadImageCircle(mCtx, item.getTeacherUserPicUrl(), icon);
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    private Context mCtx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_fragment, container, false);
        mCtx = getActivity();
        mSmartRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(mCtx)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColorId(R.color.color_999999));

        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableRefresh(true);
        mView.findViewById(R.id.appointment_tv).setOnClickListener(v -> ((MainActivity) mCtx).changeFragment(1));

        ((ScrollView) mView.findViewById(R.id.scrollView)).fullScroll(View.FOCUS_UP);
        mXBanner = mView.findViewById(R.id.xbanner);
        mView.findViewById(R.id.info_layout).setOnClickListener(v -> ((MainActivity) mCtx).changeFragment(2));

        RecyclerView recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));

        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(mCtx, MyDividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(mCtx, R.drawable.divider_item_decoration_tanslate_shape));
        recyclerView.addItemDecoration(dividerItemDecoration);
        mMyAdapter = new MyAdapter(null);
        recyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(mOnItemClickListener);
        initBanner();
        loadBanner();
        loadRecentClass();
        loadArticleList();
        return mView;
    }

    /**
     * 初始化XBanner
     */
    private void initBanner() {
        //设置广告图片点击事件
        mXBanner.setOnItemClickListener((banner, model, view, position) -> {
            GetBySpaceRsp.Content.Advertising listBean = ((GetBySpaceRsp.Content.Advertising) model);
            startActivity(WebActivity.newIntent(mCtx, listBean.getLinkUrl()));
        });
        //加载广告图片
        mXBanner.loadImage((banner, model, view, position) -> {
            GetBySpaceRsp.Content.Advertising listBean = ((GetBySpaceRsp.Content.Advertising) model);
            MyImageLoader.getInstance().displayImageByUrl(listBean.getXBannerUrl(), (ImageView) view, DisplayImageOptionsCreator.createUserDisplayImageOptionsBuilder2().build());
        });
    }

    private void loadBanner() {
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetBySpaceRsp getBySpaceRsp = HttpsServiceFactory.getBySpace(Constants.BANNER_CODE_CN);
            mHandler.post(() -> {
                if (!isAdded()) {
                    return;
                }

                mHandler.removeMessages(HANDLER_FRESH_FINISH);
                mSmartRefreshLayout.finishRefresh();
                if (getBySpaceRsp.getStatus() == 1) {
                    GetBySpaceRsp.Content content = getBySpaceRsp.getData();
                    if (content.getTerminalType() != 1 || content.getSpaceState() != 1) {
                        return;
                    }

                    List<GetBySpaceRsp.Content.Advertising> advertisings = content.getContents();
                    List<GetBySpaceRsp.Content.Advertising> temps = content.getContents();
                    int limitCount = content.getLimitCount();
                    if (advertisings.size() > limitCount) {
                        temps.addAll(advertisings.subList(0, limitCount - 1));
                    }

                    mXBanner.setAutoPlayAble(!advertisings.isEmpty());
                    mXBanner.setBannerData(content.getContents());
                }
            });
        });
    }

    private void loadRecentClass() {
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            if (!isAdded()) {
                return;
            }

            GetStudentStudyCourseList getStudentStudyCourseList = HttpsServiceFactory.getStudentStudyCourseList(UserInstance.getInstance().getUserId(), 51, 1, 1, 0);
            if (getStudentStudyCourseList.getStatus() != 1) {
                return;
            }

            Message message = new Message();
            message.what = HANDLER_LOAD_RECENT_CLASS;
            message.obj = getStudentStudyCourseList.getData();
            mHandler.sendMessage(message);
        });
    }

    private void loadArticleList() {
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetArticleListRsp getArticleListRsp = HttpsServiceFactory.getArticleList();
            mHandler.post(() -> {
                if (!isAdded()) {
                    return;
                }

                mHandler.removeMessages(HANDLER_FRESH_FINISH);
                mSmartRefreshLayout.finishRefresh();
                if (getArticleListRsp.getStatus() == 1) {
                    List<GetArticleListRsp.ArticleList> articleLists = getArticleListRsp.getData();
                    Collections.sort(articleLists, (o1, o2) -> {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }

                        String key1 = o1.getKeywords();
                        String key2 = o2.getKeywords();
                        if (key1 == null) {
                            return 1;
                        }

                        if (key2 == null) {
                            return -1;
                        }

                        return key1.compareTo(key2);
                    });

                    String language = SpUtil.getString(ConstantGlobal.LOCALE_LANGUAGE);
                    String country = SpUtil.getString(ConstantGlobal.LOCALE_COUNTRY);
                    ArrayList<GetArticleListRsp.ArticleList> arrayList = new ArrayList<>();
                    if (language.equals("en")) {
                        for (GetArticleListRsp.ArticleList articleList : articleLists) {
                            if (articleList.getCategoryId() == 32) {
                                arrayList.add(articleList);
                            }
                        }
                    } else if (language.equals("zh")) {
                        if (country.equals("HK")) {
                            for (GetArticleListRsp.ArticleList articleList : articleLists) {
                                if (articleList.getCategoryId() == 35) {
                                    arrayList.add(articleList);
                                }
                            }
                        } else {
                            for (GetArticleListRsp.ArticleList articleList : articleLists) {
                                if (articleList.getCategoryId() == 34) {
                                    arrayList.add(articleList);
                                }
                            }
                        }
                    } else {
                        Pair<String, String> pair = Utils.getLanguage();
                        if (pair.first.equals("en")) {
                            for (GetArticleListRsp.ArticleList articleList : articleLists) {
                                if (articleList.getCategoryId() == 32) {
                                    arrayList.add(articleList);
                                }
                            }
                        } else if (pair.first.equals("zh")) {
                            country = pair.second;
                            if (country.equals("HK")) {
                                for (GetArticleListRsp.ArticleList articleList : articleLists) {
                                    if (articleList.getCategoryId() == 35) {
                                        arrayList.add(articleList);
                                    }
                                }
                            } else {
                                for (GetArticleListRsp.ArticleList articleList : articleLists) {
                                    if (articleList.getCategoryId() == 34) {
                                        arrayList.add(articleList);
                                    }
                                }
                            }
                        }
                    }

                    mMyAdapter.setDataList(arrayList);
                    mMyAdapter.notifyDataSetChanged();
                }
            });
        });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mHandler.hasMessages(HANDLER_FRESH_FINISH)) {
            return;
        }

        loadBanner();
        loadRecentClass();
        loadArticleList();
        mHandler.sendEmptyMessageDelayed(HANDLER_FRESH_FINISH, 10000);
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    private static class MyAdapter extends BaseRecyclerViewAdapter<GetArticleListRsp.ArticleList> {
        public MyAdapter(List<GetArticleListRsp.ArticleList> dataList) {
            super(dataList);
            setMultiTypeDelegate(data -> R.layout.waijiao_fengcai_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, GetArticleListRsp.ArticleList item, int position) {
            ImageView img = holder.getView(R.id.img_iv);
            TextView title = holder.getView(R.id.title);
            TextView subTitle = holder.getView(R.id.subtitle);
            title.setText(item.getTitle());
            subTitle.setText(item.getSummary());
            MyImageLoader.getInstance().displayImageByUrl(item.getPicUrl(), img, DisplayImageOptionsCreator.createLeftRightTopImageOptionsBuilder().build());
        }
    }

    private final OnItemClickListener<GetArticleListRsp.ArticleList> mOnItemClickListener = (adapter, view, position) -> {
        GetArticleListRsp.ArticleList articleList = adapter.getItem(position);
        startActivity(ForeignTeachersActivity.newIntent(mCtx, articleList.getId()));
    };
}
