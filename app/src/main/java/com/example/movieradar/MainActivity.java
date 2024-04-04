package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieradar.API.APIString;
import com.example.movieradar.API.Filters;
import com.example.movieradar.API.MovieApiTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieApiTask.OnNewMovieListener {

    private final String LOG_TAG = "MainActivity";
    private final int POPULAR_MOVIES = 1;
    private final int RANDOM_MOVIES = 3;

    private MovieListAdapter mAdapter;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private ArrayList<Movie> mPopulairMovies = new ArrayList<>();
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

        // NavBar functionality
        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    //Heb geprobeerd met switch/case maar geeft errors
                    if (id == R.id.menuHomeScreen) {
//                        startActivity(new Intent(MainActivity.this, MainActivity.class));
//                        Log.i(LOG_TAG, "HomeScreen button clicked");
                        return true;
                    } else if (id == R.id.menuFilmList) {
                        startActivity(new Intent(MainActivity.this, CatalogusActivity.class));
                        Log.i(LOG_TAG, "Catalogus button clicked");
                        return true;
                    } else if (id == R.id.menuPersonal) {
                        startActivity(new Intent(MainActivity.this, PersonActivity.class));
                        Log.i(LOG_TAG, "Personal button clicked");
                        return true;
                    } else {
                        return false;
                }
            }
        });

        // Koppeling ID --> xml objecten aan Java variables
        // ImageViews
        mImagePop1 = findViewById(R.id.ivMainPopularMovie1);
        mImagePop2 = findViewById(R.id.ivMainPopularMovie2);
        mImagePop3 = findViewById(R.id.ivMainPopularMovie3);
        mImagePop4 = findViewById(R.id.ivMainPopularMovie4);
        mImagePop5 = findViewById(R.id.ivMainPopularMovie5);
        mImagePop6 = findViewById(R.id.ivMainPopularMovie6);

        // Bijbehorende TextViews
        mTextViewPop1 = findViewById(R.id.tvMainPopularMovie1);
        mTextViewPop2 = findViewById(R.id.tvMainPopularMovie2);
        mTextViewPop3 = findViewById(R.id.tvMainPopularMovie3);
        mTextViewPop4 = findViewById(R.id.tvMainPopularMovie4);
        mTextViewPop5 = findViewById(R.id.tvMainPopularMovie5);
        mTextViewPop6 = findViewById(R.id.tvMainPopularMovie6);

        // Vraag API vor popular movies, handel deze af als POPULAR_MOVIES
        loadMoviesFromAPI(APIString.getPopularUrl("day"), POPULAR_MOVIES);
        loadMoviesFromAPI(APIString.getPopularUrl("week"), RANDOM_MOVIES);


        mImagePop1.setOnClickListener(goToPopDetailPage(mMovieList.size()-1));
        mImagePop2.setOnClickListener(goToPopDetailPage(mMovieList.size()-2));
        mImagePop3.setOnClickListener(goToPopDetailPage(mMovieList.size()-3));
        mImagePop4.setOnClickListener(goToPopDetailPage(mMovieList.size()-4));
        mImagePop5.setOnClickListener(goToPopDetailPage(mMovieList.size()-5));
        mImagePop6.setOnClickListener(goToPopDetailPage(mMovieList.size()-6));

    }

    //Methode to send the right movie to the new intent
    private View.OnClickListener goToPopDetailPage(int position){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Movie movie = mPopulairMovies.get(position);
                Intent detailMovie = new Intent(MainActivity.this, MovieDetailActivity.class);
                detailMovie.putExtra(Movie.getShareKey(),movie);
                startActivity(detailMovie);
                Log.d(LOG_TAG, "Popular image " + position + " pressed");
            }
        };
    }

    private void loadMoviesFromAPI(String APIUrl, int apiIdentifier) {
        MovieApiTask task = new MovieApiTask(this, apiIdentifier);
        task.execute(APIUrl);
    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
        switch (apiIdentifier) {
            case POPULAR_MOVIES:
                setPopularMoviesHome(movies);
                mPopulairMovies = movies;
                break;
            case RANDOM_MOVIES:
                loadRecyclerView(movies);
                break;
            default:
//                loadRecyclerView(mMovieList);
                break;
        }
    }

    public void setPopularMoviesHome(ArrayList<Movie> movies) {
        // Zet de Titel onder Poster
        if (movies != null && !movies.isEmpty()) {
            mTextViewPop1.setText(movies.get(movies.size()-1).getTitle());
            mTextViewPop2.setText(movies.get(movies.size()-2).getTitle());
            mTextViewPop3.setText(movies.get(movies.size()-3).getTitle());
            mTextViewPop4.setText(movies.get(movies.size()-4).getTitle());
            mTextViewPop5.setText(movies.get(movies.size()-5).getTitle());
            mTextViewPop6.setText(movies.get(movies.size()-6).getTitle());
            // Laad plaatjes in
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-1).getPoster_path())).into(mImagePop1);
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-2).getPoster_path())).into(mImagePop2);
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-3).getPoster_path())).into(mImagePop3);
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-4).getPoster_path())).into(mImagePop4);
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-5).getPoster_path())).into(mImagePop5);
            Picasso.get().load(APIString.getBackdropUrl(movies.get(movies.size()-6).getPoster_path())).into(mImagePop6);
        }
    }

    // Vul recyclerview in
    public void loadRecyclerView(ArrayList<Movie> movies) {
        mMovieList.clear();
        mMovieList.addAll(movies);
        mAdapter.setMovieList(mMovieList);
        mAdapter.notifyDataSetChanged();
        Log.i(LOG_TAG, movies.size()+" Movies added to recyclerview");
    }
}
