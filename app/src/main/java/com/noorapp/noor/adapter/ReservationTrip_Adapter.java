package com.noorapp.noor.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noorapp.noor.Constants;
import com.noorapp.noor.R;
import com.noorapp.noor.models.ReservationsModel.Trip;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ReservationTrip_Adapter extends RecyclerView.Adapter<ReservationTrip_Adapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Trip> placeList;

    public ReservationTrip_Adapter(Context mCtx, List<Trip> filmList) {
        this.mCtx = mCtx;
        this.placeList = filmList;
    }

    @NonNull
    @Override
    public ReservationTrip_Adapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card, parent, false);
        return new ReservationTrip_Adapter.FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationTrip_Adapter.FilmsViewHolder holder, final int position) {

        final Trip results = placeList.get(position);
        if (!placeList.isEmpty()) {
            if (results.getTrip().getTranslations()!=null) {
                if (!results.getTrip().getTranslations().isEmpty()) {
                    holder.title.setText(results.getTrip().getTranslations().get(0).getName());
                }
            }
            if (!results.getTrip().getCurrency().getTranslations().isEmpty()) {
                if (results.getTrip().getCurrency().getTranslations().get(0).getIso() != null) {
                    holder.Ttotal.setText(results.getAmount() + " " + results.getTrip().getCurrency().getTranslations().get(0).getIso());
                }
            }
            if (results.getDate() != null) {
                String DateNow, DateReservstion = null;
                holder.Tdate.setText(results.getDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateToday = new Date(); //
                DateNow = sdf.format(dateToday);
                try {
                    Date date1 = sdf.parse(results.getDate());
                    DateReservstion = sdf.format(date1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (DateReservstion.compareTo(DateNow) >= 0) {
                    holder.discounts.setText(R.string.upcom);
                    holder.discounts.setBackgroundResource(R.color.yellowcolor);
                } else {
                    holder.discounts.setText(R.string.past);

                }
            }

            //   if (results.getImages().get(0).getLink() == null) {
            if (!results.getTrip().getImages().isEmpty()) {

                if (results.getTrip().getImages().get(0).getPath() != null) {
                    Picasso.with(mCtx)
                            .load(Constants.mainLink + results.getTrip().getImages().get(0).getPath())
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(holder.img);
                } else if (results.getTrip().getImages().get(0).getLink() != null) {
                    Picasso.with(mCtx)
                            .load(results.getTrip().getImages().get(0).getLink())
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(holder.img);
                } else {
                    Picasso.with(mCtx)
                            .load(Constants.mainLink + results.getTrip().getImages().get(0).getPath())
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(holder.img);
                }

            }

            holder.cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // ignore all touch events
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title, discounts, Tchild, Tadult, Tinfant, Ttotal, Tdate;
        ImageView img;
        LinearLayout cardView;

        public FilmsViewHolder(View itemview) {
            super(itemview);
            title = itemView.findViewById(R.id.offtxt);
//            Tchild = itemView.findViewById(R.id.tchild);
//            Tadult = itemView.findViewById(R.id.tadult);
//            Tinfant = itemView.findViewById(R.id.tinfant);
            Ttotal = itemView.findViewById(R.id.destxe);
            Tdate = itemView.findViewById(R.id.datet);
            discounts = itemView.findViewById(R.id.discount);
            img = itemView.findViewById(R.id.image1);
            cardView = itemView.findViewById(R.id.crd);
        }
    }
}
