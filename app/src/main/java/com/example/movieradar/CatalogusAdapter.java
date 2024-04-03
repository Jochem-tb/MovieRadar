package com.example.movieradar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieradar.API.APIString;
import com.example.movieradar.API.Filters;
import com.example.movieradar.API.MovieApiTask;

import java.util.ArrayList;


public class CatalogusAdapter extends
        RecyclerView.Adapter<CatalogusAdapter.CatalogusViewHolder> implements MovieApiTask.OnNewMovieListener {
    private final String LOG_TAG = "CatalogusAdapter";
    private LayoutInflater inflater;
    private ArrayList<Movie> mMovieList;
    private Context mContext;
    private ArrayList<Integer> checkedBoxes;
    private ArrayList<ArrayList<Movie>> movieLists;
    private CatalogusViewHolder holder;


    public CatalogusAdapter(Context context, ArrayList<Integer> checkedBoxes) {
        Log.d(LOG_TAG,"CatalogusAdapter");
        mContext = context;
            inflater = LayoutInflater.from(context);
            this.checkedBoxes = checkedBoxes;
            movieLists = new ArrayList<>();
            for (int i = 0; i < checkedBoxes.size(); i++) {
                movieLists.add(new ArrayList<>()); // Initialize movie lists for each checkbox
        }
    }

    @NonNull
    @Override
    public CatalogusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.catalogus_recyclerview_item, parent, false);
        return new CatalogusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogusViewHolder holder, int position) {
//        if (checkedBoxes.isEmpty() || position >= movieLists.size()) {
//            return; // Exit method if checkedBoxes list is empty or movieLists is empty or position is out of bounds
//        }
        this.holder = holder;
        Integer genreId = checkedBoxes.get(position);
        String genre = getGenreName(genreId);
        holder.tvCatalogusItemTitle.setText(genre);
        // Make API call for the genre
        APIString str = new APIString();
        str.filterOnGenre(Filters.with_genres, genre);
        str.finish();
        Log.i(LOG_TAG, "Chosen genre APIString created");

        MovieApiTask task = new MovieApiTask(this, 1);
        task.execute(str.getApiString());


    }
    @Override
    public int getItemCount() {
        return checkedBoxes.size();
    }

    private String getGenreName(int genreId) {
            String genre="";

            switch (genreId) {
                case 1:
                    //Action
                    genre = "28";
                    break;
                case 2:
                    //Comedy
                    genre = "35";
                    break;
                case 3:
                    //Drama
                    genre = "18";
                    break;
                case 4:
                    //Family
                    genre = "10751";
                    break;
                case 5:
                    //Fantasy
                    genre = "14";
                    break;
                case 6:
                    //Horror
                    genre = "27";
                    break;
                case 7:
                    //Romance
                    genre = "10749";
                    break;
                case 8:
                    //Science Fiction
                    genre = "878";
                    break;
            }

            return genre;
        }


    static class CatalogusViewHolder extends RecyclerView.ViewHolder {
        TextView tvCatalogusItemTitle;
        RecyclerView movieListRecyclerView;

        CatalogusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatalogusItemTitle = itemView.findViewById(R.id.tvCatalogusItemTitle);
            movieListRecyclerView = itemView.findViewById(R.id.rvCatalogusItem);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            movieListRecyclerView.setLayoutManager(layoutManager);
        }
    }


    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int position) {
        Log.d(LOG_TAG, "onMovieAvailable on CatalogusAdapterClass with movielist size: "+movies.size()+" and position: "+position);
        // Ensure that the movieLists ArrayList contains a valid list at the specified position
        while (movieLists.size() <= position) {
            movieLists.add(new ArrayList<>());
        }

        // Set the movie list at the specified position
        movieLists.set(position, movies);

        // Check if all movie lists have been updated
        boolean allListsUpdated = true;
        for (ArrayList<Movie> list : movieLists) {
            if (list.isEmpty()) {
                allListsUpdated = false;
                break;
            }
        }

        // Notify adapter only when all movie lists have been updated
        if (allListsUpdated) {
            notifyDataSetChanged(); // Notify adapter that all data sets have changed
        }

        // Check if movieLists has the necessary elements
        if (position < movieLists.size()) {
            // Populate inner RecyclerView with movies
            MovieListAdapter movieListAdapter = new MovieListAdapter(mContext, movieLists.get(position));
            holder.movieListRecyclerView.setAdapter(movieListAdapter);


        } else {
            Log.e(LOG_TAG, "movieLists does not contain the necessary element at position: " + position);
        }
    }


}

