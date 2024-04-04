package com.example.movieradar.Activity;

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

import com.example.movieradar.Datatest;
import com.example.movieradar.R;
import com.example.movieradar.Ticket;
import com.example.movieradar.database.TicketDao;
import com.example.movieradar.database.TicketDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PayActivity extends AppCompatActivity {

//      Log_Tag
    private final String LOG_TAG = "PayActivity";

//      Database entry tickets
    TicketDao ticketDao;
    TicketDatabase ticketDatabase;


//      Ticket info
    private String movieTitle;
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

//      OnCreate
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "PayActivity geopend ");
        setContentView(R.layout.activity_payticket);

    //  Initialiseer de toolbar
        toolbar = findViewById(R.id.Tb_Ticketkopen);
        toolbar.setTitle(R.string.payment_screen);
        setSupportActionBar(toolbar);

    //  Terugknop inschakelen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    //  Tickets verkijgen van OrderActivity
        // reset de count
        int countSeats = 0;
    //  Informatie verkrijgen van OrderActivity
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

//      Attributen koppelen aan Layout
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        tvMovieTitle = findViewById(R.id.tv_MovieTitle);
        tvKindOfTicket = findViewById(R.id.tv_KindOfTicket);
        Paypal = findViewById(R.id.Paypal);
        ApplePay = findViewById(R.id.ApplePay);
        Creditkaart = findViewById(R.id.CreditCard);
        Ideal = findViewById(R.id.Ideal);

//      Text zetten
        tvTotalPrice.setText("€" + totalPrice + ",00");
        tvMovieTitle.setText(movieTitle);
        tvKindOfTicket.setText(countSeats + "x " + kindOfTicket);

//      Button per betaaloptie
        Paypal.setOnClickListener(v -> showDialogPaypal());

        ApplePay.setOnClickListener(v -> showDialogApplePay());

        Ideal.setOnClickListener(v -> showDialogIdeal());

        Creditkaart.setOnClickListener(v -> showDialogCreditkaart());

        ticketDatabase = TicketDatabase.getDatabase(this);
        ticketDao = ticketDatabase.ticketDao();
    }

//      Dialog per betaaloptie
    private void showDialogPaypal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_paypal);
        dialog.show();
        betalingcomplete = dialog.findViewById(R.id.BetalingvoltooienP);
        betalingcomplete.setOnClickListener(v -> {
            emailadres = dialog.findViewById(R.id.EmailPaypal);
            wachtwoord = dialog.findViewById(R.id.PasswordPaypal);

//      Datacheck
            if(wachtwoord.getText().toString().isEmpty()) {
                Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_LONG).show();

            }
            else if(!Datatest.checkEmailAddress(emailadres.getText().toString())) {
                    Toast.makeText(PayActivity.this, "Gebruik het juiste email format! Voorbeeld johnsmith@gmail.com", Toast.LENGTH_LONG).show();
            }else if (Datatest.containsUnicodeCharacter(wachtwoord.getText().toString())) {
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            }else if (Datatest.containsUnicodeCharacter(emailadres.getText().toString())) {
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            } else {
                insertTicket();
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

        if (username.getText().toString().isEmpty() || wachtwoordApple.getText().toString().isEmpty()){
            Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_LONG).show();
        }else if (Datatest.containsUnicodeCharacter(username.getText().toString())){
            Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
        }else {
            insertTicket();
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
        if (DropdownIdeal.getSelectedItem().toString().isEmpty()){
        Toast.makeText(PayActivity.this, "Selecteer een bank om door te gaan!", Toast.LENGTH_LONG).show();
        } else {
                insertTicket();
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
                Toast.makeText(PayActivity.this, "Creditkaartnummer moet uit 16 karakters bestaan!", Toast.LENGTH_LONG).show();
            }
            if(Datatest.containsUnicodeCharacter(ckn)){
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            }
            if(!(checkcharsS >= 3)){
                Toast.makeText(PayActivity.this, "Veiligheidscode moet uit 3 tot 4 karakters bestaan!", Toast.LENGTH_LONG).show();
            }
            if(Datatest.containsUnicodeCharacter(cks)){
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            }
            if(!(checkchars == 16) || !(checkcharsS >= 3) || ExpirydateView.getText().toString().isEmpty() || creditkaartHolder.getText().toString().isEmpty()){
                Toast.makeText(PayActivity.this, "Vul alle velden in om door te gaan!", Toast.LENGTH_LONG).show();
            }
            if(Datatest.containsUnicodeCharacter(ExpirydateView.getText().toString())){
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            }
            if(Datatest.containsUnicodeCharacter(creditkaartHolder.getText().toString())){
                Toast.makeText(PayActivity.this, "Gebruik reguliere tekens! ", Toast.LENGTH_LONG).show();
            }
            if (!Datatest.expiryDate(LocalDate.parse(ExpirydateView.getText().toString()))){
                Toast.makeText(PayActivity.this, "Vul een geldige datum in! Datum moet later zijn dan huidige datum", Toast.LENGTH_LONG).show();
            }
            else {
                insertTicket();
            }
        });

        betalingcancel = dialog.findViewById(R.id.BetalenAnnuleren);
        betalingcancel.setOnClickListener(v -> dialog.hide());
//      Calender voor afloopdatum
        ImageView expiryDateButton = dialog.findViewById(R.id.Expirypicker);
        ExpirydateView = dialog.findViewById(R.id.CreditcardExpire);
        expiryDateButton.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PayActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        ExpirydateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                    },
                    year, month, day);
            datePickerDialog.show();
        });
    }

//    Toolbar backbutton functionaliteit
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//Ticket toevoegen aan database
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
