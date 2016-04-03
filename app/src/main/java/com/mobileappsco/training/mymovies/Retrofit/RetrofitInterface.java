package com.mobileappsco.training.mymovies.retrofit;

import com.mobileappsco.training.mymovies.entities.PageResults;
import com.mobileappsco.training.mymovies.entities.PageVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("discover/movie")
    Call<PageResults> discoverMovies(
            @Query("api_key") String apikey,
            @Query("sort_by") String sort_by,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<PageResults> searchMovieByTitle(
            @Query("api_key") String apikey,
            @Query("query") String title,
            @Query("sort_by") String sort_by,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("discover/movie")
    Call<PageResults> searchMovieByYear(
            @Query("api_key") String apikey,
            @Query("primary_release_year") String year,
            @Query("sort_by") String sort_by,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<PageResults> searchMovieByTitleAndYear(
            @Query("api_key") String apikey,
            @Query("query") String title,
            @Query("primary_release_year") String year,
            @Query("sort_by") String sort_by,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("movie/{id}/videos")
    Call<PageVideos> fetchVideosOfMovie(
            @Path("id") String id,
            @Query("api_key") String apikey,
            @Query("language") String language
    );
}