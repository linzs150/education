package com.one.education.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.one.education.commons.LogUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.education.R;
import com.one.education.language.MultiLanguageUtil;
import com.one.education.language.SPUtils;
import com.one.education.utils.EventBusUtils;
import com.one.education.widget.ErrorView;
import com.one.education.widget.LoadingView;
import com.one.education.widget.ProgressDialog;
import com.one.education.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.one.education.widget.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Locale;

import de.greenrobot.event.ThreadMode;
import retrofit.Call;


/**
 * @创建者 Administrator
 * @创建时间 2020/4/23 10:45
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class BaseActivity extends FragmentActivityEx {
    private static final String TAG = "BaseActivity";
    protected ArrayList<Call> mCallArray = new ArrayList<>();
    ProgressDialog mProgressDialog;
    private boolean isLoadingShown;
    private ProgressDialog mProgressDlg;
    private LoadingView mLoadingPage;
    private ErrorView mErrorPage;
    private String currentClassName;
    public Bundle s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentClassName = getClass().getName();
        this.s = savedInstanceState;
        EventBusUtils.register(this);
        LogUtils.d(TAG, currentClassName);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).init();
//        ImmersionBar.with(this)
//                .transparentStatusBar()  //透明状态栏，不写默认透明色
//                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
//                .statusBarColor(R.color.white)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.white)
//
//                .fitsSystemWindows(true, R.color.white)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .statusBarDarkFont(true)
//                .navigationBarDarkIcon(true)
//                .hideBar(BarHide.FLAG_HIDE_BAR)
//                .applySystemFits(true)
//                .init();

        initSmart();
    }

    private void initSmart() {
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.srl_header_pulling);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.srl_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.srl_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.srl_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.srl_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.srl_header_failed);
        ClassicsHeader.REFRESH_HEADER_SECONDARY = "释放进入二楼";
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.srl_header_update);

        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.srl_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.srl_footer_release);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.srl_footer_release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.srl_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.srl_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.srl_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.srl_footer_nothing);
    }

    public void onEvent(Locale str) {
        changeAppLanguage(str);
        this.recreate(); //刷新界面
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    protected void showLoading() {
        showLoading(View.NO_ID);
    }

    protected void showLoading(int layoutId) {
        if (!isLoadingShown) {
            isLoadingShown = true;
            if (mLoadingPage == null) {
                mLoadingPage = new LoadingView(this);
            }
            displayLoadingPage(layoutId, mLoadingPage);
        }
    }

    protected void dismissLoadingPage() {
        if (mLoadingPage != null && isLoadingShown) {
            ViewGroup parent = (ViewGroup) mLoadingPage.getParent();
            if (parent != null) {
                isLoadingShown = false;
                parent.removeView(mLoadingPage);
            }
        }
    }

    protected void showError() {
        showLoading(View.NO_ID);
    }

    protected void showError(int layoutId) {
        if (mErrorPage == null) {
            mErrorPage = new ErrorView(this);
            mErrorPage.setOnClickListener(createErrorPageClickListener());
        }
        if (!mErrorPage.isShown()) {
            if (mErrorPage.getParent() != null) {
                dismissErrorPage();
            }
            displayLoadingPage(layoutId, mErrorPage);
        }
    }

    private View.OnClickListener createErrorPageClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissErrorPage();
                onErrorClick(v);
            }
        };
    }

    protected void onErrorClick(View view) {
    }

    protected void dismissErrorPage() {
        if (mErrorPage != null) {
            ViewGroup parent = (ViewGroup) mErrorPage.getParent();
            if (parent != null) {
                parent.removeView(mErrorPage);
            }
        }
    }

    boolean displayLoadingPage(int layoutId, View view) {
        ViewGroup layout = null;
        if (getView() != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) getView().findViewById(layoutId);
                //TODO
                if (layout != null && layout instanceof View && !(layout instanceof ViewGroup)) {
                    layout = (ViewGroup) layout.getParent();
                    if (layout instanceof LinearLayout) {
                        index = 0;
                    }
                }
            } else {
                layout = ((ViewGroup) this.getView());
            }
            if (layout != null) {
                layout.addView(view, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    public BaseActivity getActivity() {
        return this;
    }

    public View getView() {
        return getWindow().getDecorView();
    }

    private boolean displayView(View view, int layoutId) {
        ViewGroup layout = null;
        if (view != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) findViewById(layoutId);
            } else {
                layout = (ViewGroup) getWindow().getDecorView();
            }
            if (layout != null) {
                if (layout instanceof LinearLayout) {
                    index = 0;
                }
                layout.addView(view, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    protected ProgressDialog showProgress() {
        return showProgress((ViewGroup) getView());
    }

    protected ProgressDialog showProgress(ViewGroup viewParent) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.addToView(viewParent);
        }
        mProgressDialog.show();
        return mProgressDialog;
    }

    public void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    public void showProgress(@StringRes int msgRes) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msgRes);
    }


    public void showProgress(String msg) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msg);
    }

    public boolean progressViewIsShowing() {
        if (mProgressDlg != null) {
            return mProgressDlg.isShowing();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAllJob();
        EventBusUtils.unregister(this);
    }

    public void addJob(Call call) {
        mCallArray.add(call);
    }

    protected void clearAllJob() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mCallArray != null && !mCallArray.isEmpty()) {
                    for (Call call : mCallArray) {
                        if (call != null) {
                            call.cancel();
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 对于EditText，触摸区域外软键盘消失
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
        }
        //        return super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
        //        // 必不可少，否则所有的组件都不会有TouchEvent了
        //        if (getWindow().superDispatchTouchEvent(ev)) {
        //            return true;
        //        }
        //        return onTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    // 隐藏软键盘
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void changeAppLanguage(Locale locale) {

        MultiLanguageUtil.changeAppLanguage(this, locale, true);

//        String sta = SPUtils.getLanguageLocal(this);
//        if (!TextUtils.isEmpty(sta)) {
//            Locale myLocale = new Locale(sta);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//            } else {
//                Resources resources = getResources();
//                DisplayMetrics dm = resources.getDisplayMetrics();
//                Configuration conf = resources.getConfiguration();
//                conf.locale = myLocale;
//                resources.updateConfiguration(conf, dm);
//            }
//        }
    }
}
