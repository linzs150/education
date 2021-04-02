package com.newtonacademic.newtontutors.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newtonacademic.newtontutors.activities.BaseFragment;
import com.newtonacademic.newtontutors.activities.CollectionTearchActivity;
import com.newtonacademic.newtontutors.activities.QuestionActivity;
import com.newtonacademic.newtontutors.activities.RegisterTearchActivity;
import com.newtonacademic.newtontutors.activities.SettingActivity;
import com.newtonacademic.newtontutors.activities.StudentInformationActivity;
import com.newtonacademic.newtontutors.activities.WalletActivity;
import com.newtonacademic.newtontutors.beans.AmountResponse;
import com.newtonacademic.newtontutors.beans.PerfectMyProfileResponse;
import com.newtonacademic.newtontutors.commons.SharedPreferencesUtils;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.dialogs.DialogNormal;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.network.NetmonitorManager;
import com.newtonacademic.newtontutors.network.RestError;
import com.newtonacademic.newtontutors.network.RestNewCallBack;
import com.newtonacademic.newtontutors.utils.ImageLoader;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.mylibrary.MyPublicInterface;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.prototypez.appjoint.AppJoint;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/23 23:10
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class MyFragment extends BaseFragment {

    private View mView;
    private LinearLayout sign_th_layout;
    private LinearLayout collection_th_layout;
    private LinearLayout wallet_layout;
    private LinearLayout means_layout;
    private LinearLayout question_layout;
    private LinearLayout kefu_layout;
    private LinearLayout tech_layout;
    private LinearLayout setting_layout;
    private TextView wallet;
    private long mWallet = 0l;
    private ScrollView scrollview;
    private CircleImageView my_icon;
    private TextView name;
    private ImageView sex;
    private TextView tg;
    private TextView grade;
    private TextView date;
    private TextView location;
    private DialogNormal dialogNormal;
    private DialogNormal dialogNormal1;
    private String email_content = "customer@newtontutor.hk";
    private String tech_content = "tech@newtontutor.hk";
    private TextView state;
    private Context mCtx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment, container, false);
        mCtx = getActivity();
        initView();
