package com.example.movieradar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.Activity.TicketDetailActivity;
import com.example.movieradar.R;
import com.example.movieradar.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder> {

    private final String LOG_TAG = "TicketListAdapter";
    private ArrayList<Ticket> mTicketList;
    private LayoutInflater inflater;

    public TicketListAdapter(Context context, ArrayList<Ticket> ticketList) {
        this.inflater = LayoutInflater.from(context);
        this.mTicketList = ticketList;
        Log.i(LOG_TAG, "TicketListAdapter");
    }

    @NonNull
    @Override
    public TicketListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.movie_ticket_item, parent, false);
        Log.i(LOG_TAG, "onCreateViewHolder");
        return new TicketListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketListViewHolder holder, int position) {
        Ticket mTicket = mTicketList.get(position);

        holder.tvTitleMovie.setText(mTicket.getTitleMovie());
        holder.tvTimeMovie.setText(mTicket.getTimeMovie());
        holder.tvDateMovie.setText(mTicket.getDatumMovie());
        holder.tvSeatMovieNumber.setText(String.valueOf(mTicket.getChairNr()));
        holder.tvRoomMovieNumber.setText(String.valueOf(mTicket.getRoomNr()));
        String text = mTicket.getTitleMovie()+mTicket.getTimeMovie()+mTicket.getDatumMovie()+mTicket.getChairNr()+mTicket.getRoomNr();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,400,400);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            holder.ivQRImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        Log.i(LOG_TAG, "onBindViewHolder");
    }

    public void setTicketList(ArrayList<Ticket> tickets) {
        this.mTicketList = tickets;
        notifyDataSetChanged();
        Log.i(LOG_TAG, "setTicketList");
    }

    @Override
    public int getItemCount() {
        return (mTicketList != null) ? mTicketList.size() : 0;
    }

    public class TicketListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitleMovie;
        public TextView tvTimeMovie;
        public TextView tvDateMovie;
        public TextView tvSeatMovieNumber;
        public TextView tvRoomMovieNumber;
        public ImageView ivQRImage;

        public TicketListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleMovie = itemView.findViewById(R.id.tvTitleMovie);
            tvTimeMovie = itemView.findViewById(R.id.tvTimeMovie);
            tvDateMovie = itemView.findViewById(R.id.tvDateMovie);
            tvSeatMovieNumber = itemView.findViewById(R.id.tvSeatMovieNumber);
            tvRoomMovieNumber = itemView.findViewById(R.id.tvRoomMovieNumber);
            ivQRImage = itemView.findViewById(R.id.ivQRImage);
            itemView.setOnClickListener(this);
            Log.i(LOG_TAG, "TicketListViewHolder");

        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d(LOG_TAG, "ListItem onClick: "+position);

            //Ticket op de huidige positie in de intent krijgen
            Ticket selectedTicket = mTicketList.get(position);

            // Nieuwe intent maken om TicketDetail te openen
            Intent intent = new Intent(v.getContext(), TicketDetailActivity.class);

            // TicketGegevens in intent stoppen
            intent.putExtra(Ticket.getShareKey(), selectedTicket);

            //Activiteit starten
            v.getContext().startActivity(intent);

        }
    }



}
