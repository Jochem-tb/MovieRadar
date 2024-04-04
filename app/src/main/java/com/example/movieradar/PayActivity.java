package com.example.movieradar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.WifiAvailableChannel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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




    TextView tvTotalPrice;
    TextView tvMovieTitle;
    TextView tvKindOfTicket;

//      activity_payticket attributen
    Toolbar toolbar;
    Button Paypal;
    Button ApplePay;
    Button Creditkaart;
    Button Ideal;
    Button betalingcancel;
    Button betalingcomplete;

//      PayPal Dialog attributen
    TextView emailadres;
    TextView wachtwoord;

//      Applepay Dialog attributen

    TextView username;
    TextView wachtwoordApple;

//      Ideal Dialog attributen
    Spinner DropdownIdeal;

//      Creditkaart Dialog attributen
    TextView creditkaartNum;
    TextView creditkaartHolder;
    TextView creditkaartSecurity;
    private TextView ExpirydateView;
    private ImageView ExpiryDateButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "PayActivity geopend ");
        setContentView(R.layout.activity_payticket);

//        Initialiseer de toolbar

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

    //  Button per betaaloptie
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

    //    Dialog per betaaloptie
    private void showDialogPaypal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_paypal);
        dialog.show();
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienP);
        betalingcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailadres = dialog.findViewById(R.id.EmailPaypal);
                wachtwoord = dialog.findViewById(R.id.PasswordPaypal);
                if(!emailadres.getText().toString().isEmpty() && !wachtwoord.getText().toString().isEmpty()){
                    Intent intent = getIntent();
                    tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
                    Intent payActivity = new Intent(PayActivity.this, PersonActivity.class);
                    payActivity.putExtra(Ticket.getShareKey(),tickets);
                    startActivity(payActivity);
                } else {
                    Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }
    private void showDialogApplePay() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_applepay);
        dialog.show();

        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienA);

        betalingcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username = dialog.findViewById(R.id.GebruikerApple);
            wachtwoordApple = dialog.findViewById(R.id.WachtwoordApple);

            if (!username.getText().toString().isEmpty() && !wachtwoordApple.getText().toString().isEmpty()){
                Intent intent = getIntent();
                tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
                Intent payActivity = new Intent(PayActivity.this, PersonActivity.class);
                payActivity.putExtra(Ticket.getShareKey(),tickets);
                startActivity(payActivity);
            }else {
                Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
            }
            }
        });

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
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienI);
        betalingcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DropdownIdeal = dialog.findViewById(R.id.DropdownIdeal);
            if (!DropdownIdeal.getSelectedItem().toString().isEmpty()){
                Intent intent = getIntent();
                tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
                Intent payActivity = new Intent(PayActivity.this, PersonActivity.class);
                payActivity.putExtra(Ticket.getShareKey(),tickets);
                startActivity(payActivity);
            }else{
                Toast.makeText(PayActivity.this, "Selecteer een bank om door te gaan!", Toast.LENGTH_SHORT).show();
            }
            }
        });

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
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienC);
        betalingcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditkaartNum = dialog.findViewById(R.id.CreditcardNum);
                creditkaartHolder = dialog.findViewById(R.id.CreditCardholder);
                creditkaartSecurity = dialog.findViewById(R.id.CreditcardSecurity);
                ExpirydateView = dialog.findViewById(R.id.CreditcardExpire);

                String ckn = creditkaartNum.getText().toString();
                int checkchars = ckn.length();
                String cks = creditkaartSecurity.getText().toString();
                int checkcharsS = cks.length();
                if (checkchars != 16) {
                    Toast.makeText(PayActivity.this, "Creditkaartnummer moet uit 16 karakters bestaan!", Toast.LENGTH_SHORT).show();
                }
                if(!(checkcharsS >= 3)){
                    Toast.makeText(PayActivity.this, "Veiligheidscode moet uit 3 tot 4 karakters bestaan!", Toast.LENGTH_SHORT).show();
                }
                if(checkchars == 16 && checkcharsS >= 3 && !ExpirydateView.getText().toString().isEmpty() && !creditkaartHolder.getText().toString().isEmpty()){
                    Intent intent = getIntent();
                    tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
                    Intent payActivity = new Intent(PayActivity.this, PersonActivity.class);
                    payActivity.putExtra(Ticket.getShareKey(),tickets);
                    startActivity(payActivity);
                }
                else {
                    Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

//    Toolbar backbutton functionaliteit
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





}
