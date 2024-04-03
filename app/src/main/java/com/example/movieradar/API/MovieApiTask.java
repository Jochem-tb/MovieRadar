package com.example.movieradar.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.movieradar.Genre;
import com.example.movieradar.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MovieApiTask extends AsyncTask<String, Void, ArrayList<Movie>> {


    private Context context;
    private final String LOG_TAG = "MovieApiTask";


    private ArrayList<Movie> movies = new ArrayList<>();
    private OnNewMovieListener listener;
    private int apiIdentifier;


    // Interface voor Listener
    public interface OnNewMovieListener {
        void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier);
    }

    //Constructor voor listener
    public MovieApiTask(OnNewMovieListener listener, int apiIdentifier) {
        super();
        Log.i(LOG_TAG, "Constructor + Identifier");
        this.apiIdentifier = apiIdentifier;
        this.listener = listener;
    }

    //Constructor voor listener
    public MovieApiTask(OnNewMovieListener listener) {
        super();
        Log.i(LOG_TAG, "Constructor");
        this.apiIdentifier = 1;
        this.listener = listener;
    }


    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        ArrayList<Movie> movies = new ArrayList<>();
        String urlString = params[0];
        Log.i(LOG_TAG, "doInBackground " + urlString);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ")
                .build();
        try {
            if(apiIdentifier==52){
                Log.d(LOG_TAG, "Parse with apiIdentifier = "+apiIdentifier);
                Response response = client.newCall(request).execute();
                movies = retrieveKey(response);
            }else if (apiIdentifier ==1) {
                Log.d(LOG_TAG, "Parse with apiIdentifier = "+apiIdentifier);
                Response response = client.newCall(request).execute();
                movies = betterJsonPars(response);
            }else if (apiIdentifier ==2) {
                Log.d(LOG_TAG, "Parse with apiIdentifier = "+apiIdentifier);
                Response response = client.newCall(request).execute();
                movies = specificJsonParseMovie(response);
            }else if (apiIdentifier ==15) {
                Log.d(LOG_TAG, "Parse with apiIdentifier = "+apiIdentifier);
                Response response = client.newCall(request).execute();
                movies = betterJsonPars(response);
            }
            else{
                Log.d(LOG_TAG, "Invalid apiIdentifier = "+apiIdentifier);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error fetching movie data", e);
        }
        return movies;
    }




    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        Log.i(LOG_TAG, "onPostExecute");
        listener.onMovieAvailable(movies, apiIdentifier);
    }

    private ArrayList<Movie> retrieveKey(Response response){
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject videosObject = jsonObject.getJSONObject("videos");
            JSONArray resultsArray = videosObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultObject = resultsArray.getJSONObject(i);
                String type = resultObject.optString("type");

                if ("Trailer".equals(type)) {
                    String key = resultObject.getString("key");
                    Movie m = new Movie();
                    m.setKey(key);
                    movieList.add(m);
                    Log.d(LOG_TAG, "Trailer key = "+key);
                    break; // Assuming only one trailer is needed
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return movieList;
    }


    private ArrayList<Movie> betterJsonPars(Response response) {
        Log.i(LOG_TAG, "betterJsonPars");
        Log.i("ResponseString", String.valueOf(response));

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        try {
            String jsonResponse = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.get("results");
            if (resultsNode != null && resultsNode.isArray()) {
                for (JsonNode movieNode : resultsNode) {
                    Movie movie = objectMapper.treeToValue(movieNode, Movie.class);
                    movieArrayList.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieArrayList;
    }

    private ArrayList<Movie> specificJsonParseMovie(Response response) {
        Log.i(LOG_TAG, "specificJsonParseMovie");
        Log.i("ResponseString", String.valueOf(response));

        try {
            String jsonResponse = response.body().string();
            ArrayList<Movie> movieArrayList = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                Movie movie = objectMapper.treeToValue(rootNode, Movie.class);
                movieArrayList.add(movie);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieArrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
