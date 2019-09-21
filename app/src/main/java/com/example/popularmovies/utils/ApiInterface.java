package com.example.popularmovies.utils;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/{selected}")
    Call<Response> getResponse(@Path("selected") String selected, @Query("api_key") String api_key);

}
