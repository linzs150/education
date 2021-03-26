package uikit.business.team.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.one.education.education.R;
import com.netease.nimlib.sdk.team.model.TeamMember;

import uikit.business.team.helper.TeamHelper;
import uikit.common.ui.imageview.HeadImageView;

/**
 * Created by hzchenkang on 2016/12/2.
 */

public class TeamMemberListHolder extends RecyclerView.ViewHolder {

    private HeadImageView headImageView;

    private TextView nameTextView;

    private View container;


    public TeamMemberListHolder(View itemView) {
        super(itemView);
        headImageView = (HeadImageView) itemView.findViewById(R.id.imageViewHeader);
        nameTextView = (TextView) itemView.findViewById(R.id.textViewName);
        container = itemView;
    }

    public void refresh(TeamMember member) {
        headImageView.resetImageView();
        nameTextView.setText(TeamHelper.getTeamMemberDisplayName(member.getTid(), member.getAccount()));
        headImageView.loadBuddyAvatar(member.getAccount());
        container.setTag(member);
    }

}
