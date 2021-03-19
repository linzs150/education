package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.one.education.beans.SubjectResponse;
import com.one.education.commons.LogUtils;
import com.one.education.education.R;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<SubjectResponse.Subject> subjectList;
    private List<SubjectResponse.Subject> choiceList;

    private SearchListener listener;

    public List<SubjectResponse.Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectResponse.Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public interface SearchListener {
        void click(SubjectResponse.Subject subject);

    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void updateTeacherInfo(List<SubjectResponse.Subject> subjectList) {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public void getSubjectInfo(List<SubjectResponse.Subject> subjectList) {
        choiceList = subjectList;
    }

    public List<SubjectResponse.Subject> getSubjectInfo() {
//        choiceList = subjectList;
        return subjectList;
    }

    public CourseAdapter(Context context, List<SubjectResponse.Subject> list) {
        mCtx = context;
        subjectList = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return subjectList == null ? 0 : subjectList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HolderView holderView = (HolderView) holder;
        SubjectResponse.Subject subject = subjectList.get(position);

        if (subject.getIsNeedLevel() == 0) {
            holderView.item1_layout.setVisibility(View.VISIBLE);
            holderView.item2_layout.setVisibility(View.GONE);
            holderView.item3_layout.setVisibility(View.GONE);

            if (subject.getIsNeedMark() == 0) {
                holderView.course_name1.setVisibility(View.VISIBLE);
                holderView.et_subject1.setVisibility(View.GONE);
                holderView.et_subject1_layout.setVisibility(View.GONE);
                holderView.course_name1.setText(subject.getSubjectName());
            } else if (subject.getIsNeedMark() == 1 || subject.getIsNeedMark() == 2) {
                holderView.course_name1.setVisibility(View.VISIBLE);
                holderView.et_subject1.setVisibility(View.VISIBLE);
                holderView.et_subject1_layout.setVisibility(View.VISIBLE);
                holderView.et_subject1.setText(subject.getMark());
                holderView.course_name1.setText(subject.getSubjectName());
                holderView.et_subject1.setHint(TextUtils.isEmpty(subject.getMarkHoldPlace()) ? "" : subject.getMarkHoldPlace());
            }

            holderView.cb_subject1.setChecked(subject.isCheck());

            if (subject.getSubjectName().equals("Theory of Knowledge") || subject.getSubjectName().equals("Extended Essay")) {
                holderView.cb_subject1.setChecked(true);
                subject.setCheck(true);
                holderView.cb_subject1.setClickable(false);
            }

            holderView.et_subject1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    subject.setMark(s.toString());
                }
            });

            holderView.et_subject3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    subject.setMark(s.toString());
                }
            });


            holderView.cb_subject1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                    if (isChecked) {
//                        subject.setCheck(false);
//                    } else {
//                        subject.setCheck(true);
////                        subjectList.get(position).setCheck(!isChecked);
//                    }
                    subject.setCheck(isChecked);

//                    if(subject.getSubjectName().equals("Theory of Knowledge") || subject.getSubjectName().equals("Extended Essay")){
//                        holderView.cb_subject1.setChecked(true);
//                        holderView.cb_subject1.setClickable(false);
//                    }

                    listener.click(subject);

                }
            });

