package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.newtonacademic.newtontutors.beans.ImageBean;
import com.newtonacademic.newtontutors.beans.PerfectMyProfileResponse;
import com.newtonacademic.newtontutors.beans.StudentBeanInfo;
import com.newtonacademic.newtontutors.commons.LogUtils;
import com.newtonacademic.newtontutors.commons.ToastUtils;
import com.newtonacademic.newtontutors.dialogs.ActionSheetDialog;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.utils.ImageLoader;
import com.newtonacademic.newtontutors.utils.ProviderUtil;
import com.newtonacademic.newtontutors.utils.Utilts;
import com.newtonacademic.newtontutors.widget.NewTimePickerView;
import com.newtonacademic.mylibrary.ConstantGlobal;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @????????? Administrator
 * @???????????? 2020/4/29 22:13
 * @?????? ${TODO}
 * @????????? $Author$
 * @???????????? $Date$
 * @???????????? ${TODO}
 **/

public class StudentsImproveInformation extends BaseActivity {

    private RelativeLayout leftBtnLayout;
    private TextView tvTitle;
    private LinearLayout edit_layout;
    private TextView iv_next;
    private Context mCtx;
    private final int PHOTO_CROP = 1;
    private final int CAPTURE_CROP = 2;
    private ImageView tv_student_card;
    private int dw = 150;
    private int dh = 150;
    private EditText etSurname;
    private EditText etInputFirst;
    private TextView etInputBirth;
    private EditText etInputContact;
    private EditText etInputSchool;
    private TextView etInputCurrent;
    private RadioButton radio_man;
    private RadioButton radio_female;
    private EditText etInputGener;
    private RadioButton radio_basic;
    private RadioButton radio_interme;
    private RadioButton radio_fluent;
    private PerfectMyProfileResponse perfectMyProfileResponse;
    private CircleImageView my_icon;
    private String birthDay = "";
    private EditText etInputPhone;
    private EditText etInputSkype;
    private EditText etInputWechat;
    private int mYearGener = 0;
    private StudentBeanInfo beanInfo;
    private LinearLayout tv_student_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_improve_information);
        initView();
        initData();

        setListener();
    }


    private void initView() {

        leftBtnLayout = findViewById(R.id.leftBtnLayout);
        tvTitle = findViewById(R.id.tvTitle);
        iv_next = findViewById(R.id.iv_next);
        tv_student_card = findViewById(R.id.tv_student_card);

        etSurname = findViewById(R.id.etSurname);
        etInputFirst = findViewById(R.id.etInputFirst);
        etInputBirth = findViewById(R.id.etInputBirth);
        etInputContact = findViewById(R.id.etInputContact);
        etInputSchool = findViewById(R.id.etInputSchool);
        etInputCurrent = findViewById(R.id.etInputCurrent);
        radio_man = findViewById(R.id.radio_man);
        radio_female = findViewById(R.id.radio_female);
        etInputGener = findViewById(R.id.etInputGener);
        radio_basic = findViewById(R.id.radio_basic);
        radio_interme = findViewById(R.id.radio_interme);
        radio_fluent = findViewById(R.id.radio_fluent);
        my_icon = findViewById(R.id.my_icon);
        etInputPhone = findViewById(R.id.etInputPhone);
        etInputSkype = findViewById(R.id.etInputSkype);
        etInputWechat = findViewById(R.id.etInputWechat);
        tv_student_layout = findViewById(R.id.tv_student_layout);

        tvTitle.setText(getString(R.string.complete_student_info));
        mCtx = this;

    }

    private void initData() {
        perfectMyProfileResponse = (PerfectMyProfileResponse) getIntent().getSerializableExtra("student");
        beanInfo = (StudentBeanInfo) getIntent().getSerializableExtra("register_student");
        initTimePicker();
        if (perfectMyProfileResponse != null) {
            etSurname.setText(TextUtils.isEmpty(perfectMyProfileResponse.getLastName()) ? "" : perfectMyProfileResponse.getLastName());
            etInputFirst.setText(TextUtils.isEmpty(perfectMyProfileResponse.getFirstName()) ? "" : perfectMyProfileResponse.getFirstName());
            etInputBirth.setText(Utilts.dateToInt(Long.valueOf(perfectMyProfileResponse.getBirthday())));
            birthDay = perfectMyProfileResponse.getBirthday();
            etInputContact.setText(TextUtils.isEmpty(perfectMyProfileResponse.getEmail()) ? "" : perfectMyProfileResponse.getEmail());
            etInputSchool.setText(TextUtils.isEmpty(perfectMyProfileResponse.getSchool()) ? "" : perfectMyProfileResponse.getSchool());
            mYearGener = Integer.valueOf(perfectMyProfileResponse.getSchoolYear());
            etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, Integer.valueOf(perfectMyProfileResponse.getSchoolYear())));
            if (perfectMyProfileResponse.getSex() == 1) {
                radio_man.setChecked(true);
            } else if (perfectMyProfileResponse.getSex() == 2) {
                radio_female.setChecked(true);
            } else {
                radio_female.setChecked(false);
                radio_man.setChecked(false);
                etInputGener.setText("??????");
            }

