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
import com.example.submission3.FavoritActivity;
import com.example.submission3.R;
import com.example.submission3.details.TVShowsDetail;
import com.example.submission3.models.TVShowsData;

import java.util.ArrayList;

import static com.example.submission3.CustomApplication.db;

public class TVShowsFavAdapter extends RecyclerView.Adapter<TVShowsFavAdapter.TVShowsCardViewHolder> {
    private Context context;
    private ArrayList<TVShowsData> tvShowsData;

    public TVShowsFavAdapter(Context context, ArrayList<TVShowsData> tvShowsData) {
        this.context = context;
        this.tvShowsData = tvShowsData;
    }

    @NonNull
    @Override
    public TVShowsFavAdapter.TVShowsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_tvshows_favolist, viewGroup, false);
        return new TVShowsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsFavAdapter.TVShowsCardViewHolder tvShowsCardViewHolder, final int i) {
        tvShowsCardViewHolder.bind(tvShowsData.get(i));

        tvShowsCardViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.dao().delTvshowFav(tvShowsData.get(i).getId());
                context.startActivity(new Intent(context, FavoritActivity.class));
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowsData.size();
    }

    public class TVShowsCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgTvShow, delete;
        TextView tvTvShowName;
        TextView tvTvShowRelease;
        TextView tvTvShowDesc;

        TVShowsCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTvShow = itemView.findViewById(R.id.img_poster_favtvshow);
            tvTvShowName = itemView.findViewById(R.id.tv_title_favtvshow);
            tvTvShowRelease = itemView.findViewById(R.id.tv_release_favtvshow);
            tvTvShowDesc = itemView.findViewById(R.id.tv_overview_favtvshow);
            delete = itemView.findViewById(R.id.delete);

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
