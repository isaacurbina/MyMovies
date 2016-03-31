package com.mobileappsco.training.mymovies.traditionalproviders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "com.mobileappsco.training.mymovies.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " (" +
                MoviesContract.MovieEntry._ID + " BIGINT PRIMARY KEY," +
                MoviesContract.MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.POSTER_PATH + " TEXT, " +
                MoviesContract.MovieEntry.ADULT + " BOOLEAN NOT NULL, " +
                MoviesContract.MovieEntry.OVERVIEW + " TEXT, " +
                MoviesContract.MovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.BACKDROP_PATH + " TEXT, " +
                MoviesContract.MovieEntry.POPULARITY + " FLOAT NOT NULL, " +
                MoviesContract.MovieEntry.VOTE_COUNT + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.VOTE_AVERAGE + " FLOAT NOT NULL " +
                " );";

        final String SQL_CREATE_TRAILERS_TABLE =
                "CREATE TABLE " + MoviesContract.TrailerEntry.TABLE_NAME + " (" +
                MoviesContract.TrailerEntry._ID + " TEXT PRIMARY KEY," +
                MoviesContract.TrailerEntry.ISO_639_1 + " TEXT, " +
                MoviesContract.TrailerEntry.ISO_3166_1 + " TEXT, " +
                MoviesContract.TrailerEntry.KEY + " TEXT NOT NULL, " +
                MoviesContract.TrailerEntry.NAME + " TEXT NOT NULL, " +
                MoviesContract.TrailerEntry.SITE + " TEXT, " +
                MoviesContract.TrailerEntry.SIZE + " TEXT, " +
                MoviesContract.TrailerEntry.TYPE + " TEXT, " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TrailerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}