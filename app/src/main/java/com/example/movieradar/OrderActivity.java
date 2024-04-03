package com.example.movieradar;

import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private final String LOG_TAG = "OrderActivity";
    private String[] date;
    private String[] time;
    private final String[] kindOfTicket = {"Kind", "Volwassene"};
    private static final int priceChild = 5;
    private static final int priceAdult = 10;
    private int selectedChairsCount;
    private Movie mMovie;
    private boolean isAdult;

    // All Textviews, Spinners and Buttons
    TextView tvTitle;
    TextView tvCountChairs;
    TextView tvTotalPrice;
    Spinner spDropdownDate;
    Spinner spDropdownTime;
    Spinner spDropdownTicketKind;
    Button bCancel;
    Button bOrder;

    // All cinema seats
    //row 1
    ImageView row1Seat1;
    ImageView row1Seat2;
    ImageView row1Seat3;
    ImageView row1Seat4;
    ImageView row1Seat5;
    ImageView row1Seat6;

    //row 2
    ImageView row2Seat1;
    ImageView row2Seat2;
    ImageView row2Seat3;
    ImageView row2Seat4;
    ImageView row2Seat5;
    ImageView row2Seat6;
    ImageView row2Seat7;
    ImageView row2Seat8;

    //row 3
    ImageView row3Seat1;
    ImageView row3Seat2;
    ImageView row3Seat3;
    ImageView row3Seat4;
    ImageView row3Seat5;
    ImageView row3Seat6;
    ImageView row3Seat7;
    ImageView row3Seat8;

    //row 4
    ImageView row4Seat1;
    ImageView row4Seat2;
    ImageView row4Seat3;
    ImageView row4Seat4;
    ImageView row4Seat5;
    ImageView row4Seat6;
    ImageView row4Seat7;
    ImageView row4Seat8;

    //row 5
    ImageView row5Seat1;
    ImageView row5Seat2;
    ImageView row5Seat3;
    ImageView row5Seat4;
    ImageView row5Seat5;
    ImageView row5Seat6;
    ImageView row5Seat7;
    ImageView row5Seat8;

    //row 6
    ImageView row6Seat1;
    ImageView row6Seat2;
    ImageView row6Seat3;
    ImageView row6Seat4;
    ImageView row6Seat5;
    ImageView row6Seat6;
    ImageView row6Seat7;
    ImageView row6Seat8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Setting counters to 0
        selectedChairsCount = 0;

        //Linking of the Textviews, Spinners and Buttons
        tvTitle = findViewById(R.id.tv_MovieTitle);
        tvCountChairs = findViewById(R.id.tv_countChairs);
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        spDropdownDate = findViewById(R.id.dateSpinner);
        spDropdownTime = findViewById(R.id.timeSpinner);
        spDropdownTicketKind = findViewById(R.id.kindOfTicketSpinner);
        bCancel = findViewById(R.id.cancel_button);
        bOrder = findViewById(R.id.order_button);


        //Linking of the cinema chairs
        //row 1
        row1Seat1 = findViewById(R.id.row1Seat1);
        row1Seat2 = findViewById(R.id.row1Seat2);
        row1Seat3 = findViewById(R.id.row1Seat3);
        row1Seat4 = findViewById(R.id.row1Seat4);
        row1Seat5 = findViewById(R.id.row1Seat5);
        row1Seat6 = findViewById(R.id.row1Seat6);

        //row 2
        row2Seat1 = findViewById(R.id.row2Seat1);
        row2Seat2 = findViewById(R.id.row2Seat2);
        row2Seat3 = findViewById(R.id.row2Seat3);
        row2Seat4 = findViewById(R.id.row2Seat4);
        row2Seat5 = findViewById(R.id.row2Seat5);
        row2Seat6 = findViewById(R.id.row2Seat6);
        row2Seat7 = findViewById(R.id.row2Seat7);
        row2Seat8 = findViewById(R.id.row2Seat8);

        //row 3
        row3Seat1 = findViewById(R.id.row3Seat1);
        row3Seat2 = findViewById(R.id.row3Seat2);
        row3Seat3 = findViewById(R.id.row3Seat3);
        row3Seat4 = findViewById(R.id.row3Seat4);
        row3Seat5 = findViewById(R.id.row3Seat5);
        row3Seat6 = findViewById(R.id.row3Seat6);
        row3Seat7 = findViewById(R.id.row3Seat7);
        row3Seat8 = findViewById(R.id.row3Seat8);

        //row 4
        row4Seat1 = findViewById(R.id.row4Seat1);
        row4Seat2 = findViewById(R.id.row4Seat2);
        row4Seat3 = findViewById(R.id.row4Seat3);
        row4Seat4 = findViewById(R.id.row4Seat4);
        row4Seat5 = findViewById(R.id.row4Seat5);
        row4Seat6 = findViewById(R.id.row4Seat6);
        row4Seat7 = findViewById(R.id.row4Seat7);
        row4Seat8 = findViewById(R.id.row4Seat8);

        //row 5
        row5Seat1 = findViewById(R.id.row5Seat1);
        row5Seat2 = findViewById(R.id.row5Seat2);
        row5Seat3 = findViewById(R.id.row5Seat3);
        row5Seat4 = findViewById(R.id.row5Seat4);
        row5Seat5 = findViewById(R.id.row5Seat5);
        row5Seat6 = findViewById(R.id.row5Seat6);
        row5Seat7 = findViewById(R.id.row5Seat7);
        row5Seat8 = findViewById(R.id.row5Seat8);

        //row 6
        row6Seat1 = findViewById(R.id.row6Seat1);
        row6Seat2 = findViewById(R.id.row6Seat2);
        row6Seat3 = findViewById(R.id.row6Seat3);
        row6Seat4 = findViewById(R.id.row6Seat4);
        row6Seat5 = findViewById(R.id.row6Seat5);
        row6Seat6 = findViewById(R.id.row6Seat6);
        row6Seat7 = findViewById(R.id.row6Seat7);
        row6Seat8 = findViewById(R.id.row6Seat8);

        // cinema seats onclicklisteners
        //row 1
        row1Seat1.setOnClickListener(getSeatClickListener());
        row1Seat2.setOnClickListener(getSeatClickListener());
        row1Seat3.setOnClickListener(getSeatClickListener());
        row1Seat4.setOnClickListener(getSeatClickListener());
        row1Seat5.setOnClickListener(getSeatClickListener());
        row1Seat6.setOnClickListener(getSeatClickListener());

        //row 2
        row2Seat1.setOnClickListener(getSeatClickListener());
        row2Seat2.setOnClickListener(getSeatClickListener());
        row2Seat3.setOnClickListener(getSeatClickListener());
        row2Seat4.setOnClickListener(getSeatClickListener());
        row2Seat5.setOnClickListener(getSeatClickListener());
        row2Seat6.setOnClickListener(getSeatClickListener());
        row2Seat7.setOnClickListener(getSeatClickListener());
        row2Seat8.setOnClickListener(getSeatClickListener());

        //row 3
        row3Seat1.setOnClickListener(getSeatClickListener());
        row3Seat2.setOnClickListener(getSeatClickListener());
        row3Seat3.setOnClickListener(getSeatClickListener());
        row3Seat4.setOnClickListener(getSeatClickListener());
        row3Seat5.setOnClickListener(getSeatClickListener());
        row3Seat6.setOnClickListener(getSeatClickListener());
        row3Seat7.setOnClickListener(getSeatClickListener());
        row3Seat8.setOnClickListener(getSeatClickListener());

        //row 4
        row4Seat1.setOnClickListener(getSeatClickListener());
        row4Seat2.setOnClickListener(getSeatClickListener());
        row4Seat3.setOnClickListener(getSeatClickListener());
        row4Seat4.setOnClickListener(getSeatClickListener());
        row4Seat5.setOnClickListener(getSeatClickListener());
        row4Seat6.setOnClickListener(getSeatClickListener());
        row4Seat7.setOnClickListener(getSeatClickListener());
        row4Seat8.setOnClickListener(getSeatClickListener());

        //row 5
        row5Seat1.setOnClickListener(getSeatClickListener());
        row5Seat2.setOnClickListener(getSeatClickListener());
        row5Seat3.setOnClickListener(getSeatClickListener());
        row5Seat4.setOnClickListener(getSeatClickListener());
        row5Seat5.setOnClickListener(getSeatClickListener());
        row5Seat6.setOnClickListener(getSeatClickListener());
        row5Seat7.setOnClickListener(getSeatClickListener());
        row5Seat8.setOnClickListener(getSeatClickListener());

        //row 6
        row6Seat1.setOnClickListener(getSeatClickListener());
        row6Seat2.setOnClickListener(getSeatClickListener());
        row6Seat3.setOnClickListener(getSeatClickListener());
        row6Seat4.setOnClickListener(getSeatClickListener());
        row6Seat5.setOnClickListener(getSeatClickListener());
        row6Seat6.setOnClickListener(getSeatClickListener());
        row6Seat7.setOnClickListener(getSeatClickListener());
        row6Seat8.setOnClickListener(getSeatClickListener());

        //Setting title

        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Movie.getShareKey());

        if (mMovie != null) {
            Log.d(LOG_TAG, "Title has been loaded");
            tvTitle.setText(mMovie.getTitle());
        } else {
            Log.d(LOG_TAG, "Title couldn't been loaded");
            tvTitle.setText("Ticket bestellen");
        }

        //Cancel Button
        bCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(LOG_TAG, "Cancel button clicked");
                finish();
            }
        });

        //Order Button
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Order button clicked");
            }
        });

        //Creation of an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.kindOfTicket_array,
                        android.R.layout.simple_spinner_item);

        //Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        spDropdownTicketKind.setAdapter(staticAdapter);

        //Methode to look what kind of tickets it is.
        spDropdownTicketKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Makes the boolean true of false. Standard = true
                if (position == 0) { // Adult
                    isAdult = true;
                    Log.d(LOG_TAG, "isAdult: " + isAdult);
                } else if (position == 1) { // Child
                    isAdult = false;
                    Log.d(LOG_TAG, "isAdult: " + isAdult);
                }
                setTicketInformation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // If nothing is selected or errors happen:
                isAdult = true;
                Log.d(LOG_TAG, "Nothing selected; isAdult: " + isAdult);
            }
        });
    }

    private View.OnClickListener getSeatClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage((ImageView) v);
            }
        };
    }

    public void changeImage(ImageView imageView) {
        String tag = (String) imageView.getTag();
            if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.unavailable_chair_foreground))){
                Toast.makeText(this, "Deze stoel is al bezet", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "unavailable chair pushed");
            } else if (tag == null || !tag.equals("selected")) {
                imageView.setImageResource(R.drawable.selected_chair_foreground);
                imageView.setTag("selected");
                selectedChairsCount++;
                Log.i(LOG_TAG, "available chair changed to selected" );
            } else {
                imageView.setImageResource(R.drawable.available_chair_foreground);
                imageView.setTag(null);
                selectedChairsCount--;
                Log.i(LOG_TAG, "selected chair changed to available");
            }
        setTicketInformation();
    }

    public void setTicketInformation(){
        int totalPrice = isAdult ? selectedChairsCount * priceAdult : selectedChairsCount * priceChild;
        tvCountChairs.setText(String.valueOf(selectedChairsCount));
        tvTotalPrice.setText("€" + totalPrice + ",00");
    }



}