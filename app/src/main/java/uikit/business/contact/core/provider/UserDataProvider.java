package uikit.business.contact.core.provider;

import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uikit.api.NimUIKit;
import uikit.business.contact.core.item.AbsContactItem;
import uikit.business.contact.core.item.ContactItem;
import uikit.business.contact.core.item.ItemTypes;
import uikit.business.contact.core.query.TextQuery;
import uikit.business.contact.core.util.ContactHelper;
import uikit.common.util.log.LogUtil;
import uikit.impl.cache.UIKitLogTag;

final class UserDataProvider {
    public static List<AbsContactItem> provide(TextQuery query) {
        List<UserInfo> sources = query(query);
        List<AbsContactItem> items = new ArrayList<>(sources.size());
        for (UserInfo u : sources) {
            items.add(new ContactItem(ContactHelper.makeContactFromUserInfo(u), ItemTypes.FRIEND));
        }

        LogUtil.i(UIKitLogTag.CONTACT, "contact provide data size =" + items.size());
        return items;
    }

    private static final List<UserInfo> query(TextQuery query) {

        List<String> friends = NimUIKit.getContactProvider().getUserInfoOfMyFriends();
        List<UserInfo> users = NimUIKit.getUserInfoProvider().getUserInfo(friends);
        if (query == null) {
            return users;
        }

        UserInfo user;
        for (Iterator<UserInfo> iter = users.iterator(); iter.hasNext(); ) {
            user = iter.next();
            boolean hit = ContactSearch.hitUser(user, query) || (ContactSearch.hitFriend(user, query));
            if (!hit) {
                iter.remove();
            }
        }
        return users;
    }
}