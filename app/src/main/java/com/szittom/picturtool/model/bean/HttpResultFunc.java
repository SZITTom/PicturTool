package com.szittom.picturtool.model.bean;

import com.szittom.picturtool.http.ApiException;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 *            Created by SZITTom on 2016/4/19.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (!httpResult.isStatus()) {
            throw new ApiException(100);
        }
        return httpResult.getTngou();
    }
}
