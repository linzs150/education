package uikit.business.ait.selector.holder;

import android.widget.TextView;

import com.one.education.education.R;
import com.netease.nimlib.sdk.team.model.TeamMember;

import uikit.business.ait.selector.model.AitContactItem;
import uikit.business.team.helper.TeamHelper;
import uikit.common.ui.imageview.HeadImageView;
import uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import uikit.common.ui.recyclerview.holder.BaseViewHolder;
import uikit.common.ui.recyclerview.holder.RecyclerViewHolder;

/**
 * Created by hzchenkang on 2017/6/21.
 */

public class TeamMemberViewHolder extends RecyclerViewHolder<BaseQuickAdapter, BaseViewHolder, AitContactItem<TeamMember>> {

    private HeadImageView headImageView;

    private TextView nameTextView;

    public TeamMemberViewHolder(BaseQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(BaseViewHolder holder, AitContactItem<TeamMember> data, int position, boolean isScrolling) {
        inflate(holder);
        refresh(data.getModel());
    }

    public void inflate(BaseViewHolder holder) {
        headImageView = holder.getView(R.id.imageViewHeader);
        nameTextView = holder.getView(R.id.textViewName);
    }

    public void refresh(TeamMember member) {
        headImageView.resetImageView();
        nameTextView.setText(TeamHelper.getTeamMemberDisplayName(member.getTid(), member.getAccount()));
        headImageView.loadBuddyAvatar(member.getAccount());
    }
}
