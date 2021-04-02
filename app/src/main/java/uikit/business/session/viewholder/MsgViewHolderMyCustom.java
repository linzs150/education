package uikit.business.session.viewholder;

import android.text.TextUtils;
import android.widget.TextView;

import com.newtonacademic.newtontutors.activities.BaseActivity;
import com.newtonacademic.newtontutors.beans.OrderQueryResponse;
import com.newtonacademic.newtontutors.classappointment.OrderConfirmActivity;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.mylibrary.ConstantGlobal;
import com.newtonacademic.mylibrary.TaughtSubjects;

import java.util.Vector;

import uikit.business.session.extension.MyCustomAttachment;
import uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import uikit.util.DateUtilts;
import uikit.util.SpUtil;
import uikit.util.TimeUtils;

import static java.util.Locale.getDefault;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderMyCustom extends MsgViewHolderBase {

    private TextView dateWeekTv;
    private TextView startTime;
    private TextView endTime;
    private TextView classCountTv;
    private TextView moneyTv;
    private MyCustomAttachment.Data data;

    public MsgViewHolderMyCustom(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getContentResId() {
        return R.layout.nim_message_mycustom;
    }

    @Override
    protected boolean isShowHeadImage() {
        return true;
    }

    @Override
    public void inflateContentView() {
        dateWeekTv = findViewById(R.id.date_week_tv);
        startTime = findViewById(R.id.start_time_tv);
        endTime = findViewById(R.id.end_time_tv);
        classCountTv = findViewById(R.id.class_tv);
        moneyTv = findViewById(R.id.money_tv);
    }

    @Override
    public void onItemClick() {
        if (data == null) {
            return;
        }

        courseQuery(data.getOrderCode());
    }

    private void courseQuery(String orderCode) {
        ((BaseActivity) context).showProgress("");
        ((BaseActivity) context).addJob(NetmonitorManager.courseOrderQuery(orderCode, new RestNewCallBack<OrderQueryResponse>() {
            @Override
            public void success(OrderQueryResponse orderCreateResponse) {
                ((BaseActivity) context).closeProgress();
                OrderQueryResponse.Certificates certificates = orderCreateResponse.getCertificates();
                if (certificates != null && !TextUtils.isEmpty(certificates.getOrderCode())) {
                    Vector<Long> selectTimes = new Vector<>();
                    float coursePrice = orderCreateResponse.getCertificates().getAmount();
                    TaughtSubjects taughtSubjects = new TaughtSubjects();
                    for (OrderQueryResponse.Data.CourseList courseList : orderCreateResponse.getData().getCourseList()) {
                        if (courseList.getOrderCode().equals(orderCode)) {
                            taughtSubjects.setCourseName(courseList.getCourseName());
                            taughtSubjects.setCourseId(courseList.getCourseId());
                            taughtSubjects.setCreateTime(courseList.getCreateTime());
                            taughtSubjects.setId(courseList.getId());
                            taughtSubjects.setTeacherId(courseList.getTeacherId());
                            taughtSubjects.setSubjectId(courseList.getSubjectId());
                            taughtSubjects.setSubjectName(courseList.getSubjectName());
                            selectTimes.add(courseList.getBeginTime() * 1000);
                        }
                    }

                    long teachId = orderCreateResponse.getData().getTeacherId();
                    context.startActivity(OrderConfirmActivity.newPayOrderIntent(context, teachId, coursePrice, selectTimes, taughtSubjects, orderCreateResponse));
                } else {
                    ((BaseActivity) context).closeProgress();
                    ToastUtils.showToastShort(context.getString(R.string.order_expired));
                }
            }

            @Override
            public void failure(RestError error) {
                ((BaseActivity) context).closeProgress();
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    @Override
    public void bindContentView() {
        MyCustomAttachment msgAttachment = (MyCustomAttachment) message.getAttachment();
        data = msgAttachment.getContent();
        if (data == null) {
            return;
        }

        int type = 0;//0中文 1英文
        String spLanguage = SpUtil.getString(context, ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(context, ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            type = 0;
        } else {
            if (spLanguage.equals("zh")) {
                type = 0;
            } else if (spLanguage.equals("en")) {
                type = 1;
            }
        }

        long date = Long.parseLong(data.getBeginTime()) * 1000;
        String time = TimeUtils.GetSpeciaTime(context, date, TimeUtils.DEFAULT_TIME_FORMA3);

        String[] dateArray;
        String dateStr;
        if (type == 0) {
            dateArray = time.split("-");
            dateStr = String.format(getDefault(), "%s-%s-%s", dateArray[0], dateArray[1], dateArray[2]);
        } else {
            dateArray = time.split("/");
            dateStr = String.format(getDefault(), "%s/%s/%s", dateArray[0], dateArray[1], dateArray[2]);
        }

        String week = DateUtilts.getWeek(context, date);
        String startTimeStr = TimeUtils.GetTime(date, TimeUtils.DEFAULT_TIME_FORMA2);
        String endTimeStr = TimeUtils.GetTime(date + 60 * 60 * 1000, TimeUtils.DEFAULT_TIME_FORMA2);


        dateWeekTv.setText(dateStr + " " + week);
        startTime.setText(context.getString(R.string.start_time_format, startTimeStr.split(" ")[1]));
        endTime.setText(context.getString(R.string.end_time_format, endTimeStr.split(" ")[1]));
        classCountTv.setText(String.valueOf(data.getCourseCount()));
        moneyTv.setText(context.getString(R.string.money_symbol) + data.getCoursePrice());
    }
}
