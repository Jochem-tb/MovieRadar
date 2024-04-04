package com.example.movieradar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.movieradar.database.TicketDao;
import com.example.movieradar.database.TicketDao_Impl;
import com.example.movieradar.database.TicketDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TicketDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = "TicketDetailActivity";
    private Executor executor = Executors.newSingleThreadExecutor();
    private View rootView;
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
        ivPrint = findViewById(R.id.iv_ticket_detail_delete);
        ivPrint.setOnClickListener(view -> deleteTicket());

        rootView = findViewById(android.R.id.content);



        //Data uit intent halen
        Intent intent = getIntent();
        mTicket = (Ticket) intent.getSerializableExtra(Ticket.getShareKey());

        setupUI();

    }
    private void deleteTicket(){
            // Cocktails toevoegen of verwijderen van favorieten
            executor.execute(new Runnable() {
                TicketDatabase db = TicketDatabase.getDatabase(TicketDetailActivity.this);
                TicketDao t = db.ticketDao();
                @Override
                public void run() {
                    if (mTicket != null) {
                        t.delete(mTicket);
                        startActivity(new Intent(TicketDetailActivity.this, PersonActivity.class));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Removed from Tickets", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
     }


    private void setupUI() {
        tvTitle.setText(mTicket.getTitleMovie());
        tvRowSeat.setText("Rij: "+mTicket.getRowNr()+"     StoelNr: "+mTicket.getChairNr());
        tvDateTime.setText("Datum: "+mTicket.getDatumMovie()+"     Tijd: "+mTicket.getTimeMovie());
        String text = mTicket.getTitleMovie()+mTicket.getTimeMovie()+mTicket.getDatumMovie()+mTicket.getChairNr()+mTicket.getRoomNr();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,900,900);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            ivQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private void captureScreenAndExport() {
        File screenshotFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png");

        // Create a Bitmap with the same dimensions as the rootView
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a Canvas using the bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the rootView onto the Canvas
        rootView.draw(canvas);

        try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            Toast.makeText(this, "Screenshot saved to " + screenshotFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

            // Open the saved screenshot using an Intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri screenshotUri = FileProvider.getUriForFile(this, "com.example.movieradar.fileprovider", screenshotFile);
            intent.setDataAndType(screenshotUri, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (IOException e) {
            Log.e("CaptureScreen", "Error saving screenshot", e);
            Toast.makeText(this, "Error saving screenshot", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when the home button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

