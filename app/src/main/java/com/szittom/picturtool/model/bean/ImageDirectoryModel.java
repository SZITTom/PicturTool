package com.szittom.picturtool.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.szittom.picturtool.utils.L;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一个图片文件夹对应实体
 * <p>
 * Created by SZITTom on 2016/5/13.
 */
public class ImageDirectoryModel implements Parcelable {

    /**
     * 图片的路径,对应图片是否被选中的数组
     */
    public ArrayList<SingleImageModel> images;

    public ImageDirectoryModel() {
        images = new ArrayList<SingleImageModel>();
    }

    public ArrayList<SingleImageModel> getImages() {
        return images;
    }

    /**
     * 把一张图片path添加进这个文件夹中
     *
     * @param path 图片地址
     */
    public void addImage(String path, boolean isPicked, long date, long id) {
        SingleImageModel image = new SingleImageModel(path, isPicked, date, id);
        images.add(image);
    }

    public void addSingleImageModel(SingleImageModel model) {
        images.add(model);
    }

    /**
     * 将一张图片从该文件夹中删除
     *
     * @param path 图片地址
     */
    public void removeImage(String path) {
        for (SingleImageModel image : images) {
            if (image.isThisImage(path)) {
                images.remove(image);
                break;
            }
        }
    }

    /**
     * 选中该图片
     *
     * @param path 图片地址
     */
    public void setImage(String path) {
        for (SingleImageModel image : images) {
            if (image.isThisImage(path)) {
                if (image.isPicked) {
                    L.e("zhao", "this image is picked!!!");
                }
                image.isPicked = true;
                break;
            }
        }
    }

    /**
     * 不选中该图片
     *
     * @param path 图片地址
     */
    public void unsetImage(String path) {
        for (SingleImageModel image : images) {
            if (image.isThisImage(path)) {
                if (!image.isPicked) {
                    L.e("zhao", "this image isn't picked!!!");
                }
                image.isPicked = false;
                break;
            }
        }
    }

    /**
     * 转变图片的选中状态
     */
    public void toggleSetImage(int position) {
        SingleImageModel model = images.get(position);
        model.isPicked = !model.isPicked;
    }

    /**
     * 转变图片的选中状态
     */
    public void toggleSetImage(String path) {
        for (SingleImageModel model : images) {
            if (model.path.equalsIgnoreCase(path)) {
                model.isPicked = !model.isPicked;
                break;
            }
        }
    }

    /**
     * 返回该文件夹的所有文件数量
     */
    public int getImageCounts() {
        return images.size();
    }

    /**
     * 根据图片的位置返回该图片的url
     *
     * @param position 图片位置
     */
    public String getImagePath(int position) {
        return images.get(position).path;
    }

    /**
     * 根据图片的位置返回该图片是否被选中
     *
     * @param position 图片位置
     */
    public boolean getImagePickOrNot(int position) {
        return images.get(position).isPicked;
    }

    /**
     * 该文件夹中是否有选中的图片
     */
    public boolean hasChoosePic() {
        for (SingleImageModel model : images) {
            if (model.isPicked)
                return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.images);
    }

    protected ImageDirectoryModel(Parcel in) {
        this.images = in.createTypedArrayList(SingleImageModel.CREATOR);
    }

    public static final Creator<ImageDirectoryModel> CREATOR = new Creator<ImageDirectoryModel>() {
        @Override
        public ImageDirectoryModel createFromParcel(Parcel source) {
            return new ImageDirectoryModel(source);
        }

        @Override
        public ImageDirectoryModel[] newArray(int size) {
            return new ImageDirectoryModel[size];
        }
    };
}
