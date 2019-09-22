package com.example.popularmovies.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHandler {

    public Retrofit getRtrofit(Retrofit retrofit){
        Retrofit retrofitHolder;
        if (retrofit == null){
            retrofitHolder = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofit = retrofitHolder;
        }

        return retrofit;
    }
}
