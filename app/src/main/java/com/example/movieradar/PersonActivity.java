package com.example.movieradar;

import com.example.movieradar.database.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.database.TicketDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity {

    private final String LOG_TAG = "PersonActivity";
    private final int NUM_TICKETS_LOADED = 5;

    TextView nameAccount;
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

        fillTickets();
    }

    private void fillTickets() {
        Log.d(LOG_TAG, "fillTickets");
        new GetAndSetTicketsTask().execute();
    }

    private class GetAndSetTicketsTask extends AsyncTask<Void, Void, Void> {
        ArrayList<Ticket> list;
        @Override
        protected Void doInBackground(Void... voids) {
//            for (int i=0; i<10; i++){
//                ticketDao.insert(new Ticket("Dummy "+i, LocalTime.now().toString(),"2024-04-0"+i,i,i));
//            }
            ;
            list = new ArrayList<>(ticketDao.getTopTicketsForToday(LocalDate.now().toString(), NUM_TICKETS_LOADED));
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




