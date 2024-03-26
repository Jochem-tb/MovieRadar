package com.example.movieradar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieApiTask.OnNewMovieListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ev1 = findViewById(R.id.id1);
        EditText ev2 = findViewById(R.id.id2);
        EditText ev3 = findViewById(R.id.id3);

        APIString str = new APIString();
        str.search("spiderman");
        str.isAdult(true);
        str.setPage(1);
        str.sort(SortType.POPULARITY_DESC);
        str.finish();
        ev1.setText(str.getApiString());

        MovieApiTask task = new MovieApiTask(this);
        task.execute("https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=731b0900535ff5476ae98c326ef7413c");
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            Log.i("MAIN",movies.get(i).toString());
        }
    }

    @Override
    public void OnNewMovieListener(ArrayList<Movie> movies) {

    }
}