package com.szittom.picturtool.config;

/**
 * Created by SZITTom on 2016/5/3.
 */
public class DBConfig {
    public static final String DATABASE_NAME = "searchPicture.db";
    public static final String DownloadTable="download";
    public static final String CollectTable="collect";
    public  static final int DATABASE_VERSION = 1;

    public static final String creatDownloadTable="create table if not exists "+ DownloadTable+
            "(id integer primary key autoincrement," +
            "fileName varchar," +
            "largeImgUrl varchar," +
            "height integer," +
            "width integer)";


    public static final String createCollectTable ="create table if not exists "+ CollectTable+
            "(id integer primary key autoincrement," +
            "smallImgUrl varchar," +
            "largeImgUrl varchar," +
            "height integer," +
            "width integer)";

    public static final String upgradeDownloadTable = "drop table if exists " + DownloadTable;
}