//            if (subjectList.get(position).isCheck()) {
//                holderView.cb_subject1.setChecked(true);
//            }


        } else if (subject.getIsNeedLevel() == 1) {
            holderView.item1_layout.setVisibility(View.GONE);
            if (subject.getIsNeedMark() == 0) {
                holderView.item2_layout.setVisibility(View.VISIBLE);
                holderView.item3_layout.setVisibility(View.GONE);
                holderView.course_name2.setText(subject.getSubjectName());
                holderView.cb_subject2.setChecked(subject.isCheck());
                if (subject.getSubjectName().equals("Theory of Knowledge") || subject.getSubjectName().equals("Extended Essay")) {
                    holderView.cb_subject2.setChecked(true);
                    subject.setCheck(true);
                    holderView.cb_subject2.setClickable(false);
                }
                if (subject.getSubjectLevel() == 1) {
                    holderView.tv_higher2.setChecked(true);

                } else if (subject.getSubjectLevel() == 2) {
                    holderView.tv_standard2.setChecked(true);
                } else {
                    holderView.tv_higher2.setChecked(false);
                    holderView.tv_standard2.setChecked(false);
                }

                holderView.cb_subject2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        LogUtils.e("lzs", isChecked + "");
                        if (!isChecked) {
                            holderView.tv_higher2.setChecked(false);
                            holderView.tv_standard2.setChecked(false);
                            subject.setCheck(false);
                        } else {
                            subject.setCheck(true);
                            if (subject.getSubjectLevel() == 1) {
                                holderView.tv_higher2.setChecked(true);

                                holderView.tv_standard2.setChecked(false);
                            } else if (subject.getSubjectLevel() == 2) {
                                holderView.tv_higher2.setChecked(false);
                                holderView.tv_standard2.setChecked(true);
                            } else if (subject.getSubjectLevel() == 0) {
                                holderView.tv_higher2.setChecked(false);
                                holderView.tv_standard2.setChecked(false);
                            }
                            listener.click(subject);
                        }
                    }
                });

                holderView.tv_higher2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {

                        } else {
//                            if (!holderView.cb_subject2.isChecked()) {
                                holderView.cb_subject2.setChecked(true);
                                holderView.tv_higher2.setChecked(true);
                                subject.setCheck(true);
                                subject.setSubjectLevel(1);
                                listener.click(subject);
//                            }
                        }
                    }
                });

                holderView.tv_standard2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {

                        } else {
//                            if (!holderView.cb_subject2.isChecked()) {
                                holderView.cb_subject2.setChecked(true);
                                holderView.tv_standard2.setChecked(true);
                                subject.setCheck(true);
                                subject.setSubjectLevel(2);
                                listener.click(subject);
//                            }
                        }
                    }
                });

            } else if (subject.getIsNeedMark() == 1 || subject.getIsNeedMark() == 2) {

                if (subject.getSubjectLevel() == 1) {
                    holderView.tv_higher2.setChecked(true);

                } else if (subject.getSubjectLevel() == 2) {
                    holderView.tv_standard2.setChecked(true);
                } else {
                    holderView.tv_higher2.setChecked(false);
                    holderView.tv_standard2.setChecked(false);
                }

                holderView.cb_subject3.setChecked(subject.isCheck());


                if (subject.getSubjectName().equals("Theory of Knowledge") || subject.getSubjectName().equals("Extended Essay")) {
                    holderView.cb_subject3.setChecked(true);
                    subject.setCheck(true);
                    holderView.cb_subject3.setClickable(false);
                }


                holderView.item3_layout.setVisibility(View.VISIBLE);
                holderView.item2_layout.setVisibility(View.GONE);
                holderView.et_subject3.setText(TextUtils.isEmpty(subject.getMark()) ? "" : subject.getMark());
                holderView.course_name3.setText(subject.getSubjectName());
                holderView.et_subject3.setHint(TextUtils.isEmpty(subject.getMarkHoldPlace()) ? "" : subject.getMarkHoldPlace());
                holderView.cb_subject3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {
                            holderView.tv_higher3.setChecked(false);
                            holderView.tv_standard3.setChecked(false);
                            subject.setCheck(false);
                        } else {
                            subject.setCheck(true);
                            if (subject.getSubjectLevel() == 1) {
                                holderView.tv_higher3.setChecked(true);
                                holderView.tv_standard3.setChecked(false);
                            } else if (subject.getSubjectLevel() == 2) {
                                holderView.tv_higher3.setChecked(false);
                                holderView.tv_standard3.setChecked(true);
                            } else if (subject.getSubjectLevel() == 0) {
                                holderView.tv_higher3.setChecked(false);
                                holderView.tv_standard3.setChecked(false);
                            }
                        }
                        listener.click(subject);
                    }
                });

                holderView.tv_higher3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {

                        } else {

//                            if(subject.getSubjectName().equals("Theory of Knowledge") || subject.getSubjectName().equals("Extended Essay")){
//                                holderView.cb_subject3.setClickable(false);
//                            }

//                            if (!holderView.cb_subject3.isChecked()) {
                                holderView.cb_subject3.setChecked(true);
                                holderView.tv_higher3.setChecked(true);
                                subject.setCheck(true);
                                subject.setSubjectLevel(1);
                                listener.click(subject);
//                            }
                        }
                    }
                });

                holderView.tv_standard3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {

                        } else {
//                            if (!holderView.cb_subject3.isChecked()) {
                                holderView.cb_subject3.setChecked(true);
                                holderView.tv_standard3.setChecked(true);
                                subject.setCheck(true);
                                subject.setSubjectLevel(2);
                                listener.click(subject);
//                            }
                        }
                    }
                });
            }
        }

