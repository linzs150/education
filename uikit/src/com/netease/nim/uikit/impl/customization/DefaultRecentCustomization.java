package com.netease.nim.uikit.impl.customization;

import android.content.Context;

import com.netease.nim.uikit.api.model.recent.RecentCustomization;
import com.netease.nim.uikit.business.session.helper.TeamNotificationHelper;
import com.netease.nim.uikit.common.media.imagepicker.Utils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.one.mylibrary.MyPublicInterface;

import java.util.ArrayList;
import java.util.List;

import io.github.prototypez.appjoint.AppJoint;

/**
 * Created by huangjun on 2017/9/29.
 */

public class DefaultRecentCustomization extends RecentCustomization {

    MyPublicInterface myPublicInterface = AppJoint.service(MyPublicInterface.class);

    /**
     * 最近联系人列表项文案定制
     *
     * @param recent 最近联系人
     * @return 默认文案
     */
    public String getDefaultDigest(RecentContact recent) {

        boolean isSimple = myPublicInterface.isSimpleChinese();
        switch (recent.getMsgType()) {
            case text:
                return recent.getContent();
            case image:
                return !isSimple?"[Image]":"[图片]";
            case video:
                return !isSimple?"[video]":"[视频]";
            case audio:
                return !isSimple?"[Audio]":"[语音消息]";
            case location:
                return !isSimple?"[Location]":"[位置]";
            case file:
                return !isSimple?"[File]":"[文件]";
            case tip:
                List<String> uuids = new ArrayList<>();
                uuids.add(recent.getRecentMessageId());
                List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (messages != null && messages.size() > 0) {
                    return messages.get(0).getContent();
                }
                return !isSimple?"Notification":"[通知提醒]";
            case notification:
                return TeamNotificationHelper.getTeamNotificationText(recent.getContactId(),
                        recent.getFromAccount(),
                        (NotificationAttachment) recent.getAttachment());
            case robot:
                return !isSimple?"Robot News":"[机器人消息]";
            case nrtc_netcall:
                return String.format("[%s]", MsgTypeEnum.nrtc_netcall.getSendMessageTip());
            default:
                return !isSimple?"Custom Message": "[自定义消息] ";
        }
    }
}
