package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    String posterSize;
    String backdropSize;
    String baseUrl;

    public Config(JSONObject jsonObject) throws JSONException {
        posterSize = jsonObject.getJSONArray("poster_sizes").optString(3, "w342");
        backdropSize = jsonObject.getJSONArray("backdrop_sizes").optString(2, "w780");
        baseUrl = jsonObject.getString("secure_base_url");
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
