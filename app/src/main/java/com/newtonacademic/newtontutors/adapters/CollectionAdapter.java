package com.newtonacademic.newtontutors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.newtonacademic.newtontutors.beans.TeacherBean;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.utils.Utilts;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @创建者 Administrator
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public CollectionAdapter(Context context, List<TeacherBean.TeacherList> list) {
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

        HolderView holderView = (HolderView) holder;
        final TeacherBean.TeacherList teach = teacherBeen.get(position);
        holderView.name.setText(teach.getTeacherName());
        holderView.name.setText(teach.getTeacherName());
        if (teach.getSex() == 1) {
            holderView.sex.setImageResource(R.drawable.boy_sex);
        } else if (teach.getSex() == 0) {
            holderView.sex.setImageResource(R.drawable.sex_female);
        }

        holderView.th_age.setText(teach.getTeachingExperience() + "年教龄");
        holderView.score.setText(teach.getCommentStar() + "");
        holderView.mRatingBar.setRating(teach.getCommentStar());
        holderView.date.setText(Utilts.dateToInt(teach.getBirthday()));

        holderView.collection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(0, teach);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.item_collection_th, parent, false);
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
            collection_layout = itemView.findViewById(R.id.collection_layout);
        }

        CircleImageView imageView;
        TextView name;
        ImageView sex;
        TextView th_age;
        TextView score;
        RatingBar mRatingBar;
        TextView location;
        TextView date;
        LinearLayout collection_layout;
    }

}
