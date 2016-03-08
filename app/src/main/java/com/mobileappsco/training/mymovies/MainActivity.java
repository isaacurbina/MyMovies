package com.mobileappsco.training.mymovies;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements SearchFragment.SearchFragmentListener {

    private View mProgressView;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set icon in toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Get fragments
        searchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
        makeJsonObjectRequest();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void sendQueryURL(Uri uri) {

    }

    private void makeJsonObjectRequest() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());
        String request = AppController.apiUrl+
                "/discover/movie?primary_release_date.lte="+now+
                "&api_key="+AppController.apiKey;
        AppController.helper.logAndToast(getApplicationContext(), "request volley: " + request, Log.INFO);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                request, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                AppController.helper.logAndToast(getApplicationContext(), response.toString(), Log.INFO);

                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        Cinematic c;
                        c = Cinematic.findById(Cinematic.class, item.getInt("id"));
                        if (c == null)
                            c = new Cinematic();
                        if (item.has("id"))
                            c.id = item.getInt("id");
                        if (item.has("title") && item.getString("title")!=null)
                            c.title = item.getString("title");
                        if (item.has("overview") && item.getString("overview")!=null)
                            c.overview = item.getString("overview");
                        if (item.has("release_date") && item.getString("release_date")!=null)
                            c.release_date = item.getString("release_date");
                        if (item.has("poster_path") && item.getString("poster_path")!=null)
                            c.poster_path = item.getString("poster_path");
                        if (item.has("adult"))
                            c.adult = item.getBoolean("adult");
                        if (item.has("vote_average"))
                            c.vote_average = item.getDouble("vote_average");
                        if (item.has("video_path") && item.getString("video_path")!=null)
                            c.video_path = item.getString("video_path");
                        if (item.has("original_language") && item.getString("original_language")!=null)
                            c.original_language = item.getString("original_language");

                        AppController.helper.logAndToast(getApplicationContext(), "Cinematic ID: " + c.id + " Title: " + c.title, Log.INFO);
                        c.save();
                        searchFragment.cinematics.add(c);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.helper.logAndToast(getApplicationContext(), "VOLLEY ERROR: " + error.getMessage(), Log.ERROR);
                searchFragment.cinematics = Cinematic.listAll(Cinematic.class);
                searchFragment.adapter.notifyDataSetChanged();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}

