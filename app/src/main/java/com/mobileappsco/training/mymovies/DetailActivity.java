package com.mobileappsco.training.mymovies;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobileappsco.training.mymovies.Entities.Favorites;
import com.mobileappsco.training.mymovies.Entities.PageResults;
import com.mobileappsco.training.mymovies.Entities.PageVideos;
import com.mobileappsco.training.mymovies.Entities.Result;
import com.mobileappsco.training.mymovies.Entities.Video;
import com.mobileappsco.training.mymovies.Retrofit.RetrofitInterface;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private YouTubePlayerFragment youTubeFragment;
    TextView detailTitle;
    TextView detailOriginalTitle;
    TextView detailOverview;
    TextView detailVoteAverage;
    TextView detailReleaseDate;
    ImageView detailPoster;
    String API_KEY;
    String API_IMAGE_URL;
    String YOUTUBE_API_KEY;
    String language;
    String YOUTUBE_VIDEO_V;
    ActionBar actionBar;
    String result_id;

    // TODO read videos from youtube for the detail view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_detail);

        // Set icon in toolbar
        actionBar = getActionBar();
        //actionBar.setDisplayShowHomeEnabled(true);

        // Retrieving the API keys for TheMovieDB and YouTube
        API_KEY = getString(R.string.API_KEY);
        API_IMAGE_URL = getString(R.string.API_IMAGES_URL);
        YOUTUBE_API_KEY = getString(R.string.YOUTUBE_API_KEY);
        language = getString(R.string.language);

        // Point to the Views we need to display informatino
        detailTitle = (TextView) findViewById(R.id.detail_title);
        detailOriginalTitle = (TextView) findViewById(R.id.detail_original_title);
        detailOverview = (TextView) findViewById(R.id.detail_overview);
        detailVoteAverage = (TextView) findViewById(R.id.detail_vote_average);
        detailReleaseDate = (TextView) findViewById(R.id.detail_release_date);
        detailPoster = (ImageView) findViewById(R.id.detail_poster);

        if (getIntent().hasExtra("result_id")) {
            initDetails();
        }
    }

    public void initDetails() {
        result_id = getIntent().getExtras().getString("result_id");
        //List<Result> results = Result.find(Result.class, "mid = ?", result_id);
        List<Result> results = SugarRecord.find(Result.class, "id = ?", result_id);
        if (results.size()>0) {
            displayDetails(results.get(0));
        }
    }

    public void displayDetails(Result result) {
        //Toast.makeText(this, "Title: "+result.getTitle()+"\nYear: "+result.getReleaseDate(), Toast.LENGTH_SHORT).show();
        detailTitle.setText(result.getTitle());
        detailOriginalTitle.setText(result.getOriginalTitle());
        detailOverview.setText(result.getOverview());
        detailVoteAverage.setText(result.getVoteAverage().toString());
        detailReleaseDate.setText(result.getReleaseDate());
        loadYoutubeVideo(result.getId().toString());
        Glide.with(this)
                .load(API_IMAGE_URL + result.getPosterPath())
                .into(detailPoster);
        //actionBar.setTitle(result.getTitle());
    }

    public void loadYoutubeVideo(String id) {
        // TODO start asynctask here
        Log.i("MYTAG", "Loading media for: " + id);
        FetchMovieMedia mytask = new FetchMovieMedia();
        mytask.execute(id, API_KEY, language);
    }

    public void setupVideo(String v) {
        Log.i("MYTAG", "Loading video: " + v);
        // YouTube initialization
        YOUTUBE_VIDEO_V = v;
        //youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        //youTubeView.initialize(YOUTUBE_API_KEY, this);
        youTubeFragment = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        youTubeFragment.initialize(YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(YOUTUBE_VIDEO_V); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_favorite) {
            Favorites fav = new Favorites();
            fav.setId(Long.parseLong(result_id));
            SugarRecord.save(fav);
            Toast.makeText(this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchMovieMedia extends AsyncTask<String, Integer, List<Video>> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Video> resultstask) {
            super.onPostExecute(resultstask);
            try {
                if (resultstask==null) {
                    Toast.makeText(DetailActivity.this, "No media found", Toast.LENGTH_SHORT).show();
                } else {
                    if (resultstask.size()>0) {
                        Video ytvideo = resultstask.get(resultstask.size()-1);
                        setupVideo(ytvideo.getKey().toString());
                    } else {
                        Log.i("MYTAG", "No media found");
                    }
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected List<Video> doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.API_JSON_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitInterface rfInterface = retrofit.create(RetrofitInterface.class);
            Call<PageVideos> request = rfInterface.fetchVideosOfMovie(
                    params[0],
                    params[1],
                    params[2]);

            List<Video> resultsasync = new ArrayList<>();
            PageVideos pages = null;
            try {
                pages = request.execute().body();
                resultsasync = pages.getResults();
            } catch (Exception e) {
                Log.e("MYTAG", "Error getting media "+e.getMessage());
            }

            return resultsasync;
        }
    }
}
