package com.example.movieradar;

import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieradar.API.APIString;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = "MovieDetailActivity";
    private Movie mMovie;

    TextView mMovieGenre;
    TextView mMoviePegi;
    TextView mMovieTime;
    TextView mMovieRating;
    TextView mMovieDetails;
    ImageView mMovieBackground;

    CircleImageView mFavoriteButton;
    CircleImageView mTrailerButton;
    Button mBuyTicketButton;

    LinearLayout checkboxContainer;
    AlertDialog listDialog;

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

        mFavoriteButton = findViewById(R.id.bt_detail_favorite);
        mTrailerButton = findViewById(R.id.bt_detail_trailer);
        mBuyTicketButton = findViewById(R.id.bt_detail_buy);

        //Data uit intent halen
        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Movie.getKey());

        //Movie data invullen
        if (mMovie != null) {
            toolbar.setTitle(mMovie.getTitle());
            mMovieTime.setText(String.valueOf(mMovie.getRuntime()));
            mMovieRating.setText(String.valueOf(mMovie.getVote_average()));
            mMovieDetails.setText(mMovie.getOverview());
            Picasso.get().load(APIString.getBackdropUrl(mMovie.getBackdrop_path())).into(mMovieBackground);
        }


        listDialog = setupListDialogView();
        setupLongButtonListener(listDialog);
        setupNormalButtonListerners();


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
                        //TODO apply changes op lijsten & database
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
                Toast.makeText(v.getContext(),"Favorite Button Kort", Toast.LENGTH_SHORT).show();
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
            // Geef backbutton functionaliteit
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
