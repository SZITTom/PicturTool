package com.szittom.picturtool.app;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.jude.beam.Beam;
import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.beam.bijection.ActivityLifeCycleDelegateProvider;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.szittom.picturtool.BuildConfig;
import com.szittom.picturtool.utils.L;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Created by SZITTom on 2016/4/18.
 */
public class App extends Application{

    private static App sInstance;

    public static App getInstance(){
        if (null == sInstance){
            synchronized (App.class) {
                if (null == sInstance) {
                    sInstance = new App();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Beam.init(this);
        Beam.setActivityLifeCycleDelegateProvider(new ActivityLifeCycleDelegateProvider() {
            @Override
            public ActivityLifeCycleDelegate createActivityLifeCycleDelegate(Activity activity) {
                return new ActivityDelegate(activity);
            }
        });

        //创建文件缓存路径 修改Picasso文件缓存的默认路径
        String imageCacheDir = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + getPackageName() + File.separator + "picasso";
        File file = new File(imageCacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        /*Picasso picasso = new Picasso.Builder(this).downloader(
                new OkHttpDownloader(new File(imageCacheDir))).build();
        Picasso.setSingletonInstance(picasso);*/

        if (BuildConfig.DEBUG) {
            ButterKnife.setDebug(true);
            L.isDebug = true;
            //这行代码会将你加载的图片的来源通过颜色的方式在图片的左上角有展示：
            Picasso.with(this).setIndicatorsEnabled(true);
            //红色：代表从网络下载的图片
            //黄色：代表从磁盘缓存加载的图片
            //绿色：代表从内存中加载的图片
        }

    }
}
