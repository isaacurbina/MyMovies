
package com.mobileappsco.training.mymovies.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class PageVideos extends SugarRecord {

    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("results")
    @Expose
    private List<Video> results = new ArrayList<Video>();

    /**
     * 
     * @return
     *     The mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * 
     * @param mid
     *     The mid
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Video> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<Video> results) {
        this.results = results;
    }

}
