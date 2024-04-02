package com.example.movieradar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.movieradar.API.APIString;
import com.example.movieradar.API.MovieApiTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Catalogus extends AppCompatActivity implements MovieApiTask.OnNewMovieListener {

    private final String LOG_TAG = "Catalogus";
    private final int POPULAR_MOVIES = 1;
    private final int RANDOM_MOVIES = 2;
    BottomNavigationView btmNavView;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView rvCatalogusVertical;
    private CatalogusAdapter catalogusAdapter;
    private TableLayout tlCatalogus;
    private Button bFilterMenuToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogus);
        toolbar = findViewById(R.id.tbCatalogus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvCatalogusVertical = findViewById(R.id.rvCatalogus);
        rvCatalogusVertical.setLayoutManager(new LinearLayoutManager(this));
        catalogusAdapter = new CatalogusAdapter(this, mMovieList);
        rvCatalogusVertical.setAdapter(catalogusAdapter);

        loadMoviesFromAPI(APIString.getPopularUrl(), RANDOM_MOVIES);

        btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.tbCatalogus);

        tlCatalogus = findViewById(R.id.tlCatalogus);
        tlCatalogus.setVisibility(View.GONE);

        bFilterMenuToggle = findViewById(R.id.bFilterMenuToggle);
        bFilterMenuToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tlCatalogus.getVisibility() == View.VISIBLE) {
                    tlCatalogus.setVisibility(View.GONE);
                } else {
                    tlCatalogus.setVisibility(View.VISIBLE);
                }
            }
        });

        //NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                //Heb geprobeerd met switch/case maar geeft errors
                if (id == R.id.menuHomeScreen) {
                    //Intent Catalogus = new Intent(Catalogus.this, Catalogus.class);
                    Log.i(LOG_TAG, "HomeScreen button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
                    //Intent Catalogus = new Intent(Catalogus.this, Catalogus.class);
                    Log.i(LOG_TAG, "catalogus button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
                    //Intent Persoonlijk = new Intent(Catalogus.this, Persoonlijk.class);
                    Log.i(LOG_TAG, "Persoonlijk button clicked");
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void loadMoviesFromAPI(String APIUrl, int apiIdentifier) {
        MovieApiTask task = new MovieApiTask(this, apiIdentifier);
        task.execute(APIUrl);
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
        switch (apiIdentifier) {
            case POPULAR_MOVIES:
                mMovieList = movies;
                break;
            case RANDOM_MOVIES:
                loadRecyclerView(movies);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnNewMovieListener(ArrayList<Movie> movies) {

    }

    public void loadRecyclerView(ArrayList<Movie> movies) {
        mMovieList.clear();
        mMovieList.addAll(movies);
        Log.i(LOG_TAG, "Movies added to recyclerview");
    }


}