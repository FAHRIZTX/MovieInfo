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
import me.fahriztx.movieinfo.Models.HomeResults;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeHolder>{

    private List<HomeResults> homeResults;

    public HomeRecyclerAdapter(List<HomeResults> results) {
        this.homeResults = results;
    }

    @NonNull
    @Override

    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);

        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.bind(homeResults.get(position));
    }

    @Override
    public int getItemCount() {
        return homeResults.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_image) ImageView imageView;
        @BindView(R.id.list_title) TextView textView;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final HomeResults homeResults){

            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500"+homeResults.getPosterPath()).into(imageView);
            textView.setText(homeResults.getOriginalTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra("MOVIE_ID", homeResults.getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
