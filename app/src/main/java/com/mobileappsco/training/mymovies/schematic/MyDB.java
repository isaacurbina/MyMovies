package com.mobileappsco.training.mymovies.schematic;

import com.mobileappsco.training.mymovies.contentprovider.VideoColumns;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by admin on 3/30/2016.
 */
@Database(version = MyDB.VERSION)
public final class MyDB {

    public static final int VERSION = 1;

    @Table(ResultColumns.class) public static final String RESULT = "Result";
    @Table(VideoColumns.class) public static final String VIDEO = "Video";
}