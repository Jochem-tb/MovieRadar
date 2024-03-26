package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity";
    BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.menuHomeScreen);

        //NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    int id = menuItem.getItemId();
                    //Heb geprobeerd met switch/case maar geeft errors
                    if (id == R.id.menuHomeScreen) {
                        Intent HomeScreen= new Intent(MainActivity.this, MainActivity.class);
                        Log.i(LOG_TAG, "HomeScreen button clicked");
                        return true;
                    } else if (id == R.id.menuFilmList) {
                        Intent Catalogus = new Intent(MainActivity.this, Catalogus.class);
                        Log.i(LOG_TAG, "catalogus button clicked");
                        return true;
                    } else if (id == R.id.menuFilmList) {
                        Intent Persoonlijk = new Intent(MainActivity.this, Persoonlijk.class);
                        Log.i(LOG_TAG, "Persoonlijk button clicked");
                        return true;
                    } else {
                        return false;
                }
            }
        });
    }
}
