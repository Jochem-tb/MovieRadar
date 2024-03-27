package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.movieradar.API.APIString;
import com.example.movieradar.API.MovieApiTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieApiTask.OnNewMovieListener {

    private final String LOG_TAG = "MainActivity";
    public static final String MOVIE = "MainActivity";
    private final int POPULAR_MOVIES = 1;
    private final int RANDOM_MOVIES = 2;

    private MovieListAdapter mAdapter;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    RecyclerView rvMainMovieList;
    ImageView mImagePop1;
    ImageView mImagePop2;
    ImageView mImagePop3;
    ImageView mImagePop4;
    ImageView mImagePop5;
    ImageView mImagePop6;
    TextView mTextViewPop1;
    TextView mTextViewPop2;
    TextView mTextViewPop3;
    TextView mTextViewPop4;
    TextView mTextViewPop5;
    TextView mTextViewPop6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMainMovieList = findViewById(R.id.rvMainMovieList);
        rvMainMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new MovieListAdapter(this, mMovieList);
        rvMainMovieList.setAdapter(mAdapter);

        BottomNavigationView btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.menuHomeScreen);

        //NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                // Heb geprobeerd met switch/case maar geeft errors
                if (id == R.id.menuHomeScreen) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    Log.i(LOG_TAG, "HomeScreen button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
//                    startActivity(new Intent(MainActivity.this, Catalogus.class));
                    Log.i(LOG_TAG, "catalogus button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
//                    startActivity(new Intent(MainActivity.this, Persoonlijk.class));
                    Log.i(LOG_TAG, "Persoonlijk button clicked");
                    return true;
                } else {
                    return false;
                }
            }
        });

        //Koppeling ID --> xml objecten aan Java variables
        //ImageViews
        mImagePop1 = findViewById(R.id.ivMainPopularMovie1);
        mImagePop2 = findViewById(R.id.ivMainPopularMovie2);
        mImagePop3 = findViewById(R.id.ivMainPopularMovie3);
        mImagePop4 = findViewById(R.id.ivMainPopularMovie4);
        mImagePop5 = findViewById(R.id.ivMainPopularMovie5);
        mImagePop6 = findViewById(R.id.ivMainPopularMovie6);

        //Bijbehorende TextViews
        mTextViewPop1 = findViewById(R.id.tvMainPopularMovie1);
        mTextViewPop2 = findViewById(R.id.tvMainPopularMovie2);
        mTextViewPop3 = findViewById(R.id.tvMainPopularMovie3);
        mTextViewPop4 = findViewById(R.id.tvMainPopularMovie4);
        mTextViewPop5 = findViewById(R.id.tvMainPopularMovie5);
        mTextViewPop6 = findViewById(R.id.tvMainPopularMovie6);

        loadMoviesFromAPI(APIString.getPopularUrl(), POPULAR_MOVIES);
        loadMoviesFromAPI(APIString.getPopularUrl(), RANDOM_MOVIES);
    }

    private void loadMoviesFromAPI(String APIUrl, int apiIdentifier) {
        MovieApiTask task = new MovieApiTask(this, apiIdentifier);
        task.execute(APIUrl);
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
        switch (apiIdentifier){
            case POPULAR_MOVIES:
                setPopularMoviesHome(movies);
                break;
            case RANDOM_MOVIES:
                loadRecyclerView(movies);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnNewMovieListener(ArrayList<Movie> movies){

    }

    public void setPopularMoviesHome(ArrayList<Movie> movies){
        //Zet de Titel onder Poster
        mTextViewPop1.setText(movies.get(0).getTitle());
        mTextViewPop2.setText(movies.get(1).getTitle());
        mTextViewPop3.setText(movies.get(2).getTitle());
        mTextViewPop4.setText(movies.get(3).getTitle());
        mTextViewPop5.setText(movies.get(4).getTitle());
        mTextViewPop6.setText(movies.get(5).getTitle());
        //Laad plaatjes in
    }

    public void loadRecyclerView(ArrayList<Movie> movies) {
        mMovieList.clear();
        mMovieList.addAll(movies);
        mAdapter.setMovieList(mMovieList);
        mAdapter.notifyDataSetChanged();
        Log.i(LOG_TAG, "Movies added to recyclerview");
    }
}
