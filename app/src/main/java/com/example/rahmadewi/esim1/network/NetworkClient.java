package com.example.rahmadewi.esim1.network;

import com.example.rahmadewi.esim1.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public class NetworkClient {
    private static Retrofit retrofit;
    public static final String BASE_URL = "http://esimsurabaya.tk/api/";

    public static Retrofit getRetrofit(){

        if(retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
