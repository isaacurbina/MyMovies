package com.mobileappsco.training.mymovies.Retrofit;

import com.mobileappsco.training.mymovies.Entities.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("discover/movie")
    Call<Page> discoverMovies(@Query("api_key") String apikey,
                              @Query("sort_by") String sort_by,
                              @Query("page") String page);
    @GET("search/movie")
    Call<Page> searchMovieByTitle(@Query("api_key") String apikey,
                                  @Query("query") String title,
                                  @Query("sort_by") String sort_by,
                                  @Query("page") String page);

    @GET("discover/movie")
    Call<Page> searchMovieByYear(@Query("api_key") String apikey,
                                 @Query("primary_release_year") String year,
                                 @Query("sort_by") String sort_by,
                                 @Query("page") String page);
    @GET("search/movie")
    Call<Page> searchMovieByTitleAndYear(@Query("api_key") String apikey,
                                         @Query("query") String title,
                                        @Query("primary_release_year") String year,
                                        @Query("sort_by") String sort_by,
                                        @Query("page") String page);
}