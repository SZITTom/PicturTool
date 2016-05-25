package com.szittom.picturtool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.szittom.picturtool.R;
import com.szittom.picturtool.config.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yyf
 * @date 2016/2/22
 * @description PicassoUtils
 */
public class PicassoUtils {

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImageUrl(Context context, String url, ImageView imageView) {
        if (AppConfig.getShouldWifi()) {
            Picasso.with(context)
                    .load(url)
                    //加载SD卡上的图片
                    //.load(new File(s))
                    //设置图片配置信息
                    .config(Bitmap.Config.RGB_565)
                    //设置占位图
                    .placeholder(R.drawable.ic_loading)
                    //设置加载图片失败时的占位图
                    .error(R.drawable.icn_close)
                    /*每次请求网络都是走网络，永远不使用内存缓存和文件缓存
                    .networkPolicy(NetworkPolicy.NO_STORE,NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE)*/
                    /*每次请求网络，只启用内存缓存，不启用二级缓存文件缓存(DiskCache)
                    .networkPolicy(NetworkPolicy.NO_STORE,NetworkPolicy.NO_CACHE)*/
                    /*每次请求网络，只启用二级缓存(DiskCache)，不启用一级缓存（LRUCache）
                    .memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE)*/
                    //如果你什么都没有配置，Picasso默认是内存缓存和文件缓存都是启用的。
                    //按照控件的大小进行图片放大缩小
                    .fit()
                    //目标控件
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.icn_close);
        }
    }

    public static void displayImageUrl(Context context, String url, Transformation transformation, ImageView imageView) {
        if (AppConfig.getShouldWifi()) {
            Picasso.with(context)
                    .load(url)
                    //加载SD卡上的图片
                    //.load(new File(s))
                    .transform(transformation)
                    //设置图片配置信息
                    .config(Bitmap.Config.RGB_565)
                    //设置占位图
                    .placeholder(R.drawable.ic_loading)
                    //设置加载图片失败时的占位图
                    .error(R.drawable.icn_close)
                    //如果你什么都没有配置，Picasso默认是内存缓存和文件缓存都是启用的。
                    //按照控件的大小进行图片放大缩小
                    .fit()
                    //目标控件
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.icn_close);
        }
    }


    /**
     * 加载本地图片
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void displayImageUrl(Context context, File file, ImageView imageView) {
        if (file.length() > 0) {
            Picasso.with(context)
                    .load(file)
                    //设置图片配置信息
                    .config(Bitmap.Config.RGB_565)
                    //设置占位图
                    .placeholder(R.drawable.ic_loading)
                    //设置加载图片失败时的占位图
                    .error(R.drawable.icn_close)
                    //按照控件的大小进行图片放大缩小
                    .fit()
                    //目标控件
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.icn_close);
        }
    }

    public static void displayImageUrl(Context context, File file, Transformation transformation, PhotoView imageView) {
        if (file.length() > 0) {
            Picasso.with(context)
                    .load(file)
                    .transform(transformation)
                    //设置图片配置信息
                    .config(Bitmap.Config.RGB_565)
                    //设置占位图
                    .placeholder(R.drawable.ic_loading)
                    //设置加载图片失败时的占位图
                    .error(R.drawable.icn_close)
                    //按照控件的大小进行图片放大缩小
                    .fit()
                    //目标控件
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.icn_close);
        }
    }


    /**
     * 加载assets图片
     *
     * @param context
     * @param assets
     * @param imageView
     */
    public static void displayImageAssets(Context context, String assets, ImageView imageView) {
        //获取输入流
        try {
            InputStream is = context.getAssets().open(assets);//"ss.png"
            //将流转成File对象
            File fil2 = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + assets);//xxx.png
            if (!fil2.exists()) {
                fil2.createNewFile();
            }
            inputstreamtofile(is, fil2);
            displayImageUrl(context, fil2, imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载完成的图片做成圆形
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImageUrlTransform(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                //.transform(new CircleTransform())
                //设置图片配置信息（设置后出现黑边）
                //.config(Bitmap.Config.RGB_565)
                //设置占位图
                .placeholder(R.drawable.ic_loading)
                //设置加载图片失败时的占位图
                .error(R.drawable.icn_close)
                //按照控件的大小进行图片放大缩小
                .fit()
                //目标控件
                .into(imageView);
    }

    /**
     * Picasso加载listview的item图片时，如何实现用户滑动时，Picasso暂停当前加载图片，用户滑动结束时，Picasso继续加载图片。
     * 实现这个功能，需要用到tag(context)这个方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImageUrlList(final Context context, String url, ImageView imageView) {
        //Activity调用时只需要设置OnScrollListener对象时，传入MyScrollListener即可。
        //listView.setOnScrollListener(new MyScrollListener(this));
        Picasso.with(context)
                .load(url)
                //设置图片配置信息
                .config(Bitmap.Config.RGB_565)
                //设置占位图
                .placeholder(R.mipmap.ic_launcher)
                //设置加载图片失败时的占位图
                .error(R.mipmap.ic_launcher)
                //按照控件的大小进行图片放大缩小
                .fit()
                .tag(context)
                //目标控件
                .into(imageView);

    }


}
