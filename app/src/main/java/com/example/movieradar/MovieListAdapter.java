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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private final String LOG_TAG = "MovieListAdapter";
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private LayoutInflater inflater;

    public MovieListAdapter(Context context, ArrayList<Movie> MovieList) {
        inflater = LayoutInflater.from(context);
        this.mMovieList = MovieList;
    }

    @NonNull
    @Override
    public MovieListAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieListAdapter.MovieListViewHolder(ItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {

        Movie movie = mMovieList.get(position);

        holder.tvMovieListItem.setText(movie.getTitle());

        String url = movie.getPoster_path();
        Picasso.get().load(url).into(holder.ivMovieListItem);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvMovieListItem;
        public ImageView ivMovieListItem;
        public MovieListViewHolder(@NonNull View itemView, MovieListAdapter movieListAdapter) {
            super(itemView);
            tvMovieListItem = itemView.findViewById(R.id.tvMovieListItem);
            ivMovieListItem = itemView.findViewById(R.id.ivMovieListPoster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Movie selectedMovie = mMovieList.get(position);

            //Intent intent = new Intent(v.getContext(), MovieDetails.class);
            //intent.putExtra(MainActivity.MOVIE, selectedMovie);
            //v.getContext().startActivity(intent);

        }
    }
}