//        holderView.course_name.setText(subjectList.get(position).getSubjectName());
//        if (subjectList.get(position).getIsNeedMark() == 1) {
//            holderView.choice_layout.setVisibility(View.VISIBLE);
//        } else {
//            holderView.choice_layout.setVisibility(View.GONE);
//        }
//
//        if (subjectList.get(position).getIsNeedLevel() == 1) {
//            holderView.rg_course.setVisibility(View.VISIBLE);
//        } else {
//            holderView.rg_course.setVisibility(View.GONE);
//        }

//        holderView.tv_higher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//
//                }
//            }
//        });
//
//        holderView.tv_standard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//
//                }
//            }
//        });

//        holderView.cb_subject1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                subjectList.get(position).setCheck(!isChecked);
//            }
//        });
//
//        if (subjectList.get(position).isCheck()) {
//            holderView.cb_subject.setChecked(true);
//        }
    }


    public List<SubjectResponse.Subject> getSubjectStatus() {
        return subjectList;
    }

    @
            Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = null;
        HolderView holder = null;
        inflate = layoutInflater.inflate(R.layout.item_subject, parent, false);
        holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            item1_layout = itemView.findViewById(R.id.item1_layout);
            item2_layout = itemView.findViewById(R.id.item2_layout);
            item3_layout = itemView.findViewById(R.id.item3_layout);
            cb_subject1 = itemView.findViewById(R.id.cb_subject1);
            cb_subject2 = itemView.findViewById(R.id.cb_subject2);
            cb_subject3 = itemView.findViewById(R.id.cb_subject3);
            et_subject1_layout = itemView.findViewById(R.id.et_subject1_layout);

            course_name1 = itemView.findViewById(R.id.course_name1);
            course_name2 = itemView.findViewById(R.id.course_name2);
            course_name3 = itemView.findViewById(R.id.course_name3);
            et_subject1 = itemView.findViewById(R.id.et_subject1);
            et_subject3 = itemView.findViewById(R.id.et_subject3);
            rg_course2 = itemView.findViewById(R.id.rg_course2);
            rg_course3 = itemView.findViewById(R.id.rg_course3);
            tv_higher2 = itemView.findViewById(R.id.tv_higher2);
            tv_higher3 = itemView.findViewById(R.id.tv_higher3);
            tv_standard2 = itemView.findViewById(R.id.tv_standard2);
            tv_standard3 = itemView.findViewById(R.id.tv_standard3);
        }

        LinearLayout item1_layout;
        LinearLayout item2_layout;
        LinearLayout item3_layout;
        CheckBox cb_subject1;
        CheckBox cb_subject2;
        CheckBox cb_subject3;
        TextView course_name1;
        TextView course_name2;
        TextView course_name3;
        LinearLayout et_subject1_layout;

        EditText et_subject1;
        EditText et_subject3;
        RadioGroup rg_course2;
        RadioGroup rg_course3;
        RadioButton tv_higher2;
        RadioButton tv_standard2;
        RadioButton tv_higher3;
        RadioButton tv_standard3;
    }

}
