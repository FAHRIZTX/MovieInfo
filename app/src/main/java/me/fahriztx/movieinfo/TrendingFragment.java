package me.fahriztx.movieinfo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.fahriztx.movieinfo.Api.ApiServices;
import me.fahriztx.movieinfo.Api.BaseApi;
import me.fahriztx.movieinfo.Models.GetTrendingModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment {

    @BindView(R.id.rc_trending) RecyclerView recyclerView;
    @BindView(R.id.rf_trending) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pg_trending) ProgressBar progressBar;
    @BindView(R.id.rl_trending) RelativeLayout relativeLayout;

    private TrendingRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"), Color.parseColor("#C90000"), Color.parseColor("#FFC800"), Color.parseColor("#0FB700"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false);
            }
        });

        getData(true);

        return view;
    }

    private void getData(final Boolean showLoad) {
        ApiServices services = new BaseApi().init();
        if (showLoad) {
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            MainActivity.setBackgroundColor("#FFFFFF");
        }
        try{
            services.getTrendingResults("8a8becf78d444856d44964d686aafe1a")
                    .enqueue(new Callback<GetTrendingModel>() {
                        @Override
                        public void onResponse(@NonNull Call<GetTrendingModel> call, @NonNull Response<GetTrendingModel> response) {
                            GetTrendingModel search = response.body();

                            swipeRefreshLayout.setRefreshing(false);

                            if (showLoad) {
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setBackgroundColor(Color.parseColor("#1b1b1b"));
                                MainActivity.setBackgroundColor("#1b1b1b");
                            }

                            if (search != null) {
                                recyclerAdapter = new TrendingRecyclerAdapter(search.getResults());
                                recyclerView.setAdapter(recyclerAdapter);
                            } else {
                                Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetTrendingModel> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Check Your Connection!!!", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
        }catch (Exception error){

        }
    }
}
