package uikit.business.session.module;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

import uikit.api.model.CreateMessageCallback;

public interface IMultiRetweetMsgCreator {
    void create(List<IMMessage> msgList, boolean shouldEncrypt,  CreateMessageCallback callback);
}
