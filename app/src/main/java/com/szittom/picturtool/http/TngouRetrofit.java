package com.szittom.picturtool.http;

import com.szittom.picturtool.model.bean.Gallery;
import com.szittom.picturtool.model.bean.Galleryclass;
import com.szittom.picturtool.model.bean.HttpResult;
import com.szittom.picturtool.model.bean.HttpResultShow;
import com.szittom.picturtool.model.bean.Picture;
import com.szittom.picturtool.model.bean.SosoSearcher;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SZITTom on 2016/4/19.
 * 服务器接口
 */
public interface TngouRetrofit {

    @GET("http://pic.sogou.com/pics")
    Observable<SosoSearcher.SosoImage.WallImageResult> getImageList(

            @Query("reqType") String ajax,
            @Query("reqFrom") String result,
            @Query("query") String word,
            @Query("start" ) int page);

    @GET("/tnfs/api/classify")
    Observable<HttpResult<List<Galleryclass>>> getAPIClassif();

    @GET("/tnfs/api/list")
    Observable<HttpResult<List<Gallery>>> getAPIList(
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("id") int id);

    @FormUrlEncoded
    @POST("/tnfs/api/show")
    Observable<HttpResultShow<List<Picture>>> getAPIShow(
            @Field("id") String id
    );


}
