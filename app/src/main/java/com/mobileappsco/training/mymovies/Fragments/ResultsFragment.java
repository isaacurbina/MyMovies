package com.mobileappsco.training.mymovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappsco.training.mymovies.Adapters.RVAdapter;
import com.mobileappsco.training.mymovies.DetailActivity;
import com.mobileappsco.training.mymovies.Entities.Favorites;
import com.mobileappsco.training.mymovies.Entities.PageResults;
import com.mobileappsco.training.mymovies.Entities.Result;
import com.mobileappsco.training.mymovies.Listeners.EndlessRecyclerOnScrollListener;
import com.mobileappsco.training.mymovies.Listeners.RecyclerItemClickListener;
import com.mobileappsco.training.mymovies.MainActivity;
import com.mobileappsco.training.mymovies.R;
import com.mobileappsco.training.mymovies.Retrofit.RetrofitInterface;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsFragment extends Fragment {

    String search_title, search_year, show_favorites;
    ResultsFragmentListener searchListener;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    RVAdapter adapter;
    Context context;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    boolean loading = false;
    int columns = 1;
    int page = 1;
    String language;

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

    public static ResultsFragment newInstance(String title, String year, String favorites) {
        final Bundle args = new Bundle();
        args.putString("search_title", title);
        args.putString("search_year", year);
        args.putString("show_favorites", favorites);
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
            show_favorites = args.getString("show_favorites");
            Log.i("MYTAG", "variables "+
                            search_title+" - "+
                            search_year+" - "+
                            show_favorites
            );
        }

        this.columns = getSupportedColumns(container.getRootView());
        language = getResources().getString(R.string.language);

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
                    if (isNetworkAvailable()) {
                        fetchContent();
                        Toast.makeText(context, "Loading page " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView card_id = (TextView) view.findViewById(R.id.card_id);
                        String result_id = card_id.getText().toString();
                        //Toast.makeText(context, "click en :" + card_id.getText(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, DetailActivity.class);
                        i.putExtra("result_id", result_id);
                        startActivity(i);
                    }
                })
        );
        // fetch movies for the first page
        fetchContent();
        return v;
    }

    public void fetchContent() {
        // If we have internet we fetch information from the REST service
        if (valueSet(show_favorites)) {
            FetchFavoritesTask mytask = new FetchFavoritesTask();
            mytask.execute();
        } else if (isNetworkAvailable()) {
            FetchMoviesOnlineTask mytask = new FetchMoviesOnlineTask();
            mytask.execute(
                    search_title,
                    search_year,
                    String.valueOf(page),
                    language,
                    getResources().getString(R.string.API_JSON_URL));
        } else { // if no internet, we try to load info from our database*/
            FetchMoviesOfflineTask mytask = new FetchMoviesOfflineTask();
            mytask.execute(
                    search_title,
                    search_year);
        }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void updateResult(Result res1, Result res2) {
        res2.setPosterPath(res1.getPosterPath());
        res2.setAdult(res1.getAdult());
        res2.setOverview(res1.getOverview());
        res2.setReleaseDate(res1.getReleaseDate());
        res2.setGenreIds(res1.getGenreIds());
        res2.setOriginalTitle(res1.getOriginalTitle());
        res2.setOriginalLanguage(res1.getOriginalLanguage());
        res2.setTitle(res1.getTitle());
        res2.setBackdropPath(res1.getBackdropPath());
        res2.setPopularity(res1.getPopularity());
        res2.setVoteCount(res1.getVoteCount());
        res2.setVideo(res1.getVideo());
        res2.setVoteAverage(res1.getVoteAverage());
        SugarRecord.save(res2);
    }

    public boolean valueSet(String text) {
        if (text != null && text.length()>0)
            return true;
        else
            return false;
    }

    private class FetchMoviesOfflineTask extends AsyncTask<String, Integer, List<Result>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Result> resultstask) {
            super.onPostExecute(resultstask);
            adapter = new RVAdapter(context, resultstask);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected List<Result> doInBackground(String... params) {
            String search_title = params[0];
            String search_year = params[1];
            boolean hasTitle = valueSet(search_title);
            boolean hasYear = valueSet(search_year);
            search_title = "%"+search_title+"%";
            List<Result> resultstask;
            if (hasTitle && !hasYear) {
                resultstask = SugarRecord.find(
                        Result.class,
                        "title LIKE ? OR original_title LIKE ? OR overview LIKE ?",
                        search_title, search_title, search_title);
                Log.i("MYTAG", "Offline search by title: "+resultstask.size());
            } else if (!hasTitle && hasYear) {
                resultstask = SugarRecord.find(
                        Result.class,
                        "release_date LIKE ?",
                        search_year+"%");
                Log.i("MYTAG", "Offline search by year: "+resultstask.size());
            } else if (hasTitle && hasYear) {
                resultstask = SugarRecord.find(
                        Result.class,
                        "(title LIKE ? OR original_title LIKE ? OR overview LIKE ?) AND release_date LIKE ?",
                        search_title, search_title, search_title, search_year+"%");
                Log.i("MYTAG", "Offline search by year: "+resultstask.size());
            } else {
                resultstask = SugarRecord.listAll(Result.class, "popularity DESC");
            }
            return resultstask;
        }
    }

    private class FetchMoviesOnlineTask extends AsyncTask<String, Integer, List<Result>> {

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
                    Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show();
                } else {
                    if (adapter != null) {
                        adapter.addResultList(resultstask);
                    } else {
                        adapter = new RVAdapter(context, resultstask);
                        recyclerView.setAdapter(adapter);
                    }
                    // TODO read from database if no internet
                    for (Result res1 : resultstask) {
                        // List<Result> res2 = Result.find(Result.class, "id = ?", res1.getId().toString());
                        List<Result> res2 = SugarRecord.find(Result.class, "id = ?", res1.getId().toString());
                        if (res2.size()==0) {
                            SugarRecord.save(res1);
                            //Log.i("MYTAG", "saving a result "+res1.getId());
                        } else {
                            //Log.i("MYTAG", "already had, updating result " + res1.getId());
                            updateResult(res1, res2.get(0));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("MYTAG", "ERROR for: " +e.getMessage());
            }
            loading = false;
        }

        @Override
        protected List<Result> doInBackground(String... params) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(params[4])
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitInterface rfInterface = retrofit.create(RetrofitInterface.class);

            boolean hasTitle = false;
            boolean hasYear = false;
            String search_title = params[0];
            String search_year = params[1];
            String api_key = MainActivity.API_KEY;
            String page = params[2];
            String language = params[3];
            hasTitle = valueSet(params[0]);
            hasYear = valueSet(params[1]);

            Call<PageResults> request;
            if (hasTitle && !hasYear) {
                request = rfInterface.searchMovieByTitle(
                        api_key,
                        search_title,
                        "popularity.desc",
                        page,
                        language);
            } else if (!hasTitle && hasYear) {
                request = rfInterface.searchMovieByYear(
                        api_key,
                        search_year,
                        "popularity.desc",
                        page,
                        language);
            } else if (hasTitle && hasYear) {
                request = rfInterface.searchMovieByTitleAndYear(
                        api_key,
                        search_title,
                        search_year,
                        "primary_release_year.desc",
                        page,
                        language);
            } else {
                request = rfInterface.discoverMovies(
                        api_key,
                        "popularity.desc",
                        page,
                        language);
            }

            PageResults pages = null;
            List<Result> resultsasync = new ArrayList<>();
            try {
                pages = request.execute().body();
                resultsasync = pages.getResults();
            } catch (Exception e) {
                Log.e("MYTAG", "Error getting results "+e.getMessage());
            }
            return resultsasync;

        }
    }

    private class FetchFavoritesTask extends AsyncTask<String, Integer, List<Result>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Result> resultstask) {
            super.onPostExecute(resultstask);
            adapter = new RVAdapter(context, resultstask);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected List<Result> doInBackground(String... params) {

            List<Result> resultstask = new ArrayList<>();
            List<Favorites> favorites = SugarRecord.listAll(Favorites.class, "id DESC");
            for (Favorites fav1: favorites) {
                Result res1 = SugarRecord.findById(Result.class, fav1.getId());
                if (res1 != null)
                    resultstask.add(res1);
            }
            return resultstask;
        }
    }

}
