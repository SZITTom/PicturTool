package com.szittom.picturtool.config;

import com.szittom.picturtool.app.App;
import com.szittom.picturtool.utils.PreferenceUtils;

/**
 * Created by Administrator on 2016/5/24.
 */
public class AppConfig {
    private static App sInstance = App.getInstance();

    /**
     * 仅wifi下加载图片
     */
    public static final String SHOULDWIFI = "onlywifi";

    public static boolean getShouldWifi() {
        return PreferenceUtils.getPrefBoolean(sInstance, SHOULDWIFI, false);
    }

    public static void putShouldWifi(boolean isFirstStart) {
        PreferenceUtils.setPrefBoolean(sInstance, SHOULDWIFI, isFirstStart);
    }

    /**
     * 推送通知
     */
    public static final String SHOULDPUSH = "shouldpush";

    public static boolean getShouldPush() {
        return PreferenceUtils.getPrefBoolean(sInstance, SHOULDPUSH, true);
    }

    public static void putShouldPush(boolean checked) {
        PreferenceUtils.setPrefBoolean(sInstance, SHOULDPUSH, checked);
    }
}
