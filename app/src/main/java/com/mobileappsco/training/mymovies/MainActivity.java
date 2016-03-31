package com.mobileappsco.training.mymovies;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mobileappsco.training.mymovies.fragments.ResultsFragment;
import com.mobileappsco.training.mymovies.fragments.SearchFormFragment;
import com.mobileappsco.training.mymovies.schematic.ResultColumns;
import com.mobileappsco.training.mymovies.schematic.MoviesProvider;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements ResultsFragment.ResultsFragmentListener, SearchFormFragment.FormFragmentListener {

    FragmentManager fragmentManager = getFragmentManager();
    ResultsFragment resultsFragment;
    SearchFormFragment searchFormFragment;
    String RESTAG = "RESTAG";
    public static String API_JSON_URL, API_IMAGES_URL, API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set icon in toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        API_KEY = getResources().getString(R.string.API_KEY);
        API_JSON_URL = getResources().getString(R.string.API_JSON_URL);
        API_IMAGES_URL = getResources().getString(R.string.API_IMAGES_URL);

        // Get fragments
        if (findViewById(R.id.results_fragment_container) != null) {
            if (savedInstanceState == null) {
                resultsFragment = new ResultsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.results_fragment_container, resultsFragment, RESTAG)
                        .commit();
            }
        }
        searchFormFragment = (SearchFormFragment) getSupportFragmentManager().findFragmentById(R.id.searchform_fragment);
        getMovies();
    }

    @Override
    public void bridgeWithForm(String search_title, String search_year) {
        // if it's tablet landscape
        if (findViewById(R.id.results_fragment_container) != null) {
            resultsFragment = ResultsFragment.newInstance(search_title, search_year, "");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.results_fragment_container, resultsFragment, RESTAG)
                    .commit();
        } else { // rest of the devices
            Intent i = new Intent(this, ResultsActivity.class);
            i.putExtra("search_title", search_title);
            i.putExtra("search_year", search_year);
            i.putExtra("show_favorites", "");
            startActivity(i);
        }
    }

    @Override
    public void displayFavorites() {
        // if it's tablet landscape
        if (findViewById(R.id.results_fragment_container) != null) {
            resultsFragment = ResultsFragment.newInstance("", "", "fav");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.results_fragment_container, resultsFragment, RESTAG)
                    .commit();
        } else { // rest of the devices
            Intent i = new Intent(this, ResultsActivity.class);
            i.putExtra("show_favorites", "fav");
            startActivity(i);
        }
    }

    @Override
    public void bridgeWithResults(String q) {

    }

    public void getMovies() {
        try {
            Log.i("PROVIDER", "getMovies()");

            String URL = "content://" + MoviesProvider.AUTHORITY + "/" + MoviesProvider.MOVIES_PATH;

            Log.i("PROVIDER", "URI: " + URL);
            Uri movies = Uri.parse(URL);
            Cursor c = managedQuery(movies, MoviesProvider.MOVIES_PROJECTION, null, null, ResultColumns.POPULARITY + " DESC");
            Log.i("PROVIDER", "Registries: " + c.getCount());
            if (c.moveToFirst()) {
                do {
                    Log.i("PROVIDER", "Cursor: "+c.getLong(c.getColumnIndex(ResultColumns._ID.toUpperCase())) +
                            ", " + c.getString(c.getColumnIndex(ResultColumns.ORIGINAL_TITLE.toUpperCase())) +
                            ", " + c.getString(c.getColumnIndex(ResultColumns.ORIGINAL_LANGUAGE.toUpperCase())));
                } while (c.moveToNext());
            }
        } catch (Exception e) {

        }
    }
}

