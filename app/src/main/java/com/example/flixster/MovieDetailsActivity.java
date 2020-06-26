package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    RatingBar rbVoteAverage;
    TextView tvDate;
    TextView tvOverview;

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
    }
}