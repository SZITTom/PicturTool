package com.szittom.picturtool.model;

import com.szittom.picturtool.http.TngouClient;
import com.szittom.picturtool.model.bean.Galleryclass;
import com.szittom.picturtool.model.bean.HttpResultFunc;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SZITTom on 2016/4/19.
 */
public class ClassifyModel {

    public static Observable<List<Galleryclass>> getClassifys() {
        Observable<List<Galleryclass>> observable = TngouClient.getInstance().getAPIClassif()
                .map(new HttpResultFunc<List<Galleryclass>>())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
