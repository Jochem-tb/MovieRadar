package com.example.movieradar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CatalogusAdapter extends
        RecyclerView.Adapter<CatalogusAdapter.CatalogusViewHolder> {

    private ArrayList<MovieListAdapter> adapterList;
    private LayoutInflater inflater;

    public CatalogusAdapter(Context context, ArrayList<MovieListAdapter> adapterList) {
        inflater = LayoutInflater.from(context);
        this.adapterList = adapterList;
    }

    @NonNull
    @Override
    public CatalogusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.catalogus_recyclerview_item, parent, false);
        return new CatalogusViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogusViewHolder holder, int position) {
        MovieListAdapter movieListAdapter = adapterList.get(position);

        //holder.tvCatalogusItemTitle.setText

    }

    @Override
    public int getItemCount() {
        adapterList.size();
    }

    public class CatalogusViewHolder extends RecyclerView.ViewHolder {

        public TextView tvcatalogusItemTitle;
        public RecyclerView MovieListAdapter;

        public CatalogusViewHolder(@NonNull View itemView, CatalogusAdapter catalogusAdapter) {
            super(itemView);

            tvcatalogusItemTitle = itemView.findViewById(R.id.tvCatalogusItemTitle);
            MovieListAdapter = itemView.findViewById(R.id.rvCatalogusItem);
        }
    }
}
