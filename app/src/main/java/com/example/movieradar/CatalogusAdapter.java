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


    public CatalogusAdapter(Context context, ArrayList<Movie> movieList, ArrayList<Integer> checkedBoxes) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.mMovieList = movieList;
        this.checkedBoxes = checkedBoxes;
    }

    @NonNull
    @Override
    public CatalogusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.catalogus_recyclerview_item, parent, false);
        return new CatalogusViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogusViewHolder holder, int position) {


        String genre = "";

        for (int i = 0; i <= checkedBoxes.size();) {
            switch (checkedBoxes.get(i)) {
                case 1:
                    genre = "Action";
                    Log.i(LOG_TAG, "Action genre selected");
                    break;
                case 2:
                    genre = "Comedy";
                    Log.i(LOG_TAG, "Comedy genre selected");
                    break;
                case 3:
                    genre = "Drama";
                    Log.i(LOG_TAG, "Drama genre selected");
                    break;
                case 4:
                    genre = "Family";
                    Log.i(LOG_TAG, "Family genre selected");
                    break;
                case 5:
                    genre = "Fantasy";
                    Log.i(LOG_TAG, "Fantasy genre selected");
                    break;
                case 6:
                    genre = "Horror";
                    Log.i(LOG_TAG, "Horror genre selected");
                    break;
                case 7:
                    genre = "Romance";
                    Log.i(LOG_TAG, "Romance genre selected");
                    break;
                case 8:
                    genre = "Science Fiction";
                    Log.i(LOG_TAG, "Science Fiction genre selected");
                    break;
            }

            APIString str = new APIString();
            str.filter(Filters.with_genres, genre);
            str.finish();
            Log.i(LOG_TAG, "Chosen genre APIString created");

            MovieApiTask task = new MovieApiTask(this, 23);
            task.execute(str.getApiString());

            Log.i(LOG_TAG, "Loaded " + mMovieList.size() + " movies");
            holder.tvCatalogusItemTitle.setText("Genre:");
            MovieListAdapter movieListAdapter = new MovieListAdapter(mContext, mMovieList);
            holder.movieListAdapter.setAdapter(movieListAdapter);
            Log.i(LOG_TAG, "OnBindViewHolder");
            break;
        }
    }

    @Override
    public int getItemCount() {
        return checkedBoxes.size();
    }

    public class CatalogusViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCatalogusItemTitle;
        public RecyclerView movieListAdapter;

        public CatalogusViewHolder(@NonNull View itemView, CatalogusAdapter catalogusAdapter) {
            super(itemView);

            tvCatalogusItemTitle = itemView.findViewById(R.id.tvCatalogusItemTitle);
            movieListAdapter = itemView.findViewById(R.id.rvCatalogusItem);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            movieListAdapter.setLayoutManager(layoutManager);
        }
    }

    public void onNewMovieListener() {

    }

    @Override
    public void onMovieAvailable(ArrayList<Movie> movies, int apiIdentifier) {

    }

}