//            etInputGener.setText(Utilts.schoolYear(Integer.valueOf(perfectMyProfileResponse.getSchoolYear())));
            etInputSkype.setText(perfectMyProfileResponse.getSkype());
            etInputWechat.setText(perfectMyProfileResponse.getWechat());
            etInputPhone.setText(perfectMyProfileResponse.getMobileNO());

            if (!TextUtils.isEmpty(perfectMyProfileResponse.getUserPhotoUrl())) {
                ImageLoader.loadAdImage(perfectMyProfileResponse.getUserPhotoUrl(), my_icon);
            } else {
            }

            if (!TextUtils.isEmpty(perfectMyProfileResponse.getStudentIdCard())) {
                tv_student_card.setVisibility(View.VISIBLE);
                tv_student_layout.setVisibility(View.GONE);
                ImageLoader.loadAdImage(perfectMyProfileResponse.getStudentIdCard(), tv_student_card);
            } else {
                tv_student_card.setVisibility(View.GONE);
                tv_student_layout.setVisibility(View.VISIBLE);
            }

            if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 1) {
                radio_basic.setChecked(true);
            } else if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 2) {
                radio_interme.setChecked(true);
            } else if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 3) {
                radio_fluent.setChecked(true);
            }

            studentCard = perfectMyProfileResponse.getStudentIdCard();

            studentPhoto = perfectMyProfileResponse.getUserPhotoUrl();

        }
    }

    NewTimePickerView pvTime1;

    //??????????????????
    private void initTimePicker() {

        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");

        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);
        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);
        Calendar selectedDate = Calendar.getInstance();//??????????????????
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);

        //???????????????

        pvTime1 = new NewTimePickerView.Builder(this, new NewTimePickerView.OnTimeSelectListener() {

            @Override

            public void onTimeSelect(Date date, View v) {//??????????????????

                // ?????????????????????v,??????show()???????????????????????? View ???????????????show??????????????????????????????v??????null

                /*btn_Time.setText(getTime(date));*/

//                birthDay = ""+date.getYear() + date.getMonth() + date.getDay();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                birthDay = "" + year + (month <= 9 ? "0" + month : month) + (day <= 9 ? "0" + day : day);
                etInputBirth.setText(Utilts.getTime(date));

            }

        })
                .setType(new boolean[]{true, true, true, false, false, false}) //?????????????????? ????????????????????????????????????????????????
                .setLabel("???", "???", "???", "", "", "")//?????????????????????????????????
                .isCenterLabel(true)
                .setTitleText(getString(R.string.input_birth))
                .setDividerColor(Color.parseColor("#999999"))
                .setTextColorCenter(Color.parseColor("#333333"))//????????????????????????
                .setTextColorOut(Color.parseColor("#999999"))//?????????????????????????????????
                .setContentSize(21)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0, 10, 0, 0, 0)//??????X???????????????[ -90 , 90??]
                .setRangDate(startDate, endDate)
                .setCancelColor(Color.parseColor("#393A3A"))
                .setSubmitColor(Color.parseColor("#393A3A"))
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.sure))
                .setSubCalSize(13)
                .setTitleSize(13)
                .setDecorView(null)//                .setBackgroundId(0x00FFFFFF) //????????????????????????
                .build();
    }


    private void setListener() {


        my_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPhoto(0);
            }
        });

        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNext();
            }
        });

        tv_student_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPhoto(1);
            }
        });

        etInputBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                initTimePicker();
                pvTime1.show();
            }
        });

        etInputCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (actionSheetDialog != null) {
//                    actionSheetDialog.show();
//                } else {
//                    createGrade();
//                    actionSheetDialog.show();
//                }
                createGener();
            }
        });
    }

    private void createGener() {
        new ActionSheetDialog(this).builder().setCancelable(false).setTitle(getString(R.string.select_your_school)).setCanceledOnTouchOutside(false)
                .addSheetItem(getString(R.string.juniormiddle1), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        mYearGener = 7;
                        etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
                    }
                }).addSheetItem(getString(R.string.juniormiddle2), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                mYearGener = 8;
                etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
            }


        }).addSheetItem(getString(R.string.juniormiddle3), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                mYearGener = 9;
                etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
            }


        }).addSheetItem(getString(R.string.high1), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                mYearGener = 10;
                etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
            }


        }).addSheetItem(getString(R.string.high2), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                mYearGener = 11;
                etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
            }


        }).addSheetItem(getString(R.string.high3), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                mYearGener = 12;
                etInputCurrent.setText(Utilts.schoolYear(StudentsImproveInformation.this, mYearGener));
            }


        }).show();
    }


    private void setNext() {


        if (TextUtils.isEmpty(etInputFirst.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }

        if (TextUtils.isEmpty(etSurname.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }

        if (TextUtils.isEmpty(etInputBirth.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }
        if (TextUtils.isEmpty(etInputContact.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }
        if (TextUtils.isEmpty(etInputPhone.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }
        if (TextUtils.isEmpty(etInputCurrent.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.complete_info));
            return;
        }
        if (!Utilts.isMobileNO(etInputPhone.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.please_input_correct_phone));
            return;
        }

        if (!Utilts.isEmail(etInputContact.getText().toString().trim())) {
            ToastUtils.showToastShort(getString(R.string.please_input_correct_email));
            return;
        }

        if (perfectMyProfileResponse == null) {
            perfectMyProfileResponse = new PerfectMyProfileResponse();
        }


        if (perfectMyProfileResponse != null) {
            //            etSurname.setText(TextUtils.isEmpty(perfectMyProfileResponse.getLastName()) ? "" : perfectMyProfileResponse.getLastName());

            if (TextUtils.isEmpty(etSurname.getText().toString().trim()) && TextUtils.isEmpty(etInputFirst.getText().toString().trim())) {
                ToastUtils.showToastShort(getString(R.string.enter_your_name));
                return;
            }

            perfectMyProfileResponse.setLastName(etSurname.getText().toString().trim());
            perfectMyProfileResponse.setUserName(etSurname.getText().toString() + etInputFirst.getText().toString().trim());
            //            etInputFirst.setText(TextUtils.isEmpty(perfectMyProfileResponse.getFirstName()) ? "" : perfectMyProfileResponse.getFirstName());
            perfectMyProfileResponse.setFirstName(etInputFirst.getText().toString().trim());
            //            etInputBirth.setText(Utilts.dateToInt(Long.valueOf(perfectMyProfileResponse.getBirthday())));
//            perfectMyProfileResponse.setBirthday(etInputBirth.getText().toString().trim());
            perfectMyProfileResponse.setBirthday(birthDay);
            //            etInputContact.setText(TextUtils.isEmpty(perfectMyProfileResponse.getEmail()) ? "" : perfectMyProfileResponse.getEmail());
            perfectMyProfileResponse.setEmail(etInputContact.getText().toString().trim());
            //            etInputSchool.setText(TextUtils.isEmpty(perfectMyProfileResponse.getSchool()) ? "" : perfectMyProfileResponse.getSchool());
            perfectMyProfileResponse.setSchool(etInputSchool.getText().toString().trim());
            //            etInputCurrent.setText(Utilts.schoolYear(Integer.valueOf(perfectMyProfileResponse.getSchoolYear())));

            perfectMyProfileResponse.setSchoolYear(mYearGener + "");
            perfectMyProfileResponse.setSkype(etInputSkype.getText().toString().trim());
            perfectMyProfileResponse.setWechat(etInputWechat.getText().toString().trim());
            perfectMyProfileResponse.setMobileNO(etInputPhone.getText().toString().trim());

            //            if (perfectMyProfileResponse.getSex() == 1) {
            //                radio_man.setChecked(true);
            //            } else if (perfectMyProfileResponse.getSex() == 2) {
            //                radio_female.setChecked(true);
            //            } else {
            //                radio_female.setChecked(false);
            //                radio_man.setChecked(false);
            //                etInputGener.setText("??????");
            //            }

            if (radio_man.isChecked()) {
                perfectMyProfileResponse.setSex(1);
            } else {
                perfectMyProfileResponse.setSex(2);
            }


            //            if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 1) {
            //                radio_basic.setChecked(true);
            //            } else if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 2) {
            //                radio_interme.setChecked(true);
            //            } else if (Integer.valueOf(perfectMyProfileResponse.getEnglishSpokenLevel()) == 3) {
            //                radio_fluent.setChecked(true);
            //            }

            if (radio_basic.isChecked()) {
                perfectMyProfileResponse.setEnglishSpokenLevel(1 + "");
            } else if (radio_fluent.isChecked()) {
                perfectMyProfileResponse.setEnglishSpokenLevel(2 + "");

            } else if (radio_interme.isChecked()) {
                perfectMyProfileResponse.setEnglishSpokenLevel(3 + "");

            }

            //            studentCard = perfectMyProfileResponse.getStudentIdCard();
            perfectMyProfileResponse.setStudentIdCard(studentCard);
//            perfectMyProfileResponse.setUserPhotoUrl(studentPhoto);
            perfectMyProfileResponse.setUserPhoto(studentPhoto);
        }


        Intent intent = new Intent(mCtx, StudentInformationStepActivity.class);
        intent.putExtra("profile", perfectMyProfileResponse);
        if (beanInfo != null) {
            intent.putExtra("register_student", beanInfo);
        }
        startActivityForResult(intent, 12);
    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if (resultCode == 92 && requestCode == 12) {
    //            setResult(93);
    //            finish();
    //        }
    //    }

    ActionSheetDialog actionSheetDialog;

    //????????????
    private void createGrade() {

        String[] temp = getResources().getStringArray(R.array.grade_list);

        final List<String> grades = new ArrayList<>();

        for (String str : temp) {

            grades.add(str);
        }

        actionSheetDialog = new ActionSheetDialog(this).builder().setTitle("???????????????").addSheetItems(grades, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                etInputCurrent.setText(grades.get(which));
            }
        });

    }

    private void createPhoto(int type) {

        this.type = type;

        new ActionSheetDialog(this).builder().setCancelable(false).setTitle(mCtx.getString(R.string.select_photo)).setCanceledOnTouchOutside(false)
                .addSheetItem(mCtx.getString(R.string.camera), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        if (!XXPermissions.isHasPermission(StudentsImproveInformation.this, Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)) {
                            XXPermissions.with(StudentsImproveInformation.this)
//                                    .constantRequest() //????????????????????????????????????????????????????????????????????????
                                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE) //????????????6.0???????????????8.0??????????????????
                                    .request(new OnPermission() {

                                        @Override
                                        public void hasPermission(List<String> granted, boolean isAll) {
                                            gotoCamera(type);
                                        }

                                        @Override
                                        public void noPermission(List<String> denied, boolean quick) {
                                            ToastUtils.showToastShort(mCtx.getString(R.string.your_camera));
                                        }
                                    });
                        } else {
                            gotoCamera(type);
                        }
                    }
                }).addSheetItem(mCtx.getString(R.string.photo_album), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {


                if (!XXPermissions.isHasPermission(StudentsImproveInformation.this, Permission.READ_EXTERNAL_STORAGE)) {
                    XXPermissions.with(StudentsImproveInformation.this)
//                            .constantRequest() //????????????????????????????????????????????????????????????????????????
                            .permission(Permission.READ_EXTERNAL_STORAGE) //????????????6.0???????????????8.0??????????????????
                            .request(new OnPermission() {

                                @Override
                                public void hasPermission(List<String> granted, boolean isAll) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(intent, PHOTO_CROP);
                                }

                                @Override
                                public void noPermission(List<String> denied, boolean quick) {
//                                    ToastUtils.showToastShort("???????????????????????????????????????");
                                    ToastUtils.showToastShort(mCtx.getString(R.string.your_photo));
                                }
                            });
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, PHOTO_CROP);
                }


            }


        }).show();
    }

    private int type = 0;

    private void gotoCamera(int type) {
        this.type = type;
        File outputfile = new File(getActivity().getExternalCacheDir(), "output.png");
        try {
            if (outputfile.exists()) {
                outputfile.delete();//??????
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            imageuri = Uri.fromFile(outputfile);
        } else {
            /**
             * 7.0 ??????????????????????????????????????????Uri????????????????????????FileProvider
             * ????????????????????????MIUI?????????????????????size???0?????????
             */
            imageuri = FileProvider.getUriForFile(this, ProviderUtil.getFileProviderName(this), outputfile);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, CAPTURE_CROP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 12:
                if (resultCode == 92) {
                    setResult(93);
                    finish();
                }
                break;
            case PHOTO_CROP:
                if (resultCode == RESULT_OK) {
                    startActivityForResult(CutForPhoto(data.getData()), 3);
                }

                break;
            case CAPTURE_CROP:
                if (resultCode == RESULT_OK) {
                    //                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    //                    cropPhoto(Uri.fromFile(temp));// ????????????
                    String path = this.getExternalCacheDir().getPath();
                    String name = "output.png";
                    startActivityForResult(CutForCamera(path, name), 3);

                }

                break;
            case 3:
                if (mCutUri != null) {
                    try {
                        //??????????????????????????????????????????
                        Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(mCutUri));
                        if (bitmap != null) {
                            String file = ProviderUtil.bitmaptoString(bitmap);
                            updatePic(file);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    Uri mCutUri = null;

    private File cutfile;

    /**
     * ????????????
     *
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        try {
            //????????????
            Intent intent = new Intent("com.android.camera.action.CROP");
            //???????????????????????????????????????
            cutfile = new File(Environment.getExternalStorageDirectory().getPath(), "cutcamera.png"); //??????????????????
            if (cutfile.exists()) { //?????????????????????????????????,?????????????????????????????????????????????????????????????????????????????????????????????
                cutfile.delete();
            }
            cutfile.createNewFile();
            //????????? uri
            Uri imageUri = uri; //???????????? uri
            Uri outputUri = null; //????????? uri
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            // crop???true?????????????????????intent??????????????????view????????????
            intent.putExtra("crop", true);
            // aspectX,aspectY ??????????????????????????????????????????
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //????????????????????????
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("scale", true);
            //??????????????????????????????oom??????????????????false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //????????????
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ???????????????????????????
     *
     * @param camerapath ??????
     * @param imgname    img ?????????
     * @return
     */
    @NonNull
    private Intent CutForCamera(String camerapath, String imgname) {
        try {

            //???????????????????????????????????????
            cutfile = new File(Environment.getExternalStorageDirectory().getPath(), "cutcamera.png"); //??????????????????
            if (cutfile.exists()) { //?????????????????????????????????,?????????????????????????????????????????????????????????????????????????????????????????????
                cutfile.delete();
            }
            cutfile.createNewFile();
            //????????? uri
            Uri imageUri = null; //???????????? uri
            Uri outputUri = null; //????????? uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //?????????????????????
            File camerafile = new File(camerapath, imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(this, "com.one.education.education.provider", camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //????????? uri ????????????????????????????????? bitmap???
            mCutUri = outputUri;
            // crop???true?????????????????????intent??????????????????view????????????
            intent.putExtra("crop", true);
            // aspectX,aspectY ??????????????????????????????????????????
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //????????????????????????
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("scale", true);
            //??????????????????????????????oom??????????????????false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //????????????
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String file;

    //????????????
    private void updatePic(String file) {
        this.file = file;
        //        addJob(NetmonitorManager.uploadPic(file, "edu", dw, dh, new RestNewCallBack<UploadPicUResponse>() {
        //            @Override
        //            public void success(UploadPicUResponse uploadPicUResponse) {
        //                if (uploadPicUResponse.isSuccess()) {
        //                    LogUtils.e("ceshi", JSONObject.toJSONString(uploadPicUResponse));
        //                }
        //            }
        //
        //            @Override
        //            public void failure(RestError error) {
        //                ToastUtils.showToastShort(error.msg);
        //            }
        //        }));

        new Thread(runnable).start();

    }

    private String studentCard = "";
    private String studentPhoto = "";


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String tt = (String) msg.obj;

            ImageBean bean = JSONObject.parseObject(tt, ImageBean.class);

            if (bean.getStatus() == 1) {
                if (type == 0) {
                    studentPhoto = bean.getUrl();
                    ImageLoader.loadAdImage(studentPhoto, my_icon);

                } else if (type == 1) {
                    studentCard = bean.getUrl();
                    ImageLoader.loadAdImage(studentCard, tv_student_card);
                }
            }
            LogUtils.e("ceshi", tt);

        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: http request.
            //            Message msg = new Message();
            //            Bundle data = new Bundle();
            //            Map<String, String> map = new HashMap<>();
            //            map.put("fn", "edu");
            //            map.put("dw", dw + "");
            //            map.put("dh", dh + "");
            //            String result = UpdateImage.uploadFile(new File(file), Constants.Net.URL + "upload/uploadPic", map);
            //            LogUtils.e("ceshi", result + "");
            //            data.putString("value", result);
            //            msg.setData(data);
            //            handler.sendMessage(msg);

            String imge = uploadImage(ConstantGlobal.Net.URL + "upload/uploadPic", cutfile, "cutcamera.png");
            Message message = new Message();
            message.obj = imge;
            handler.sendMessage(message);

        }

    };


    private File f;

    private File convertBitmapToFile(Bitmap bitmap) {
        try {
            // create a file to write bitmap data
            f = new File(this.getCacheDir(), "portrait");
            f.createNewFile();

            // convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {

        }
        return f;
    }


    // ????????????
    private String uploadResult;

    public String uploadImage(String URL, File file, String fileName) {
        try {
            // MultipartBuilder?????????????????????query
            // addFormDataPart?????????@param [String]name, [String]value
            // addFormDataPart?????????@param [String]name, [String]fileName, [String]fileType, [String]file
            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("uploadFile", fileName,
                            RequestBody.create(MediaType.parse("image/png"), file))
                    .addFormDataPart("fn", "edu")
                    //                    .addFormDataPart("action", "updateProtrait")
                    .addFormDataPart("dw", "150")
                    .addFormDataPart("dh", "150")
                    .build();

            // request????????? @param [String]URL, [RequestBody]requestBody
            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            // response????????????????????????
            Response response = new OkHttpClient().newCall(request).execute();
            // ???response?????????string
            uploadResult = response.body().string();

        } catch (IOException e) {
            uploadResult = e.toString();
        }
        return uploadResult;
    }

}
