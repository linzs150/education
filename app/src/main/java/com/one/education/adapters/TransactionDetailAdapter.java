package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.one.education.beans.OrderListResponse;
import com.one.education.education.R;
import com.one.education.utils.Utilts;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class TransactionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<OrderListResponse.OrderList> teacherBeen;

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

    public void updateTeacherInfo(List<OrderListResponse.OrderList> teacherBeen) {
        this.teacherBeen = teacherBeen;
        notifyDataSetChanged();
    }

    public TransactionDetailAdapter(Context context, List<OrderListResponse.OrderList> list) {
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
        OrderListResponse.OrderList orderList = teacherBeen.get(position);
        holderView.rechage.setText(channelByName(orderList.getChannel()));
        holderView.date.setText(Utilts.dateToLong(orderList.getCreateTime()));
        holderView.money.setText("+US$ " + orderList.getFinalAmount());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.detail_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            money = itemView.findViewById(R.id.money);
            rechage = itemView.findViewById(R.id.rechage);
            date = itemView.findViewById(R.id.date);
        }

        TextView rechage;
        TextView date;
        TextView money;


    }

    private String channelByName(String channel) {

        String pal = "";
        switch (channel) {
            case "pay_pal":
//                return "充值余额";
                pal = mCtx.getString(R.string.recharge_yy);
                break;
            case "wexin":
                pal = "";
                break;
            case "alipay":
                pal = "";
                break;
            case "balance":
                pal = "";
                break;
        }

        return pal;

    }


}
