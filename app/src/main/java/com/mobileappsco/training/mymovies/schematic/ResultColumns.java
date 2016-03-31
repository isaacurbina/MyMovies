package com.mobileappsco.training.mymovies.schematic;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by admin on 3/30/2016.
 */
public interface ResultColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    String POSTER_PATH = "poster_path";
    @DataType(DataType.Type.TEXT) @NotNull
    String ADULT = "adult";
    @DataType(DataType.Type.TEXT)
    String OVERVIEW = "overview";
    @DataType(DataType.Type.TEXT)
    String RELEASE_DATE = "release_date";
    @DataType(DataType.Type.TEXT) @NotNull
    String ORIGINAL_TITLE = "original_title";
    @DataType(DataType.Type.TEXT) @NotNull
    String ORIGINAL_LANGUAGE = "original_language";
    @DataType(DataType.Type.TEXT) @NotNull
    String TITLE = "title";
    @DataType(DataType.Type.TEXT) @NotNull
    String BACKDROP_PATH = "backdrop_path";
    @DataType(DataType.Type.REAL) @NotNull
    String POPULARITY = "popularity";
    @DataType(DataType.Type.INTEGER) @NotNull
    String VOTE_COUNT = "vote_count";
    @DataType(DataType.Type.TEXT) @NotNull
    String VIDEO = "video";
    @DataType(DataType.Type.REAL) @NotNull
    String VOTE_AVERAGE = "vote_average";
}
