package com.szittom.picturtool.model;

import com.szittom.picturtool.http.ApiException;
import com.szittom.picturtool.http.TngouClient;
import com.szittom.picturtool.model.bean.HttpResultShow;
import com.szittom.picturtool.model.bean.Picture;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SZITTom on 2016/4/21.
 */
public class ShowDetailsModel {

    public static Observable<List<Picture>> getAPIShows(String id){
        Observable<List<Picture>> observable = TngouClient.getInstance().getAPIShow(id)
                .map(new Func1<HttpResultShow<List<Picture>>, List<Picture>>() {
                    @Override
                    public List<Picture> call(HttpResultShow<List<Picture>> listHttpResultShow) {
                        if (!listHttpResultShow.isStatus()) {
                            throw new ApiException(100);
                        }
                        return listHttpResultShow.getList();
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return observable;
    }
}
