package com.example.movieradar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;

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
    private LinearLayout lCatalogusCheckboxes;
    private Button bFilterMenuToggle;
    ArrayList<Integer> checkedBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogus);
        toolbar = findViewById(R.id.tbCatalogus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvCatalogusVertical = findViewById(R.id.rvCatalogus);
        rvCatalogusVertical.setLayoutManager(new LinearLayoutManager(this));
        catalogusAdapter = new CatalogusAdapter(this, mMovieList, checkedBoxes);
        rvCatalogusVertical.setAdapter(catalogusAdapter);

        btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.tbCatalogus);

        lCatalogusCheckboxes = findViewById(R.id.lCatalogusCheckboxes);
        lCatalogusCheckboxes.setVisibility(View.GONE);

        int numCheckBox = 8;
        //Plaats numCheckBox CheckBoxen in de Containter
        for (int i = 0; i < numCheckBox; i++) {
            CheckBox checkBox = new CheckBox(this);
            //TODO Methode voor ophalen genre naam
            checkBox.setText("GENRE NAAM " + (i + 1));
            lCatalogusCheckboxes.addView(checkBox);
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
                                checkedBoxes.add(i);
                            } else {
                                Log.d(LOG_TAG, "Checkbox " + (i + 1) + " is not checked");
                            }
                        }
                    }
                } else {
                    lCatalogusCheckboxes.setVisibility(View.VISIBLE);
                }
            }
        });

        loadMoviesFromAPI(APIString.getPopularUrl(), RANDOM_MOVIES);

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