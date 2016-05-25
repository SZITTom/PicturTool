package com.szittom.picturtool.model;

import com.szittom.picturtool.http.TngouClient;
import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.model.bean.HttpResult;
import com.szittom.picturtool.model.bean.HttpResultFunc;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SZITTom on 2016/4/20.
 */
public class NetImageModel {

    public static int ROWS = 20;

    public static Observable<List<Gallery>> getNetImageList(int page, int id){
        Observable<List<Gallery>> observable = TngouClient.getInstance().getAPIList(page,ROWS,id)
                .map(new HttpResultFunc<List<Gallery>>())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
