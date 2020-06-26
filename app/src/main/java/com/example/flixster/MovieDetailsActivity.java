package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        // Displaying movie title, release date, and overview
        tvTitle.setText(movie.getTitle());

        String releaseDate = String.format("Released: %s", movie.getReleaseDate());
        tvDate.setText(releaseDate);

        tvOverview.setText(movie.getOverview());

        // Displaying vote average with rating stars
        float voteAverage = movie.getVoteAverage().floatValue();
        if (voteAverage > 0) {
            voteAverage /= 2.0f;
        }
        rbVoteAverage.setRating(voteAverage);
    }
}