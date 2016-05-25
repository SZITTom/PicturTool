package com.szittom.picturtool.utils;

import android.os.Environment;

import com.szittom.picturtool.app.App;

/**
 * Created by SZITTom on 2016/5/17.
 */
public class CommonUtils {


    /**
     * 检测SDcard是否存在
     *
     * @return
     */
    public static boolean isExistSDcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
            return true;
        else {
            return false;
        }
    }


    /**
     * 获取存储路径
     */
    public static String getDataPath() {
        String path;
        if (isExistSDcard())
            path = Environment.getExternalStorageDirectory().getPath() + "/albumSelect";
        else
            path = App.getInstance().getFilesDir().getPath();
        if (!path.endsWith("/"))
            path = path + "/";
        return path;
    }
}
