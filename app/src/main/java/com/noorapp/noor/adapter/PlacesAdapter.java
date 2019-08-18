package com.noorapp.noor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noorapp.noor.R;
import com.noorapp.noor.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Place> guideList;

    public PlacesAdapter(Context mCtx, List<Place> filmList) {
        this.mCtx = mCtx;
        this.guideList = filmList;
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_plases_card, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {

        Place results = guideList.get(position);

      /*  holder.title.setText(results.getName());
        Picasso.with(mCtx)
                .load(results.getImage())
                .placeholder(R.drawable.rest)
                .error(R.drawable.rest)
                .into(holder.img);*/
       /* holder.title.setText(results.getTitle());
        holder.poster_path.setText(results.getPoster_path());
        holder.original_title.setText(results.getOriginal_language());*/
    }

    @Override
    public int getItemCount() {
        return guideList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;


        public FilmsViewHolder(View itemview) {
            super(itemview);

            title = itemView.findViewById(R.id.titleplace);
            img = itemView.findViewById(R.id.image1);
        }
    }
}
