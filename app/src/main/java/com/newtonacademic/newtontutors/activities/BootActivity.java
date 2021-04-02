package com.newtonacademic.newtontutors.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.newtonacademic.newtontutors.db.DBManager;
import com.newtonacademic.newtontutors.db.IDMLResultListener;
import com.newtonacademic.newtontutors.db.bean.DUser;
import com.newtonacademic.newtontutors.MainActivity;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.login.LoginActivity;
import com.newtonacademic.newtontutors.register.RegisterStep2Activity;
import com.newtonacademic.newtontutors.user.UserInfo;
import com.newtonacademic.newtontutors.user.UserInstance;
import com.newtonacademic.newtontutors.widget.NimOutLineDialog;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-04-26
 * @desc 启动页
 * @email fzhlaiyy@intretech.com
 */
public class BootActivity extends AppCompatActivity {
    private static final String TAG = "BootActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boot);
        findViewById(R.id.register_btn).setOnClickListener(v -> {
            startActivityForResult(RegisterStep2Activity.newIntent(BootActivity.this), 20001);
        });

        findViewById(R.id.login_btn).setOnClickListener(v -> startActivityForResult(LoginActivity.newIntent(BootActivity.this), 20001));
        checkPermission();

        Intent intent = getIntent();
        if (intent != null) {
            String info = intent.getStringExtra("event");
            if (info != null && info.equals("im_out_line")) {
                showImOutLineDialog();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20001 && resultCode == 95) {
            finish();
        }
    }

    private void showImOutLineDialog() {
        if (mNimOutLineDialog != null && mNimOutLineDialog.isShowing()) {
            return;
        }

        mNimOutLineDialog = new NimOutLineDialog(BootActivity.this);
        mNimOutLineDialog.show();

    }

    private NimOutLineDialog mNimOutLineDialog = null;

    private final IDMLResultListener<DUser> mGetLatestLoginUser = result -> runOnUiThread(() -> {
        if (isFinishing()) {
            Log.i(TAG, "isFinishing.");
            return;
        }

        if (null == result) {
            Log.i(TAG, "latest login user is null.");
            findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
            return;
        }

        if (!TextUtils.isEmpty(result.getToken())) {
            //免密登录
            UserInfo userInfo = new UserInfo();
            userInfo.setToken(result.getToken());
            userInfo.setAccount(result.getAccount());
            userInfo.setMobileNO(result.getCellphone());
            userInfo.setLastLoginTime(result.getLastLoginTime());
            userInfo.setSex(result.getSex());
            userInfo.setUid((int) result.getUserId());
            userInfo.setUserName(result.getUserName());
            userInfo.setUserNick(result.getUserNick());
            userInfo.setUserPhoto(result.getIconResource());
            userInfo.setUserType(result.getUserType());
            UserInstance.getInstance().initialize(userInfo);
            startActivity(new Intent(BootActivity.this, MainActivity.class));
            finish();
        } else {
            findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
        }
    });

    private void checkPermission() {
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        DBManager.getInstance().getUserDao().getLatestLoginRecord(mGetLatestLoginUser);
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        Log.i(TAG, "quick = " + quick);
                        DBManager.getInstance().getUserDao().getLatestLoginRecord(mGetLatestLoginUser);
                    }
                });
    }

}
