package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String TAG = "MovieAdapter";

    Context context;        // Defines where this adapter is being constructed from
    List<Movie> movies;     // Data source (Movie is model class)

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        // Inflates the item_movie layout we previously defined to get a view
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves population data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the position that was passed
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Return total count of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Member variables will hold references to the different views in our design
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        // Populate each view in the VH with its corresponding data in the Movie object
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());

            String imgUrl;
            int placeholder, maxCharsDisplayed;
            // If the phone is in portrait mode
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // set imageUrl = posterPath
                maxCharsDisplayed = 150;
                imgUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }
            // Else
            else {
                // set imageUrl = backdropPath
                maxCharsDisplayed = 250;
                imgUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            }

            String overview;
            // If the movie's overview is less than 146 characters, we don't need to
            // concatenate a "..." as the entire overview can be displayed
            if (movie.getOverview().length() < maxCharsDisplayed - 4) {
                overview = movie.getOverview();
            }
            // Else we have too long of an overview so we need to concatenate our overview
            // with a "..." to imply there's more to read
            else {
                String sub = movie.getOverview().substring(0, maxCharsDisplayed - 4);
                overview = String.format("%s...", sub);
            }
            tvOverview.setText(overview);

            // We need to use Glide to load movie poster/backdrop as no
            // built-in Android library does this
            int radius = 30, margin = 0;
            Glide.with(context)
                    .load(imgUrl)
                    .placeholder(placeholder)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    //.override(ivPoster.getWidth(), ivPoster.getHeight())
                    .into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            // Retrieve and confirm valid position
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the movie at the retrieved position
                Movie movie = movies.get(position);

                // Creating an intent to show MovieDetailsActivity
                final Intent intent = new Intent(context, MovieDetailsActivity.class);

                // Pass the movie to new Activity, serialized by Parcel.wrap()
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                // Display the activity
                context.startActivity(intent);
            }
        }
    }
}
