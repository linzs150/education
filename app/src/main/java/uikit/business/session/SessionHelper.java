package uikit.business.session;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;

import uikit.api.NimUIKit;
import uikit.api.wrapper.NimMessageRevokeObserver;
import uikit.business.session.extension.CustomAttachParser;
import uikit.business.session.extension.CustomAttachment;
import uikit.business.session.viewholder.MsgViewHolderMyCustom;

/**
 * UIKit自定义消息界面用法展示类
 */
public class SessionHelper {

    public static void init() {
        // 注册自定义消息附件解析器
        NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
        // 注册各种扩展消息类型的显示ViewHolder
        NimUIKit.registerMsgItemViewHolder(CustomAttachment.class, MsgViewHolderMyCustom.class);
        // 注册消息转发过滤器
        registerMsgForwardFilter();
        // 注册消息撤回过滤器
        registerMsgRevokeFilter();
        // 注册消息撤回监听器
        registerMsgRevokeObserver();
    }

    /**
     * 消息转发过滤器
     */
    private static void registerMsgForwardFilter() {
        NimUIKit.setMsgForwardFilter(message -> false);
    }

    /**
     * 消息撤回过滤器
     */
    private static void registerMsgRevokeFilter() {
        NimUIKit.setMsgRevokeFilter(message -> false);
    }

    /**
     * 注册消息撤回监听器
     */
    private static void registerMsgRevokeObserver() {
        NIMClient.getService(MsgServiceObserve.class).observeRevokeMessage(new NimMessageRevokeObserver(), true);
    }
}
