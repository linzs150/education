package com.one.education.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.one.education.adapters.QuestionAdapter;
import com.one.education.beans.GetFaqResponse;
import com.one.education.beans.QuestRequest;
import com.one.education.beans.QuestionResponse;
import com.one.education.commons.LogUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.education.R;
import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.widget.PullRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/30 11:41
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class QuestionActivity extends BaseActivity {

    private PullRecyclerView pullRecyclerView;
    private RecyclerView recycler_view;
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private List<QuestionResponse.Question> questionList = new ArrayList<>();
    private QuestionAdapter mAdapter;
    private QuestionResponse questionResp;
    //    private List<GetFaqResponse.GetFaq> getFaqResponse;
    private Map<Integer, GetFaqResponse.GetFaq> getFaqResponse = new HashMap<>();
    private Map<Integer, Boolean> maps = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        pullRecyclerView = findViewById(R.id.pull_recycler_view);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        recycler_view = findViewById(R.id.recycler_view);
        tvTitle.setText(getString(R.string.faqs));
        pullRecyclerView.setPullToRefresh(true);
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recycler_view.setLayoutManager(manager);
        mAdapter = new QuestionAdapter(this, questionList);
        recycler_view.setAdapter(mAdapter);
        pullRecyclerView.notifyDataSetChanged();


        getFaqList();
    }

    private void getFaqList() {
        QuestRequest request = new QuestRequest();
        request.setPageNO(1);
        request.setPageSize(200);
        String spLanguage = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spCountry) && TextUtils.isEmpty(spCountry)) {
            request.setCategoryId(28);
        } else {
            if (spLanguage.equals("zh")) {
                if (spCountry.equals("CN")) {
                    request.setCategoryId(28);
                } else if (spCountry.equals("HK")) {
                    request.setCategoryId(36);
                }
            } else if (spLanguage.equals("en")) {
                request.setCategoryId(29);
            }
        }
        showProgress();
        addJob(NetmonitorManager.getArticleList(request, new RestNewCallBack<QuestionResponse>() {
            @Override
            public void success(QuestionResponse questionResponse) {
                closeProgress();
                pullRecyclerView.finishLoad(true);
                if (questionResponse.isSuccess()) {
                    maps.clear();

                    questionResp = questionResponse;

                    for (int i = 0; i < questionResponse.getData().size(); i++) {
                        Map<Integer, Boolean> map = new TreeMap<Integer, Boolean>();
                        maps.put(i, false);
                    }

                    //                    getFaqResponse = new ArrayList<GetFaqResponse.GetFaq>(questionResponse.getData().size());
                    LogUtils.e("ceshi", JSONArray.toJSONString(questionResponse.getData()));
                    updateQuest(questionResponse);
                } else {
                    ToastUtils.showToastShort(questionResponse.getDescript());
                }
            }

            @Override
            public void failure(RestError error) {
                closeProgress();
                ToastUtils.showToastShort(error.msg);

            }
        }));
    }

    private void updateQuest(QuestionResponse response) {
        if (response != null) {
            if (response.getData() != null && response.getData().size() > 0) {
                mAdapter.updateQuestionInfo(response.getData());
                mAdapter.setMap(maps);
                pullRecyclerView.notifyDataSetChanged();
            }
        }
    }

    private void setListener() {

        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullRecyclerView.setOnPullListener(new PullRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView recyclerView) {
                getFaqList();

            }

            @Override
            public void onLoadMore(RecyclerView recyclerView) {

            }
        });

        mAdapter.setQuestionListener(new QuestionAdapter.SearchListener() {
            @Override
            public void click(int position) {

                if (mAdapter.getMap() != null && mAdapter.getMap().size() > 0) {
                    if (mAdapter.getMap().get(position)) {
                        mAdapter.getMap().put(position, false);
                    } else {
                        mAdapter.getMap().put(position, true);
                    }
                }

                if (QuestionActivity.this.getFaqResponse != null && QuestionActivity.this.getFaqResponse.size() > 0
                        && QuestionActivity.this.getFaqResponse.get(position) != null) {
                    mAdapter.getFag(position, mAdapter.getMap().get(position), getFaqResponse.get(position));
                    //                    mAdapter.

                } else {
                    if (questionResp != null && questionResp.getData() != null) {
                        getFaq(position, mAdapter.getMap().get(position), questionResp.getData().get(position).getId());
                    }
                }


            }
        });

    }


    private void getFaq(final int pos, final boolean flag, long id) {
        showProgress();
        addJob(NetmonitorManager.getArticle(id, new RestNewCallBack<GetFaqResponse>() {
            @Override
            public void success(GetFaqResponse getFaqResponse) {

                closeProgress();
                QuestionActivity.this.getFaqResponse.put(pos, getFaqResponse.getData());

                mAdapter.getFag(pos, flag, getFaqResponse.getData());

            }

            @Override
            public void failure(RestError error) {
                closeProgress();
            }
        }));
    }
}
