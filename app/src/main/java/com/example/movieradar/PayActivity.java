package com.example.movieradar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class PayActivity extends AppCompatActivity {

    private static final String TAG = "";
    private final String LOG_TAG = "PayActivity";
    private ArrayList<Ticket> tickets;
    private String movieTitle;
    private int totalPrice;
    private int countSeats;
    private String kindOfTicket;
    private boolean isAdult;
    Button Paypal;
    Button ApplePay;
    Button Creditkaart;
    Button Ideal;
    Toolbar toolbar;
    TextView tvTotalPrice;
    TextView tvMovieTitle;
    TextView tvKindOfTicket;

    //PayPal Dialog attributen

    Button betalingcancel;
    Button betalingcomplete;
    TextView emailadres;
    TextView wachtwoord;
    private TextView ExpirydateView;
    private ImageView ExpiryDateButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "PayActivity geopend ");
        setContentView(R.layout.activity_payticket);

        toolbar = findViewById(R.id.Tb_Ticketkopen);
        toolbar.setTitle(R.string.payment_screen);
        setSupportActionBar(toolbar);

        // Terugknop inschakelen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Getting tickets from OrderActivity
        countSeats = 0; // resets count
        //Getting information from OrderActivity
        Intent intent = getIntent();
        totalPrice = getIntent().getIntExtra("totalPrice", 0);
        isAdult = getIntent().getBooleanExtra("isAdult", true);
        tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                movieTitle = ticket.getTitleMovie();
                countSeats++;
                Log.d("TicketInfo: ","Movie title: " + ticket.getTitleMovie() + ", Room: " + ticket.getRoomNr() + ", Chair: " + ticket.getChairNr() + ", Row: " + ticket.getRowNr() + ", Time: " + ticket.getTimeMovie() + ", Datum: " + ticket.getDatumMovie());
            }
        } else {
            Log.e("TicketInfo", "Tickets ArrayList is null");
        }

        if (isAdult){
            kindOfTicket = "volwassen";
        } else {
            kindOfTicket = "kinderen";
        }

        //Setting attributes to layout
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        tvMovieTitle = findViewById(R.id.tv_MovieTitle);
        tvKindOfTicket = findViewById(R.id.tv_KindOfTicket);
        Paypal = findViewById(R.id.Paypal);
        ApplePay = findViewById(R.id.ApplePay);
        Creditkaart = findViewById(R.id.CreditCard);
        Ideal = findViewById(R.id.Ideal);

        //Setting text
        tvTotalPrice.setText("€" + totalPrice + ",00");
        tvMovieTitle.setText(movieTitle);
        tvKindOfTicket.setText(countSeats + "x " + kindOfTicket);
        Paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPaypal();
            }
        });
        ApplePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogApplePay();
            }
        });
        Ideal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogIdeal();
            }
        });
        Creditkaart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreditkaart();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button (in this case, finish the activity)
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showDialogPaypal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_paypal);
        dialog.show();
        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }
    private void showDialogIdeal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ideal);
        dialog.show();
        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }
    private void showDialogCreditkaart() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_creditkaart);
        dialog.show();
        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        // on below line we are initializing our variables.
        ExpiryDateButton = dialog.findViewById(R.id.Expirypicker);
        ExpirydateView = dialog.findViewById(R.id.CreditcardExpire);

        // on below line we are adding click listener for our pick date button
        ExpiryDateButton.setOnClickListener(v -> {
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
                    PayActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our text view.
                        ExpirydateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });
    }
    private void showDialogApplePay() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_applepay);
        dialog.show();

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }

}
