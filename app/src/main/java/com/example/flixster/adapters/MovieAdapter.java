package com.example.flixster.adapters;

import android.content.Context;
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
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Member variables will hold references to the different views in our design
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        // Populate each view in the VH with its corresponding data in the Movie object
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imgUrl;
            int placeholder;
            // If the phone is in portrait mode
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // set imageUrl = posterPath
                imgUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }
            // Else
            else {
                // set imageUrl = backdropPath
                imgUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            }

            // We need to use Glide to load movie poster/backdrop as no
            // built-in Android library does this
            int radius = 30, margin = 0;
            Glide.with(context)
                    .load(imgUrl)
                    .placeholder(placeholder)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .override(ivPoster.getWidth(), ivPoster.getHeight())
                    .into(ivPoster);
        }
    }
}
