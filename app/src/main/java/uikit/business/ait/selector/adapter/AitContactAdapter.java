package uikit.business.ait.selector.adapter;

import android.support.v7.widget.RecyclerView;

import com.newtonacademic.newtontutors.R;

import java.util.List;

import uikit.business.ait.selector.holder.RobotViewHolder;
import uikit.business.ait.selector.holder.SimpleLabelViewHolder;
import uikit.business.ait.selector.holder.TeamMemberViewHolder;
import uikit.business.ait.selector.model.AitContactItem;
import uikit.business.ait.selector.model.ItemType;
import uikit.common.ui.recyclerview.adapter.BaseMultiItemQuickAdapter;
import uikit.common.ui.recyclerview.holder.BaseViewHolder;

/**
 * Created by hzchenkang on 2017/6/21.
 */

public class AitContactAdapter extends BaseMultiItemQuickAdapter<AitContactItem, BaseViewHolder> {

    public AitContactAdapter(RecyclerView recyclerView, List<AitContactItem> data) {
        super(recyclerView, data);
        addItemType(ItemType.SIMPLE_LABEL, R.layout.nim_ait_contact_label_item, SimpleLabelViewHolder.class);
        addItemType(ItemType.ROBOT, R.layout.nim_ait_contact_robot_item, RobotViewHolder.class);
        addItemType(ItemType.TEAM_MEMBER, R.layout.nim_ait_contact_team_member_item, TeamMemberViewHolder.class);
    }

    @Override
    protected int getViewType(AitContactItem item) {
        return item.getViewType();
    }

    @Override
    protected String getItemKey(AitContactItem item) {
        return "" + item.getViewType() + item.hashCode();
    }
}
