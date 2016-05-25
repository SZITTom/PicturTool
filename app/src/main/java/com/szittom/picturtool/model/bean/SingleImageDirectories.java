package com.szittom.picturtool.model.bean;

/**
 * 一个文件夹中的图片数据实体
 *
 * Created by SZITTom on 2016/5/13.
 */
public class SingleImageDirectories {
    /** 父目录的路径 */
    public String directoryPath;
    /** 目录下的所有图片实体 */
    public ImageDirectoryModel images;
    public boolean isChecked;
}
