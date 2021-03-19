package com.one.education.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.util.Vector;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class ConnectBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "ConnectBroadcastReceiver";
    private static final Vector<INetworkChangeListener> sNetWorkChangeListeners = new Vector<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            boolean connected = null != info && NetworkInfo.State.CONNECTED == info.getState();
            Log.i(TAG, "onReceive : connected = " + connected);
            notifyNetworkChange(connected);
        }
    }

    private static void notifyNetworkChange(boolean connected) {
        synchronized (sNetWorkChangeListeners) {
            for (INetworkChangeListener listener : sNetWorkChangeListeners) {
                listener.onChange(connected);
            }
        }
    }

    public static void regListener(INetworkChangeListener listener) {
        if (null == listener) {
            return;
        }

        synchronized (sNetWorkChangeListeners) {
            sNetWorkChangeListeners.add(listener);
        }
    }

    public static void unregListener(INetworkChangeListener listener) {
        if (null == listener) {
            return;
        }

        synchronized (sNetWorkChangeListeners) {
            sNetWorkChangeListeners.remove(listener);
        }
    }

    public interface INetworkChangeListener {
        void onChange(boolean connected);
    }
}
