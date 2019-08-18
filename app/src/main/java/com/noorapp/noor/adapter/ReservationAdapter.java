package com.noorapp.noor.adapter;import android.content.Context;import android.content.Intent;import android.graphics.Paint;import android.support.annotation.NonNull;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.TextView;import com.noorapp.noor.Constants;import com.noorapp.noor.Details_Activity;import com.noorapp.noor.R;import com.noorapp.noor.models.ReservationsModel.Place;import com.squareup.picasso.Picasso;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Date;import java.util.List;public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.FilmsViewHolder> {    private Context mCtx;    private List<Place> placeList;    public ReservationAdapter(Context mCtx, List<Place> filmList) {        this.mCtx = mCtx;        this.placeList = filmList;    }    @NonNull    @Override    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card, parent, false);        return new FilmsViewHolder(view);    }    @Override    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {        final Place results = placeList.get(position);        if (!placeList.isEmpty()) {            if (!results.getPlace().getTranslations().isEmpty()) {                holder.title.setText(results.getPlace().getTranslations().get(0).getName());            }            if (!results.getPlace().getCurrency().getTranslations().isEmpty()) {                if (results.getPlace().getCurrency().getTranslations().get(0).getIso() != null) {                    holder.Ttotal.setText(results.getAmount() + " " + results.getPlace().getCurrency().getTranslations().get(0).getIso());                }            }            if (results.getDate() != null) {                String DateNow, DateReservstion = null;                holder.Tdate.setText(results.getDate());                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");                Date dateToday = new Date(); //                DateNow = sdf.format(dateToday);                try {                    Date date1 = sdf.parse(results.getDate());                    DateReservstion = sdf.format(date1);                } catch (ParseException e) {                    e.printStackTrace();                }                if (DateReservstion.compareTo(DateNow) >= 0) {                    holder.discounts.setText(R.string.upcom);                    holder.discounts.setBackgroundResource(R.color.yellowcolor);                } else {                    holder.discounts.setText(R.string.past);                    holder.discounts.setBackgroundResource(R.color.colorAccent);                }            }            if (!results.getPlace().getImages().isEmpty()) {                if (results.getPlace().getImages().get(0).getPath() != null) {                    Picasso.with(mCtx)                            .load(Constants.mainLink + results.getPlace().getImages().get(0).getPath())                            .placeholder(R.drawable.placeholder)                            .error(R.drawable.placeholder)                            .into(holder.img);                } else if (results.getPlace().getImages().get(0).getLink() != null) {                    Picasso.with(mCtx)                            .load( results.getPlace().getImages().get(0).getLink())                            .placeholder(R.drawable.placeholder)                            .error(R.drawable.placeholder)                            .into(holder.img);                } else {                    Picasso.with(mCtx)                            .load(Constants.mainLink + results.getPlace().getImages().get(0).getLink())                            .placeholder(R.drawable.placeholder)                            .error(R.drawable.placeholder)                            .into(holder.img);                }            }            holder.cardView.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View v) {                   /* Log.e("placeidd", String.valueOf(results.getId()));                    Intent intent = new Intent(mCtx, Details_Activity.class);                    intent.putExtra("placeID", results.getId());                    mCtx.startActivity(intent);*/                }            });        }       /* holder.title.setText(results.getTitle());        holder.poster_path.setText(results.getPoster_path());        holder.original_title.setText(results.getOriginal_language());*/    }    @Override    public int getItemViewType(int position) {        return position;    }    @Override    public int getItemCount() {        return placeList.size();    }    public class FilmsViewHolder extends RecyclerView.ViewHolder {        TextView title, discounts, Tchild, Tadult, Tinfant, Ttotal, Tdate;        ImageView img;        LinearLayout cardView;        public FilmsViewHolder(View itemview) {            super(itemview);            title = itemView.findViewById(R.id.offtxt);            Ttotal = itemView.findViewById(R.id.destxe);            Tdate = itemView.findViewById(R.id.datet);            discounts = itemView.findViewById(R.id.discount);            img = itemView.findViewById(R.id.image1);            cardView = itemView.findViewById(R.id.crd);        }    }}