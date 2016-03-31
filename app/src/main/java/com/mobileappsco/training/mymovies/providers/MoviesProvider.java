package com.mobileappsco.training.mymovies.providers;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MoviesProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDBHelper mOpenHelper;

    static final int MOVIES = 100;
    static final int MOVIE_WITH_ID = 101;
    static final int MOVIES_WITH_TITLE = 102;
    static final int MOVIES_WITH_YEAR = 103;
    static final int MOVIES_WITH_TITLE_YEAR = 104;
    static final int TRAILERS = 200;
    static final int TRAILERS_WITH_ID = 201;

    private static final SQLiteQueryBuilder sMoviesQueryBuilder;

    static{
        sMoviesQueryBuilder = new SQLiteQueryBuilder();
    }

    // movies.id = ?
    private static final String sMovieIDSelection =
            MoviesContract.MovieEntry.TABLE_NAME+
                    "." + MoviesContract.MovieEntry._ID + " = ? ";

    // movies.title LIKE ? AND release_date LIKE ?
    private static final String sMovieTitleYearSelection =
            "("+MoviesContract.MovieEntry.TABLE_NAME+
                    "." + MoviesContract.MovieEntry.TITLE + " LIKE ? OR " +
                    "." + MoviesContract.MovieEntry.ORIGINAL_TITLE + " LIKE ?) AND " +
                    MoviesContract.MovieEntry.RELEASE_DATE + " LIKE ? ";

    // movies.title LIKE ?
    private static final String sMovieTitleSelection =
            "("+MoviesContract.MovieEntry.TABLE_NAME+
                    "." + MoviesContract.MovieEntry.TITLE + " LIKE ? OR " +
                    "." + MoviesContract.MovieEntry.ORIGINAL_TITLE + " LIKE ?) ";

    // release_date LIKE ?
    private static final String sMovieYearSelection =
            MoviesContract.MovieEntry.RELEASE_DATE + " LIKE ? ";

    // trailers.id = ?
    private static final String sTrailerIDSelection =
            MoviesContract.TrailerEntry.TABLE_NAME+
                    "." + MoviesContract.TrailerEntry._ID + " = ? ";

    // Method to make values suitable for LIKE in SQL queries
    private String prepareLike(String value) {
        return "%"+value+"%";
    }

    private Cursor getMovies(Uri uri, String[] projection, String sortOrder) {
        String[] selectionArgs;
        String selection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMovieWithID(Uri uri, String[] projection, String sortOrder) {
        String id = MoviesContract.MovieEntry.getFirstParam(uri);
        String[] selectionArgs;
        String selection = sMovieIDSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesWithTitle(Uri uri, String[] projection, String sortOrder) {
        String title = MoviesContract.MovieEntry.getFirstParam(uri);
        title = prepareLike(title);
        String[] selectionArgs;
        String selection = sMovieTitleSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                new String[]{title},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesWithYear(Uri uri, String[] projection, String sortOrder) {
        String year = MoviesContract.MovieEntry.getFirstParam(uri);
        year = prepareLike(year);
        String[] selectionArgs;
        String selection = sMovieYearSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                new String[]{year},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesWithTitleYear(Uri uri, String[] projection, String sortOrder) {
        String title = MoviesContract.MovieEntry.getFirstParam(uri);
        String year = MoviesContract.MovieEntry.getSecondParam(uri);
        title = prepareLike(title);
        year = prepareLike(year);
        String[] selectionArgs;
        String selection = sMovieTitleYearSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                new String[]{title, year},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTrailers(Uri uri, String[] projection, String sortOrder) {
        String[] selectionArgs;
        String selection = sTrailerIDSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTrailersWithID(Uri uri, String[] projection, String sortOrder) {
        String id = MoviesContract.MovieEntry.getFirstParam(uri);
        String[] selectionArgs;
        String selection = sTrailerIDSelection;
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MoviesContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MoviesContract.PATH_MOVIE_ID + "/*", MOVIE_WITH_ID);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES_TITLE + "/*", MOVIES_WITH_TITLE);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES_YEAR + "/*", MOVIES_WITH_YEAR);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES_TITLE_YEAR + "/*/*", MOVIES_WITH_TITLE_YEAR);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES + "/*/*", MOVIES_WITH_TITLE_YEAR);
        matcher.addURI(authority, MoviesContract.PATH_TRAILERS + "/*", TRAILERS_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MoviesContract.MovieEntry.CONTENT_TYPE_MOVIES;
            case MOVIE_WITH_ID:
                return MoviesContract.MovieEntry.CONTENT_TYPE_MOVIE_WITH_ID;
            case MOVIES_WITH_TITLE:
                return MoviesContract.MovieEntry.CONTENT_TYPE_MOVIES_WITH_TITLE;
            case MOVIES_WITH_YEAR:
                return MoviesContract.MovieEntry.CONTENT_TYPE_MOVIES_WITH_YEAR;
            case MOVIES_WITH_TITLE_YEAR:
                return MoviesContract.MovieEntry.CONTENT_TYPE_MOVIES_WITH_TITLE_YEAR;
            case TRAILERS:
                return MoviesContract.TrailerEntry.CONTENT_TYPE_TRAILERS;
            case TRAILERS_WITH_ID:
                return MoviesContract.TrailerEntry.CONTENT_TYPE_TRAILERS_WITH_ID;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "movies"
            case MOVIES:
            {
                retCursor = getMovies(uri, projection, sortOrder);
                break;
            }
            // "movies/*"
            case MOVIE_WITH_ID: {
                retCursor = getMovieWithID(uri, projection, sortOrder);
                break;
            }
            // "movies/title/*"
            case MOVIES_WITH_TITLE: {
                retCursor = getMoviesWithTitle(uri, projection, sortOrder);
                break;
            }
            // "movies/year/*"
            case MOVIES_WITH_YEAR: {
                retCursor = getMoviesWithYear(uri, projection, sortOrder);
                break;
            }
            // "movies/titleyear/*/*"
            case MOVIES_WITH_TITLE_YEAR: {
                retCursor = getMoviesWithTitleYear(uri, projection, sortOrder);
                break;
            }
            // "trailers"
            case TRAILERS: {
                retCursor = getTrailers(uri, projection, sortOrder);
                break;
            }
            // "trailers/*"
            case TRAILERS_WITH_ID: {
                retCursor = getTrailersWithID(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES: {
                long _id = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.MovieEntry.CONTENT_URI;
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRAILERS: {
                long _id = db.insert(MoviesContract.TrailerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.TrailerEntry.buildTrailersUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(
                        MoviesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRAILERS:
                rowsDeleted = db.delete(
                        MoviesContract.TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(MoviesContract.MovieEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TRAILERS:
                rowsUpdated = db.update(MoviesContract.TrailerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}