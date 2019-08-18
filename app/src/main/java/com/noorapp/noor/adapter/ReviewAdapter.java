package com.noorapp.noor.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noorapp.noor.Constants;
import com.noorapp.noor.R;
import com.noorapp.noor.models.PlacesDetailsModel.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.FilmsViewHolder> {

    private Context mCtx;
    private List<Review> placeList;
    private  int widthss;

    public ReviewAdapter(Context mCtx, List<Review> filmList,int widthsizs) {
        this.mCtx = mCtx;
        this.placeList = filmList;
        this.widthss=widthsizs;
    }

    @NonNull
    @Override
    public ReviewAdapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);
        return new ReviewAdapter.FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.FilmsViewHolder holder, int position) {

        // final Place results = placeList.get(position);
        holder.rate.setText(placeList.get(position).getReview());
        holder.name.setText(placeList.get(position).getUser().getUsername());
        holder.date.setText(placeList.get(position).getCreatedAt());
        holder.vote.setRating(Integer.parseInt(placeList.get(position).getVotes()));
        // mRatingBar.setRating(int)
        //  holder..setText(placeList.get(position).getUser().getUsername());


        // holder.desc.setText("Welcome to the BMW Group. Our focus on the premium idea and the principle of sustainability appeals to people worldwide.");
        if (!placeList.get(position).getUser().getAvatar().isEmpty()){
            Picasso.with(mCtx)
                    .load(Constants.mainLink + placeList.get(position).getUser().getAvatar())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgreview);
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
        TextView rate, name, date, desc;
        RatingBar vote;
        ImageView imgreview;
        RelativeLayout relativeLayout;

        public FilmsViewHolder(View itemview) {
            super(itemview);

            rate = itemView.findViewById(R.id.txtreviewnum);
            name = itemView.findViewById(R.id.namereview);
            date = itemView.findViewById(R.id.datereview);
            //desc = itemView.findViewById(R.id.descreview);
            vote = itemView.findViewById(R.id.ratingBar);
            imgreview = itemView.findViewById(R.id.imagereview);
            relativeLayout = itemview.findViewById(R.id.mRelative);



//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            ((AppCompatActivity)mCtx).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int quarterWidth = displayMetrics.widthPixels/12;

            relativeLayout.getLayoutParams().width = widthss- 160;
        }
    }


}
