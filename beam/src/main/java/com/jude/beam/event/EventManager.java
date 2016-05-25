package com.jude.beam.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SZITTom on 2016/5/9.
 */
public class EventManager {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    // ----------------------post
    public static void post(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    public static void post(int tagInt) {
        post(new BaseEvent(null, tagInt));
    }

    public static void post(String tagString) {
        post(new BaseEvent(null, tagString));
    }

    public static void post(Object data, int tagInt) {
        post(new BaseEvent(data, tagInt));
    }

    public static void post(Object data, String tagString) {
        post(new BaseEvent(data, tagString));
    }

    public static void post(Object data, int tagInt, String tagString) {
        post(new BaseEvent(data, tagInt, tagString));
    }
}
