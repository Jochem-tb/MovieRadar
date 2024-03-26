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

        APIString str = new APIString();
        str.search("spiderman");
        str.isAdult(true);
        str.setPage(1);
        str.sort(SortType.POPULARITY_DESC);
        str.finish();
        ev1.setText(str.getApiString());
    }
}