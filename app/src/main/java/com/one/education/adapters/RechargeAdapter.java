package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.education.beans.RechargeSetupResponse;
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

public class RechargeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<RechargeSetupResponse.RechargeSetup> rechargeBeen;

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

    public void updateTeacherInfo(List<RechargeSetupResponse.RechargeSetup> rechargeBeen) {
        this.rechargeBeen = rechargeBeen;

        notifyDataSetChanged();
    }

    private int pos;

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void updateTeacherInfo(int position) {

        for (int i = 0; i < rechargeBeen.size(); i++) {
            if (i == position) {
                rechargeBeen.get(i).setSelect(true);
            } else {
                rechargeBeen.get(i).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    public void clearSelected() {
        for (RechargeSetupResponse.RechargeSetup bean : rechargeBeen) {
            bean.setSelect(false);
        }
        notifyDataSetChanged();
    }

    public boolean ifSelected() {
        for (RechargeSetupResponse.RechargeSetup bean : rechargeBeen) {
            if (bean.isSelect()) {
                return true;
            }
        }

        return false;
    }

    public RechargeAdapter(Context context, List<RechargeSetupResponse.RechargeSetup> list) {
        mCtx = context;
        rechargeBeen = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return rechargeBeen == null ? 0 : rechargeBeen.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HolderView holderView = (HolderView) holder;

        if (rechargeBeen.get(position).isSelect()) {
            holderView.iv_select.setSelected(true);
            holderView.price.setSelected(true);
            holderView.active.setSelected(true);
        } else {
            holderView.iv_select.setSelected(false);
            holderView.price.setSelected(false);
            holderView.active.setSelected(false);
        }

        if (rechargeBeen.get(position).getGive() == 0l) {
            holderView.active.setVisibility(View.GONE);
            holderView.price.setText("US$ " + rechargeBeen.get(position).getAmount() + "");
        } else {
            holderView.active.setVisibility(View.VISIBLE);
            holderView.price.setText("US$ " + rechargeBeen.get(position).getAmount() + "");
            holderView.active.setText(mCtx.getString(R.string.pay_get_extra, rechargeBeen.get(position).getAmount() + "", rechargeBeen.get(position).getGive() + ""));
//            holderView.active.setText("送 US$ " + rechargeBeen.get(position).getGive());
        }


        holderView.price_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(position);
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.recharge_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            active = itemView.findViewById(R.id.active);
            price = itemView.findViewById(R.id.price);
            iv_select = itemView.findViewById(R.id.iv_select);
            price_layout = itemView.findViewById(R.id.price_layout);
        }

        FrameLayout price_layout;
        ImageView iv_select;
        TextView price;
        TextView active;

    }


}
