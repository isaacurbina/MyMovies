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
import android.widget.Toast;

import com.mobileappsco.training.mymovies.Listeners.EndlessRecyclerOnScrollListener;
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
    ResultsFragmentListener searchListener;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    RVAdapter adapter;
    Context context;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    boolean loading = false;
    int columns = 1;
    int page = 1;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    // Dynamically display different columns according to the device width
    public int getSupportedColumns(View root) {
        if (root.findViewById(R.id.tablet_portrait) != null)
            return 3;
        else if (root.findViewById(R.id.tablet_land) != null)
            return 4;
        else if (root.findViewById(R.id.phone_land) != null)
            return 3;
        else
            return 2;
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

        this.columns = getSupportedColumns(container.getRootView());
        Helpers.logAndToast(getContext(), "Supports => " + this.columns + " columns", Log.INFO);

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_results, container, false);
        // Recycler View in ResultsFragment
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_list_results);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...
                if (!loading) {
                    loading = true;
                    page++;
                    Toast.makeText(context, "Loading page "+page, Toast.LENGTH_SHORT).show();
                    FetchMoviesTask mytask = new FetchMoviesTask();
                    mytask.execute(search_title, search_year, String.valueOf(page));
                }
            }
        });
        FetchMoviesTask mytask = new FetchMoviesTask();
        mytask.execute(search_title, search_year, String.valueOf(page));
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ResultsFragmentListener) {
            searchListener = (ResultsFragmentListener) context;
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

    public interface ResultsFragmentListener {
        void bridgeWithResults(String q);
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
        protected void onPostExecute(List<Result> resultstask) {
            super.onPostExecute(resultstask);
            try {
                if (resultstask==null) {
                    Helpers.logAndToast(getContext(), "No results found", Log.INFO);
                } else {
                    //results.addAll(resultstask);
                    if (adapter != null) {
                        adapter.addResultList(resultstask);
                    } else {
                        adapter = new RVAdapter(context, resultstask);
                        recyclerView.setAdapter(adapter);
                    }
                }
            } catch (Exception e) {
                Log.e("MYTAG", "ERROR for: " +e.getMessage());
            }
            loading = false;
        }

        @Override
        protected List<Result> doInBackground(String... params) {

            Helpers.logAndToast(getContext(), "String... params => " + params[0] + ", " + params[1] + ", " + params[2], Log.INFO);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.API_JSON_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitInterface rfInterface = retrofit.create(RetrofitInterface.class);

            boolean hasTitle = false;
            boolean hasYear = false;
            String search_title = params[0];
            String search_year = params[1];
            String api_key = MainActivity.API_KEY;
            String page = params[2];
            if (params[0] != null && params[0].length()>0) {
                hasTitle = true;
            }
            if (params[1] != null && params[1].length()>0) {
                hasYear = true;
            }

            Call<Page> request;
            if (hasTitle == true && hasYear == false) {
                request = rfInterface.searchMovieByTitle(
                        api_key,
                        search_title,
                        "popularity.desc",
                        page);
                Helpers.logAndToast(getContext(), "searchMovieByTitle", Log.INFO);
            } else if (hasTitle == false && hasYear == true) {
                request = rfInterface.searchMovieByYear(
                        api_key,
                        search_year,
                        "vote_average.desc",
                        page);
                Helpers.logAndToast(getContext(), "searchMovieByYear", Log.INFO);
            } else if (hasTitle == true && hasYear == true) {
                request = rfInterface.searchMovieByTitleAndYear(
                        api_key,
                        search_title,
                        search_year,
                        "primary_release_year.desc",
                        page);
                Helpers.logAndToast(getContext(), "searchMovieByTitleAndYear", Log.INFO);
            } else {
                request = rfInterface.discoverMovies(
                        api_key,
                        "popularity.desc",
                        page);
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
