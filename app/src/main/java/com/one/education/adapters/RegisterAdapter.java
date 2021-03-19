package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.one.education.EducationAppliction;
import com.one.education.beans.TeacherBean;
import com.one.education.education.R;
import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.utils.ImageLoader;
import com.one.education.utils.Utilts;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class RegisterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<TeacherBean.TeacherList> teacherBeen;

    private SearchListener listener;

    public interface SearchListener {
        void click(int position, TeacherBean.TeacherList teacherList);
    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateTeacherInfo(List<TeacherBean.TeacherList> teacherBeen) {
        this.teacherBeen = teacherBeen;
        notifyDataSetChanged();
    }

    public RegisterAdapter(Context context, List<TeacherBean.TeacherList> list) {
        mCtx = context;
        teacherBeen = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return teacherBeen == null ? 0 : teacherBeen.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final TeacherBean.TeacherList teach = teacherBeen.get(position);
        HolderView holderView = (HolderView) holder;
        holderView.name.setText(teach.getTeacherName());
        if (teach.getSex() == 1) {
            holderView.sex.setImageResource(R.drawable.male);
        } else if (teach.getSex() == 0) {
            holderView.sex.setImageResource(R.drawable.female);
        }


        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("en")) {
                if (teach.getTeachingExperience() == 0 || teach.getTeachingExperience() == 1) {

//                    holderView.th_age.setText(mCtx.getString(R.string.teaching_experience_single, teach.getTeachingExperience()));
                    holderView.th_age.setText("Teaching: " + teach.getTeachingExperience() + " year");
                } else {
                    holderView.th_age.setText(mCtx.getString(R.string.teaching_experience, teach.getTeachingExperience()));
                }
            } else {
                holderView.th_age.setText(mCtx.getString(R.string.teaching_experience, teach.getTeachingExperience()));
            }
        }


//        holderView.th_age.setText(mCtx.getString(R.string.teaching_experience, teach.getTeachingExperience()));
        holderView.score.setText(teach.getCommentStar() + "");
        holderView.mRatingBar.setRating(Utilts.ratingJS(teach.getCommentStar()));
        holderView.date.setText(Utilts.dateToInt(teach.getBirthday()));
        if (!TextUtils.isEmpty(teach.getTeacherPhotoUrl())) {
            ImageLoader.loadAdImage(teach.getTeacherPhotoUrl(), holderView.imageView);
        }


        holderView.kj_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(0, teach);
                }
            }
        });

        holderView.lx_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(1, teach);
                }
            }
        });

        holderView.th_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(2, teach);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.item_register_th, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.head);
            name = itemView.findViewById(R.id.name);
            sex = itemView.findViewById(R.id.sex);
            th_age = itemView.findViewById(R.id.th_age);
            score = itemView.findViewById(R.id.score);
            mRatingBar = itemView.findViewById(R.id.mRatingBar);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            kj_layout = itemView.findViewById(R.id.kj_layout);
            lx_layout = itemView.findViewById(R.id.lx_layout);
            th_layout = itemView.findViewById(R.id.th_layout);
        }

        CircleImageView imageView;
        TextView name;
        ImageView sex;
        TextView th_age;
        TextView score;
        RatingBar mRatingBar;
        TextView location;
        TextView date;
        LinearLayout kj_layout;
        LinearLayout lx_layout;
        LinearLayout th_layout;
    }

}
