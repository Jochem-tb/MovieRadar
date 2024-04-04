package com.example.movieradar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class TicketDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = "TicketDetailActivity";
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvRowSeat;
    private TextView tvDateTime;
    private ImageView ivQR;
    private ImageView ivPrint;
    private Ticket mTicket;

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

        //Data uit intent halen
        Intent intent = getIntent();
        mTicket = (Ticket) intent.getSerializableExtra(Ticket.getShareKey());

        setupUI();

    }

    private void setupUI() {
        tvTitle.setText(mTicket.getTitleMovie());
        tvRowSeat.setText("Rij: "+mTicket.getRowNr()+"     StoelNr: "+mTicket.getChairNr());
        tvDateTime.setText("Datum: "+mTicket.getDatumMovie()+"     Tijd: "+mTicket.getTimeMovie());
        String text = mTicket.getTitleMovie()+mTicket.getTimeMovie()+mTicket.getDatumMovie()+mTicket.getChairNr()+mTicket.getRoomNr();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,800,800);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            ivQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
