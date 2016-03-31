package com.mobileappsco.training.mymovies.schematic;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public interface VideoColumns {
    @DataType(DataType.Type.TEXT) @PrimaryKey
    String _ID = "mid";
    @DataType(DataType.Type.TEXT)
    String ISO_639_1 = "iso_639_1";
    @DataType(DataType.Type.TEXT)
    String ISO_3166_1 = "iso_3166_1";
    @DataType(DataType.Type.TEXT) @NotNull
    String KEY = "key";
    @DataType(DataType.Type.TEXT)
    String NAME = "name";
    @DataType(DataType.Type.TEXT)
    String SITE = "site";
    @DataType(DataType.Type.INTEGER)
    String SIZE = "size";
    @DataType(DataType.Type.TEXT) @NotNull
    String TYPE = "type";
}