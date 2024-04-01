package com.example.movieradar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.API.APIString;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private final String LOG_TAG = "MovieListAdapter";
    private ArrayList<Movie> mMovieList;
    private LayoutInflater inflater;

    public MovieListAdapter(Context context, ArrayList<Movie> movieList) {
        inflater = LayoutInflater.from(context);
        this.mMovieList = movieList;
        Log.i(LOG_TAG, "MovieListAdapter");
    }

    @NonNull
    @Override
    public MovieListAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        Log.i(LOG_TAG, "OnCreateViewHolder");
        return new MovieListViewHolder(ItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {

        Movie movie = mMovieList.get(position);

        holder.tvMovieListItem.setText(movie.getTitle());
        Picasso.get().load(APIString.getBackdropUrl(mMovieList.get(position).getPoster_path())).into(holder.ivMovieListItem);
        Log.i(LOG_TAG, "OnBindViewHolder");
    }

    public void setMovieList(ArrayList<Movie> movies) {

        this.mMovieList = movies;
        this.notifyDataSetChanged();
        Log.i(LOG_TAG, "setMovieList");
    }

    @Override
    public int getItemCount() {
        return (mMovieList != null) ? mMovieList.size(): 0;
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvMovieListItem;
        public ImageView ivMovieListItem;
        public MovieListViewHolder(@NonNull View itemView, MovieListAdapter movieListAdapter) {
            super(itemView);
            tvMovieListItem = itemView.findViewById(R.id.tvMovieListItem);
            ivMovieListItem = itemView.findViewById(R.id.ivMovieListPoster);

            itemView.setOnClickListener(this);
            Log.i(LOG_TAG, "MovieListViewHolder");
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d(LOG_TAG, "ListItem onClick: "+position);

            //Movie op de huidige positie in de intent krijgen
            Movie selectedMovie = mMovieList.get(position);

            // Nieuwe intent maken om MovieDetailActivity te openen
            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);

            // MovieGegevens in intent stoppen
            intent.putExtra(Movie.getKey(), selectedMovie);

            //Activiteit starten
            v.getContext().startActivity(intent);

        }
    }
}
