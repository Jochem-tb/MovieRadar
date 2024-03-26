package com.example.movieradar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApiTask extends AsyncTask<String, Void, ArrayList<Movie>> {


    private Context context;
    private final String LOG_TAG = "MovieApiTask";

    private ArrayList<Movie> movies = new ArrayList<>();
    private OnNewMovieListener listener;

    private static final String JSON_RESULTS = "results";
    private static final String JSON_ADULT = "adult";
    private static final String JSON_BACKDROP_PATH = "backdrop_path";
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_ORIGINAL_LANGUAGE = "original_language";
    private static final String JSON_ORIGINAL_TITLE = "original_title";
    private static final String JSON_OVERVIEW = "overview";
    private static final String JSON_POSTER_PATH = "poster_path";
    private static final String JSON_MEDIA_TYPE = "media_type";
    private static final String JSON_GENRE_IDS = "genre_ids";
    private static final String JSON_POPULARITY = "popularity";
    private static final String JSON_RELEASE_DATE = "release_date";
    private static final String JSON_VIDEO = "video";
    private static final String JSON_VOTE_AVERAGE = "vote_average";
    private static final String JSON_VOTE_COUNT = "vote_count";


    // Interface voor Listener
    public interface OnNewMovieListener {
        void onMovieAvailable(ArrayList<Movie> movies);

        void OnNewMovieListener(ArrayList<Movie> movies);
    }

    //Constructor voor listener
    public MovieApiTask(OnNewMovieListener listener) {
        super();
        Log.i(LOG_TAG, "Constructor");
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
                .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                .build();
        try {
            Response response = client.newCall(request).execute();
            movies = jsonParseResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        Log.i(LOG_TAG, "onPostExecute");

    }

    private ArrayList<Movie> jsonParseResponse(Response response) {
        Log.i(LOG_TAG, "jsonParseResponse");
        Log.i("ResponseString", String.valueOf(response));
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        ArrayList<Genre> genreArrayList = new ArrayList<>();

        try {
            String jsonResponse = response.body().string(); // Extract response body as String
            Log.i(LOG_TAG,jsonResponse);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray(JSON_RESULTS);
            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i); // No need to cast, already JSONObject
                boolean adult = movie.getBoolean(JSON_ADULT);
                String backdropPath = movie.getString(JSON_BACKDROP_PATH);
                String id = movie.getString(JSON_ID);
                String title = movie.getString(JSON_TITLE);
                String originalLanguage = movie.getString(JSON_ORIGINAL_LANGUAGE);
                String originalTitle = movie.getString(JSON_ORIGINAL_TITLE);
                String overview = movie.getString(JSON_OVERVIEW);
                String posterPath = movie.getString(JSON_POSTER_PATH);
                String mediaType = movie.getString(JSON_MEDIA_TYPE);
                JSONArray genreIds = movie.getJSONArray(JSON_GENRE_IDS);
                for (int j = 0; j < genreIds.length(); j++) {
                    Genre gen = new Genre();
                    gen.setId(genreIds.getInt(j));
                    genreArrayList.add(gen);
                }
                double popularity = movie.getDouble(JSON_POPULARITY);
                String releaseDate = movie.getString(JSON_RELEASE_DATE);
                boolean video = movie.getBoolean(JSON_VIDEO);
                double voteAverage = movie.getDouble(JSON_VOTE_AVERAGE);
                int voteCount = movie.getInt(JSON_VOTE_COUNT);

                movieArrayList.add(new Movie(
                        Integer.valueOf(id),
                        adult,
                        backdropPath,
                        genreArrayList,
                        originalLanguage,
                        overview,
                        popularity,
                        posterPath,
                        releaseDate,
                        title,
                        video,
                        (float)voteAverage,
                        voteCount
                ));
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return movieArrayList;
    }
}
