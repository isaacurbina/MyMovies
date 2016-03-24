package com.mobileappsco.training.mymovies.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobileappsco.training.mymovies.Entities.Result;
import com.mobileappsco.training.mymovies.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ResultViewHolder>  {

    List<Result> results;
    Context context;
    String API_IMAGE_URL;

    public RVAdapter(Context context, List<Result> results){
        this.context = context;
        this.results = results;
        API_IMAGE_URL = context.getString(R.string.API_IMAGES_URL);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ResultViewHolder cvh = new ResultViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int i) {
        holder.card_title.setText(results.get(i).getTitle());
        holder.card_vote_average.setText(Double.toString(results.get(i).getVoteAverage()));
        holder.card_overview.setText(results.get(i).getOverview());
        holder.card_id.setText(String.valueOf(results.get(i).getId()));
        Glide.with(context)
                .load(API_IMAGE_URL + results.get(i).getPosterPath())
                .into(holder.card_poster);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addResult(Result result) {
        results.add(result);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addResultList(List<Result> list) {
        if (results == null)
            results = list;
        else
            results.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView card_poster;
        TextView card_title;
        TextView card_vote_average;
        TextView card_overview;
        TextView card_id;

        ResultViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
            card_poster = (ImageView)itemView.findViewById(R.id.card_poster);
            card_title = (TextView)itemView.findViewById(R.id.card_title);
            card_vote_average = (TextView)itemView.findViewById(R.id.card_vote_average);
            card_overview = (TextView)itemView.findViewById(R.id.card_overview);
            card_id = (TextView)itemView.findViewById(R.id.card_id);
        }

    }

}
