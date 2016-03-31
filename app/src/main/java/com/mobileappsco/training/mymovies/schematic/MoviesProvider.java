package com.mobileappsco.training.mymovies.schematic;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = MoviesProvider.AUTHORITY,
        database = TheMovieDB.class,
        packageName = MoviesProvider.AUTHORITY)
public final class MoviesProvider {

    public static final String AUTHORITY = "com.mobileappsco.training.mymovies";

    public static final String[] MOVIES_PROJECTION = new String[]{
            MovieColumns._ID,
            MovieColumns.POSTER_PATH,
            MovieColumns.ADULT,
            MovieColumns.OVERVIEW,
            MovieColumns.RELEASE_DATE,
            MovieColumns.ORIGINAL_TITLE,
            MovieColumns.ORIGINAL_LANGUAGE,
            MovieColumns.TITLE,
            MovieColumns.BACKDROP_PATH,
            MovieColumns.POPULARITY,
            MovieColumns.VOTE_COUNT,
            MovieColumns.VIDEO,
            MovieColumns.VOTE_AVERAGE
    };

    public static final String[] TRAILERS_PROJECTION = new String[]{
            TrailerColumns._ID,
            TrailerColumns.ISO_639_1,
            TrailerColumns.ISO_3166_1,
            TrailerColumns.KEY,
            TrailerColumns.NAME,
            TrailerColumns.SITE,
            TrailerColumns.SIZE,
            TrailerColumns.TYPE
    };

    @TableEndpoint(table = TheMovieDB.MOVIES)
    public static class Movies {
        // General query, returns a set of records
        @ContentUri(
                path = "movies",
                type = "vnd.android.cursor.dir/list",
                defaultSort = MovieColumns.POPULARITY + " ASC")
        public static final Uri MOVIES = Uri.parse("content://" + AUTHORITY + "/movies");
        // Query by ID, returns a single record
        @InexactContentUri(
                path = "movies/#",
                name = "MOVIE_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/movies/" + id);
        }
        // Query by year, returns a set of records
        @InexactContentUri(
                path = "movies/year/#",
                name = "YEAR",
                type = "vnd.android.cursor.dir/list",
                whereColumn = MovieColumns.RELEASE_DATE,
                pathSegment = 1)
        public static Uri withYear(String year) {
            return Uri.parse("content://" + AUTHORITY + "/results/year/" + year);
        }
        // Query by title, returns a set of records
        @InexactContentUri(
                path = "movies/title/#",
                name = "TITLE",
                type = "vnd.android.cursor.dir/list",
                whereColumn = MovieColumns.TITLE,
                pathSegment = 1)
        public static Uri withTitle(String title) {
            return Uri.parse("content://" + AUTHORITY + "/results/title/" + title);
        }
        // Query by title, returns a set of records
        @InexactContentUri(
                path = "/title/#/year/*",
                name = "TITLEYEAR",
                type = "vnd.android.cursor.dir/list",
                whereColumn = {MovieColumns.TITLE, MovieColumns.RELEASE_DATE},
                pathSegment = {1,2})
        public static Uri withTitleAndYear(String title, String year) {
            return Uri.parse("content://" + AUTHORITY + "/results/title/" + title + "/year/" + year);
        }
    }
    @TableEndpoint(table = TheMovieDB.TRAILERS)
    public static class Trailers {
        // General query, returns a set of records
        @ContentUri(
                path = "trailers",
                type = "vnd.android.cursor.dir/list",
                defaultSort = TrailerColumns._ID + " DESC")
        public static final Uri TRAILERS = Uri.parse("content://" + AUTHORITY + "/trailers");
        // Query by ID, returns a set of records
        @InexactContentUri(
                path = "trailers/#",
                name = "TRAILER_ID",
                type = "vnd.android.cursor.dir/list",
                whereColumn = TrailerColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/trailers/" + id);
        }
    }

}