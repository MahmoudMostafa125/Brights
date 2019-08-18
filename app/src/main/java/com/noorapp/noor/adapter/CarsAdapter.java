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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Constants;
import com.noorapp.noor.R;
import com.noorapp.noor.Trips_Activity;
import com.noorapp.noor.models.TransportationModel.Transportation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Transportation> placeList;

    public CarsAdapter(Context mCtx, List<Transportation> filmList) {
        this.mCtx = mCtx;
        this.placeList = filmList;
    }

    @NonNull
    @Override
    public CarsAdapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_plases_card2, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.35);
        layoutParams.height = (int) (parent.getWidth() * 0.40);
        return new CarsAdapter.FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.FilmsViewHolder holder, final int position) {

        final Transportation results = placeList.get(position);

        if (!results.getTranslations().isEmpty()) {
            if (results.getTranslations().get(0).getName()!=null) {
                holder.title.setText(results.getTranslations().get(0).getName());
            }

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
       /* holder.title.setText(results.getTitle());
        holder.poster_path.setText(results.getPoster_path());
        holder.original_title.setText(results.getOriginal_language());*/
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("placeidd", String.valueOf(results.getId()));
                    Intent intent = new Intent(mCtx, Trips_Activity.class);
                    intent.putExtra("placeID", position);
                    mCtx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        LinearLayout cardView;


        public FilmsViewHolder(View itemview) {
            super(itemview);

            title = itemView.findViewById(R.id.titleplace);
            img = itemView.findViewById(R.id.image1);
            cardView = itemView.findViewById(R.id.lin);
        }
    }
}
