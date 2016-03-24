package com.mobileappsco.training.mymovies.Retrofit;

import com.mobileappsco.training.mymovies.Entities.PageResults;
import com.mobileappsco.training.mymovies.Entities.Result;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface rfInterface = retrofit.create(RetrofitInterface.class);

        Call<PageResults> request = rfInterface.discoverMovies("cac0b89ef7b5aa3a980f240f7c20af68",
                                            "popularity.desc",
                                            "1",
                                            "pt");

        PageResults pages = null;

        try {
            pages = request.execute().body();
            for (Result result : pages.getResults()) {
                System.out.println(result.getId()+" "+result.getTitle());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}