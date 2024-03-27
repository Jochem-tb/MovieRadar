package com.example.movieradar;

import static com.example.movieradar.R.id.idBtnPickDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class Persoonlijk extends AppCompatActivity {
    private final String LOG_TAG = "Persoonlijk";
    BottomNavigationView btmNavView;

    private TextView selectedDateTV;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persoonlijk);


        // on below line we are initializing our variables.
        Button pickDateBtn = findViewById(idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);

        // on below line we are adding click listener for our pick date button
        pickDateBtn.setOnClickListener(v -> {
            // on below line we are getting
            // the instance of our calendar.
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    Persoonlijk.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our text view.
                        selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });

//        btmNavView = findViewById(R.id.btmNavViewMain);
//        btmNavView.setSelectedItemId(R.id.menuHomeScreen);

        //NavBar functionality
//        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                int id = menuItem.getItemId();
//                //Heb geprobeerd met switch/case maar geeft errors
//                if (id == R.id.menuHomeScreen) {
//                    Intent HomeScreen= new Intent(MainActivity.this, MainActivity.class);
//                    Log.i(LOG_TAG, "HomeScreen button clicked");
//                    return true;
//                } else if (id == R.id.menuFilmList) {
//                    Intent Catalogus = new Intent(MainActivity.this, Catalogus.class);
//                    Log.i(LOG_TAG, "catalogus button clicked");
//                    return true;
//                } else if (id == R.id.menuFilmList) {
//                    Intent Persoonlijk = new Intent(MainActivity.this, Persoonlijk.class);
//                    Log.i(LOG_TAG, "Persoonlijk button clicked");
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }
}