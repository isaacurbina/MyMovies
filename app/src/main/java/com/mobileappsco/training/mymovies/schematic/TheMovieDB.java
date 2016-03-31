package com.mobileappsco.training.mymovies.schematic;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = TheMovieDB.VERSION,
        packageName = MoviesProvider.AUTHORITY,
        fileName = "com.mobileappsco.training.mymovies.db")
public final class TheMovieDB {

    public static final int VERSION = 1;

    @Table(ResultColumns.class) public static final String MOVIES = "Result";
    @Table(VideoColumns.class) public static final String TRAILERS = "Video";
}