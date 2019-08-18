package com.noorapp.noor.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noorapp.noor.Book_Car_Activity;
import com.noorapp.noor.Constants;
import com.noorapp.noor.Login_option_Activity;
import com.noorapp.noor.R;
import com.noorapp.noor.models.TransportationModel.Trip;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Trip> placeList;
    View view;


    public TripsAdapter(Context mCtx, List<Trip> filmList) {
        this.mCtx = mCtx;
        this.placeList = filmList;
    }

    @NonNull
    @Override
    public TripsAdapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_card, parent, false);
        return new TripsAdapter.FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripsAdapter.FilmsViewHolder holder, final int position) {

        final Trip results = placeList.get(position);

        if (!results.getTranslations().isEmpty()) {
            holder.title.setText(results.getTranslations().get(0).getName());
            holder.desc.setText(results.getTranslations().get(0).getDescription());
            if (results.getPassengers() != null) {
                holder.passesngers.setText(view.getResources().getString(R.string.passengers) + results.getPassengers());
            }
            if (!results.getCurrency().getTranslations().isEmpty()) {
                if (results.getCurrency().getTranslations().get(0).getIso() != null) {
                    if (results.getPrice() != null) {
                        holder.price.setText(results.getPrice() + " " + results.getCurrency().getTranslations().get(0).getIso());

                    } else {
                        holder.discounts.setVisibility(View.GONE);
                    }
                    if (results.getOldPrice() != null) {
                        holder.oldprice.setText(results.getOldPrice() + " " + results.getCurrency().getTranslations().get(0).getIso());
                        holder.oldprice.setPaintFlags(holder.oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        holder.discounts.setVisibility(View.GONE);

                    }


                    if (results.getPrice() != null && results.getOldPrice() != null) {
                        Log.e("ANAANANA",results.getPrice());
                        float newprice = Float.parseFloat(results.getPrice());
                        float oldprice = Float.parseFloat(results.getOldPrice());
                        float x = 1 - (newprice / oldprice);
                        holder.discounts.setText(Math.round(x * 100) + "%");
                    }else{
                        Log.e("JKJKJJK","KAKAKAKAKKAKAAK");
                        holder.discounts.setVisibility(View.GONE);
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
                                    .load(results.getImages().get(0).getLink())
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
                }
            }
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
            final String API = sharedPref.getString("api_token", null);

       /* holder.title.setText(results.getTitle());
        holder.poster_path.setText(results.getPoster_path());
        holder.original_title.setText(results.getOriginal_language());*/
            holder.btnbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (API == null) {
                        Intent intent = new Intent(v.getContext(), Login_option_Activity.class);
                        v.getContext().startActivity(intent);
                    } else {
                        Log.e("placeidd", String.valueOf(results.getId()));
                        Intent intent = new Intent(mCtx, Book_Car_Activity.class);
                        intent.putExtra("placeID", results.getId());
                        intent.putExtra("placePRICE", results.getPrice());
                        intent.putExtra("placeNAME", results.getTranslations().get(0).getName());
                        intent.putExtra("placeDESC", results.getTranslations().get(0).getDescription());
                        intent.putExtra("placeISO", results.getCurrency().getCurrencyCode());

                      //  intent.putExtra("PlaceTerms", results.getTranslations().get(0));
                        v.getContext().startActivity(intent);
                    }
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (API == null) {
                        Intent intent = new Intent(v.getContext(), Login_option_Activity.class);
                        v.getContext().startActivity(intent);
                    } else {
                        Log.e("placeidd", String.valueOf(results.getId()));
                        Intent intent = new Intent(mCtx, Book_Car_Activity.class);
                        intent.putExtra("placeID", results.getId());
                        intent.putExtra("placePRICE", results.getPrice());
                        intent.putExtra("placeNAME", results.getTranslations().get(0).getName());
                        intent.putExtra("placeDESC", results.getTranslations().get(0).getDescription());
                        intent.putExtra("placeISO", results.getCurrency().getCurrencyCode());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }else{
            holder.discounts.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, passesngers, price, oldprice, discounts;
        ImageView img;
        LinearLayout cardView;
        Button btnbook;

        public FilmsViewHolder(View itemview) {
            super(itemview);
            title = itemView.findViewById(R.id.offtxt);
            desc = itemView.findViewById(R.id.destxe);
            passesngers = itemView.findViewById(R.id.passen);
            price = itemView.findViewById(R.id.price);
            oldprice = itemView.findViewById(R.id.priceold);
            img = itemView.findViewById(R.id.image1);
            cardView = itemView.findViewById(R.id.crd);
            discounts = itemView.findViewById(R.id.discount);
            btnbook = itemView.findViewById(R.id.bookbtnid);
        }
    }
}
