package com.mobileappsco.training.mymovies.schematic;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = MyContentProvider.AUTHORITY, database = MyDB.class)
public final class MyContentProvider {

    public static final String AUTHORITY = "com.mobileappsco.training.mymovies";

    @TableEndpoint(table = MyDB.RESULT)
    public static class Results {
        // General query, returns a set of records
        @ContentUri(
                path = "results",
                type = AUTHORITY + "/results",
                defaultSort = ResultColumns.POPULARITY + " ASC")
        public static final Uri LISTS = Uri.parse("content://" + AUTHORITY + "/results");
        // Query by ID, returns a single record
        @InexactContentUri(
                path = "/#",
                name = "RESULT_ID",
                type = AUTHORITY + "/result",
                whereColumn = ResultColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/result/" + id);
        }
        // Query by year, returns a set of records
        @InexactContentUri(
                path = "/year/#",
                name = "YEAR",
                type = AUTHORITY + "/results",
                whereColumn = ResultColumns.RELEASE_DATE,
                pathSegment = 1)
        public static Uri withYear(String year) {
            return Uri.parse("content://" + AUTHORITY + "/results/year/" + year);
        }
        // Query by title, returns a set of records
        @InexactContentUri(
                path = "/title/#",
                name = "TITLE",
                type = AUTHORITY + "/results",
                whereColumn = ResultColumns.TITLE,
                pathSegment = 1)
        public static Uri withTitle(String title) {
            return Uri.parse("content://" + AUTHORITY + "/results/title/" + title);
        }
        // Query by title, returns a set of records
        @InexactContentUri(
                path = "/title/#/year/#",
                name = "TITLE",
                type = AUTHORITY + "/results",
                whereColumn = {ResultColumns.TITLE, ResultColumns.RELEASE_DATE},
                pathSegment = {1,2})
        public static Uri withTitleAndYear(String title, String year) {
            return Uri.parse("content://" + AUTHORITY + "/results/title/" + title + "/year/" + year);
        }
    }
    @TableEndpoint(table = MyDB.VIDEO)
    public static class Video {

    }

}
