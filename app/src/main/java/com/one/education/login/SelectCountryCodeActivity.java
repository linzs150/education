package com.one.education.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.EducationAppliction;
import com.one.education.activities.BaseActivity;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.OnItemClickListener;
import com.one.education.adapter.ViewHolder;
import com.one.education.commons.ToastUtils;
import com.one.education.education.R;
import com.one.education.language.SpUtil;
import com.one.education.retrofit.HttpsServiceFactory;
import com.one.education.retrofit.model.GetCountryList;
import com.one.education.thread.ThreadPoolProxyFactory;
import com.one.education.utils.ContactsUtils;
import com.one.education.utils.sort.CharacterParser;
import com.one.education.utils.sort.IndexBar;
import com.one.education.utils.sort.PinyinComparator;
import com.one.education.widget.FloatingBarItemDecoration;
import com.one.mylibrary.ConstantGlobal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-07-08
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class SelectCountryCodeActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, SelectCountryCodeActivity.class);
    }

    private MyAdapter mMyAdapter;
    private IndexBar mSideBar;
    private TextView dialog;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private LinearLayout top_layout; // 顶部悬浮的layout
    private TextView xuanfaText; // 悬浮的文字
    private int lastFirstVisibleItem = -1;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinkedHashMap<Integer, String> mHeaderList;//首字母
    private int mType = 0; //0 中文 1英文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country_code);
        findViewById(R.id.back_iv).setOnClickListener(v -> finish());

        initView();

        initLaunge();

        showProgress("");
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(() -> {
            GetCountryList getCountryList = HttpsServiceFactory.getCountryList();
            runOnUiThread(() -> {
                if (isFinishing()) {
                    return;
                }

                closeProgress();
                if (getCountryList.getStatus() == 1) {
                    firstZiMu(getCountryList.getData());
                } else {
                    ToastUtils.showToastLong(getCountryList.getDescript());
                }
            });
        });

        setListener();
    }


    private void firstZiMu(List<GetCountryList.Data> lists) {
        for (GetCountryList.Data data : lists) {
            data.setSortLetters(ContactsUtils.getAbbreviation(mType == 0 ? (data.getChineseName()).substring(0, 1) :
                    data.getEnglishName().substring(0, 1)));
        }
        Collections.sort(lists, pinyinComparator);
        preOperation(lists);
    }

    private void preOperation(List<GetCountryList.Data> mContactList) {
        mHeaderList.clear();
        if (mContactList.size() == 0) {
            return;
        }
        addHeaderToList(0, mContactList.get(0).getSortLetters());
        for (int i = 1; i < mContactList.size(); i++) {
            if (!mContactList.get(i - 1).getSortLetters().equalsIgnoreCase(mContactList.get(i).getSortLetters())) {
                addHeaderToList(i, mContactList.get(i).getSortLetters());
            }
        }

        mSideBar = findViewById(R.id.share_add_contact_sidebar);
        mSideBar.setNavigators(new ArrayList<>(mHeaderList.values()));
        mSideBar.setOnTouchingLetterChangedListener(new IndexBar.OnTouchingLetterChangeListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                dialog.setVisibility(View.VISIBLE);
                dialog.setText(s);
                for (Integer position : mHeaderList.keySet()) {
                    if (mHeaderList.get(position).equals(s)) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                        return;
                    }
                }
            }

            @Override
            public void onTouchingStart(String s) {
                dialog.setVisibility(View.GONE);
            }

            @Override
            public void onTouchingEnd(String s) {
                dialog.setVisibility(View.GONE);
            }
        });

        initRecycler(mContactList);
    }


    private void initRecycler(List<GetCountryList.Data> data) {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(
                new FloatingBarItemDecoration(this, mHeaderList));
        recyclerView.setHasFixedSize(true);


        mMyAdapter = new MyAdapter();
        mMyAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setDataList(data);
        mMyAdapter.notifyDataSetChanged();

    }

    private void addHeaderToList(int index, String header) {
        mHeaderList.put(index, header);
    }

    private void setListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    private void initView() {
        dialog = findViewById(R.id.dialog);
        top_layout = findViewById(R.id.top_layout);
        xuanfaText = findViewById(R.id.top_char);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        recyclerView = findViewById(R.id.recycle_view);
        if (mHeaderList == null) {
            mHeaderList = new LinkedHashMap<>();
        }


    }

    private void initLaunge() {
        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            mType = 0;
        } else {
            if (spLanguage.equals("en")) {
                mType = 1;
            } else if (spLanguage.equals("zh")) {
                if (spCountry.equals("HK") || spCountry.equals("hk")) {
                    mType = 1;
                } else {
                    mType = 0;
                }
            }
        }
    }

    private OnItemClickListener<GetCountryList.Data> mOnItemClickListener = (adapter, view, position) -> {
        Intent intent = new Intent();
        intent.putExtra("country_phone_code", adapter.getItem(position).getPhotoCode());
        setResult(RESULT_OK, intent);
        finish();
    };

    private class MyAdapter extends BaseRecyclerViewAdapter<GetCountryList.Data> {
        public MyAdapter() {
            setMultiTypeDelegate(data -> R.layout.select_country_code_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, GetCountryList.Data item, int position) {
            TextView country_name = holder.getView(R.id.country_name);
            TextView country_phone_code = holder.getView(R.id.country_phone_code);
            country_name.setText(mType == 0 ? item.getChineseName() : item.getEnglishName());
            String photoCode = item.getPhotoCode();
            if (TextUtils.isEmpty(photoCode)) {
                country_phone_code.setText("");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                int index = -1;
                for (char code : photoCode.toCharArray()) {
                    index++;
                    if (code == '0') {
                        continue;
                    }

                    stringBuilder.append(photoCode.substring(index));
                    break;
                }

                if (stringBuilder.toString().length() == 0) {
                    stringBuilder.append("0");
                }

                country_phone_code.setText("+" + stringBuilder.toString());
            }
        }
    }


}
