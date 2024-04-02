package com.example.movieradar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

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
    private final int SEARCH_MOVIE = 15;
    BottomNavigationView btmNavView;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private ArrayList<Movie> mSearchResultsList = new ArrayList<>();
    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView rvCatalogusVertical;
    private RecyclerView rvCatalogusTop;
    private CatalogusAdapter catalogusAdapter;
    private MovieListAdapter movieListAdapter;
    private TableLayout tlCatalogus;
    private Button bFilterMenuToggle;

    private SearchView svCatalogus;

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
        rvCatalogusTop = findViewById(R.id.rvCatalogusTop);
        rvCatalogusTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieListAdapter = new MovieListAdapter(this, mSearchResultsList);
        rvCatalogusTop.setAdapter(movieListAdapter);

        loadMoviesFromAPI(APIString.getPopularUrl(), RANDOM_MOVIES);

        btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.tbCatalogus);

        tlCatalogus = findViewById(R.id.tlCatalogus);
        tlCatalogus.setVisibility(View.GONE);

        svCatalogus = findViewById(R.id.svCatalogus);
        svCatalogus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true; // Return true to indicate that the query has been handled.
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



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

    private void performSearch(String query){
//        rvCatalogusTop.setVisibility(View.GONE);
        MovieApiTask task = new MovieApiTask(this, SEARCH_MOVIE);
        APIString str = new APIString();
        str.search(query);
        str.finish();
        task.execute(str.getApiString());
//        https://api.themoviedb.org/3/search/movie?query=Spider&include_adult=false&language=en-US&page=1

//        task.execute("https://api.themoviedb.org/3/search/movie?query=Spider&include_adult=false&language=en-US&page=1&api_key=731b0900535ff5476ae98c326ef7413c");
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
        switch (apiIdentifier) {
            case POPULAR_MOVIES:
                Log.d(LOG_TAG, "MovieAvailable with "+POPULAR_MOVIES);
                mMovieList = movies;
                break;
            case RANDOM_MOVIES:
                Log.d(LOG_TAG, "MovieAvailable with "+RANDOM_MOVIES);
                loadRecyclerView(movies);
                break;
            case SEARCH_MOVIE:
                Log.d(LOG_TAG, "MovieAvailable with "+SEARCH_MOVIE);
                loadSearchOnTopView(movies);
                break;
            default:
                break;
        }
    }

    private void loadSearchOnTopView(ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "loadSearchOnTopView with: "+movies.size()+" movies");
        mSearchResultsList.clear();
        mSearchResultsList.addAll(movies);
        movieListAdapter.setMovieList(mSearchResultsList);
        movieListAdapter.notifyDataSetChanged();
    }


    public void loadRecyclerView(ArrayList<Movie> movies) {
        mMovieList.clear();
        mMovieList.addAll(movies);
        Log.i(LOG_TAG, "Movies added to recyclerview");
    }


}