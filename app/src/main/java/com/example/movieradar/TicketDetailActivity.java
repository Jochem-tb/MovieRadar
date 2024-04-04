package com.example.movieradar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TicketDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = "TicketDetailActivity";
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvRowSeat;
    private TextView tvDateTime;
    private ImageView ivQR;
    private ImageView ivPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        Log.i(LOG_TAG, "onCreate");

        toolbar = findViewById(R.id.tb_detail_ticket);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.ticket_detail_title);

        // Terugknop inschakelen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setup views
        tvTitle = findViewById(R.id.tv_detail_ticket_title);
        tvRowSeat = findViewById(R.id.tv_detail_ticket_row_and_seat);
        tvDateTime = findViewById(R.id.tv_detail_ticket_time_and_date);

        ivQR = findViewById(R.id.iv_ticket_detail_QR);
        ivPrint = findViewById(R.id.iv_ticket_detail_print);
    }
}
