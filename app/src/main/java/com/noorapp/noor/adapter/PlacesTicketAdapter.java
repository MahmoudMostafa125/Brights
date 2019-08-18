package com.noorapp.noor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noorapp.noor.R;
import com.noorapp.noor.models.Ticket;

import java.util.List;


public class PlacesTicketAdapter extends RecyclerView.Adapter<PlacesTicketAdapter.FilmsViewHolder> {

  private Context mCtx;
  private List<Ticket> placeTicketsList;

  public PlacesTicketAdapter(Context mCtx, List<Ticket> filmList) {
    this.mCtx = mCtx;
    this.placeTicketsList = filmList;
  }

  @NonNull
  @Override
  public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);
    return new FilmsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
    Ticket results = placeTicketsList.get(position);
    holder.title.setText(results.getId());
  }

  @Override
  public int getItemCount() {
    return placeTicketsList.size();
  }

  public class FilmsViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    ImageView img;


    public FilmsViewHolder(View itemview) {
      super(itemview);

      title = itemView.findViewById(R.id.offtxt);
      img = itemView.findViewById(R.id.image1);
    }
  }
}
