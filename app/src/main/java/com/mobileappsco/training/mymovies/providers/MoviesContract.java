package com.mobileappsco.training.mymovies.providers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.mobileappsco.training.mymovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOVIE_ID = "movies";
    public static final String PATH_MOVIES_TITLE = "movies/title";
    public static final String PATH_MOVIES_YEAR = "movies/year";
    public static final String PATH_MOVIES_TITLE_YEAR = "movies/titleyear";
    public static final String PATH_TRAILERS = "trailers";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI
                        .buildUpon()
                        .appendPath(PATH_MOVIES)
                        .build();

        public static final String CONTENT_TYPE_MOVIES =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_TYPE_MOVIE_WITH_ID =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_ID;
        public static final String CONTENT_TYPE_MOVIES_WITH_YEAR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES_YEAR;
        public static final String CONTENT_TYPE_MOVIES_WITH_TITLE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES_TITLE;
        public static final String CONTENT_TYPE_MOVIES_WITH_TITLE_YEAR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES_TITLE_YEAR;

        // Fields for Result
        public static final String TABLE_NAME = "Movies";
        public static final String _ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VOTE_AVERAGE = "vote_average";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildYearUri(String year) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(RELEASE_DATE, year)
                    .build();
        }

        public static Uri buildTitleUri(String title) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(TITLE, title)
                    .build();
        }

        public static Uri buildTitleYearUri(String title, String year) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(TITLE, title)
                    .appendQueryParameter(RELEASE_DATE, title)
                    .build();
        }

        public static String getFirstParam(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getSecondParam(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();

        public static final String CONTENT_TYPE_TRAILERS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;
        public static final String CONTENT_TYPE_TRAILERS_WITH_ID =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        // Fields for Video
        public static final String TABLE_NAME = "Trailers";
        public static final String _ID = "id";
        public static final String ISO_639_1 = "iso_639_1";
        public static final String ISO_3166_1 = "iso_3166_1";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";

        public static Uri buildTrailersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getFirstParam(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

}