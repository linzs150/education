package com.newtonacademic.newtontutors.classappointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.db.DBManager;
import com.newtonacademic.newtontutors.db.IDMLResultListener;
import com.newtonacademic.newtontutors.db.bean.DUserSearch;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.user.UserInstance;
import com.newtonacademic.newtontutors.widget.flowlayout.FlowLayout;
import com.newtonacademic.newtontutors.widget.flowlayout.TagAdapter;
import com.newtonacademic.newtontutors.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-05-08
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class TeacherSearchActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, TeacherSearchActivity.class);
    }

    private final static String TAG = "TeacherSearchActivity";
    private FlowLayoutAdapter mHistoryFlowLayoutAdapter = null;
    private FlowLayoutAdapter mDiscoverCategoryFlowLayoutAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search);
        findViewById(R.id.back_btn).setOnClickListener(mOnClickListener);
        findViewById(R.id.delete_iv).setOnClickListener(mOnClickListener);

        TagFlowLayout historyFlowLayout = findViewById(R.id.search_history_flow_layout);
        historyFlowLayout.setOnTagClickListener(mOnHistoryTagClickListener);
        mHistoryFlowLayoutAdapter = new FlowLayoutAdapter(null);
        historyFlowLayout.setAdapter(mHistoryFlowLayoutAdapter);

        TagFlowLayout discoverFlowLayout = findViewById(R.id.search_discover_flow_layout);
        discoverFlowLayout.setOnTagClickListener(mOnCategoryTagClickListener);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("测试" + i);
        }

        mDiscoverCategoryFlowLayoutAdapter = new FlowLayoutAdapter(datas);
        discoverFlowLayout.setAdapter(mDiscoverCategoryFlowLayoutAdapter);

        ((EditText) findViewById(R.id.search_edt)).setOnEditorActionListener(mOnEditorActionListener);
        updateView();
    }

    private void updateView() {
        DBManager.getInstance().getUserSearchDao().getUserSearchByUserId(UserInstance.getInstance().getUserId(), new IDMLResultListener<List<DUserSearch>>() {
            @Override
            public void onResult(List<DUserSearch> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isFinishing()) {
                            return;
                        }

                        List<String> messages = new ArrayList<>();
                        for (DUserSearch userSearch : result) {
                            messages.add(userSearch.getMessage());
                        }

                        mHistoryFlowLayoutAdapter.setTagDatas(messages);
                        mHistoryFlowLayoutAdapter.notifyDataChanged();
                        findViewById(R.id.search_history_layout).setVisibility(messages.isEmpty() ? View.GONE : View.VISIBLE);
                    }
                });
            }
        });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_btn) {
                finish();
            } else if (v.getId() == R.id.delete_iv) {
                int userId = UserInstance.getInstance().getUserId();
                showProgress(getString(R.string.deleting));
                DBManager.getInstance().getUserSearchDao().delete(userId, new IDMLResultListener<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinishing()) {
                                    return;
                                }

                                closeProgress();
                                updateView();
                            }
                        });
                    }
                });
            }
        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String message = v.getText().toString();
                DUserSearch dUserSearch = new DUserSearch();
                dUserSearch.setUserId(UserInstance.getInstance().getUserId());
                dUserSearch.setMessage(message);
                DBManager.getInstance().getUserSearchDao().save(dUserSearch, new IDMLResultListener<Long>() {
                    @Override
                    public void onResult(Long result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinishing()) {
                                    return;
                                }

                                updateView();
                            }
                        });
                    }
                });

                return true;
            }

            return false;
        }
    };

    /**
     * 点击搜索历史
     */
    private TagFlowLayout.OnTagClickListener mOnHistoryTagClickListener = new TagFlowLayout.OnTagClickListener() {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            if (position >= 0 && position < mHistoryFlowLayoutAdapter.getCount()) {
                String data = mHistoryFlowLayoutAdapter.getItem(position);

            }

            return false;
        }
    };

    /**
     * 点击发现搜索
     */
    private TagFlowLayout.OnTagClickListener mOnCategoryTagClickListener = new TagFlowLayout.OnTagClickListener() {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            if (position >= 0 && position < mDiscoverCategoryFlowLayoutAdapter.getCount()) {
                String data = mDiscoverCategoryFlowLayoutAdapter.getItem(position);
            }

            return false;
        }
    };


    /**
     * 区域适配器
     */
    private class FlowLayoutAdapter extends TagAdapter<String> {
        FlowLayoutAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String data) {
            View view = LayoutInflater.from(TeacherSearchActivity.this).inflate(R.layout.teacher_search_tag_layout, null, false);
            TextView textView = view.findViewById(R.id.content_tv);
            textView.setText(data);
            return view;
        }
    }

}
