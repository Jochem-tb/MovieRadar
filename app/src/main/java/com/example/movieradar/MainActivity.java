package com.example.movieradar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ev1 = findViewById(R.id.id1);
        EditText ev2 = findViewById(R.id.id2);
        EditText ev3 = findViewById(R.id.id3);

        APIString str1 = new APIString();
        APIString str2 = new APIString();
        APIString str3 = new APIString();
        str1.filter(Filters.with_genres,"Action");
        str2.filter(Filters.year,"2000");
        str3.isAdult(true);
        ev1.setText(str1.finish());
        ev2.setText(str2.finish());
        ev3.setText(str3.finish());
    }
}