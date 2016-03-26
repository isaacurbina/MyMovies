package com.mobileappsco.training.mymovies;

import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobileappsco.training.mymovies.Fragments.ResultsFragment;
import com.mobileappsco.training.mymovies.Fragments.SearchFormFragment;

public class ResultsActivity extends AppCompatActivity implements ResultsFragment.ResultsFragmentListener{

    FragmentManager fragmentManager = getFragmentManager();
    ResultsFragment resultsFragment;
    public static String API_JSON_URL, API_IMAGES_URL, API_KEY;
    String RESTAG = "RESTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Set icon in toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        API_KEY = getResources().getString(R.string.API_KEY);
        API_JSON_URL = getResources().getString(R.string.API_JSON_URL);
        API_IMAGES_URL = getResources().getString(R.string.API_IMAGES_URL);

        String search_title = "";
        String search_year = "";
        String show_favorites = "";
        if (getIntent().hasExtra("search_title"))
            search_title = getIntent().getExtras().getString("search_title");
        if (getIntent().hasExtra("search_title"))
            search_year = getIntent().getExtras().getString("search_year");
        if (getIntent().hasExtra("show_favorites"))
            show_favorites = getIntent().getExtras().getString("show_favorites");
        resultsFragment = ResultsFragment.newInstance(search_title, search_year, show_favorites);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.results_fragment_container, resultsFragment, RESTAG)
                .commit();
    }

    @Override
    public void bridgeWithResults(String q) {

    }
}
