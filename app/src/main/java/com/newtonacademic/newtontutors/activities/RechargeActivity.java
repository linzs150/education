package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.newtonacademic.newtontutors.adapters.RechargeAdapter;
import com.newtonacademic.newtontutors.beans.OrderCreateResponse;
import com.newtonacademic.newtontutors.beans.PayOrderResponse;
import com.newtonacademic.newtontutors.beans.PayResult;
import com.newtonacademic.newtontutors.beans.RechargeSetupResponse;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.dialogs.DialogPay;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.fragments.WebActivity;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.utils.EventBusUtils;
import com.newtonacademic.newtontutors.widget.SpaceItemDecoration;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/26 23:29
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class RechargeActivity extends BaseActivity {

    private RecyclerView vertical_recyclerview;
    private GridLayoutManager manager;
    private RechargeAdapter mAdapter;
    private List<RechargeSetupResponse.RechargeSetup> rechargeBeanList = new ArrayList<>();
    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private Button btn_recharge;
    private DialogPay dialogPay;
    private long customPrice;//被选择的金额
    private long etInutPrice;//自定义金额
    private long choicePrice;//选择的金额
    private long mId = 0;
    private EditText etInputPrice;
    private Context mCtx;
    IWXAPI msgApi;
    //    private TextView tv_inputPrice;
    //    private RelativeLayout input_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_activity);
        initView();
        initData();
        rechargeSetupList();
        setListener();
    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        btn_recharge = findViewById(R.id.btn_recharge);
        etInputPrice = findViewById(R.id.etInputPrice);

        //        tv_inputPrice = findViewById(R.id.tv_inputPrice);
        //        input_layout = findViewById(R.id.input_layout);
        tvTitle.setText(getString(R.string.up_credit));
        mCtx = this;
    }

    private void rechargeSetupList() {
        addJob(NetmonitorManager.getSetupList(new RestNewCallBack<RechargeSetupResponse>() {
            @Override
            public void success(RechargeSetupResponse rechargeSetupResponse) {
                if (rechargeSetupResponse.isSuccess()) {
                    updateRecharge(rechargeSetupResponse.getData());
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private void updateRecharge(List<RechargeSetupResponse.RechargeSetup> rechargeSetupList) {
        rechargeBeanList.clear();
        for (RechargeSetupResponse.RechargeSetup setup : rechargeSetupList) {
            rechargeBeanList.add(setup);
        }

        customPrice = rechargeSetupList.get(0).getAmount();
        mId = rechargeSetupList.get(0).getId();
        mAdapter.updateTeacherInfo(rechargeBeanList);
        mAdapter.updateTeacherInfo(0);

    }

    private void initData() {
        vertical_recyclerview = (RecyclerView) findViewById(R.id.vertical_recyclerview);

        //        rechargeBeanList.add(new RechargeBean());
        //        rechargeBeanList.add(new RechargeBean());
        //        rechargeBeanList.add(new RechargeBean());
        //        rechargeBeanList.add(new RechargeBean());
        //        rechargeBeanList.add(new RechargeBean());


        manager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        vertical_recyclerview.setLayoutManager(manager);
        vertical_recyclerview.addItemDecoration(new SpaceItemDecoration(this, 4));
        vertical_recyclerview.setAnimation(null);


        mAdapter = new RechargeAdapter(this, rechargeBeanList);
        vertical_recyclerview.setAdapter(mAdapter);
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null && mAdapter.ifSelected()) {
                    choicePrice = customPrice;
                } else {
                    choicePrice = etInutPrice;
                }
                dialogPay();
            }
        });

        mAdapter.setSearchListener(new RechargeAdapter.SearchListener() {
            @Override
            public void click(int position) {
                if (rechargeBeanList != null && rechargeBeanList.size() > 0) {
                    if (position < rechargeBeanList.size() && rechargeBeanList.get(position) != null) {
                        rechargeBeanList.get(position).isSelect();
                        customPrice = Long.valueOf(rechargeBeanList.get(position).getAmount());
                        mId = rechargeBeanList.get(position).getId();
                        mAdapter.updateTeacherInfo(position);
                    }
                }
                closefocus();
            }


        });

        etInputPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mAdapter != null) {
                        mAdapter.clearSelected();
//                        if (!TextUtils.isEmpty(etInputPrice.getText().toString().trim())) {
//                            customPrice = Long.valueOf(etInputPrice.getText().toString().trim().replace("", ""));
//                        } else {
//                            customPrice = 0l;
//                        }
                        return;
                    }
                    //                    etInputPrice.setText(customPrice + "");
                    etInputPrice.setText(etInutPrice == 0l ? "" : etInutPrice + "");
                    etInputPrice.setSelection(etInutPrice == 0l ? 0 : (etInutPrice + "").length());
                } else {
                    if (!TextUtils.isEmpty(etInputPrice.getText().toString().trim())) {
//                        customPrice = Long.valueOf(etInputPrice.getText().toString().trim().replace("", ""));
                        mId = 0;
                        etInputPrice.setText(etInutPrice + "");
                    }
                }
            }
        });

        etInputPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mAdapter != null && mAdapter.ifSelected()) { //是否被选择
//
//                } else {
                etInutPrice = Long.valueOf(s.toString().replace("", ""));
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //        etInputPrice.addTextChangedListener(new TextWatcher() {
        //            @Override
        //            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //
        //            }
        //
        //            @Override
        //            public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
        //            }
        //
        //            @Override
        //            public void afterTextChanged(Editable s) {
        //
        //                if (TextUtils.isEmpty(s)) {
        //                    customPrice = 0;
        //                } else {
        //                    try {
        //                        customPrice = Long.valueOf(s.toString().replace("元", ""));
        //                    } catch (Exception e) {
        //
        //                    }
        //                }
        //
        //            }
        //        });

        //        input_layout.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                LogUtils.e("ceshi","88888");
        //                editYN(true);
        //            }
        //        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }

    //展开支付界面
    private void dialogPay() {
        if (dialogPay == null) {
            dialogPay = new DialogPay(mCtx);
        }
        dialogPay.clearPayType();
        dialogPay.setPrice(choicePrice);
        dialogPay.show();
        dialogPay.setTypePay(new DialogPay.PayInterface() {
            @Override
            public void payType(int type) {
                pay(type);
            }
        });
        dialogPay.setTvPayListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPay(4);
            }
        });
    }

    //支付类型不同跳转不同支付方法

    private void pay(int type) {
        switch (type) {
            case Constants.WX_PAY:
                break;
            case Constants.UNION_PAY:
                break;
            case Constants.ALIBA_PAY:
                break;
            case Constants.PAyPAL:
                //                payPay(Constants.PAyPAL);
                break;
        }
    }

    private void payPay(int type) {
        showProgress("");
        addJob(NetmonitorManager.orderCreate("pay_pal", mId, new RestNewCallBack<OrderCreateResponse>() {
            @Override
            public void success(OrderCreateResponse orderCreateResponse) {
                closeProgress();
                if (orderCreateResponse.getData() != null)
                    payOrder(orderCreateResponse.getData());
            }

            @Override
            public void failure(RestError error) {

            }
        }));
    }


    //发起支付

    private void payOrder(OrderCreateResponse.OrderCreate orderCreateResponse) {

        showProgress();
        addJob(NetmonitorManager.payOrder("",orderCreateResponse, new RestNewCallBack<PayOrderResponse>() {
            @Override
            public void success(PayOrderResponse payOrderResponse) {
                closeProgress();
                dialogPay.dismiss();
                if (payOrderResponse.isSuccess()) {
                    if (payOrderResponse.getData() != null) {
                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(payOrderResponse.getData().getApproveUrl());
//                        intent.setData(content_url);
//                        startActivity(intent);
                        startActivity(WebActivity.newIntent(RechargeActivity.this, payOrderResponse.getData().getApproveUrl(), getString(R.string.pay)));
                    }
                }
            }

            @Override
            public void failure(RestError error) {
                dialogPay.dismiss();
                closeProgress();
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    //使editText失去焦点
    private void closefocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(etInputPrice.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        etInputPrice.clearFocus();
        //        editYN(false);
        //        etInputPrice.setFocusable(false);
        //        etInputPrice.setFocusableInTouchMode(false);
    }

    //支付寳
    private void payWithZhiFuBao(final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechargeActivity.this);
                // 调用支付接口，获取支付结果

                String result = alipay.pay(payInfo, false);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                aliHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private PayReq req;

    //微信支付

    private void payWithWechat(final String payInfo) {
        //        WXPayEntryActivity.setInputNum(inputNum);
        //        WXPayEntryActivity.setMoneyData(moneyData);
        //        WXPayEntryActivity.setPayResponse(payResponse);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                sendWXPayReq(payInfo);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private void sendWXPayReq(String PayInfo) {
        req = new PayReq();

        JSONObject payInfoJson = JSON.parseObject(PayInfo);
        JSONObject wxJson = payInfoJson.getJSONObject("payInfo");
        req.appId = wxJson.getString("appid");
        req.partnerId = wxJson.getString("partnerid");
        req.prepayId = wxJson.getString("prepayid");
        req.packageValue = wxJson.getString("package_");
        req.nonceStr = wxJson.getString("noncestr");
        req.timeStamp = wxJson.getString("timestamp");
        req.sign = wxJson.getString("sign");

        msgApi.registerApp(req.appId);
        msgApi.sendReq(req);

    }

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler aliHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //                        Toast.makeText(BillRechargeMainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        ToastUtils.showToastShort(mCtx, "支付成功");
                        EventBusUtils.post("pay_success");


                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            //                            Toast.makeText(BillRechargeMainActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            ToastUtils.showToastShort(mCtx, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            //                            Toast.makeText(BillRechargeMainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            ToastUtils.showToastShort(mCtx, "支付失败");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    //                    Toast.makeText(BillRechargeMainActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    ToastUtils.showToastShort(mCtx, "检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


}
