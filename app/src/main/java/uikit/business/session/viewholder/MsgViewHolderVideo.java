package uikit.business.session.viewholder;

import com.one.education.education.R;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;

import uikit.business.session.activity.WatchVideoActivity;
import uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import uikit.common.util.media.BitmapDecoder;

/**
 * Created by zhoujianghua on 2015/8/5.
 */
public class MsgViewHolderVideo extends MsgViewHolderThumbBase {

    public MsgViewHolderVideo(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getContentResId() {
        return R.layout.nim_message_item_video;
    }

    @Override
    public void onItemClick() {
        WatchVideoActivity.start(context, message);
    }

    @Override
    public String thumbFromSourceFile(String path) {
        VideoAttachment attachment = (VideoAttachment) message.getAttachment();
        String thumb = attachment.getThumbPathForSave();
        return BitmapDecoder.extractThumbnail(path, thumb) ? thumb : null;
    }
}
