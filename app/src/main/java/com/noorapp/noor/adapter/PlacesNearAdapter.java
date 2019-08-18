package com.noorapp.noor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noorapp.noor.Constants;
import com.noorapp.noor.Details_Activity;
import com.noorapp.noor.R;
import com.noorapp.noor.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlacesNearAdapter extends RecyclerView.Adapter<PlacesNearAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Place> placeList;

    public PlacesNearAdapter(Context mCtx, List<Place> filmList) {
        this.mCtx = mCtx;
        this.placeList = filmList;
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_plases_card, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        // placeList = new ArrayList<Place>();
        if (placeList.size() != 0) {
            final Place results = placeList.get(position);
            if (!results.getTranslations().isEmpty()) {
                holder.title.setText(placeList.get(position).getTranslations().get(0).getName());
                if (!results.getImages().isEmpty()) {
                    if (results.getImages().get(0).getPath() != null) {
                        Picasso.with(mCtx)
                                .load(Constants.mainLink + results.getImages().get(0).getPath())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(holder.img);
                    } else if (results.getImages().get(0).getLink() != null) {
                        Picasso.with(mCtx)
                                .load( results.getImages().get(0).getLink())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(holder.img);
                    } else {
                        Picasso.with(mCtx)
                                .load(Constants.mainLink + results.getImages().get(0).getPath())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(holder.img);
                    }
                }


                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mCtx, Details_Activity.class);
                        intent.putExtra("placeID", results.getId());
                        mCtx.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
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
