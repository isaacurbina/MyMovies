package com.mobileappsco.training.mymovies.schematic;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by admin on 3/30/2016.
 */
@Database(version = TheMovieDB.VERSION,
        packageName = MoviesProvider.AUTHORITY)
public final class TheMovieDB {

    public static final int VERSION = 1;

    @Table(MovieColumns.class) public static final String MOVIES = "Movies";
    @Table(TrailerColumns.class) public static final String TRAILERS = "Trailers";
}