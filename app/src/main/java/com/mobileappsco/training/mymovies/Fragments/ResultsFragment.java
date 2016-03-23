package com.mobileappsco.training.mymovies.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileappsco.training.mymovies.Entities.Page;
import com.mobileappsco.training.mymovies.Entities.Result;
import com.mobileappsco.training.mymovies.Helpers;
import com.mobileappsco.training.mymovies.MainActivity;
import com.mobileappsco.training.mymovies.R;
import com.mobileappsco.training.mymovies.Adapters.RVAdapter;
import com.mobileappsco.training.mymovies.Retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsFragment extends Fragment {

    String search_title, search_year;
    SearchFragmentListener searchListener;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    List<Result> results;
    RVAdapter adapter;
    Context context;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    boolean loading = false;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(String title, String year) {
        final Bundle args = new Bundle();
        args.putString("search_title", title);
        args.putString("search_year", year);
        ResultsFragment resultsFragment = new ResultsFragment();
        resultsFragment.setArguments(args);
        return resultsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        // Get arguments if a search was performed
        Bundle args = getArguments();
        if (args!=null) {
            search_title = args.getString("search_title");
            search_year = args.getString("search_year");
            Helpers.logAndToast(getContext(), "Received title (" + search_title + ") and year (" + search_year + ")", Log.INFO);
        }

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_results, container, false);
        // Recycler View in ResultsFragment
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_list_results);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        FetchMoviesTask mytask = new FetchMoviesTask();
        mytask.execute(search_title, search_year);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            searchListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchListener = null;
    }

    public interface SearchFragmentListener {
        void bridgeWithSearch(String q);
    }

    private class FetchMoviesTask extends AsyncTask<String, Integer, List<Result>> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            try {
                if (results==null) {
                    Helpers.logAndToast(getContext(), "No results found", Log.INFO);
                } else {
                    adapter = new RVAdapter(context, results);
                    recyclerView.setAdapter(adapter);
                }
            } catch (Exception e) {
                Log.e("MYTAG", "ERROR for: " +e.getMessage());
            }
        }

        @Override
        protected List<Result> doInBackground(String... params) {

            Helpers.logAndToast(getContext(), "String... params => "+params[0]+", "+params[1], Log.INFO);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.API_JSON_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitInterface rfInterface = retrofit.create(RetrofitInterface.class);

            boolean hasTitle = false;
            boolean hasYear = false;
            if (params[0] != null && params[0].length()>0)
                hasTitle = true;
            if (params[1] != null && params[1].length()>0)
                hasYear = true;

            Call<Page> request;
            if (hasTitle == true && hasYear == false) {
                request = rfInterface.searchMovieByTitle(MainActivity.API_KEY, params[0], "popularity.desc");
                Helpers.logAndToast(getContext(), "searchMovieByTitle", Log.INFO);
            } else if (hasTitle == false && hasYear == true) {
                request = rfInterface.searchMovieByYear(MainActivity.API_KEY, params[1], "vote_average.desc");
                Helpers.logAndToast(getContext(), "searchMovieByYear", Log.INFO);
            } else if (hasTitle == true && hasYear == true) {
                request = rfInterface.searchMovieByTitleAndYear(MainActivity.API_KEY, params[0], params[1], "primary_release_year.desc");
                Helpers.logAndToast(getContext(), "searchMovieByTitleAndYear", Log.INFO);
            } else {
                request = rfInterface.discoverMovies(MainActivity.API_KEY, "popularity.desc");
                Helpers.logAndToast(getContext(), "discoverMovies", Log.INFO);
            }

            Page pages = null;
            List<Result> resultsasync = new ArrayList<>();
            try {
                pages = request.execute().body();
                resultsasync = pages.getResults();
            } catch (Exception e) {
                Helpers.logAndToast(getContext(), "Error getting results "+e.getMessage(), Log.INFO);
            }
            return resultsasync;

        }
    }

}
