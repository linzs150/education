package com.one.education.classappointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.one.education.EducationAppliction;
import com.one.education.activities.ActivityMgr;
import com.one.education.activities.BaseActivity;
import com.one.education.activities.ModifyPasswordActivity;
import com.one.education.adapter.BaseRecyclerViewAdapter;
import com.one.education.adapter.ViewHolder;
import com.one.education.beans.ExistPayPwdResponse;
import com.one.education.beans.OrderCreateResponse;
import com.one.education.beans.OrderQueryResponse;
import com.one.education.beans.PayOrderResponse;
import com.one.education.beans.Products;
import com.one.education.beans.TeacherProfileItem;
import com.one.education.beans.TeacherProfileResponse;
import com.one.education.commons.LogUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.dialogs.DialogNormal;
import com.one.education.education.R;
import com.one.education.fragments.WebActivity;
import com.one.education.language.SpUtil;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.TimeUtils;
import com.one.education.utils.Utilts;
import com.one.education.utils.toast.DateUtilts;
import com.one.education.widget.PaySelectDialog;
import com.one.mylibrary.ConstantGlobal;
import com.one.mylibrary.TaughtSubjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.util.Locale.getDefault;

/**
 * @author laiyongyang
 * @date 2020-05-18
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class OrderConfirmActivity extends BaseActivity {
    /**
     * @param context
     * @param teacherBaseInfo
     * @param coursePrice     订单总额
     * @param selectTimes     单位是毫秒
     * @param courseName
     * @return
     */
    public static Intent newIntent(Context context, TeacherProfileItem teacherBaseInfo,
                                   float coursePrice, Vector<Long> selectTimes, TaughtSubjects courseName) {
        Intent intent = new Intent(context, OrderConfirmActivity.class);
        intent.putExtra(INTENT_DATA, teacherBaseInfo);
        intent.putExtra(INTENT_DATA_2, coursePrice);
        intent.putExtra(INTENT_DATA_3, selectTimes);
        intent.putExtra(INTENT_DTAT_4, courseName);
        intent.putExtra("teacherId", teacherBaseInfo.getTeacherId());
        return intent;
    }

    /**
     * @param context
     * @param teacherId
     * @param coursePrice 订单总额
     * @param selectTimes 单位是毫秒
     * @param courseName
     * @param response
     * @return
     */
    public static Intent newPayOrderIntent(Context context, long teacherId,
                                           float coursePrice, Vector<Long> selectTimes,
                                           TaughtSubjects courseName, OrderQueryResponse response) {
        Intent intent = new Intent(context, OrderConfirmActivity.class);
        intent.putExtra("teacherId", teacherId);
        intent.putExtra(INTENT_DATA_2, coursePrice);
        intent.putExtra(INTENT_DATA_3, selectTimes);
        intent.putExtra(INTENT_DTAT_4, courseName);
        intent.putExtra("pay_order", true);
        intent.putExtra("start_soon", response);
        return intent;
    }

    private static final String INTENT_DATA = "data";
    private static final String INTENT_DATA_2 = "coursePrice";
    private static final String INTENT_DATA_3 = "selectTimes";
    private static final String INTENT_DTAT_4 = "courseName";
    private MyAdapter mMyAdapter;
    private final List<Data> mList = new ArrayList<>();
    private float mCousePrice = 0;
    //0余额， 1表示Paypal
    private int mPayMethod = 0;
    private TextView mPayMethodTv;
    private int mType = 0;//0中文 1英文
    String spLanguage;
    String spCountry;
    private TaughtSubjects courseName;
    private DialogNormal dialogNormal;
    private DialogNormal payDialogNormal;
    TeacherProfileItem teacherBaseInfo;
    private List<Products> products = new ArrayList<>();
    private OrderQueryResponse orderQueryResponse;
    private Context mCtx;
    TextView nameTv;
    TextView classAgeTv;
    ImageView icon;
    List<Long> selectTimes = new ArrayList<>();
    private long teacherId;
    TextView moneyTv1;
    /**
     * 是否是老师推荐的
     */
    private boolean payOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this;
        setContentView(R.layout.activity_order_confirm);
        Intent intent = getIntent();
        teacherBaseInfo = (TeacherProfileItem) intent.getSerializableExtra(INTENT_DATA);
        mCousePrice = intent.getFloatExtra(INTENT_DATA_2, 0f);
        selectTimes = (List<Long>) intent.getSerializableExtra(INTENT_DATA_3);
        courseName = (TaughtSubjects) intent.getSerializableExtra(INTENT_DTAT_4);
        orderQueryResponse = (OrderQueryResponse) intent.getSerializableExtra("start_soon");
        teacherId = intent.getLongExtra("teacherId", 0L);
        payOrder = intent.getBooleanExtra("pay_order", false);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter();
        spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            mType = 0;
        } else {
            if (spLanguage.equals("zh")) {
                mType = 0;

            } else if (spLanguage.equals("en")) {
                mType = 1;
            }
        }

        int count = selectTimes.size();
        for (long date : selectTimes) {
            String time = TimeUtils.GetSpeciaTime(date, TimeUtils.DEFAULT_TIME_FORMA3);

            String[] dateArray;
            String dateStr;
            if (mType == 0) {
                dateArray = time.split("-");
                dateStr = String.format(getDefault(), "%s-%s-%s", dateArray[0], dateArray[1], dateArray[2]);
            } else {
                dateArray = time.split("/");
                dateStr = String.format(getDefault(), "%s/%s/%s", dateArray[0], dateArray[1], dateArray[2]);
            }

            String week = DateUtilts.getWeek(this, date);
            String startTime = TimeUtils.GetTime(date, TimeUtils.DEFAULT_TIME_FORMA2);
            String endTime = TimeUtils.GetTime(date + 60 * 60 * 1000, TimeUtils.DEFAULT_TIME_FORMA2);

            Data data = new Data();
            data.setDate(dateStr);
            data.setWeek(week);
            data.setStartTime(startTime.split(" ")[1]);
            data.setEndTime(endTime.split(" ")[1]);
            data.setClassCount(1);
            data.setMoney((int) (mCousePrice / count));
            mList.add(data);

            Products product = new Products();

            product.setBeginTime(date / 1000);
            product.setEndTime((date + 60 * 60 * 1000) / 1000);
            product.setCourseCount(1);
            product.setCourseId(courseName.getCourseId());
            product.setCourseName(courseName.getCourseName());
            products.add(product);

        }

        mMyAdapter.setDataList(mList);
        recyclerView.setAdapter(mMyAdapter);
        findViewById(R.id.back_iv).setOnClickListener(mOnClickListener);
        findViewById(R.id.immediately_order).setOnClickListener(mOnClickListener);
        icon = findViewById(R.id.icon);
        nameTv = findViewById(R.id.teacher_name_tv);


        classAgeTv = findViewById(R.id.class_age_tv);
        if (teacherBaseInfo != null) {
            updateTeacherInformation(teacherBaseInfo);
        } else {
            showProgress();
            addJob(NetmonitorManager.teacherGetBaseProfile(teacherId, new RestNewCallBack<TeacherProfileResponse>() {
                @Override
                public void success(TeacherProfileResponse teacherProfileResponse) {
                    closeProgress();
                    if (teacherProfileResponse != null) {
                        if (teacherProfileResponse.isSuccess()) {
                            updateTeacherInformation(teacherProfileResponse.getData());
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
    }

    private void updateTeacherInformation(TeacherProfileItem teacherBaseInfo) {
        if (teacherBaseInfo != null) {
            this.teacherBaseInfo = teacherBaseInfo;
            if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            } else {
                if (spLanguage.equals("en")) {
                    if (teacherBaseInfo.getTeachingExperience() == 0 || teacherBaseInfo.getTeachingExperience() == 1) {
                        classAgeTv.setText("Teaching: " + teacherBaseInfo.getTeachingExperience() + " year");
                    } else {
                        classAgeTv.setText(mCtx.getString(R.string.teaching_experience, teacherBaseInfo.getTeachingExperience()));
                    }
                } else {
                    classAgeTv.setText(mCtx.getString(R.string.teaching_experience, teacherBaseInfo.getTeachingExperience()));
                }
            }

            nameTv.setText(teacherBaseInfo.getTeacherName());
            TextView pay_method_tv = findViewById(R.id.pay_method_tv);
            pay_method_tv.setOnClickListener(v -> {
                showMenuDialog();
            });

            mPayMethodTv = findViewById(R.id.pay_method_tv);
            mPayMethodTv.setText(mCtx.getString(R.string.yu_e_pay));
            mPayMethodTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.yu_e, 0, R.drawable.setting_down, 0);
            moneyTv1 = findViewById(R.id.all_money_tv);
            moneyTv1.setText(mCtx.getString(R.string.order_money_symbol, (int) mCousePrice));

            ((RatingBar) findViewById(R.id.score_rating_bar)).setRating(Utilts.ratingJS(teacherBaseInfo.getCommentStar()));
            ImageLoader.loadAdImage(teacherBaseInfo.getUserPicUrl(), icon);
        }

    }

    //从待支付、待邀请确认
    private void orderWaitPay(OrderQueryResponse response) {
        if (response != null) {

        }
    }

    private final View.OnClickListener mOnClickListener = v -> {
        if (v.getId() == R.id.back_iv) {
            finish();
        } else if (v.getId() == R.id.immediately_order) {
            if (mPayMethod == 0) {
                verifyExistPayPwd();
            } else if (mPayMethod == 1) {
                orderCreate("");
            }
        }
    };

    private static class Data {
        String date;
        String week;
        String startTime;
        String endTime;
        int classCount;
        float money;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getClassCount() {
            return classCount;
        }

        public void setClassCount(int classCount) {
            this.classCount = classCount;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<Data> {

        public MyAdapter() {
            setMultiTypeDelegate(data -> R.layout.order_confirm_item_layout);
        }

        @Override
        public void bindViewHolder(ViewHolder holder, Data item, int position) {
            TextView dateWeekTv = holder.getView(R.id.date_week_tv);
            TextView startTime = holder.getView(R.id.start_time_tv);
            TextView endTime = holder.getView(R.id.end_time_tv);
            TextView classCountTv = holder.getView(R.id.class_tv);
            TextView moneyTv = holder.getView(R.id.money_tv);
            dateWeekTv.setText(item.getDate() + "   " + item.getWeek());
            startTime.setText(mCtx.getString(R.string.start_time_format, item.getStartTime()));
            endTime.setText(mCtx.getString(R.string.end_time_format, item.getEndTime()));
            classCountTv.setText(String.valueOf(item.getClassCount()));
            moneyTv.setText(mCtx.getString(R.string.money_symbol) + (int) item.getMoney());
        }
    }

    private PaySelectDialog mPaySelectDialog = null;

    /**
     * 显示更多多画框
     */
    private void showMenuDialog() {
        if (null != mPaySelectDialog && mPaySelectDialog.isShowing()) {
            return;
        }

        View.OnClickListener oneClick = v -> {
            mPayMethod = 0;
            mPayMethodTv.setText(mCtx.getString(R.string.yu_e_pay));
            mPayMethodTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.yu_e, 0, R.drawable.setting_down, 0);
        };
        View.OnClickListener twoClick = v -> {
            mPayMethod = 1;
            mPayMethodTv.setText(mCtx.getString(R.string.pay_pal_pay));
            mPayMethodTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.paypal, 0, R.drawable.setting_down, 0);
        };
        mPaySelectDialog = new PaySelectDialog(OrderConfirmActivity.this, oneClick, twoClick);
        mPaySelectDialog.select(mPayMethod);
        mPaySelectDialog.show();
    }

    /**
     * paypal发起支付
     */
    private void payOrder(OrderCreateResponse orderCreateResponse) {
        showProgress();
        addJob(NetmonitorManager.payOrder("", orderCreateResponse.getData(), new RestNewCallBack<PayOrderResponse>() {
            @Override
            public void success(PayOrderResponse payOrderResponse) {
                closeProgress();
                if (payOrderResponse.isSuccess()) {
                    mPayState = 0;
                    if (payOrderResponse.getData() != null) {
                        Intent intent = new Intent();
                        startActivityForResult(WebActivity.newIntent(OrderConfirmActivity.this, payOrderResponse.getData().getApproveUrl(), getString(R.string.pay)), 10008);
                    }
                }
            }

            @Override
            public void failure(RestError error) {
                mPayState = 1;
                closeProgress();
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    //余额支付的时候,判断是否有支付密码

    private void verifyExistPayPwd() {

        LogUtils.e("lzs", JSON.toJSONString(products));

        addJob(NetmonitorManager.verifyExistPayPwd(new RestNewCallBack<ExistPayPwdResponse>() {
            @Override
            public void success(ExistPayPwdResponse existPayPwdResponse) {
                if (existPayPwdResponse.isSuccess()) {
                    if (existPayPwdResponse.isExistPayPwd()) {
                        jumpRechargePay();
                    } else {
                        jumpModifyRechargePsd();
                    }
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    private String pwd;

    //余额支付的时候弹出支付密码
    private void jumpRechargePay() {
        if (payDialogNormal == null) {
            payDialogNormal = new DialogNormal(this);
        }
        payDialogNormal.setEtValue();
        payDialogNormal.setClearEtValue();
        payDialogNormal.setTitle(14, mCtx.getString(R.string.enter_payment_password), Gravity.CENTER);
        payDialogNormal.show();

        payDialogNormal.setRightBtn(mCtx.getString(R.string.confirm_pay), v -> {
            pwd = payDialogNormal.getEtValue();
            if (TextUtils.isEmpty(pwd)) {
                ToastUtils.showToastShort(mCtx.getString(R.string.enter_payment_password));
                return;
            }
            orderCreate(pwd);
            payDialogNormal.dismiss();
        });
        payDialogNormal.setLeftBtn(mCtx.getString(R.string.cancel), v -> payDialogNormal.dismiss());

    }

    //创建订单
    private void orderCreate(String pwd) {
        if (mPayState == 1) {
            if (failOrderCreateResponse != null) {
                if (mPayMethod == 0) {
                    failOrderCreateResponse.getData().setPayChannel("balance");
                    payOrderCourse(pwd, failOrderCreateResponse);
                } else {
                    failOrderCreateResponse.getData().setPayChannel("pay_pal");
                    payOrder(failOrderCreateResponse);
                }
            }
        } else {
            //如果是老师推荐的课程直接支付， 不需要创建订单
            LogUtils.e("lzs", JSON.toJSONString(products));
            showProgress();
            if (payOrder) {
                OrderQueryResponse.Certificates certificates = orderQueryResponse.getCertificates();
                if (certificates == null) {
                    return;
                }

                OrderCreateResponse.OrderCreate orderCreate = new OrderCreateResponse.OrderCreate();
                orderCreate.setAmount(certificates.getAmount());
                orderCreate.setBody(certificates.getBody());
                orderCreate.setClientIp(certificates.getClientIp());
                orderCreate.setCurrency(certificates.getCurrency());
                orderCreate.setExtra(certificates.getExtra());
                orderCreate.setOrderCode(certificates.getOrderCode());
                orderCreate.setPayChannel(mPayMethod == 0 ? "balance" : "pay_pal");
                orderCreate.setProductCount(certificates.getProductCount());
                orderCreate.setSign(certificates.getSign());
                orderCreate.setSubject(certificates.getSubject());
                orderCreate.setTimeExpire(certificates.getTimeExpire());
                OrderCreateResponse orderCreateResponse = new OrderCreateResponse();
                orderCreateResponse.setData(orderCreate);
                if (mPayMethod == 0) {
                    payOrderCourse(pwd, orderCreateResponse);
                } else {
                    payOrder(orderCreateResponse);
                }
                return;
            }

            addJob(NetmonitorManager.courseOrderCreate(teacherBaseInfo.getTeacherId() + "", mPayMethod == 0 ? "balance" : "pay_pal", JSON.toJSONString(products), new RestNewCallBack<OrderCreateResponse>() {
                @Override
                public void success(OrderCreateResponse orderCreateResponse) {
                    LogUtils.e("lzs", JSONObject.toJSONString(orderCreateResponse));
                    closeProgress();
                    failOrderCreateResponse = orderCreateResponse;
                    if (mPayMethod == 0) {
                        payOrderCourse(pwd, orderCreateResponse);
                    } else {
                        payOrder(orderCreateResponse);
                    }
                }

                @Override
                public void failure(RestError error) {
                    closeProgress();
                    ToastUtils.showToastShort(error.msg);
                }
            }));
        }

    }

    private int mPayState = 0;//1失败 0 成功
    private OrderCreateResponse failOrderCreateResponse;

    //支付
    private void payOrderCourse(String pwd, OrderCreateResponse orderCreateResponse) {

        addJob(NetmonitorManager.payOrder(pwd, orderCreateResponse.getData(), new RestNewCallBack<PayOrderResponse>() {
            @Override
            public void success(PayOrderResponse payOrderResponse) {
                closeProgress();
                mPayState = 0;
                if (payOrder) {
                    ToastUtils.showToastShort(OrderConfirmActivity.this, getString(R.string.appointment_successful));
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ActivityMgr.get().backToMainActivityClassScheduleFragment();
                } else {
                    setResult(1004);
                    finish();
                }
            }

            @Override
            public void failure(RestError error) {
                closeProgress();
                mPayState = 1;
                ToastUtils.showToastShort(error.msg);
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10008 && resultCode == 10009) {
            setResult(1004);
            finish();
        }
    }

    //余额支付未设置密码跳转密码修改
    private void jumpModifyRechargePsd() {
        if (dialogNormal == null) {
            dialogNormal = new DialogNormal(this);
        }
        dialogNormal.setTitle(14, mCtx.getString(R.string.payment_password_not_yet_set), Gravity.CENTER);
        dialogNormal.setContent(mCtx.getString(R.string.set_your_payment_password), Gravity.CENTER);
        dialogNormal.setLeftBtn(mCtx.getString(R.string.set_up_later), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNormal.dismiss();
            }
        });
        dialogNormal.setRightBtn(mCtx.getString(R.string.set_up_now), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmActivity.this, ModifyPasswordActivity.class);
                mCtx.startActivity(intent);
                dialogNormal.dismiss();
            }
        });
        dialogNormal.show();

    }


}
