package com.example.movieradar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.movieradar.database.TicketDao;
import com.example.movieradar.database.TicketDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PayActivity extends AppCompatActivity {

    private final String LOG_TAG = "PayActivity";
    private String movieTitle;

    TicketDao ticketDao;
    TicketDatabase ticketDatabase;


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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Getting tickets from OrderActivity
        int countSeats = 0; // resets count
        //Getting information from OrderActivity
        Intent intent = getIntent();
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        boolean isAdult = getIntent().getBooleanExtra("isAdult", true);
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) intent.getSerializableExtra(Ticket.getShareKey());
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                movieTitle = ticket.getTitleMovie();
                countSeats++;
                Log.d("TicketInfo: ","Movie title: " + ticket.getTitleMovie() + ", Room: " + ticket.getRoomNr() + ", Chair: " + ticket.getChairNr() + ", Row: " + ticket.getRowNr() + ", Time: " + ticket.getTimeMovie() + ", Datum: " + ticket.getDatumMovie());
            }
        } else {
            Log.e("TicketInfo", "Tickets ArrayList is null");
        }

        String kindOfTicket;
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
        Paypal.setOnClickListener(v -> showDialogPaypal());

        ApplePay.setOnClickListener(v -> showDialogApplePay());

        Ideal.setOnClickListener(v -> showDialogIdeal());

        Creditkaart.setOnClickListener(v -> showDialogCreditkaart());

        ticketDatabase = TicketDatabase.getDatabase(this);
        ticketDao = ticketDatabase.ticketDao();
    }

    //    Dialog per betaaloptie
    private void showDialogPaypal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_paypal);
        dialog.show();
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienP);
        betalingcomplete.setOnClickListener(v -> {
            emailadres = dialog.findViewById(R.id.EmailPaypal);
            wachtwoord = dialog.findViewById(R.id.PasswordPaypal);
            if(!emailadres.getText().toString().isEmpty() && !wachtwoord.getText().toString().isEmpty()){
                insertTicket();
            } else {
                Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
            }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(v -> dialog.hide());

    }
    private void showDialogApplePay() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_applepay);
        dialog.show();

        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienA);

        betalingcomplete.setOnClickListener(v -> {
        username = dialog.findViewById(R.id.GebruikerApple);
        wachtwoordApple = dialog.findViewById(R.id.WachtwoordApple);

        if (!username.getText().toString().isEmpty() && !wachtwoordApple.getText().toString().isEmpty()){
        insertTicket();
        }else {
            Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
        }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(v -> dialog.hide());

    }
    private void showDialogIdeal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ideal);
        dialog.show();
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienI);
        betalingcomplete.setOnClickListener(v -> {
        DropdownIdeal = dialog.findViewById(R.id.DropdownIdeal);
        if (!DropdownIdeal.getSelectedItem().toString().isEmpty()){
        insertTicket();
        }else{
            Toast.makeText(PayActivity.this, "Selecteer een bank om door te gaan!", Toast.LENGTH_SHORT).show();
        }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(v -> dialog.hide());

    }
    private void showDialogCreditkaart() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_creditkaart);
        dialog.show();
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienC);
        betalingcomplete.setOnClickListener(v -> {
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
                insertTicket();
            }
            else {
                Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_SHORT).show();
            }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(v -> dialog.hide());
        // on below line we are initializing our variables.
        ImageView expiryDateButton = dialog.findViewById(R.id.Expirypicker);
        ExpirydateView = dialog.findViewById(R.id.CreditcardExpire);

        // on below line we are adding click listener for our pick date button
        expiryDateButton.setOnClickListener(v -> {
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
        if (item.getItemId() == android.R.id.home) {// Handle the back button (in this case, finish the activity)
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertTicket() {
        Log.d(LOG_TAG, "insertTicket");
        new insertPaidTickets().execute();
    }

    private class insertPaidTickets extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();
            ArrayList<Ticket> z = (ArrayList<Ticket>)intent.getSerializableExtra(Ticket.getShareKey());
            if (z != null) {
                for(Ticket t: z){
                    ticketDao.insert(new Ticket(t.getTitleMovie(), t.getTimeMovie(), t.getDatumMovie(), t.getChairNr(), t.getRowNr()));
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(PayActivity.this, PersonActivity.class));

        }
    }




}
