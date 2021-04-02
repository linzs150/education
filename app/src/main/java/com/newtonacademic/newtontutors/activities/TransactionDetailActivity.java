package com.newtonacademic.newtontutors.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newtonacademic.newtontutors.adapters.TransactionDetailAdapter;
import com.newtonacademic.newtontutors.beans.OrderListResponse;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.widget.CustomDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * @创建者 Administrator
 * @创建时间 2020/4/26 21:38
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TransactionDetailActivity extends BaseActivity {

    //    private List<TransactionResponse.Transaction> transactionLists = new ArrayList<>();
    private List<OrderListResponse.OrderList> orderLists = new ArrayList<>();
    private TransactionDetailAdapter mAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_activity);
        initView();
        initData();
        orderList();
        setListener();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.transaction_summary));
    }

    private void initData() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(manager);

        CustomDecoration divider = new CustomDecoration(getActivity(), CustomDecoration.VERTICAL_LIST, R.drawable.item_height, 0);
        recyclerView.addItemDecoration(divider);

        mAdapter = new TransactionDetailAdapter(this, orderLists);
        recyclerView.setAdapter(mAdapter);

    }

    private void orderList() {
        addJob(NetmonitorManager.getOrderList( new RestNewCallBack<OrderListResponse>() {
            @Override
            public void success(OrderListResponse orderListResponse) {
                if (orderListResponse.isSuccess()) {
                    orderLists.clear();
                    List<OrderListResponse.OrderList> temp = new ArrayList<OrderListResponse.OrderList>(orderListResponse.getData());
                    orderLists.addAll(temp);
                    mAdapter.updateTeacherInfo(orderLists);
                }
            }

            @Override
            public void failure(RestError error) {
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
