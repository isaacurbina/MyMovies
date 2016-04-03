package com.mobileappsco.training.mymovies.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;

@Table
public class Favorites {

    @SerializedName("id")
    @Expose
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
