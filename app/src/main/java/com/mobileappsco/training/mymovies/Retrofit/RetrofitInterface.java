package com.mobileappsco.training.mymovies.Retrofit;

import com.mobileappsco.training.mymovies.Entities.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("discover/movie?sort_by=popularity.desc")
    Call<Page> discoverMovies();
}