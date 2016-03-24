package com.mobileappsco.training.mymovies.Retrofit;

import com.mobileappsco.training.mymovies.Entities.PageResults;
import com.mobileappsco.training.mymovies.Entities.PageVideos;

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

    // http://api.themoviedb.org/3/movie/285/videos?api_key=cac0b89ef7b5aa3a980f240f7c20af68
    @GET("movie/{id}/videos")
    Call<PageVideos> fetchVideosOfMovie(
            @Query("api_key") String apikey,
            @Path("id") String id
    );
}