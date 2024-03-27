package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity";
//    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMainMovieList = findViewById(R.id.rvMainMovieList);
        rvMainMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        Adapter = new MovieListAdapter(this, movieList);
//        rvMainMovieList.setAdapter(Adapter);

        BottomNavigationView btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.menuHomeScreen);

        //NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    //Heb geprobeerd met switch/case maar geeft errors
                    if (id == R.id.menuHomeScreen) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        Log.i(LOG_TAG, "HomeScreen button clicked");
                        return true;
                    } else if (id == R.id.menuFilmList) {
//                        startActivity(new Intent(MainActivity.this, CatalogusActivity.class));
                        Log.i(LOG_TAG, "Catalogus button clicked");
                        return true;
                    } else if (id == R.id.menuFilmList) {
//                        startActivity(new Intent(MainActivity.this, PersonActivity.class));
                        Log.i(LOG_TAG, "Person button clicked");
                        return true;
                    } else {
                        return false;
                }
            }
        });
    }
}
