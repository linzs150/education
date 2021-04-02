package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.newtonacademic.newtontutors.beans.BaseBean;
import com.newtonacademic.newtontutors.beans.LoginResponse;
import com.newtonacademic.newtontutors.beans.NotifyPlanResponse;
import com.newtonacademic.newtontutors.commons.CleanDataUtils;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.commons.SharedPreferencesUtils;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.db.DBManager;
import com.newtonacademic.newtontutors.dialogs.ActionSheetDialog;
import com.newtonacademic.newtontutors.dialogs.DialogNormal;
import com.newtonacademic.newtontutors.dialogs.DialogSettingLogout;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.language.SpUtil;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.user.UserInstance;
import com.newtonacademic.newtontutors.utils.EventBusUtils;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.mylibrary.ConstantGlobal;

import java.util.Locale;

/**
 * @创建者 Administrator
 * @创建时间 2020/5/1 0:56
 * @描述 设置
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class SettingActivity extends BaseActivity {

    private TextView tvTitle;
    private RelativeLayout leftBtnLayout;
    private TextView btn_logout;
    private DialogSettingLogout settingDialog;
    private Context mCtx;
    private LinearLayout clear_layout;
    private TextView tv_total;
    private TextView tv_login;
    private int mType = 0;//登录状态 0未登录 1已登录
    private LinearLayout about_layout;
    private LinearLayout language_layout;
    private TextView tv_language;
    private LinearLayout time_layout;
    private CheckBox radio_setting;
    private TextView tv_time;
    private int notifyPlan = 1;
    private View view1;
    private DialogNormal dialogNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        mCtx = this;
        initView();
        initPlan();
        initData();
        setListener();
    }

    private void getCurrentPlan(int plan) {
        if (plan == 0) {
            radio_setting.setChecked(false);
            time_layout.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        } else {
            radio_setting.setChecked(true);
            time_layout.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            notifyPlan = plan;
            tv_time.setText(Utilts.notifyPlan(mCtx, plan));
        }
    }

    private void initPlan() {
        addJob(NetmonitorManager.getNotifyPlan(new RestNewCallBack<NotifyPlanResponse>() {
            @Override
            public void success(NotifyPlanResponse notifyPlanResponse) {
                if (notifyPlanResponse != null) {
                    getCurrentPlan(notifyPlanResponse.getData());
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));

    }

    private void initView() {
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        btn_logout = findViewById(R.id.btn_logout);
        clear_layout = findViewById(R.id.clear_layout);
        tv_total = findViewById(R.id.tv_total);
        tv_login = findViewById(R.id.tv_login);
        about_layout = findViewById(R.id.about_layout);
        language_layout = findViewById(R.id.language_layout);
        tv_language = findViewById(R.id.tv_language);
        time_layout = findViewById(R.id.time_layout);
        tv_time = findViewById(R.id.tv_time);
        radio_setting = findViewById(R.id.radio_setting);
        view1 = findViewById(R.id.view1);
        tvTitle.setText(mCtx.getString(R.string.setting));
        initLogin();

    }

    private void initData() {
        try {

            String totalCacheSize = CleanDataUtils.getTotalCacheSize(mCtx);
            tv_total.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断是否登录
    private void initLogin() {

        String spLanguage = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
            tv_language.setText("");
        } else {
            if (spLanguage.equals("zh")) {
                if (spCountry.equals("CN")) {
                    tv_language.setText("简体中文");
                } else if (spCountry.equals("HK")) {
                    tv_language.setText("繁體中文");
                }
            } else if (spLanguage.equals("en")) {
                tv_language.setText("English");
            }
        }

        if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstance().getString(Constants.TOKEN, ""))) {
            String userInfo = SharedPreferencesUtils.getInstance().getString("userInfo", "");
            if (!TextUtils.isEmpty(userInfo)) {
                //            SharedPreferencesUtils.getInstance().putInt("uid", 0);
                //            SharedPreferencesUtils.getInstance().putString(Constants.TOKEN, "");
                LoginResponse loginResponse = JSONObject.parseObject(userInfo, LoginResponse.class);
                if (loginResponse != null) {
                    tv_login.setText("当前账号登录账号：" + (TextUtils.isEmpty(loginResponse.getUserName()) ? "" : loginResponse.getUserName()));
                }
            } else {
                tv_login.setText("当前账号登录账号：" + "");
            }
            btn_logout.setText(mCtx.getString(R.string.log_out));
            mType = 1;
        } else {
            tv_login.setText("您当前没有登录账号");
            btn_logout.setText(mCtx.getString(R.string.log_in));
            mType = 0;
        }
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radio_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifyPlan = 1;
                    getNotifyPlan(notifyPlan);
                } else {
                    notifyPlan = 0;
                    getNotifyPlan(notifyPlan);
                }
                getCurrentPlan(notifyPlan);
            }
        });

        time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotifyPlan();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 1) {
                    if (settingDialog == null) {
                        settingDialog = new DialogSettingLogout(mCtx);

                    }
                    settingDialog.show();
                    settingDialog.setCancel(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingDialog.dismiss();
                        }
                    });

                    settingDialog.setExit(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingDialog.dismiss();
                            logout();
                        }
                    });
                } else if (mType == 0) {
                    Intent intent = new Intent(mCtx, BootActivity.class);
                    mCtx.startActivity(intent);
                    finish();
                }
            }
        });

        clear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogNormal == null) {
                    dialogNormal = new DialogNormal(mCtx);
                }
                dialogNormal.setTitle(mCtx.getString(R.string.clear_cache));
                dialogNormal.setLeftBtn(mCtx.getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNormal.dismiss();
                    }
                });

                dialogNormal.setRightBtn(mCtx.getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNormal.dismiss();
                        clearCache();
                    }
                });

                dialogNormal.show();

            }
        });

        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                mCtx.startActivity(intent);
            }
        });

        language_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLanguage();
            }
        });
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        String totalCacheSize = CleanDataUtils.getTotalCacheSize(mCtx);
                        if (!TextUtils.isEmpty(totalCacheSize)) {
                            tv_total.setText(totalCacheSize);
                        }
                    } catch (Exception e) {
                    }
                    break;
            }
            return false;
        }
    });

    //清除缓存
    private void clearCache() {
        CleanDataUtils.clearAllCache(mCtx);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToastShort(mCtx.getString(R.string.cache_cleared));
                mHandler.sendEmptyMessage(1);
            }
        }, 500);

        CleanDataUtils.clearWebViewCache1();

    }

    private void logout() {
        addJob(NetmonitorManager.logout(new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
                if (baseBean.isSuccess()) {
                    long userId = UserInstance.getInstance().getUserId();
                    DBManager.getInstance().getUserDao().clearSessionToken(userId);
                    NIMClient.getService(AuthService.class).logout();
//                    ActivityMgr.get().backToLoginActivity(null);
                    ActivityMgr.get().backToLoginActivity(null);
                }
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }


    //设置提醒
    private void createNotifyPlan() {

        new ActionSheetDialog(mCtx).builder().setCancelable(false).setTitle(mCtx.getString(R.string.set_alarm_time)).setCanceledOnTouchOutside(false)
                .addSheetItem(mCtx.getString(R.string.on_time), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_time.setText(Utilts.notifyPlan(mCtx, 1));
                        notifyPlan = 1;
                        getNotifyPlan(notifyPlan);

                    }
                }).addSheetItem(mCtx.getString(R.string.mins_prior), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                tv_time.setText(Utilts.notifyPlan(mCtx, 2));
                notifyPlan = 2;
                getNotifyPlan(notifyPlan);

            }


        }).addSheetItem(mCtx.getString(R.string.hour_prior), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                tv_time.setText(Utilts.notifyPlan(mCtx, 3));
                notifyPlan = 3;
                getNotifyPlan(notifyPlan);

            }


        }).show();

    }

    private void getNotifyPlan(int type) {
        addJob(NetmonitorManager.notifyPlanModify(type, new RestNewCallBack<BaseBean>() {
            @Override
            public void success(BaseBean baseBean) {
            }

            @Override
            public void failure(RestError error) {
                ToastUtils.showToastShort(error.msg);
            }
        }));
    }

    //设置多语言
    private void createLanguage() {

        new ActionSheetDialog(mCtx).builder().setCancelable(false).setTitle(mCtx.getString(R.string.set_language)).setCanceledOnTouchOutside(false)
                .addSheetItem("English", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_language.setText("English");
                        languageChanage(0);

                    }
                }).addSheetItem("繁體中文", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                tv_language.setText("繁體中文");
                languageChanage(1);

            }


        }).addSheetItem("简体中文", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                tv_language.setText("简体中文");
                languageChanage(2);

            }


        }).show();
    }

    //
    private void languageChanage(int type) {
        String language = "";
        String country = "";
        switch (type) {
            case 0:
                language = "en";
                country = "US";
                break;
            case 1:
                language = "zh";
                country = "HK";
                break;
            case 2:
                language = "zh";
                country = "CN";
                break;
            default:
                break;
        }
//        SPUtils.setLanguageLocal(SettingActivity.this, language);

        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(country)) {
            //如果语言和地区都是空，那么跟随系统
            SpUtil.saveString(ConstantGlobal.LOCALE_LANGUAGE, "");
            SpUtil.saveString(ConstantGlobal.LOCALE_COUNTRY, "");
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            Locale newLocale = new Locale(language, country);
            EventBusUtils.post(newLocale);
            SpUtil.saveString(ConstantGlobal.LOCALE_LANGUAGE, language);
            SpUtil.saveString(ConstantGlobal.LOCALE_COUNTRY, country);
        }

    }


}
