package com.mobileappsco.training.mymovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements SearchFragment.SearchFragmentListener, FormFragment.FormFragmentListener {

    SearchFragment searchFragment;
    FormFragment formFragment;

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
        formFragment = (FormFragment) getFragmentManager().findFragmentById(R.id.form_fragment);
    }

    @Override
    public void bridgeWithForm(String title, String year) {
        searchFragment.makeJsonObjectRequest(title, year);
    }

    @Override
    public void bridgeWithSearch(String q) {

    }
}

