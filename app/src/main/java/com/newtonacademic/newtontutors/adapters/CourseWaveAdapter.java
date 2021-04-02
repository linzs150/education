package com.newtonacademic.newtontutors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtonacademic.newtontutors.beans.SubjectResponse;
import com.newtonacademic.newtontutors.R;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class CourseWaveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public CourseWaveAdapter(Context context, List<SubjectResponse.Subject> list) {
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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.course_wave_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            imgage_kj = itemView.findViewById(R.id.imgage_kj);
            tv_ppt = itemView.findViewById(R.id.tv_ppt);
            tv_data = itemView.findViewById(R.id.tv_data);
            tv_th = itemView.findViewById(R.id.tv_th);
        }

        ImageView imgage_kj;
        TextView tv_ppt;
        TextView tv_data;
        TextView tv_th;


    }

}
