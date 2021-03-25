package com.one.education.education;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uikit.business.recent.RecentContactsFragment;
import uikit.common.util.log.sdk.util.FileUtils;
import uikit.support.permission.MPermission;
import uikit.support.permission.annotation.OnMPermissionDenied;
import uikit.support.permission.annotation.OnMPermissionGranted;
import uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.one.education.activities.ActivityMgr;
import com.one.education.activities.BaseActivity;
import com.one.education.api.SystemInterface;
import com.one.education.beans.BaseBean;
import com.one.education.beans.PerfectMyProfileResponse;
import com.one.education.beans.VersionUpdateResponse;
import com.one.education.commons.LogUtils;
import com.one.education.commons.SharedPreferencesUtils;
import com.one.education.commons.ToastUtils;
import com.one.education.db.DBManager;
import com.one.education.dialogs.DialogStudentSave;
import com.one.education.fragments.ClassAppointment;
import com.one.education.fragments.ClassScheduleFragment;
import com.one.education.fragments.HomeFragment;
import com.one.education.fragments.MyFragment;
import com.one.education.network.NetmonitorManager;
import com.one.education.network.RestError;
import com.one.education.network.RestNewCallBack;
import com.one.education.user.UserInstance;
import com.one.education.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    FragmentManager manager;
    private List<Fragment> mFragments = new ArrayList<>();
    private Fragment mContent;
    private FrameLayout container;
    private FrameLayout fl_show;

    private LinearLayout home_layout;
    private ImageView home_image;
    private TextView home_text;

    private LinearLayout st_layout;
    private ImageView st_image;
    private TextView st_text;

    private LinearLayout plan_layout;
    private ImageView plan_image;
    private TextView plan_text;

    private LinearLayout my_layout;
    private ImageView my_image;
    private TextView my_text;

    private LinearLayout chat_layout;
    private ImageView chat_image;
    private TextView chat_text;

    private int position = 0;
    private String register;
    private Context mCtx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCtx = this;
        setContentView(R.layout.activity_main);
        EventBusUtils.register(mCtx);
        //  dialogTo();
        initView();
        initFragment();
        initData();
        setListener();
        requestBasicPermission();
        if (!TextUtils.isEmpty(register) && register.equals("register")) {
            dialogTo();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(mCtx);
    }

    @MainThread
    public void onEventMainThread(String s) {
        if (!TextUtils.isEmpty(s)) {
            if (s.equals("student_reg")) {
                dialogTo();
            } else if (s.equals("pay_success")) {
                changeFragment(2);
            }
        }
    }

    private DialogStudentSave mDialogStudentSave;

    private void dialogTo() {
        if (mDialogStudentSave == null) {
            mDialogStudentSave = new DialogStudentSave(mCtx);
        }
        mDialogStudentSave.show();
        mDialogStudentSave.setSave(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogStudentSave.dismiss();
//                EventBusUtils.unregister(MainActivity.this);
                EventBus.getDefault().removeStickyEvent(mCtx);
            }
        });
    }


    private void initView() {
        home_layout = findViewById(R.id.home_layout);
        home_image = findViewById(R.id.home_image);
        home_text = findViewById(R.id.home_text);

        st_layout = findViewById(R.id.st_layout);
        st_image = findViewById(R.id.st_image);
        st_text = findViewById(R.id.st_text);

        plan_layout = findViewById(R.id.plan_layout);
        plan_image = findViewById(R.id.plan_image);
        plan_text = findViewById(R.id.plan_text);

        chat_layout = findViewById(R.id.chat_layout);
        chat_image = findViewById(R.id.chat_image);
        chat_text = findViewById(R.id.chat_text);

        my_layout = findViewById(R.id.my_layout);
        my_image = findViewById(R.id.my_image);
        my_text = findViewById(R.id.my_text);

        home_layout.setOnClickListener(this);
        st_layout.setOnClickListener(this);
        plan_layout.setOnClickListener(this);
        my_layout.setOnClickListener(this);
        chat_layout.setOnClickListener(this);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(observer, true);
    }

    private void initFragment() {

        manager = getSupportFragmentManager();
        mFragments = new ArrayList<>(5);
        Fragment fragment1 = new HomeFragment();
        Fragment fragment2 = new ClassAppointment();
        Fragment fragment3 = new ClassScheduleFragment();
        Fragment fragment4 = new RecentContactsFragment();
        Fragment fragment5 = new MyFragment();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
        mFragments.add(fragment5);

        if (mFragments != null && mFragments.size() > 0) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_show, mFragments.get(0));
            transaction.commitAllowingStateLoss();
            mContent = mFragments.get(0);
            setSelected(0);
        }
    }

    private void initData() {
        register = getIntent().getStringExtra("student_reg");
        setStudentState();
        addJob(NetmonitorManager.checkVersionUpdate(mCtx, new RestNewCallBack<VersionUpdateResponse>() {
            @Override
            public void success(VersionUpdateResponse baseBean) {
                if (baseBean != null) {
                    if (!TextUtils.isEmpty(baseBean.getStatus()) && baseBean.getStatus().equals("1")) {

                    }
                }
            }

            @Override
            public void failure(RestError error) {

            }
        }));


    }

    private void setStudentState() {
        addJob(NetmonitorManager.getMyProfile(new RestNewCallBack<PerfectMyProfileResponse>() {
            @Override
            public void success(PerfectMyProfileResponse profileResponse) {
                if (profileResponse.isSuccess()) {

                    if (profileResponse != null) {
                        SharedPreferencesUtils.getInstance().putString("state", profileResponse.getState() + "");
                    }
                }
            }

            @Override
            public void failure(RestError error) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                setSelected(0);
                break;
            case R.id.st_layout:
                setSelected(1);
                break;
            case R.id.plan_layout:
                setSelected(2);
                break;
            case R.id.chat_layout:
                String studentState = SharedPreferencesUtils.getInstance().getString("state", "");
                if (!TextUtils.isEmpty(studentState) && studentState.equals("1")) {
                    setSelected(3);
                } else {
                    ToastUtils.showToastShort(getString(R.string.been_approved));
                }

                break;
            case R.id.my_layout:
                setSelected(4);
                break;

        }
        switchContent(mContent, mFragments.get(position));
    }

    private void setListener() {

    }


    public void changeFragment(int index) {
        setSelected(index);
        switchContent(mContent, mFragments.get(index));
//        onClick(plan_layout);
    }

    private void setSelected(int index) {
        position = index;
        switch (index) {
            case 0:
                home_image.setSelected(true);
                st_image.setSelected(false);
                plan_image.setSelected(false);
                my_image.setSelected(false);
                chat_image.setSelected(false);
                home_text.setSelected(true);
                st_text.setSelected(false);
                plan_text.setSelected(false);
                my_text.setSelected(false);
                chat_text.setSelected(false);
                break;
            case 1:
                home_image.setSelected(false);
                st_image.setSelected(true);
                plan_image.setSelected(false);
                my_image.setSelected(false);
                chat_image.setSelected(false);
                home_text.setSelected(false);
                st_text.setSelected(true);
                plan_text.setSelected(false);
                my_text.setSelected(false);
                chat_text.setSelected(false);
                break;
            case 2:
                home_image.setSelected(false);
                st_image.setSelected(false);
                plan_image.setSelected(true);
                my_image.setSelected(false);
                chat_image.setSelected(false);
                home_text.setSelected(false);
                st_text.setSelected(false);
                plan_text.setSelected(true);
                my_text.setSelected(false);
                chat_text.setSelected(false);
                break;
            case 3:
                home_image.setSelected(false);
                st_image.setSelected(false);
                plan_image.setSelected(false);
                my_image.setSelected(false);
                chat_image.setSelected(true);

                home_text.setSelected(false);
                st_text.setSelected(false);
                plan_text.setSelected(false);
                my_text.setSelected(false);
                chat_text.setSelected(true);
                break;
            case 4:
                home_image.setSelected(false);
                st_image.setSelected(false);
                plan_image.setSelected(false);
                my_image.setSelected(true);
                chat_image.setSelected(false);
                home_text.setSelected(false);
                st_text.setSelected(false);
                plan_text.setSelected(false);
                my_text.setSelected(true);
                chat_text.setSelected(false);
                break;
        }
    }

    private void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction transaction = manager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fl_show, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private static final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private static final String[] BASIC_PERMISSIONS = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(MainActivity.this).setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    Observer<StatusCode> observer = (Observer<StatusCode>) status -> {
        //获取状态的描述
        String desc = status.getDesc();
        if (status.wontAutoLogin()) {
            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
            logout();
        }
    };

    private void logout() {
        addJob(NetmonitorManager.logout(new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                if (baseBean.isSuccess()) {
                    long userId = UserInstance.getInstance().getUserId();
                    DBManager.getInstance().getUserDao().clearSessionToken(userId);
                    NIMClient.getService(AuthService.class).logout();
                    ActivityMgr.get().backToLoginActivity("im_out_line");
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }
}
