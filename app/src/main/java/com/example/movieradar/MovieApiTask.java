package com.example.movieradar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApiTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private Context context;
    private static final String LOG_TAG = "MovieApiTask";

    private ArrayList<Movie> movies = new ArrayList<>();
    private OnNewMovieListener listener;



    // Interface voor Listener
    public interface OnNewMovieListener {
        void onMovieAvailable(ArrayList<Movie> movies);
    }

    //Constructor voor listener
    public MovieApiTask(OnNewMovieListener listener){
        super();
        Log.i(LOG_TAG, "Constructor");
        this.listener = listener;
    }


    @Override
    protected ArrayList<Movie> doInBackground(String... params){
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
