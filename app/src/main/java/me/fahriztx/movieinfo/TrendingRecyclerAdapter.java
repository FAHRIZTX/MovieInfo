package me.fahriztx.movieinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fahriztx.movieinfo.Models.TrendingResults;

public class TrendingRecyclerAdapter extends RecyclerView.Adapter<TrendingRecyclerAdapter.TrendingHolder>{

    List<TrendingResults> trendingResults;

    public TrendingRecyclerAdapter(List<TrendingResults> list) {
        this.trendingResults = list;
    }

    @NonNull
    @Override
    public TrendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_trending_list, parent, false);
        return new TrendingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingHolder holder, int position) {
        holder.bind(trendingResults.get(position), position);
    }

    @Override
    public int getItemCount() {
        return trendingResults.size();
    }

    class TrendingHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tr) TextView trView;
        @BindView(R.id.list_image_tr) ImageView imageView;
        @BindView(R.id.list_title_tr) TextView titleText;

        public TrendingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TrendingResults trendingResults, int i){

            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500"+trendingResults.getPosterPath()).into(imageView);
            titleText.setText(trendingResults.getOriginalTitle());
            trView.setText(String.valueOf(i+1));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra("MOVIE_ID", trendingResults.getId());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
