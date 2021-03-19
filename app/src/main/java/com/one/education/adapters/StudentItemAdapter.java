package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.one.education.beans.SubjectResponse;
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

public class StudentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<SubjectResponse.Subject> subjectList;

    private SearchListener listener;

    public interface SearchListener {
        void click(int position);
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

    public StudentItemAdapter(Context context, List<SubjectResponse.Subject> list) {
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
        holderView.tv_course_item.setText(TextUtils.isEmpty(subjectList.get(position).getSubjectName()) ? "" :
                subjectList.get(position).getSubjectName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.course_item_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            tv_course_item = itemView.findViewById(R.id.tv_course_item);
        }

        TextView tv_course_item;

    }

}
