package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "MovieDetailsActivity";
    public static final int RECOVERY_REQUEST = 1;

    public static final String baseUrl = "https://api.themoviedb.org/3/movie/";
    public static final String midUrl =  "/videos?api_key=";
    public static final String endUrl = "&language=en-US";

    Movie movie;
    TextView tvTitle;
    RatingBar rbVoteAverage;
    TextView tvDate;
    TextView tvOverview;
    ImageView ivBackdrop;
    String id;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Retrieve and unwrap movie from previous Activity using Parcels.unwrap()
        movie = Parcels.unwrap(getIntent().getExtras().getParcelable(Movie.class.getSimpleName()));

        Log.d("MovieDetailsActivity", "Movie received: " + movie.getTitle());

        // Resolve the new objects
        tvTitle = findViewById(R.id.tvTitle);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvDate = findViewById(R.id.tvDate);
        tvOverview = findViewById(R.id.tvOverview);
        ivBackdrop = findViewById(R.id.ivBackdrop);
        id = "";
        youTubePlayerView = findViewById(R.id.youtubePlayerView);

        youTubePlayerView.initialize(getString(R.string.yt_api_key), this);

        // Displaying movie title
        tvTitle.setText(movie.getTitle());

        // Displaying movie release date with concatenation
        String releaseDate = String.format("Released: %s", movie.getReleaseDate());
        tvDate.setText(releaseDate);

        // Displaying movie overview and setting scrolling movement in case of long
        // overview
        tvOverview.setText(movie.getOverview());
        tvOverview.setMovementMethod(new ScrollingMovementMethod());

        // Displaying vote average with rating stars. Need to divide to scale to rating
        // of 0 to 5 instead of 0 to 10
        float voteAverage = movie.getVoteAverage().floatValue();
        if (voteAverage > 0) {
            voteAverage /= 2.0f;
        }
        rbVoteAverage.setRating(voteAverage);

        // Displaying the backdrop image
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(ivBackdrop);

        // Constructing full url to make API request soon
        String fullUrl = String.format("%s%d%s%s%s", baseUrl, movie.getId(), midUrl, "9144031636e4d899903ed80fc2b6d903", endUrl);

        // API request to /movies/{movie_id}/videos
        AsyncHttpClient videoClient = new AsyncHttpClient();
        videoClient.get(fullUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject resultsJSONObject = results.getJSONObject(0);
                    id = resultsJSONObject.getString("key");
                    Log.d(TAG, "onSuccess");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure()");
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(id);
        youTubePlayer.setFullscreen(false);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d(TAG, "onInitializationFailure: Failed initialization");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(getString(R.string.yt_api_key), this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }
}