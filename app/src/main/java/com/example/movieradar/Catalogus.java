package com.example.movieradar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

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
    private LinearLayout lCatalogusCheckboxes;
    private MovieListAdapter movieListAdapter;
    private ImageButton bFilterMenuToggle;
    ArrayList<Integer> checkedBoxes = new ArrayList<>();
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
        catalogusAdapter = new CatalogusAdapter(this, checkedBoxes);
        rvCatalogusVertical.setAdapter(catalogusAdapter);
        
        rvCatalogusTop = findViewById(R.id.rvCatalogusTop);
        rvCatalogusTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        movieListAdapter = new MovieListAdapter(this, mSearchResultsList);

        rvCatalogusTop.setAdapter(movieListAdapter);
        rvCatalogusTop.setVisibility(View.GONE);

        loadMoviesFromAPI(APIString.getPopularUrl(), POPULAR_MOVIES);

        btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.tbCatalogus);

        lCatalogusCheckboxes = findViewById(R.id.lCatalogusCheckboxes);
        lCatalogusCheckboxes.setVisibility(View.GONE);

        // NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                // Heb geprobeerd met switch/case maar geeft errors
                if (id == R.id.menuHomeScreen) {
                    startActivity(new Intent(Catalogus.this, MainActivity.class));
                    Log.i(LOG_TAG, "HomeScreen button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
                     startActivity(new Intent(Catalogus.this, Catalogus.class));
                    Log.i(LOG_TAG, "catalogus button clicked");
                    return true;
                } else if (id == R.id.menuPersonal) {
//                     startActivity(new Intent(Catalogus.this, Persoonlijk.class));
                    Log.i(LOG_TAG, "Persoonlijk button clicked");
                    return true;
                } else {
                    return false;
                }
            }
        });


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

        int numCheckBox = 8;
        //Plaats numCheckBox CheckBoxen in de Containter
        for (int i = 1; i <= numCheckBox; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(getGenreName(i));
            checkBox.setChecked(true);
            lCatalogusCheckboxes.addView(checkBox);
            checkedBoxes.add(i);
            catalogusAdapter.notifyDataSetChanged();
        }


        bFilterMenuToggle = findViewById(R.id.bFilterMenuToggle);
        bFilterMenuToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lCatalogusCheckboxes.getVisibility() == View.VISIBLE) {
                    lCatalogusCheckboxes.setVisibility(View.GONE);
                    checkedBoxes.clear();
                    for (int i = 0; i <= lCatalogusCheckboxes.getChildCount(); i++) {
                        View view = lCatalogusCheckboxes.getChildAt(i);
                        if (view instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox) view;
                            if (checkBox.isChecked()) {
                                Log.d(LOG_TAG, "Checkbox " + (i + 1) + " is checked");
                                checkedBoxes.add(i+1);
                                catalogusAdapter.notifyDataSetChanged();
                            } else {
                                Log.d(LOG_TAG, "Checkbox " + (i + 1) + " is not checked");
                            }
                        }
                    }
                    catalogusAdapter.notifyDataSetChanged();
                } else {
                    lCatalogusCheckboxes.setVisibility(View.VISIBLE);
                }
            }
        });

        btmNavView.setSelectedItemId(R.id.tbCatalogus);
    }

    private void loadMoviesFromAPI(String APIUrl, int apiIdentifier) {
        MovieApiTask task = new MovieApiTask(this, apiIdentifier);
        task.execute(APIUrl);
    }

    private void performSearch(String query){
        rvCatalogusTop.setVisibility(View.VISIBLE);
        MovieApiTask task = new MovieApiTask(this, SEARCH_MOVIE);
        APIString str = new APIString();
        str.search(query);
        str.finish();
        task.execute(str.getApiString());
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
        switch (apiIdentifier) {
            case POPULAR_MOVIES:
                Log.d(LOG_TAG, "MovieAvailable with "+POPULAR_MOVIES);
                loadRecyclerView(movies);
                break;
            case SEARCH_MOVIE:
                Log.d(LOG_TAG, "MovieAvailable with "+SEARCH_MOVIE);
                loadSearchOnTopView(movies);
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
        movieListAdapter.notifyDataSetChanged();
        movieListAdapter.setMovieList(mMovieList);
        Log.i(LOG_TAG,  movies.size() + " added to MovieList");
    }

    public String getGenreName(int i) {

        String genre = "";

        switch (i) {
            case 1:
                genre = "Action";
                break;
            case 2:
                genre = "Comedy";
                break;
            case 3:
                genre = "Drama";
                break;
            case 4:
                genre = "Family";
                break;
            case 5:
                genre = "Fantasy";
                break;
            case 6:
                genre = "Horror";
                break;
            case 7:
                genre = "Romance";
                break;
            case 8:
                genre = "Science Fiction";
                break;
        }

        return genre;
    }
}