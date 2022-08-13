package com.hiaryabeer.receiptsystem.models;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitInstance {
    private static Retrofit Instance;
    public static Retrofit getInstance(String BASE_URL) {
        if (Instance == null)
            Instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return Instance;
    }
}
