package com.newtonacademic.newtontutors.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;

/**
 * Intent扩展：实现不用序列化能传数据<br/>
 * 该类只能在发起Intent者是继承 {@link FragmentActivityEx} 可使用
 */
public class IntentEx extends Intent {
    /**
     * 缓存Map
     */
    private HashMap<String, Object> mMap = new HashMap<>();

    public IntentEx() {
    }

    public IntentEx(Intent o) {
        super(o);
    }

    public IntentEx(String action) {
        super(action);
    }

    public IntentEx(String action, Uri uri) {
        super(action, uri);
    }

    public IntentEx(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public IntentEx(String action, Uri uri, Context packageContext, Class<?> cls) {
        super(action, uri, packageContext, cls);
    }

    public Intent putExtraEx(String name, Object value) {
        mMap.put(name, value);
        return this;
    }

    public <T> T getExtraEx(String name) {
        return (T) mMap.get(name);
    }
}
