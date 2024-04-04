package com.example.movieradar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private final String LOG_TAG = "PayActivity";
    private ArrayList<Ticket> tickets;
    Button Paypal;
    Button ApplePay;
    Button Creditkaart;
    Button Ideal;
    TextView tvTotalPrice;

    //PayPal Dialog attributen

    Button betalingcancel;
    Button betalingcomplete;
    TextView emailadres;
    TextView wachtwoord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "PayActivity geopend ");
        setContentView(R.layout.activity_payticket);

        //Getting tickets from OrderActivity
        Intent intent = getIntent();
        tickets = intent.getParcelableArrayListExtra(Ticket.getShareKey());
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                Log.d("TicketInfo: ","Movie title: " + ticket.getTitleMovie() + ", Room: " + ticket.getRoomNr() + ", Chair: " + ticket.getChairNr() + ", Row: " + ticket.getRowNr() + ", Time: " + ticket.getTimeMovie() + ", Datum: " + ticket.getDatumMovie());
            }
        } else {
            Log.e("TicketInfo", "Tickets ArrayList is null");
        }

        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        tvTotalPrice.setText("€00,00");
        Paypal = findViewById(R.id.Paypal);
        ApplePay = findViewById(R.id.ApplePay);
        Creditkaart = findViewById(R.id.CreditCard);
        Ideal = findViewById(R.id.Ideal);
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
