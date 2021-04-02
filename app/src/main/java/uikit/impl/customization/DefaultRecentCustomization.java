package uikit.impl.customization;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.newtonacademic.mylibrary.ConstantGlobal;
import com.newtonacademic.mylibrary.MyPublicInterface;

import java.util.ArrayList;
import java.util.List;

import io.github.prototypez.appjoint.AppJoint;
import uikit.api.model.recent.RecentCustomization;
import uikit.business.session.helper.TeamNotificationHelper;

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

        ConstantGlobal.LanguageType type = myPublicInterface.getLanguageType();
        switch (recent.getMsgType()) {
            case text:
                return recent.getContent();
            case image:
                if (type == ConstantGlobal.LanguageType.HK) {
                    return "圖片";
                }

                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Image]";
                }

                return "[图片]";
            case video:
                if (type == ConstantGlobal.LanguageType.HK) {
                    return "視頻";
                }

                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Video]";
                }

                return "[视频]";
            case audio:
                if (type == ConstantGlobal.LanguageType.HK) {
                    return "語音消息";
                }

                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Audio]";
                }

                return "[语音消息]";
            case location:
                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Location]";
                }

                return "[位置]";
            case file:
                if (type == ConstantGlobal.LanguageType.HK) {
                    return "檔案";
                }

                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[File]";
                }

                return "[文件]";
            case tip:
                List<String> uuids = new ArrayList<>();
                uuids.add(recent.getRecentMessageId());
                List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (messages != null && messages.size() > 0) {
                    return messages.get(0).getContent();
                }

                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Notification]";
                }

                return "[通知提醒]";
            case notification:
                return TeamNotificationHelper.getTeamNotificationText(recent.getContactId(),
                        recent.getFromAccount(),
                        (NotificationAttachment) recent.getAttachment());
            case nrtc_netcall:
                return String.format("[%s]", MsgTypeEnum.nrtc_netcall.getSendMessageTip());
            default:
                if (type == ConstantGlobal.LanguageType.EN) {
                    return "[Lesson invite]";
                }

                if (type == ConstantGlobal.LanguageType.HK) {
                    return "[課堂推薦]";
                }

                return "[课堂推荐]";
        }
    }
}
