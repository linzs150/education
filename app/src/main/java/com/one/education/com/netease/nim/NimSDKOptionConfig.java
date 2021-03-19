package com.one.education.com.netease.nim;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.netease.nim.uikit.api.wrapper.MessageRevokeTip;
import com.netease.nim.uikit.api.wrapper.NimUserInfoProvider;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderThumbBase;
import com.netease.nimlib.sdk.NosTokenSceneConfig;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.IOException;

/**
 * Created by hzchenkang on 2017/9/26.
 * <p>
 * 云信sdk 自定义的SDK选项设置
 */

public class NimSDKOptionConfig {

    public static SDKOptions getSDKOptions(Context context) {
        SDKOptions options = new SDKOptions();
        // 配置 APP 保存图片/语音/文件/log等数据的目录
        options.sdkStorageRootPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;
        // 配置附件缩略图的尺寸大小
        options.thumbnailSize = MsgViewHolderThumbBase.getImageMaxEdge();
        // 通知栏显示用户昵称和头像
        options.userInfoProvider = new NimUserInfoProvider(DemoCache.getContext());
        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = messageNotifierCustomization;
        // 在线多端同步未读数
        options.sessionReadAck = true;
        // 动图的缩略图直接下载原图
        options.animatedImageThumbnailEnabled = true;
        // 采用异步加载SDK
        options.asyncInitSDK = true;
        // 是否是弱IM场景
        options.reducedIM = false;
        // 是否检查manifest 配置，调试阶段打开，调试通过之后请关掉
        options.checkManifestConfig = false;
        // 是否启用群消息已读功能，默认关闭
        options.enableTeamMsgAck = true;
        // 打开消息撤回未读数-1的开关
        options.shouldConsiderRevokedMessageUnreadCount = true;
        // 云信私有化配置项
//        configServerAddress(options, context);
//        options.mixPushConfig = buildMixPushConfig();
        //        options.mNosTokenSceneConfig = createNosTokenScene();
        options.loginCustomTag = "Android";
        options.useXLog = false;
        // 会话置顶是否漫游
        options.notifyStickTopSession = true;
        // 设置数据库加密秘钥（替换为自己的秘钥），开启加密；如不需要加密，则去掉
        options.databaseEncryptKey = "123456";

        return options;
    }

    public static final String TEST_NOS_SCENE_KEY = "test_nos_scene_key";

    /**
     * nos 场景配置
     */
    private static NosTokenSceneConfig createNosTokenScene() {
        NosTokenSceneConfig nosTokenSceneConfig = new NosTokenSceneConfig();
        nosTokenSceneConfig.updateDefaultIMSceneExpireTime(1);
        nosTokenSceneConfig.updateDefaultProfileSceneExpireTime(2);
        // scene key 建议常量化，这样使用起来比较方便
        nosTokenSceneConfig.appendCustomScene(TEST_NOS_SCENE_KEY, 4);
        return nosTokenSceneConfig;
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    public static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + DemoCache.getContext().getPackageName();
        }
        return storageRootPath;
    }

    private static MessageNotifierCustomization messageNotifierCustomization = new MessageNotifierCustomization() {

        @Override
        public String makeNotifyContent(String nick, IMMessage message) {
            return null; // 采用SDK默认文案
        }

        @Override
        public String makeTicker(String nick, IMMessage message) {
            return null; // 采用SDK默认文案
        }

        @Override
        public String makeRevokeMsgTip(String revokeAccount, IMMessage item) {
            return MessageRevokeTip.getRevokeTipContent(item, revokeAccount);
        }
    };
}
