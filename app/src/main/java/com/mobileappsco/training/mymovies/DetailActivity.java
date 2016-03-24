package com.mobileappsco.training.mymovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.mobileappsco.training.mymovies.Entities.Result;
import com.orm.SugarRecord;

import java.util.List;

public class DetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    // TODO read videos from youtube for the detail view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent().hasExtra("result_id")) {
            initDetails();
        }
    }

    public void initDetails() {
        String result_id = getIntent().getExtras().getString("result_id");
        Log.i("MYTAG", "result_id " + result_id);
        //List<Result> results = Result.find(Result.class, "mid = ?", result_id);
        List<Result> results = SugarRecord.find(Result.class, "id = ?", result_id);
        if (results.size()>0) {
            displayDetails(results.get(0));
        }
    }

    public void displayDetails(Result result) {
        Toast.makeText(this, "Title: "+result.getTitle()+"\nYear: "+result.getReleaseDate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
