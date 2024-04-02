package com.example.movieradar;

import static com.example.movieradar.R.id.BirthdateButton;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieradar.R.id;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends AppCompatActivity {

    Connection connect;
    String connectionResult = "";

    private TextView EmailUserField;
    private TextView PasswordField;
    private TextView BirthdateView;
    private ImageView BirthDateButton;
    private TextView EmailField;
    private TextView UserField;
    private TextView PassField;
    private Button InlogButton;
    private Button RegisterButton;

    public ArrayList crud = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        // on below line we are initializing our variables.
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
                    Login.this,
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

        EmailField = findViewById(id.EmailField);
        UserField = findViewById(id.UserField);
        PassField = findViewById(id.PassField);
        InlogButton = findViewById(id.InlogButton);
        RegisterButton = findViewById(id.RegisterButton);
        EmailUserField = findViewById(id.EmailUserField);
        PasswordField = findViewById(id.PasswordField);
        InlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseTask task = new DatabaseTask("Login", crud);
                task.execute();
            }
        });

        crud.add("INSERT INTO MR-Gebruiker(EmailAdres,Gebruikersnaam,Wachtwoord,Geboortedatum) VALUES (" +
                EmailField.getText() + "," +
                UserField.getText() + "," +
                PassField.getText() + "," +
                BirthdateView.getText() + ")");

        crud.add(EmailUserField);
        crud.add(PasswordField);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseTask conhelp = new DatabaseTask();
                connect = conhelp.Connectionclass();
//                DatabaseTask task = new DatabaseTask("Insert", crud);
//                task.execute();

            }
        });

    }
    public void GetTextFromSQL(){

        DatabaseTask conhelp = new DatabaseTask();
        connect = conhelp.Connectionclass();

    }
}

