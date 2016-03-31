package com.mobileappsco.training.mymovies.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mobileappsco.training.mymovies.entities.Result;
import com.orm.SugarRecord;

import java.util.HashMap;

/**
 * Created by admin on 3/30/2016.
 */
public class ResultProvider extends ContentProvider {

    Context mContext;

    // Authority and Content URI
    static final String AUTHORITY = "com.mobileappsco.training.mymovies";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/results");

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

    // Projection Maps
    private static HashMap<String, String> RESULTS_PROJECTION_MAP;

    // Codes
    static final int RESULTS = 1;
    static final int RESULT_ID = 2;
    static final int VIDEOS = 3;
    static final int COMMENTS = 4;

    // Declaring the Uris
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "results", RESULTS);
        uriMatcher.addURI(AUTHORITY, "results/#", RESULT_ID);
        uriMatcher.addURI(AUTHORITY, "results/videos/#", VIDEOS);
        uriMatcher.addURI(AUTHORITY, "results/comments/#", VIDEOS);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "com.mobileappsco.training.mymovies.db";
    static final String RESULTS_TABLE_NAME = "Result";
    static final String VIDEOS_TABLE_NAME = "Video";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_RESULT_TABLE =
            " CREATE Result " + RESULTS_TABLE_NAME +
                    " (id BIGINT PRIMARY KEY, " +
                    " original_title TEXT NOT NULL, " +
                    " original_language TEXT NOT NULL, " +
                    " title TEXT NOT NULL, " +
                    " poster_path TEXT, " +
                    " adult BOOLEAN NOT NULL, " +
                    " overview TEXT, " +
                    " release_date TEXT NOT NULL, " +
                    " backdrop_path TEXT, " +
                    " popularity DECIMAL(5,2) NOT NULL, " +
                    " vote_count INTEGER NOT NULL, " +
                    " video BOOLEAN NOT NULL, " +
                    " vote_average DECIMAL(5,2) NOT NULL);";
    static final String CREATE_VIDEO_TABLE =
            " CREATE Video " + VIDEOS_TABLE_NAME +
                    " ( mid TEXT PRIMARY KEY, " +
                    " iso_639_1 TEXT, " +
                    " iso_3166_1 TEXT, " +
                    " key TEXT, " +
                    " name TEXT, " +
                    " site TEXT, " +
                    " size TEXT, " +
                    " type TEXT);";



    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_RESULT_TABLE);
            db.execSQL(CREATE_VIDEO_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  RESULTS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + VIDEOS_TABLE_NAME);
            onCreate(db);
        }
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
        switch (uriMatcher.match(uri)) {
            case RESULTS:
                /*List<Result> results = SugarRecord.find(Result.class, selection, selectionArgs);
                return results;*/
                break;

            case RESULT_ID:

                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
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
        /*Result res = SugarRecord.findById(Result.class, values.getAsLong("id"));
        res.setId(values.getAsLong("id"));
        res.setPosterPath(values.getAsString("poster_path"));
        res.setAdult(values.getAsBoolean("adult"));
        res.setOverview(values.getAsString("overview"));
        res.setReleaseDate(values.getAsString("release_date"));
        res.setReleaseDate(values.getAsString("release_date"));
        res.setOriginalTitle(values.getAsString("original_title"));
        res.setOriginalLanguage(values.getAsString("original_language"));
        res.setTitle(values.getAsString("title"));
        res.setBackdropPath(values.getAsString("backdrop_path"));
        res.setPopularity(values.getAsDouble("popularity"));
        res.setVoteCount(values.getAsInteger("vote_count"));
        res.setVideo(values.getAsBoolean("video"));
        res.setVoteAverage(values.getAsDouble("vote_average"));
        // Store the object in the database
        SugarRecord.save(res);
        // returning the Uri
        Uri _uri = ContentUris.withAppendedId(CONTENT_URI, res.getId());
        getContext().getContentResolver().notifyChange(_uri, null);
        return _uri;*/
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        /*long count = 0;

        // Check which Uri was requested
        switch (uriMatcher.match(uri)){
            // if its RESULTS then we delete all records of that table
            case RESULTS:
                count = SugarRecord.count(Result.class, selection, selectionArgs);
                SugarRecord.deleteAll(Result.class);
                break;
            // if its RESULT_ID then we delete that record
            case RESULT_ID:
                long id = Long.parseLong(uri.getPathSegments().get(1));
                Result res = SugarRecord.findById(Result.class, id);
                if (res!=null)
                    count = 1;
                else
                    count = 0;
                SugarRecord.delete(id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return (int) count;*/
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        /*long count = 0;

        switch (uriMatcher.match(uri)){
            case RESULTS:
                count = SugarRecord.count(Result.class, selection, selectionArgs);
                List<Result> results = SugarRecord.find(Result.class, selection, selectionArgs);
                for (int i=0; i<results.size(); i++) {
                    Result res = results.get(i);
                    SugarRecord.executeQuery("UPDATE Result SET " + selection, selectionArgs);
                }
                break;

            case RESULT_ID:
                Result res = SugarRecord.findById(Result.class, values.getAsLong("id"));
                if (res==null)
                    count = 1;
                res.setId(values.getAsLong("id"));
                res.setPosterPath(values.getAsString("poster_path"));
                res.setAdult(values.getAsBoolean("adult"));
                res.setOverview(values.getAsString("overview"));
                res.setReleaseDate(values.getAsString("release_date"));
                res.setReleaseDate(values.getAsString("release_date"));
                res.setOriginalTitle(values.getAsString("original_title"));
                res.setOriginalLanguage(values.getAsString("original_language"));
                res.setTitle(values.getAsString("title"));
                res.setBackdropPath(values.getAsString("backdrop_path"));
                res.setPopularity(values.getAsDouble("popularity"));
                res.setVoteCount(values.getAsInteger("vote_count"));
                res.setVideo(values.getAsBoolean("video"));
                res.setVoteAverage(values.getAsDouble("vote_average"));
                // Store the object in the database
                SugarRecord.save(res);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return (int) count;*/
        return 0;
    }
}