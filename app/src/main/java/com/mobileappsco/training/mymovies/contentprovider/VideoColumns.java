package com.mobileappsco.training.mymovies.contentprovider;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by admin on 3/30/2016.
 */
public class VideoColumns {
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String MID = "mid";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String ISO_639_1 = "iso_639_1";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String ISO_3166_1 = "iso_3166_1";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String KEY = "key";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String NAME = "name";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String SITE = "site";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String SIZE = "size";
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String TYPE = "type";
}
