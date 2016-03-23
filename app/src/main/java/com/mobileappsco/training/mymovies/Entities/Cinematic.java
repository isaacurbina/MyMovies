package com.mobileappsco.training.mymovies.Entities;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by admin on 3/7/2016.
 */
public class Cinematic extends SugarRecord {

    int mid;
    String title;
    String overview;
    String release_date;
    String poster_path;
    boolean adult;
    double vote_average;
    String video_path;
    String original_language;

    public Cinematic()
    {
    }

    public Cinematic(int mid,
                     String title,
                     String overview,
                     String release_date,
                     String poster_path,
                     boolean adult,
                     double vote_average,
                     String video_path,
                     String original_language)
    {
        this.mid = mid;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.adult = adult;
        this.vote_average = vote_average;
        this.video_path = video_path;
        this.original_language = original_language;
    }

}
