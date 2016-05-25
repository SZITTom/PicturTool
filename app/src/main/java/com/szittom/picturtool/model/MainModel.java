package com.szittom.picturtool.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;

/**
 * Created by SZITTom on 2016/4/18.
 */
public class MainModel extends AbsModel {

    public static MainModel getInstance(){
        return getInstance(MainModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
    }
}
