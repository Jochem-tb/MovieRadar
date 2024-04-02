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

import java.util.ArrayList;


public class CatalogusAdapter extends
        RecyclerView.Adapter<CatalogusAdapter.CatalogusViewHolder> {
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

        APIString str = new APIString();
        str.filter(Filters.with_genres, "");
        str.finish();
        str.getApiString();

        holder.tvCatalogusItemTitle.setText("TEST");
        MovieListAdapter movieListAdapter = new MovieListAdapter(mContext, mMovieList);
        holder.movieListAdapter.setAdapter(movieListAdapter);

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
}
