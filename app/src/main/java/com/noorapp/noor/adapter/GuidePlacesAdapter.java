package com.noorapp.noor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noorapp.noor.Constants;
import com.noorapp.noor.Details_Activity;
import com.noorapp.noor.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GuidePlacesAdapter extends RecyclerView.Adapter<GuidePlacesAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<com.noorapp.noor.models.GuideModel.Place> placeList;
    View view;

    public GuidePlacesAdapter(Context mCtx, List<com.noorapp.noor.models.GuideModel.Place> filmList) {
        this.mCtx = mCtx;
        this.placeList = filmList;
    }

    @NonNull
    @Override
    public GuidePlacesAdapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);
        return new GuidePlacesAdapter.FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidePlacesAdapter.FilmsViewHolder holder, int position) {

        final com.noorapp.noor.models.GuideModel.Place results = placeList.get(position);
        if (!results.getTranslations().isEmpty()) {

            holder.title.setText(results.getTranslations().get(0).getName());
            holder.desc.setText(results.getTranslations().get(0).getDescription());
            // if (results.getImages().get(0).getLink() == null) {
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

            if (results.getDistance() != null) {
                holder.distancetxt.setText(String.valueOf(results.getDistance()));
            } else {
                holder.dislinear.setVisibility(View.GONE);
            }

            if (results.getBought() != null && !results.getBought().equals("0")) {
                holder.bought.setText(results.getBought() + " " +  mCtx.getResources().getString(R.string.bought));
            } else {
                holder.bought.setVisibility(View.GONE);
            }

            if (!results.getCurrency().getTranslations().isEmpty() && !results.getPrice().equals("0")) {
                holder.newprices.setText(results.getPrice()
                        + " " + results.getCurrency().getTranslations().get(0).getIso());
            }

            if (results.getOldPrice() == null) {
                holder.discounts.setVisibility(View.GONE);
                holder.oldprices.setVisibility(View.GONE);
            } else {

                if (!results.getCurrency().getTranslations().isEmpty()) {
                    holder.oldprices.setPaintFlags(holder.oldprices.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.oldprices.setText(results.getOldPrice()
                            + " " + results.getCurrency().getTranslations().get(0).getIso());
                }
                float newprice = Float.parseFloat(results.getPrice());
                float oldprice = Float.parseFloat(results.getOldPrice());
                float x = 1 - (newprice / oldprice);
                holder.discounts.setText(Math.round(x * 100) + "%");
                Log.e("equationnn", newprice + "***" + oldprice + "***" + x + "**" + Math.round(x * 100));
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("placeidd", String.valueOf(results.getId()));
                    Intent intent = new Intent(mCtx, Details_Activity.class);
                    intent.putExtra("placeID", results.getId());
                    view.getContext().startActivity(intent);
                }
            });
        }



       /* holder.title.setText(results.getTitle());
        holder.poster_path.setText(results.getPoster_path());
        holder.original_title.setText(results.getOriginal_language());*/
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, discounts, oldprices, newprices, bought, distancetxt;
        ImageView img;
        LinearLayout cardView, dislinear;


        public FilmsViewHolder(View itemview) {
            super(itemview);

            title = itemView.findViewById(R.id.offtxt);
            desc = itemView.findViewById(R.id.destxe);
            img = itemView.findViewById(R.id.image1);
            newprices = itemView.findViewById(R.id.money);
            oldprices = itemView.findViewById(R.id.old_price);
            discounts = itemView.findViewById(R.id.discount);
            cardView = itemView.findViewById(R.id.crd);
            bought = itemview.findViewById(R.id.bought2);
            distancetxt = itemview.findViewById(R.id.dis);
            dislinear = itemview.findViewById(R.id.dislin);
        }
    }
}


