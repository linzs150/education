package com.newtonacademic.newtontutors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.utils.ImageLoader;

import java.util.List;

/**
 * @创建者 Administrator
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class IndentityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<String> videoBeanList;

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

    public void updateTeacherInfo(List<String> videoBeenList) {
        this.videoBeanList = videoBeenList;
        notifyDataSetChanged();
    }

    public IndentityAdapter(Context context, List<String> list) {
        mCtx = context;
        videoBeanList = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return videoBeanList == null ? 0 : videoBeanList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HolderView holderView = (HolderView) holder;
        ImageLoader.loadAdImage(videoBeanList.get(position), holderView.image_identity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.indentity_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            image_identity = itemView.findViewById(R.id.image_identity);
        }

        ImageView image_identity;
    }

}
