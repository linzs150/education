package uikit.business.recent.holder;

import uikit.business.team.helper.TeamHelper;
import uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;

public class SuperTeamRecentViewHolder extends TeamRecentViewHolder {

    public SuperTeamRecentViewHolder(BaseQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    public String getTeamUserDisplayName(String tid, String account) {
        return TeamHelper.getSuperTeamMemberDisplayName(tid, account);
    }
}
