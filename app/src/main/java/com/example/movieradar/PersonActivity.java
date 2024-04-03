package com.example.movieradar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity {

    private final String LOG_TAG = "PersonActivity";

    TextView nameAccount;
    RecyclerView rvTickets;
    ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
    TicketListAdapter mTicketListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        nameAccount = findViewById(R.id.tvNamePerson);
        rvTickets = findViewById(R.id.rvTickets);
        mTicketListAdapter = new TicketListAdapter(this,ticketList);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));
        rvTickets.setAdapter(mTicketListAdapter);
        nameAccount.setText("Gast");
        
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
        for (int i = 0; i < 10; i++) {
            ticketList.add(new Ticket(
                    1, // Room number
                    "Movie " + (i + 1), // Title of the movie
                    "12:00", // Time of the movie
                    "2024-04-03", // Date of the movie
                    i + 1 // Chair number
            ));
        }
        Log.d(LOG_TAG, "setTicketList with: "+ticketList.size());
        mTicketListAdapter.setTicketList(ticketList);

    }
}
