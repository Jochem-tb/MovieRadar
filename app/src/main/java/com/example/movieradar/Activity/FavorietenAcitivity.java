package com.example.movieradar.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.Adapters.MovieListAdapter;
import com.example.movieradar.Movie;
import com.example.movieradar.R;
import com.example.movieradar.database.FavoMovieDao;
import com.example.movieradar.database.FavoMovieDatabase;

import java.util.ArrayList;

public class FavorietenAcitivity extends AppCompatActivity {
    private final String LOG_TAG = "FavorietenAcitivity";

    Toolbar toolbar;
    RecyclerView recyclerViewFavorieten;
    MovieListAdapter mAdapter;
    ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    FavoMovieDatabase favoMovieDatabase;
    FavoMovieDao favoMovieDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorieten);

        Log.i(LOG_TAG, "onCreate");

        toolbar = findViewById(R.id.tb_detail_ticket);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.favorieten_title);

        // Terugknop inschakelen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setup views
        recyclerViewFavorieten = findViewById(R.id.rv_favorieten);
        mAdapter = new MovieListAdapter(this, mMovieList);
        recyclerViewFavorieten.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewFavorieten.setLayoutManager(mLayoutManager);

        favoMovieDatabase = FavoMovieDatabase.getDatabase(this);
        favoMovieDao = favoMovieDatabase.favoMovieDao();

        new GetAllFavoMovies().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAllFavoMovies().execute();
    }

    private class GetAllFavoMovies extends AsyncTask<Void, Void, Void> {
        ArrayList<Movie> list;
        @Override
        protected Void doInBackground(Void... voids) {
            list = new ArrayList<>(favoMovieDao.getAllFavoMovie());
            Log.d(LOG_TAG, "Fetched movies from Dao: "+list.size());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.setMovieList(list);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Call onBackPressed() when the home button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
