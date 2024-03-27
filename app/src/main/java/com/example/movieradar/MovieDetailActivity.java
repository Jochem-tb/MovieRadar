package com.example.movieradar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieradar.API.APIString;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = "MovieDetailActivity";
    private Movie mMovie;

    TextView mMovieGenre;
    TextView mMoviePegi;
    TextView mMovieTime;
    TextView mMovieRating;
    TextView mMovieDetails;
    ImageView mMovieBackground;

    Button mFavoriteButton;
    Button mTrailerButton;
    Button mBuyTicketButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Log.i(LOG_TAG, "onCreate");

        Toolbar toolbar = findViewById(R.id.tb_detail_movie);
        setSupportActionBar(toolbar);

        // Terugknop inschakelen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMovieGenre = findViewById(R.id.tv_detail_var_genre);
        mMoviePegi = findViewById(R.id.tv_detail_var_pegi);
        mMovieTime = findViewById(R.id.tv_detail_var_time);
        mMovieRating = findViewById(R.id.tv_detail_var_rating);
        mMovieDetails = findViewById(R.id.tv_var_moviedetails);

        mMovieBackground = findViewById(R.id.iv_detail_poster);

        //Data uit intent halen
        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Movie.getKey());

        //Movie data invullen
        if(mMovie != null){
            toolbar.setTitle(mMovie.getTitle());
            mMovieTime.setText(String.valueOf(mMovie.getRuntime()));
            mMovieRating.setText(String.valueOf(mMovie.getVote_average()));
            mMovieDetails.setText(mMovie.getOverview());
            Picasso.get().load(APIString.getBackdropUrl(mMovie.getBackdrop_path())).into(mMovieBackground);
        }

        //Button listerners
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Favorite Button Kort", Toast.LENGTH_SHORT).show();
            }
        });
        mFavoriteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),"Favorite Button Lang", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Trailer Button Kort", Toast.LENGTH_SHORT).show();
            }
        });
        mBuyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Koop Ticket Button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Methode voor toolbar-itemklikken
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toolbar-itemklikken afhandelen
        if (item.getItemId() == android.R.id.home) {
            // Geef baclbutton functionaliteit
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
