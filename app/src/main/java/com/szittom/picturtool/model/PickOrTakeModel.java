package com.szittom.picturtool.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.szittom.picturtool.model.bean.ImageDirectoryModel;
import com.szittom.picturtool.model.bean.SingleImageDirectories;
import com.szittom.picturtool.model.bean.SingleImageModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SZITTom on 2016/5/12.
 */
public class PickOrTakeModel {

    private static PickOrTakeModel sInstance;

    public PickOrTakeModel() {
    }

    public static PickOrTakeModel getInstance() {
        if (sInstance == null) {
            sInstance = new PickOrTakeModel();
        }
        return sInstance;
    }

    /**
     * 按时间排序的所有图片list
     */
    public ArrayList<SingleImageModel> startGetImageThread(Context context) {
        ArrayList<SingleImageModel> allImages = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        //获取jpeg和png格式的文件，并且按照时间进行倒序
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=\"image/jpeg\" or " +
                MediaStore.Images.Media.MIME_TYPE + "=\"image/png\"", null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (cursor != null) {
            allImages.clear();
            while (cursor.moveToNext()) {
                SingleImageModel singleImageModel = new SingleImageModel();
                singleImageModel.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                try {
                    singleImageModel.date = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
                } catch (NumberFormatException e) {
                    singleImageModel.date = System.currentTimeMillis();
                }
                try {
                    singleImageModel.id = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)));
                } catch (NumberFormatException e) {
                    singleImageModel.id = 0;
                }
                allImages.add(singleImageModel);

            }
        }
        return allImages;
    }

    /**
     * 按目录排序的所有图片list
     */
    public ArrayList<SingleImageDirectories> startSingleDir(ArrayList<SingleImageModel> data){
        ArrayList<SingleImageDirectories> singleImageData = new ArrayList<>();
        if (data == null) return null;

        for (SingleImageModel singleImageModel : data){
            //存入按照目录分配的list
            if (TextUtils.isEmpty(singleImageModel.path)){
                continue;
            }
            String path = singleImageModel.path;
            String parentPath = new File(path).getParent();
            putImageToParentDirectories(parentPath, path, singleImageModel.date, singleImageModel.id, singleImageModel.isPicked, singleImageData);
        }
        //加入全部图片 SingleImageDirectories
        SingleImageDirectories singleImageDirectories = new SingleImageDirectories();
        singleImageDirectories.directoryPath = "";
        ImageDirectoryModel imageDirectoryModel = new ImageDirectoryModel();
        imageDirectoryModel.images = data;
        singleImageDirectories.images = imageDirectoryModel;
        singleImageData.add(0,singleImageDirectories);

        return singleImageData;
    }

    private void putImageToParentDirectories(String parentPath, String path, long date, long id, boolean isPicked, ArrayList<SingleImageDirectories> singleImageData) {
        ImageDirectoryModel model = getModelFromKey(parentPath, singleImageData);
        if (model == null) {
            model = new ImageDirectoryModel();
            SingleImageDirectories directories = new SingleImageDirectories();
            directories.images = model;
            directories.directoryPath = parentPath;
            singleImageData.add(directories);
        }
        model.addImage(path, isPicked, date, id);
    }

    private ImageDirectoryModel getModelFromKey(String path,ArrayList<SingleImageDirectories> singleImageData) {
        for (SingleImageDirectories directories : singleImageData) {
            if (directories.directoryPath.equalsIgnoreCase(path)) {
                return directories.images;
            }
        }
        return null;
    }

    /**
     * 计算照片的具体时间
     * @param time
     * @return
     */
    public String calculateShowTime(long time){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mDayWeek = c.get(Calendar.DAY_OF_WEEK);
        mDayWeek -- ;
        //习惯性的还是定周一为第一天
        if (mDayWeek == 0)
            mDayWeek = 7;
        int mWeek = c.get(Calendar.WEEK_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        if((System.currentTimeMillis()-time) < (mHour*60 + mMinute)*60*1000){
            return "今天";
        }else if((System.currentTimeMillis()-time) < (mDayWeek)*24*60*60*1000){
            return "本周";
        }else if((System.currentTimeMillis()-time) < ((long)((mWeek-1)*7+mDayWeek))*24*60*60*1000){
            return "这个月";
        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", java.util.Locale.getDefault());
            return format.format(time);
        }
    }

}
