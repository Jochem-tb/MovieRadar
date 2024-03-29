package com.example.movieradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersonActivity extends AppCompatActivity {
    private final String LOG_TAG = "PersonActivity";
    BottomNavigationView btmNavView;
    ImageView ivAccountImage;
    TextView tvNameAccount;
    Button bMore;




    private TextView BirthdateView;
    private ImageView BirthDateButton;
    private TextView EmailField;
    private TextView UserField;
    private TextView PassField;
    private Button InlogButton;
    private Button RegisterButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

//        btmNavView = findViewById(R.id.btmNavViewMain);
//        btmNavView.setSelectedItemId(R.id.menuHomeScreen);

//        ivAccountImage = findViewById(R.id.ivAccountImage);
//        tvNameAccount = findViewById(R.id.tvNamePerson);
//        bMore = findViewById(R.id.bMore);
//
//        //NavBar functionality
//        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                int id = menuItem.getItemId();
//                //Heb geprobeerd met switch/case maar geeft errors
//                if (id == R.id.menuHomeScreen) {
////                    Intent HomeScreen= new Intent(MainActivity.this, MainActivity.class);
//                    Log.i(LOG_TAG, "HomeScreen button clicked");
//                    return true;
//                } else if (id == R.id.menuFilmList) {
////                    Intent Catalogus = new Intent(MainActivity.this, Catalogus.class);
//                    Log.i(LOG_TAG, "catalogus button clicked");
//                    return true;
//                } else if (id == R.id.menuPersonal) {
////                    Intent Persoonlijk = new Intent(MainActivity.this, PersonActivity.class);
//                    Log.i(LOG_TAG, "Person button clicked");
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });

        BirthDateButton = findViewById(R.id.BirthdateButton);
        BirthdateView = findViewById(R.id.BirthdateView);

        // on below line we are adding click listener for our pick date button
        BirthDateButton.setOnClickListener(v -> {
            // on below line we are getting
            // the instance of our calendar.
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    PersonActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our text view.
                        BirthdateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });

        EmailField = findViewById(R.id.EmailField);
        UserField = findViewById(R.id.UserField);
        PassField = findViewById(R.id.PassField);
        InlogButton = findViewById(R.id.InlogButton);
        RegisterButton = findViewById(R.id.RegisterButton);

        InlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLoginAuthentication();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseCon.sendCommand(DatabaseCon.createConnection(),"INSERT INTO MR-Gebruiker(EmailAdres,Gebruikersnaam,Wachtwoord,Geboortedatum) VALUES (" +
                            EmailField.getText() + "," +
                            UserField.getText() + "," +
                            PassField.getText() + "," +
                            BirthdateView.getText() + ")");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}