package com.example.submission3.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission3.R;
import com.example.submission3.details.TVShowsDetail;
import com.example.submission3.models.TVShowsData;

import java.util.ArrayList;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsCardViewHolder> {

    private ArrayList<TVShowsData> tvShowsData = new ArrayList<>();
    private Context context;

    public TVShowsAdapter(Context context) {
        this.context = context;
    }

    public TVShowsAdapter(ArrayList<TVShowsData> tvShowsData, Context context) {
        this.tvShowsData = tvShowsData;
        this.context = context;
    }

    public void setTvShowsData(ArrayList<TVShowsData> itemTvShowsData) {
        tvShowsData.clear();
        tvShowsData.addAll(itemTvShowsData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowsAdapter.TVShowsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_tvshows_list, viewGroup, false);
        return new TVShowsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsAdapter.TVShowsCardViewHolder tvShowsCardViewHolder, int i) {
        tvShowsCardViewHolder.bind(tvShowsData.get(i));
    }

    @Override
    public int getItemCount() {
        return tvShowsData.size();
    }

    public class TVShowsCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgTvShow;
        TextView tvTvShowName;
        TextView tvTvShowRelease;
        TextView tvTvShowDesc;

        TVShowsCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTvShow = itemView.findViewById(R.id.img_poster_tvshow);
            tvTvShowName = itemView.findViewById(R.id.tv_title_tvshow);
            tvTvShowRelease = itemView.findViewById(R.id.tv_release_tvshow);
            tvTvShowDesc = itemView.findViewById(R.id.tv_overview_tvshow);

            itemView.setOnClickListener(this);
        }

        void bind(TVShowsData tvData) {
            tvTvShowName.setText(tvData.getTitle());
            tvTvShowRelease.setText(tvData.getReleaseDate());
            tvTvShowDesc.setText(tvData.getOverview());
            Glide.with(itemView).load(tvData.getPosterPath())
                    .into(imgTvShow);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TVShowsData tvShowData = tvShowsData.get(position);

            tvShowData.setTitle(tvShowData.getTitle());
            tvShowData.setOverview(tvShowData.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TVShowsDetail.class);
            moveWithObjectIntent.putExtra(TVShowsDetail.EXTRA_TVSHOWS, tvShowData);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
