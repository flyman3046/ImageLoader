package com.example.flyman3046.imageloader;

import com.example.flyman3046.imageloader.model.ProductItem;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public class ApiClient {
    static Retrofit mRetrofit;
    private static final String BASEURL = "https://image-retrofit.herokuapp.com/";

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public interface ApiStores {

        @GET("./")
        Observable<List<ProductItem>> getProducts();

        @POST("./")
        Observable<Message> uploadProduct(@Body ProductItem item);
    }
}
