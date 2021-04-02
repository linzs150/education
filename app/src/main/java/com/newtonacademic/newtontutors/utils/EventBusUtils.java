package com.newtonacademic.newtontutors.utils;

import de.greenrobot.event.EventBus;

/**
 *
 *
 */
public class EventBusUtils {
    public static void register(Object object){
        if (!EventBus.getDefault().isRegistered(object)){
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object){
        if (EventBus.getDefault().isRegistered(object)){
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(Object object){
        EventBus.getDefault().post(object);
    }

}
