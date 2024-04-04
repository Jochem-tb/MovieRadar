package com.example.movieradar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.movieradar.API.APIString;
import com.example.movieradar.API.MovieApiTask;
import com.example.movieradar.database.FavoMovieDao;
import com.example.movieradar.database.FavoMovieDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieDetailActivity extends AppCompatActivity{
    private final String LOG_TAG = "MovieDetailActivity";
    private String YOUTUBE_VIDEO_ID;
    private Movie mMovie;
    private Executor executor = Executors.newSingleThreadExecutor();
    FavoMovieDao favoMovieDao;
    FavoMovieDatabase favoMovieDatabase;

    TextView mMovieGenre;
    TextView mMovieStatus;
    TextView mMovieTime;
    TextView mMovieRating;
    TextView mMovieDetails;
    TextView mMovieBudget;
    TextView mMovieRevenue;
    TextView mMovieTitle;
    ImageView mMovieBackground;

    CircleImageView mFavoriteButton;
    CircleImageView mTrailerButton;
    Button mBuyTicketButton;

    LinearLayout checkboxContainer;
    AlertDialog listDialog;
    Toolbar toolbar;

    TextView mStaticGenre;
    TextView mStaticTime;
    TextView mStaticRating;
    TextView mStaticStatus;
    TextView mStaticBudget;
    TextView mStaticRevenue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Log.i(LOG_TAG, "onCreate");

        toolbar = findViewById(R.id.tb_detail_movie);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.detail_title);

        // Terugknop inschakelen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMovieGenre = findViewById(R.id.tv_detail_var_genre);
        mMovieStatus = findViewById(R.id.tv_detail_var_status);
        mMovieTime = findViewById(R.id.tv_detail_var_time);
        mMovieRating = findViewById(R.id.tv_detail_var_rating);
        mMovieDetails = findViewById(R.id.tv_var_moviedetails);
        mMovieRevenue = findViewById(R.id.tv_detail_var_revenue);
        mMovieBudget = findViewById(R.id.tv_detail_var_budget);
        mMovieTitle = findViewById(R.id.tv_detail_ticket_title);

        mMovieBackground = findViewById(R.id.iv_ticket_detail_QR);

        mFavoriteButton = findViewById(R.id.bt_detail_favorite);
        mTrailerButton = findViewById(R.id.bt_detail_trailer);
        mBuyTicketButton = findViewById(R.id.bt_detail_buy);

        mStaticGenre = findViewById(R.id.tv_detail_stat_genre);
        mStaticStatus = findViewById(R.id.tv_detail_stat_status);
        mStaticTime = findViewById(R.id.tv_detail_stat_time);
        mStaticRating = findViewById(R.id.tv_detail_stat_rating);
        mStaticRevenue = findViewById(R.id.tv_detail_stat_revenue);
        mStaticBudget = findViewById(R.id.tv_detail_stat_budget);

        favoMovieDatabase = FavoMovieDatabase.getDatabase(this);
        favoMovieDao = favoMovieDatabase.favoMovieDao();



        BottomNavigationView btmNavView = findViewById(R.id.btmNavViewMain);

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                //Heb geprobeerd met switch/case maar geeft errors
                if (id == R.id.menuHomeScreen) {
                    startActivity(new Intent(MovieDetailActivity.this, MainActivity.class));
                    Log.i(LOG_TAG, "HomeScreen button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
                    startActivity(new Intent(MovieDetailActivity.this, CatalogusActivity.class));
                    Log.i(LOG_TAG, "Catalogus button clicked");
                    return true;
                } else if (id == R.id.menuPersonal) {
                        startActivity(new Intent(MovieDetailActivity.this, PersonActivity.class));
                    Log.i(LOG_TAG, "Personal button clicked");
                    return true;
                } else {
                    return false;
                }
            }
        });

        //Data uit intent halen
        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Movie.getShareKey());

        loadMovieDetails(mMovie.getId());
        loadMovieDataOnGui();



        listDialog = setupListDialogView();
        setupLongButtonListener(listDialog);
        setupNormalButtonListerners();

        Log.d(LOG_TAG, mMovie.toString());

        loadMovieTrailer(mMovie.getId());
    }



    private void setupLongButtonListener(AlertDialog listDialog) {
        Log.d(LOG_TAG, "setupLongButtonListener");
        mFavoriteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listDialog.show();
                return false;
            }
        });
    }

    private AlertDialog setupListDialogView() {
        Log.d(LOG_TAG, "setupListDialogView");
        //Infalting de layout.xml
        View listDialog = LayoutInflater.from(this).inflate(R.layout.list_adding_dialog_layout, null);

        //checkboxContainter koppelen aan id
        checkboxContainer = listDialog.findViewById(R.id.checkbox_container);

        //Ophalen van hoeveelheid lijsten, waar gebruiker toegang tot heeft.
//        getNumLists();
        int numCheckBox = 10;

        //Plaats numCheckBox CheckBoxen in de Containter
        for (int i = 0; i < numCheckBox; i++) {
            CheckBox checkBox = new CheckBox(this);
            //TODO Methode voor ophalen lijst namen
            checkBox.setText("LIJST NAAM " + (i + 1));
            checkboxContainer.addView(checkBox);
        }

        //Aanmaken & tonen va Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(listDialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Op moment logging voor Checked of not checked
                        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                            View view = checkboxContainer.getChildAt(i);
                            if (view instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox) view;
                                if (checkBox.isChecked()) {
                                    Log.d(LOG_TAG, "Checkbox " + (i + 1) + " is checked");
                                    // TODO: Apply logic for checked checkboxes
                                } else {
                                    Log.d(LOG_TAG, "Checkbox " + (i + 1) + " is not checked");
                                    // TODO: Apply logic for unchecked checkboxes
                                }
                            }
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        //Retourneer opgebouwde Dialog
        AlertDialog l = builder.create();
        return l;
    }



    private void setupNormalButtonListerners() {
        Log.d(LOG_TAG, "setupNormalButtonListerners");
        //Button listerners
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });
        mTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.d(LOG_TAG, "Trailer Key: " + YOUTUBE_VIDEO_ID);
              playTrailer(YOUTUBE_VIDEO_ID);
        }
    });

        mBuyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovieDataOnGui();
                Intent orderActivity = new Intent(MovieDetailActivity.this, OrderActivity.class);
                orderActivity.putExtra(Movie.getShareKey(),mMovie);
                startActivity(orderActivity);
                Log.d(LOG_TAG, "buyTicket button clicked");
            }
        });
    }

    private void playTrailer(String trailerId) {
        Log.i(LOG_TAG, "playTrailer");
        String videoUrl = "https://www.youtube.com/watch?v=" + trailerId;

        // Open de YouTube app of browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);
    }

    private void loadMovieDetails(int movieId) {
        Log.i(LOG_TAG, "loadMovieDetails");
        String apiUrl = APIString.generateMovieUrl(movieId);

        // Execute AsyncTask to make the API call
        new MovieApiTask(new MovieApiTask.OnNewMovieListener() {
            @Override
            public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
                // Check if the API call returns valid movie details
                if (movies != null && !movies.isEmpty()) {
                    // Update YOUTUBE_VIDEO_ID with the fetched details

                    mMovie = movies.get(0);
                    Log.d(LOG_TAG, "movie fetch details: "+movies.get(0).toString());
                    loadMovieDataOnGui();
                    // Check if videos are available for the movie
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
                }
            }
        }, 2).execute(apiUrl);
    }

    private void loadMovieTrailer(int movieId){
        Log.i(LOG_TAG, "loadMovieTrailer");
        String apiUrl = APIString.generateMovieUrl(movieId);
        new MovieApiTask(new MovieApiTask.OnNewMovieListener() {
            @Override
            public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {
                // Check if the API call returns valid movie details
                if (movies != null && !movies.isEmpty()) {
                    // Update YOUTUBE_VIDEO_ID with the fetched details
                    YOUTUBE_VIDEO_ID = movies.get(0).getKey();
                    Log.d(LOG_TAG, "YOUTUBE_VIDEO_ID changed by API, to: "+YOUTUBE_VIDEO_ID);
                    // Check if videos are available for the movie
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Failed to fetch trailer details", Toast.LENGTH_SHORT).show();
                }
            }
        }, 52).execute(apiUrl);
    }

    // Methode voor toolbar-itemklikken
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toolbar-itemklikken afhandelen
        if (item.getItemId() == android.R.id.home) {
            // Geef backbutton functionaliteit
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovieDataOnGui() {
        Log.d(LOG_TAG, "LOADMOVIEGUI with object: "+mMovie.toString());
        //Movie data invullen
        if (mMovie != null) {
            Log.d(LOG_TAG, "Load data into TV");
            mMovieTitle.setText(mMovie.getTitle());

            // Displaying genres
            if (mMovie.getGenres() != null && !mMovie.getGenres().isEmpty()) {
                mStaticGenre.setVisibility(View.VISIBLE);
                mMovieGenre.setVisibility(View.VISIBLE);
                StringBuilder genresStringBuilder = new StringBuilder();
                for (Genre genre : mMovie.getGenres()) {
                    genresStringBuilder.append(genre.getName()).append(", ");
                }
                // Remove the trailing comma and space
                String genresString = genresStringBuilder.substring(0, genresStringBuilder.length() - 2);
                mMovieGenre.setText(genresString);
            } else {
                mStaticGenre.setVisibility(View.GONE);
                mMovieGenre.setVisibility(View.GONE); // Hide the genre TextView if no genres are available
            }

            // Check and set movie runtime
//            Log.d(LOG_TAG, mMovie.getRuntime().toString());
            if (mMovie.getRuntime() != null && mMovie.getRuntime() != 0) {
                mStaticTime.setVisibility(View.VISIBLE);
                mMovieTime.setVisibility(View.VISIBLE);
                mMovieTime.setText(String.valueOf(mMovie.getRuntime()));
            } else {
                mStaticTime.setVisibility(View.GONE);
                mMovieTime.setVisibility(View.GONE); // Hide the runtime TextView if runtime is not available
            }

            //Check and set movie Status
            if(mMovie.getStatus() != null){
                mMovieStatus.setVisibility(View.VISIBLE);
                mStaticStatus.setVisibility(View.VISIBLE);
                mMovieStatus.setText(mMovie.getStatus());
            } else {
                mMovieStatus.setVisibility(View.GONE);
                mStaticStatus.setVisibility(View.GONE);
            }

            // Check and set movie overview
//            Log.d(LOG_TAG, mMovie.getOverview());
            if (mMovie.getOverview() != null) {
                mMovieDetails.setVisibility(View.VISIBLE);
                mMovieDetails.setText(mMovie.getOverview());
            } else {
                mMovieDetails.setVisibility(View.GONE); // Hide the overview TextView if overview is not available
            }

            // Check and load movie backdrop image
            if (mMovie.getBackdrop_path() != null) {
                mMovieBackground.setVisibility(View.VISIBLE);
                Picasso.get().load(APIString.getBackdropUrl(mMovie.getBackdrop_path())).into(mMovieBackground);
            } else {
                mMovieBackground.setVisibility(View.GONE); // Hide the backdrop ImageView if backdrop path is not available
            }

            // Check and set movie revenue
//            Log.d(LOG_TAG, mMovie.getRevenue().toString());
            if (mMovie.getRevenue() != null && mMovie.getRevenue() != 0) {
                mStaticRevenue.setVisibility(View.VISIBLE);
                mMovieRevenue.setVisibility(View.VISIBLE);
                mMovieRevenue.setText(String.valueOf(mMovie.getRevenue()));
            } else {
                mStaticRevenue.setVisibility(View.GONE);
                mMovieRevenue.setVisibility(View.GONE); // Hide the revenue TextView if revenue is not available
            }

            // Check and set movie budget
            if (mMovie.getBudget() != null && mMovie.getBudget() != 0) {
                mStaticBudget.setVisibility(View.VISIBLE);
                mMovieBudget.setVisibility(View.VISIBLE);
                mMovieBudget.setText(String.valueOf(mMovie.getBudget()));
            } else {
                mStaticBudget.setVisibility(View.GONE);
                mMovieBudget.setVisibility(View.GONE); // Hide the budget TextView if budget is not available
            }

            //Check and set movie Rating
            if(mMovie.getVote_average() != null && mMovie.getVote_average() !=0){
                mStaticRating.setVisibility(View.VISIBLE);
                mMovieRating.setVisibility(View.VISIBLE);
                mMovieRating.setText(String.valueOf(mMovie.getVote_average()));
            }else {
                mStaticRating.setVisibility(View.GONE);
                mMovieRating.setVisibility(View.GONE); // Hide the rating TextView if rating is not available
            }

        }
    }

    private void addToFavorites() {
        // Cocktails toevoegen of verwijderen van favorieten
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Chech of Movie al in favorieten staat.
                Movie existingCocktail = favoMovieDao.getMovieById(mMovie.getTitle());
                if (existingCocktail != null) {
                    // Als Movie al bestaat in DB, verwijder uit DB
                    favoMovieDao.delete(mMovie);
                    // Update de UI in mainThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Movie bestaan nog niet in DB
                    favoMovieDao.insert(mMovie);
                    // Update de UI in mainThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
