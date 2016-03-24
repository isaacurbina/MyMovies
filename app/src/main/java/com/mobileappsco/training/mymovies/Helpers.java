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


    public static void logAndToast(Context context, String message, int type) {
        boolean debug = context.getResources().getBoolean(R.bool.debug);
        if (debug) {
            switch (type) {
                case Log.INFO:
                    Log.i(context.getString(R.string.MY_TAG), message);
                    //Toast.makeText(context, "INFO >> "+message, Toast.LENGTH_SHORT).show();
                    break;
                case Log.ERROR:
                    Log.e(context.getString(R.string.MY_TAG), message);
                    //Toast.makeText(context, "ERROR >> "+message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d(context.getString(R.string.MY_TAG), message);
                    //Toast.makeText(context, "Debug >> "+message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
