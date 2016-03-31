package com.mobileappsco.training.mymovies.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mobileappsco.training.mymovies.entities.Result;
import com.orm.SugarRecord;

import java.util.HashMap;

/**
 * Created by admin on 3/30/2016.
 */
public class TheMovieDBProvider extends ContentProvider {

    Context mContext;

    // Authority and Content URI
    static final String AUTHORITY = "com.mobileappsco.training.mymovies";
    static final Uri RESULTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/results");
    static final Uri VIDEOS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/videos");
    static final Uri COMMENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/comments");

    // Fields for Result
    public final String _ID = "id";
    public final String ORIGINAL_TITLE = "original_title";
    public final String POSTER_PATH = "poster_path";
    public final String ADULT = "adult";
    public final String OVERVIEW = "overview";
    public final String RELEASE_DATE = "release_date";
    public final String ORIGINAL_LANGUAGE = "original_language";
    public final String TITLE = "title";
    public final String BACKDROP_PATH = "backdrop_path";
    public final String POPULARITY = "popularity";
    public final String VOTE_COUNT = "vote_count";
    public final String VOTE_AVERAGE = "vote_average";

    // Fields for Video
    public final String MID = "mid";
    public final String ISO_639_1 = "iso_639_1";
    public final String ISO_3166_1 = "iso_3166_1";
    public final String KEY = "key";
    public final String NAME = "name";
    public final String SITE = "site";
    public final String SIZE = "size";
    public final String TYPE = "type";

    // Projection Maps
    private static HashMap<String, String> RESULTS_PROJECTION_MAP;
    private static HashMap<String, String> VIDEOS_PROJECTION_MAP;

    // Codes
    static final int RESULTS = 1;
    static final int VIDEOS = 2;
    // TODO read comments from TheMovieDB API given the ID of the movie (Result object)
    static final int COMMENTS = 3;

    // Declaring the Uris
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "results", RESULTS);
        uriMatcher.addURI(AUTHORITY, "videos/", VIDEOS);
        uriMatcher.addURI(AUTHORITY, "comments/", COMMENTS);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        if (SugarRecord.isSugarEntity(Result.class)) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //ResultSugarRecord
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
