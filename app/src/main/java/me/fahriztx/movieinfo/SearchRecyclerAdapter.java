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
import me.fahriztx.movieinfo.Models.SearchResults;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchHolder>{

    private List<SearchResults> searchResults;

    public SearchRecyclerAdapter(List<SearchResults> results) {
        this.searchResults = results;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_horizontal, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.bind(searchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_image_hr) ImageView imageView;
        @BindView(R.id.list_title_hr) TextView textView;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final SearchResults searchResults){

            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500"+searchResults.getPosterPath()).into(imageView);
            textView.setText(searchResults.getOriginalTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra("MOVIE_ID", searchResults.getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
