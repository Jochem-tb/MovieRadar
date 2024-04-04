package com.example.movieradar.Activity;

import com.example.movieradar.R;
import com.example.movieradar.Ticket;
import com.example.movieradar.Adapters.TicketListAdapter;
import com.example.movieradar.database.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.database.TicketDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity {

    private final String LOG_TAG = "PersonActivity";
    private final int NUM_TICKETS_LOADED = 10;

    TextView nameAccount;
    Button favoButton;
    RecyclerView rvTickets;
    ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
    TicketListAdapter mTicketListAdapter;
    TicketDao ticketDao;
    TicketDatabase ticketDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        nameAccount = findViewById(R.id.tvNamePerson);
        rvTickets = findViewById(R.id.rvTickets);
        mTicketListAdapter = new TicketListAdapter(this, ticketList);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));
        rvTickets.setAdapter(mTicketListAdapter);
        favoButton = findViewById(R.id.bt_favo);
        nameAccount.setText("Gast");

        ticketDatabase = TicketDatabase.getDatabase(this);
        ticketDao = ticketDatabase.ticketDao();


        //Navbar function
        BottomNavigationView btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.menuPersonal);


        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                //Heb geprobeerd met switch/case maar geeft errors
                if (id == R.id.menuHomeScreen) {
                    startActivity(new Intent(PersonActivity.this, MainActivity.class));
                    Log.i(LOG_TAG, "HomeScreen button clicked");
                    return true;
                } else if (id == R.id.menuFilmList) {
                    startActivity(new Intent(PersonActivity.this, CatalogusActivity.class));
                    Log.i(LOG_TAG, "Catalogus button clicked");
                    return true;
//                } else if (id == R.id.menuPersonal) {
////                        startActivity(new Intent(PersonActivity.this, PersonActivity.class));
//                    Log.i(LOG_TAG, "Personal button clicked");
//                    return true;
                } else {
                    return false;
                }
            }
        });

        favoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonActivity.this, FavorietenAcitivity.class));
            }
        });

        fillTickets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView btmNavView = findViewById(R.id.btmNavViewMain);
        btmNavView.setSelectedItemId(R.id.menuPersonal);
        mTicketListAdapter.notifyDataSetChanged();
    }

    private void fillTickets() {
        Log.d(LOG_TAG, "fillTickets");
        new GetAndSetTicketsTask().execute();
    }

    private class GetAndSetTicketsTask extends AsyncTask<Void, Void, Void> {
        ArrayList<Ticket> list;
        @Override
        protected Void doInBackground(Void... voids) {
//
            list = new ArrayList<>(ticketDao.getTopTicketsForToday(NUM_TICKETS_LOADED));
            Log.d(LOG_TAG, "Fetched tickets from Dao: "+list.size());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mTicketListAdapter.setTicketList(list);
        }
    }
}




