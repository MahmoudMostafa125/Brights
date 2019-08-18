package com.noorapp.noor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noorapp.noor.Constants;
import com.noorapp.noor.Guide_Places_Activity;
import com.noorapp.noor.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlacesAdapter2 extends RecyclerView.Adapter<PlacesAdapter2.FilmsViewHolder> {

    private Context mCtx;
    private List<com.noorapp.noor.models.GuideModel.Guide> guideList;
    private int sizewidth;

    public PlacesAdapter2(Context mCtx, List<com.noorapp.noor.models.GuideModel.Guide> filmList, int widthsize) {
        this.mCtx = mCtx;
        this.guideList = filmList;
        this.sizewidth = widthsize;
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_plases_card2, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.35);
        layoutParams.height = (int) (parent.getWidth() * 0.40);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {

        final com.noorapp.noor.models.GuideModel.Guide results = guideList.get(position);
        if (!results.getTranslations().isEmpty()) {

            holder.title.setText(results.getTranslations().get(0).getName());
            //  if (results.getPlaces().get(0).getImages().get(0).getLink() == null) {
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
//            Log.e("Link", results.getPlaces().get(0).getImages().get(0).getLink());
       /* } else {
            Picasso.with(mCtx)
                    .load(results.getPlaces().get(0).getImages().get(0).getLink())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.noorimg)
                    .into(holder.img);
        }*/
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("placeidd", String.valueOf(results.getId()));
                    Intent intent = new Intent(mCtx, Guide_Places_Activity.class);
                    intent.putExtra("placeID", results.getId());
                    intent.putExtra("placename", results.getTranslations().get(0).getName());
                    mCtx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return guideList.size();
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        LinearLayout linn;

        public FilmsViewHolder(View itemview) {
            super(itemview);
            title = itemView.findViewById(R.id.titleplace);
            img = itemView.findViewById(R.id.image1);
            linn = itemView.findViewById(R.id.lin);

           // linn.getLayoutParams().width = sizewidth / 3;
        }
    }
}
