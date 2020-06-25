package com.example.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class Movie {

    // Member variables for data we want from JSON
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String baseUrl;
    String posterSize;
    String backdropSize;

    // Construct movie object from data in json object
    public Movie(JSONObject jsonObject) throws JSONException {
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.posterPath = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("title");
        this.overview = jsonObject.getString("overview");
    }

    // Takes a Json array and Config Object and constructs a list of Movie objects from the movie data
    // stored in the Json array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, Config config) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieJsonArray.length(); i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
            movies.get(movies.size() - 1).setBackdropSize(config.getBackdropSize());
            movies.get(movies.size() - 1).setPosterSize(config.getPosterSize());
            movies.get(movies.size() - 1).setBaseUrl(config.getBaseUrl());
        }

        return movies;
    }

    public void setPosterSize(String posterSize) {
        this.posterSize = posterSize;
    }

    public void setBackdropSize(String backdropSize) {
        this.backdropSize = backdropSize;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPosterPath() {
        return baseUrl + posterSize + posterPath;
    }

    public String getBackdropPath() {
        return baseUrl + backdropSize + backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
