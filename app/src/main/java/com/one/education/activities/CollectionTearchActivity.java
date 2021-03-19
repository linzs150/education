package com.one.education.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.one.education.adapters.RegisterAdapter;
import com.one.education.beans.TeacherBean;
import com.one.education.commons.ToastUtils;
import com.one.education.education.R;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.widget.smartrefresh.layout.SmartRefreshLayout;
import com.one.education.widget.smartrefresh.layout.api.RefreshLayout;
import com.one.education.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.one.education.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;
import com.one.education.widget.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 23:14
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CollectionTearchActivity extends BaseActivity  {

    private List<TeacherBean.TeacherList> teacherLists = new ArrayList<>();
    private RegisterAdapter mAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private Context mCtx;
    private SmartRefreshLayout mSmartRefreshLayout;
    private boolean mIsRefresh = true;
    private boolean mIsLoadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_teacher_layout);
        mCtx = this;
        initView();
        initData();
        collectionTeacher();
        setListener();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        //        tv_inputPrice = findViewById(R.id.tv_inputPrice);
        //        input_layout = findViewById(R.id.input_layout);
        tvTitle.setText(mCtx.getString(R.string.favourite_tutors));
        mSmartRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(mCtx));
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(mCtx)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColorId(R.color.color_999999));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadMore = true;
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefresh = true;
//        loadData(1, 0);
                collectionTeacher();
            }
        });
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableRefresh(true);
    }

//    @Override
//    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        mIsRefresh = true;
////        loadData(1, 0);
//        collectionTeacher();
//    }
//
//    @Override
//    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        mIsLoadMore = true;
////        if (mDatas.size() % 50 > 0) {
////            //没有更多数据可加载
////            mSmartRefreshLayout.finishLoadMore();
////        } else {
////            loadData(mDatas.size() / 50 + 1, mDatas.size());
////        }
//    }

    private void initData() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(manager);

        //        teacherLists.add(new TeacherBean.TeacherList());
        //        teacherLists.add(new TeacherBean.TeacherList());
        //        teacherLists.add(new TeacherBean.TeacherList());
        //        teacherLists.add(new TeacherBean.TeacherList());

        mAdapter = new RegisterAdapter(this, teacherLists);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setSearchListener(new RegisterAdapter.SearchListener() {
            @Override
            public void click(int position, TeacherBean.TeacherList teacherList) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(mCtx, CourseWareActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        break;
                    case 2:
//                        Intent intent = new Intent(mCtx, TeacherDetail1Activity.class);
//                        intent.putExtra("teacher", teacherList);
                        startActivityForResult(TeacherDetail1Activity.newIntent(mCtx, teacherList.getTeacherId()), 1000);
//                        startActivity(intent);
                        break;
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 200) {
            collectionTeacher();
        }
    }

    private void collectionTeacher() {
        addJob(NetmonitorManager.getMyFollowTeacherList(new RestNewCallBack<TeacherBean>() {
            @Override
            public void success(TeacherBean teacherBean) {
                if (teacherBean.isSuccess()) {
                    mSmartRefreshLayout.finishRefresh();
                    mIsRefresh = false;
                    teacherLists.clear();
                    List<TeacherBean.TeacherList> teachers = new ArrayList<TeacherBean.TeacherList>(teacherBean.getData());
                    teacherLists.addAll(teachers);
                    mAdapter.updateTeacherInfo(teacherLists);
                }
            }

            @Override
            public void failure(RestError error) {
                mSmartRefreshLayout.finishRefresh();
                mIsRefresh = false;
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private void setListener() {

        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
