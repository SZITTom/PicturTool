package com.szittom.picturtool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SZITTom on 2016/5/3.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

}
