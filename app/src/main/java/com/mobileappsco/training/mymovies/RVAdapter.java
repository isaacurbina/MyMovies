package com.mobileappsco.training.mymovies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by admin on 3/7/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CinematicViewHolder>{

    List<Cinematic> cinematics;
    Context context;
    Helpers helper = new Helpers();

    public RVAdapter(Context context, List<Cinematic> cinematics){
        this.context = context;
        this.cinematics = cinematics;
    }

    @Override
    public CinematicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CinematicViewHolder cvh = new CinematicViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CinematicViewHolder holder, int i) {
        holder.card_title.setText(cinematics.get(i).title);
        holder.card_vote_average.setText(Double.toString(cinematics.get(i).vote_average));
        holder.card_overview.setText(cinematics.get(i).overview);
        Glide.with(context).load(AppController.apiImage+cinematics.get(i).poster_path).into(holder.card_poster);
        //helper.logAndToast(context, "Glide loading: "+apiImage+cinematics.get(i), Log.INFO);
    }

    @Override
    public int getItemCount() {
        return cinematics.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CinematicViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView card_poster;
        TextView card_title;
        TextView card_vote_average;
        TextView card_overview;

        CinematicViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
            card_poster = (ImageView)itemView.findViewById(R.id.card_poster);
            card_title = (TextView)itemView.findViewById(R.id.card_title);
            card_vote_average = (TextView)itemView.findViewById(R.id.card_vote_average);
            card_overview = (TextView)itemView.findViewById(R.id.card_overview);
        }
    }

}
