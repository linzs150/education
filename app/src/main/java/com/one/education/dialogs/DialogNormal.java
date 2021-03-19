package com.one.education.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.one.education.EducationAppliction;
import com.one.education.education.R;

/**
 * @author 人杰地灵
 * @link http://www.ljwoo.com/
 * @date 2014-1-20 下午3:34:10
 */
public class DialogNormal {// 右确定
    private Dialog dialog;
    private View view;

    private TextView title;
    private TextView tvContent, tvContentMore, tv_content_coupon;

    private LinearLayout llEdittext;
    private TextView tvEtTips;

    private LinearLayout llBtns;
    private Button btnLeft;
    private LinearLayout llBtnLeft;
    private Button btnRight;
    private LinearLayout llBtnRight;
    private Button btnMiddle;
    private LinearLayout llBtnMiddle;
    private TextView countdown;
    private View v_right_split;
    private EditText et_value;

    private Context mContext;

    public DialogNormal(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_normal, null);
        //        dialog = new Dialog(context, R.style.dialog_layout);
        dialog = new Dialog(context, R.style.zzCustomDialog_update1);

        title = (TextView) view.findViewById(R.id.tv_title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContentMore = (TextView) view.findViewById(R.id.tv_content_more);
        tv_content_coupon = (TextView) view.findViewById(R.id.tv_content_coupon);
        et_value = view.findViewById(R.id.et_value);

        llBtns = (LinearLayout) view.findViewById(R.id.ll_btns);
        btnLeft = (Button) view.findViewById(R.id.btn_left);
        llBtnLeft = (LinearLayout) view.findViewById(R.id.ll_btn_left);
        btnRight = (Button) view.findViewById(R.id.btn_right);
        llBtnRight = (LinearLayout) view.findViewById(R.id.ll_btn_right);
        btnMiddle = (Button) view.findViewById(R.id.btn_middle);
        llBtnMiddle = (LinearLayout) view.findViewById(R.id.ll_btn_middle);
        countdown = (TextView) view.findViewById(R.id.tv_countdown);
        v_right_split = (View) view.findViewById(R.id.v_right_split);

    }

    public void setEtValue() {
        et_value.setVisibility(View.VISIBLE);
    }

    public String getEtValue() {
        return et_value.getText().toString().trim();
    }

    public void setClearEtValue() {
        et_value.setText("");
    }

    private OngetEffectListener ongetEffectListener;

    public void setOngetEffectListener(OngetEffectListener ongetEffectListener) {
        this.ongetEffectListener = ongetEffectListener;
    }

    public interface OngetEffectListener {
        public void getEffect(int type);
    }

    public void setSysAlert() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
    }

    public void setCancelableOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (dialog != null)
            return dialog.isShowing();
        return false;
    }

    public void setCancelable(boolean flag) {
        dialog.setCancelable(flag);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTitle(int textSize, String title) {
        this.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        this.title.setText(title);
    }

    public void setTitle(int textSize, String title, int gravity) {
        this.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        this.title.setGravity(gravity);
        this.title.setText(title);
    }

    public void setCountdown(String countdown) {
        this.countdown.setText(countdown);
    }

    public void setContent(String content) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(content);
    }

    public void setContent(int textSize, String content) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvContent.setText(content);
    }

    public void setContent(int textColor, CharSequence content) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setTextColor(textColor);
        tvContent.setText(content);
    }

    public void setContent(String content, int gravity) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .WRAP_CONTENT);
        //        lp.gravity = gravity;
        tvContent.setLayoutParams(lp);
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setGravity(gravity);
        tvContent.setGravity(Gravity.LEFT | Gravity.CENTER);
        tvContent.setText(content);
    }

    public void setContentColor(String content, int color) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .WRAP_CONTENT);
        //        lp.gravity = gravity;
        tvContent.setLayoutParams(lp);
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setGravity(Gravity.LEFT | Gravity.CENTER);
        tvContent.setText(content);
        tvContent.setTextColor(color);
    }

    public void setContent(Spanned content, int gravity) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .WRAP_CONTENT);
        tvContent.setLayoutParams(lp);
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setGravity(gravity);
        tvContent.setGravity(Gravity.LEFT | Gravity.CENTER);
        tvContent.setText(content);
    }

    public void setContent(CharSequence content) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(content);
    }

    public void setContent(CharSequence content, CharSequence contentMore) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tvContent.setText(content);
        if (!TextUtils.isEmpty(contentMore)) {
            tvContentMore.setVisibility(View.VISIBLE);
            tvContentMore.setText(contentMore);
            tvContentMore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }
    }

    public void setContentCoupon(CharSequence content) {
        tv_content_coupon.setVisibility(View.VISIBLE);
        tv_content_coupon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv_content_coupon.setText(content);
    }

    public void setContentStytle(String content, int length, int length1) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(setTextStytle(content, length, length1));
    }

    public void setContentHeight(int height) {
        LayoutParams params = tvContent.getLayoutParams();
        params.height = height;
        tvContent.setLayoutParams(params);
    }

    public TextView getTvEtTips() {
        return tvEtTips;
    }

    public void setLeftBtn(String str, View.OnClickListener listener) {
        llBtns.setVisibility(View.VISIBLE);
        llBtnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(str);
        btnLeft.setOnClickListener(listener);
    }

    public void setLeftBtn(int str, View.OnClickListener listener) {
        llBtns.setVisibility(View.VISIBLE);
        llBtnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(str);
        btnLeft.setOnClickListener(listener);
    }

    public void setLeftGone() {
        llBtns.setVisibility(View.GONE);
        llBtnLeft.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnRight.getLayoutParams();
        params.rightMargin = params.leftMargin;
        btnRight.setLayoutParams(params);
    }

    public void setRightBtn(String str, View.OnClickListener listener) {
        llBtns.setVisibility(View.VISIBLE);
        llBtnRight.setVisibility(View.VISIBLE);
        btnRight.setText(str);
        btnRight.setOnClickListener(listener);
        //        if (llBtnLeft.getVisibility() != View.VISIBLE) {
        v_right_split.setVisibility(View.GONE);
        //        }
    }

    public void setLeftBtnColor(int color) {
        btnLeft.setTextColor(EducationAppliction.getInstance().getResources().getColor(color));
    }

    public void setLeftBtnResource(int drawable) {
        btnLeft.setBackgroundResource(drawable);
    }

    public void setRightBtn(int str, View.OnClickListener listener) {
        llBtnRight.setVisibility(View.VISIBLE);
        btnRight.setText(str);
        btnRight.setOnClickListener(listener);
    }

    public void setMiddleBtn(String str, View.OnClickListener listener) {
        llBtns.setVisibility(View.VISIBLE);
        llBtnMiddle.setVisibility(View.VISIBLE);
        btnMiddle.setText(str);
        btnMiddle.setOnClickListener(listener);
    }

    public void setMiddleBtn(int str, View.OnClickListener listener) {
        llBtns.setVisibility(View.VISIBLE);
        llBtnMiddle.setVisibility(View.VISIBLE);
        btnMiddle.setText(str);
        btnMiddle.setOnClickListener(listener);
    }

    private SpannableString setTextStytle(String text, int length, int length1) {
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(Color.RED), 6, 6 + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.RED), 6 + length + 4, 6 + length + 4 + length1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    // public void setContentView(View customView) {
    // lyContent.removeAllViews();
    // lyContent.addView(customView);
    // }
}
