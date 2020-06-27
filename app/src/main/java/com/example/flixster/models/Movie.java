package com.example.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel // Annotation indicates class is Parcelable
public class Movie {

    // Member variables for data we want from JSON
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String posterSize;
    String backdropSize;
    String releaseDate;
    Double voteAverage;
    Integer id;

    // Default, no-arguments constructor
    public Movie() {}

    // Construct movie object from data in json object
    public Movie(JSONObject jsonObject) throws JSONException {
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.posterPath = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("title");
        this.overview = jsonObject.getString("overview");
        this.releaseDate = jsonObject.getString("release_date");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.id = jsonObject.getInt("id");
        this.posterSize = "w342";
        this.backdropSize = "w780";
    }

    // Takes a Json array and Config Object and constructs a list of Movie objects from the movie data
    // stored in the Json array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieJsonArray.length(); i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public Integer getId() { return id; }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/%s%s", posterSize, posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/%s%s", backdropSize, backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
