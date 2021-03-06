package uikit.business.recent.holder;

import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import uikit.impl.NimUIKitImpl;

public class CommonRecentViewHolder extends RecentViewHolder {

    CommonRecentViewHolder(BaseQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    protected String getContent(RecentContact recent) {
        return descOfMsg(recent);
    }

    @Override
    protected String getOnlineStateContent(RecentContact recent) {
        if (recent.getSessionType() == SessionTypeEnum.P2P && NimUIKitImpl.enableOnlineState()) {
            return NimUIKitImpl.getOnlineStateContentProvider().getSimpleDisplay(recent.getContactId());
        } else {
            return super.getOnlineStateContent(recent);
        }
    }

    String descOfMsg(RecentContact recent) {
        if (recent.getMsgType() == MsgTypeEnum.text
            || recent.getMsgType() == MsgTypeEnum.nrtc_netcall) {
            return recent.getContent();
        } else if (recent.getMsgType() == MsgTypeEnum.tip) {
            String digest = null;
            if (getCallback() != null) {
                digest = getCallback().getDigestOfTipMsg(recent);
            }

            if (digest == null) {
                digest = NimUIKitImpl.getRecentCustomization().getDefaultDigest(recent);
            }

            return digest;
        } else if (recent.getAttachment() != null) {
            String digest = null;
            if (getCallback() != null) {
                digest = getCallback().getDigestOfAttachment(recent, recent.getAttachment());
            }

            if (digest == null) {
                digest = NimUIKitImpl.getRecentCustomization().getDefaultDigest(recent);
            }

            return digest;
        } else if (recent.getSessionType() == SessionTypeEnum.Ysf) {
            return recent.getContent();
        }

        return "[??????]";
    }
}
