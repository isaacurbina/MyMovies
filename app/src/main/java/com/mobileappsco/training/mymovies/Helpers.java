package com.mobileappsco.training.mymovies;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 3/7/2016.
 */
public class Helpers {

    // Example image
    // http://image.tmdb.org/t/p/w500/rSZs93P0LLxqlVEbI001UKoeCQC.jpg&api_key=cac0b89ef7b5aa3a980f240f7c20af68

    // Example new movies
    // http://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22&api_key=cac0b89ef7b5aa3a980f240f7c20af68

    String MYTAG = "MyMovies";

    public String getApiKey(Context context) {
        String apiKey= "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiKey = bundle.getString("API_KEY");
            logAndToast(context, "API_KEY = " + apiKey, Log.INFO);
        } catch (PackageManager.NameNotFoundException e) {
            logAndToast(context, "Failed to load meta-data, NameNotFound: " + e.getMessage(), Log.ERROR);
        } catch (NullPointerException e) {
            logAndToast(context, "Failed to load meta-data, NullPointer: " + e.getMessage(), Log.ERROR);
        }
        return apiKey;
    }

    public String getAPIURL(Context context) {
        String apiUrl= "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiUrl = bundle.getString("API_URL");
            logAndToast(context, "API_URL = " + apiUrl, Log.INFO);
        } catch (PackageManager.NameNotFoundException e) {
            logAndToast(context, "Failed to load meta-data, NameNotFound: " + e.getMessage(), Log.ERROR);
        } catch (NullPointerException e) {
            logAndToast(context, "Failed to load meta-data, NullPointer: " + e.getMessage(), Log.ERROR);
        }
        return apiUrl;
    }

    public String getAPIImage(Context context) {
        String apiImage= "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiImage = bundle.getString("API_IMAGE");
            logAndToast(context, "API_IMAGE = " + apiImage, Log.INFO);
        } catch (PackageManager.NameNotFoundException e) {
            logAndToast(context, "Failed to load meta-data, NameNotFound: " + e.getMessage(), Log.ERROR);
        } catch (NullPointerException e) {
            logAndToast(context, "Failed to load meta-data, NullPointer: " + e.getMessage(), Log.ERROR);
        }
        return apiImage;
    }


    public void logAndToast(Context context, String message, int type) {
        switch (type) {
            case Log.INFO:
                Log.i(MYTAG, message);
                //Toast.makeText(context, "INFO >> "+message, Toast.LENGTH_SHORT).show();
                break;
            case Log.ERROR:
                Log.e(MYTAG, message);
                //Toast.makeText(context, "ERROR >> "+message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.d(MYTAG, message);
                //Toast.makeText(context, "Debug >> "+message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
