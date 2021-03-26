package uikit.business.session.module;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

import uikit.api.model.CreateMessageCallback;

public class MultiRetweetMsgCreatorFactory {
    private static IMultiRetweetMsgCreator msgCreator;

    public static void registerCreator(IMultiRetweetMsgCreator creator) {
        msgCreator = creator;
    }

    public static void createMsg(List<IMMessage> msgList, boolean shouldEncrypt, CreateMessageCallback callback) {
        if (msgCreator == null) {
            callback.onFailed(CreateMessageCallback.FAILED_CODE_NOT_SUPPORT);
            return;
        }
        msgCreator.create(msgList, shouldEncrypt, callback);
    }

}
