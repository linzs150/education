package uikit.business.session.viewholder;

import uikit.business.session.extension.DefaultCustomAttachment;
import uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderDefCustom extends MsgViewHolderText {

    public MsgViewHolderDefCustom(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected String getDisplayText() {
        DefaultCustomAttachment attachment = (DefaultCustomAttachment) message.getAttachment();
        return "type: " + attachment.getType() + ", data: " + attachment.getContent();
    }
}
