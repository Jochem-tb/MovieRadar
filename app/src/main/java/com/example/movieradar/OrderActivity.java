package com.example.movieradar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private final String LOG_TAG = "OrderActivity";
    private String[] date;
    private String[] time;
    private final String[] kindOfTicket = {"Kind", "Volwassene"};

    TextView tvTitle;
    TextView tvCountChairs;
    TextView tvTotalPrice;
    Spinner spDropdownDate;
    Spinner spDropdownTime;
    Spinner spDropdownTicketKind;
    Button bCancel;
    Button bOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tvTitle = findViewById(R.id.tv_MovieTitle);
        tvCountChairs = findViewById(R.id.tv_countChairs);
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        spDropdownDate = findViewById(R.id.dateSpinner);
        spDropdownTime = findViewById(R.id.timeSpinner);
        spDropdownTicketKind = findViewById(R.id.kindOfTicketSpinner);
        bCancel = findViewById(R.id.cancel_button);
        bOrder = findViewById(R.id.order_button);

//        ArrayAdapter<String>adapter = new ArrayAdapter<String>(OrderActivity.this,
//                android.R.layout.simple_spinner_item,kindOfTicket);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spDropdownTicketKind.setAdapter(adapter);
//        spDropdownTicketKind.setOnItemSelectedListener(this);
//
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//
//        switch (position) {
//            case 0:
//                // Whatever you want to happen when the first item gets selected
//                break;
//            case 1:
//                // Whatever you want to happen when the second item gets selected
//                break;
//            case 2:
//                // Whatever you want to happen when the thrid item gets selected
//                break;
//
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // TODO Auto-generated method stub
    }
}