//        initData();
        setListener();
        return mView;
    }

    private void initLaungue() {
        MyPublicInterface myPublicInterface = AppJoint.service(MyPublicInterface.class);
        myPublicInterface.changeLanguage();
    }

    private void initView() {

        my_icon = mView.findViewById(R.id.my_icon);
        name = mView.findViewById(R.id.name);
        sex = mView.findViewById(R.id.sex);
        tg = mView.findViewById(R.id.tg);
        grade = mView.findViewById(R.id.grade);
        date = mView.findViewById(R.id.date);
        location = mView.findViewById(R.id.location);


        sign_th_layout = mView.findViewById(R.id.sign_th_layout);
        collection_th_layout = mView.findViewById(R.id.collection_th_layout);
        wallet_layout = mView.findViewById(R.id.wallet_layout);
        means_layout = mView.findViewById(R.id.means_layout);
        question_layout = mView.findViewById(R.id.question_layout);
        kefu_layout = mView.findViewById(R.id.kefu_layout);
        setting_layout = mView.findViewById(R.id.setting_layout);
        wallet = mView.findViewById(R.id.wallet);
        tech_layout = mView.findViewById(R.id.tech_layout);
        state = mView.findViewById(R.id.state);
        //        scrollview = mView.findViewById(R.id.scrollview);
        //        scrollview.smoothScrollTo(0, 0);

    }

    private void initData() {
        getBaseProfile();
        getAmount();
    }

    private void getAmount() {
        addJob(NetmonitorManager.getAmount(new RestNewCallBack<AmountResponse>() {
            @Override
            public void success(AmountResponse amountResponse) {
                if (amountResponse.isSuccess()) {
                    wallet.setText(mCtx.getText(R.string.balance) + " US$ " + amountResponse.getBalance());
                }
            }

            @Override
            public void failure(RestError error) {

            }
        }));
    }

    //获取学生基本信息
    private void getBaseProfile() {
        addJob(NetmonitorManager.getMyProfile(new RestNewCallBack<PerfectMyProfileResponse>() {
            @Override
            public void success(PerfectMyProfileResponse profileResponse) {
                if (profileResponse.isSuccess()) {

                    if (profileResponse != null) {
                        SharedPreferencesUtils.getInstance().putString("state", profileResponse.getState() + "");
                        updateStudent(profileResponse);
                    }
                }
            }

            @Override
            public void failure(RestError error) {

            }
        }));
    }

    //学生基本信息
    private void updateStudent(PerfectMyProfileResponse profle) {

        if (profle != null) {
//            if (!TextUtils.isEmpty(profle.getStudentIdCard())) {
//                ImageLoader.loadAdImage(profle.getStudentIdCard(), my_icon);
//            } else {
//            }
            if (!TextUtils.isEmpty(profle.getUserPhotoUrl())) {
                ImageLoader.loadAdImage(profle.getUserPhotoUrl(), my_icon);
            } else {
            }
            if (!TextUtils.isEmpty(profle.getUserName())) {
                name.setText(profle.getUserName());
            }
            if (profle.getSex() == 1) {
                sex.setImageResource(R.drawable.boy_sex);
            } else if (profle.getSex() == 2) {
                sex.setImageResource(R.drawable.sex);
            }
            grade.setText(Utilts.schoolYear(mCtx, Integer.valueOf(profle.getSchoolYear())) + "");

            if (!TextUtils.isEmpty(profle.getBirthday())) {
                date.setText(Utilts.dateToInt(Long.valueOf(profle.getBirthday())));
            }

            if (profle.getState() == 1) {
                state.setVisibility(View.GONE);
            } else {
                state.setVisibility(View.VISIBLE);
                state.setText(Utilts.getAuditStatus(mCtx, profle.getState()));
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initLaungue();
        initData();
    }

    private void setListener() {
        wallet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, WalletActivity.class);
                mCtx.startActivity(intent);

            }
        });

        sign_th_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, RegisterTearchActivity.class);
                mCtx.startActivity(intent);
            }
        });

        means_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, StudentInformationActivity.class);
                startActivityForResult(intent, 109);
            }
        });

        setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SettingActivity.class);
                mCtx.startActivity(intent);
            }
        });

        collection_th_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, CollectionTearchActivity.class);
                mCtx.startActivity(intent);
            }
        });

        question_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, QuestionActivity.class);
                mCtx.startActivity(intent);
            }
        });

        kefu_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogNormal == null) {
                    dialogNormal = new DialogNormal(mCtx);
                }
                dialogNormal.setTitle(getString(R.string.email_to));
                dialogNormal.setContent(email_content);
                dialogNormal.setLeftBtn(getString(R.string.left_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNormal.dismiss();
                    }
                });

                dialogNormal.setRightBtn(getString(R.string.copy_email), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) mCtx.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData mClipData = ClipData.newPlainText("Label", email_content);
                        cm.setPrimaryClip(mClipData);
                        ToastUtils.showToastShort(getString(R.string.customer_service));
                        dialogNormal.dismiss();
                    }
                });

                dialogNormal.show();


                //                Intent intent = new Intent(getActivity(), P2PMessageActivity.class);
                //                startActivity(intent);

                //                if (!XXPermissions.isHasPermission(getActivity(), Permission.CALL_PHONE)) {
                //
                //                } else {
                //                    callPhone("10086");
                //                }
            }
        });


        tech_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogNormal1 == null) {
                    dialogNormal1 = new DialogNormal(mCtx);
                }
                dialogNormal1.setTitle(getString(R.string.tech_to));
                dialogNormal1.setContent(tech_content);
                dialogNormal1.setLeftBtn(getString(R.string.left_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNormal1.dismiss();
                    }
                });

                dialogNormal1.setRightBtn(getString(R.string.copy_email), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) mCtx.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData mClipData = ClipData.newPlainText("Label", email_content);
                        cm.setPrimaryClip(mClipData);
                        ToastUtils.showToastShort(getString(R.string.customer_service));
                        dialogNormal1.dismiss();
                    }
                });

                dialogNormal1.show();

            }
        });


    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109 && resultCode == 201) {
            ToastUtils.showToastShort(getString(R.string.student_save));
        }
    }
}
