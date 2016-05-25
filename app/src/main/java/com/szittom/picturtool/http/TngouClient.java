package com.szittom.picturtool.http;

import com.szittom.picturtool.config.API;
import com.szittom.picturtool.model.bean.HttpResult;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by SZITTom on 2016/4/19.
 */
public class TngouClient {

    private static final int DEFAULT_TIMEOUT = 5;

    private static TngouRetrofit tngouRetrofit;

    public static TngouRetrofit getInstance() {
        if (tngouRetrofit == null) {
            createService();
        }
        return tngouRetrofit;
    }

    private static void createService() {
        tngouRetrofit = createRetrofit().create(TngouRetrofit.class);
    }

    private static Retrofit createRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//NONE
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }


}
