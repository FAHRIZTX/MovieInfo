package me.fahriztx.movieinfo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import me.fahriztx.movieinfo.Models.GetHomeModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @BindView(R.id.rc_home) RecyclerView recyclerView;
    @BindView(R.id.rf_home) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pg_home) ProgressBar progressBar;
    @BindView(R.id.rl_home) RelativeLayout relativeLayout;

    private HomeRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        getData(true);
        super.onCreate(savedInstanceState);

        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"), Color.parseColor("#C90000"), Color.parseColor("#FFC800"), Color.parseColor("#0FB700"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false);
            }
        });
        return view;
    }

    private void getData(final Boolean showLoad){

        if (showLoad) {
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            MainActivity.setBackgroundColor("#FFFFFF");
        }

        ApiServices service = new BaseApi().init();
        try{
            service
                    .getHomeResults("8a8becf78d444856d44964d686aafe1a", "popularity.desc")
                    .enqueue(new Callback<GetHomeModel>() {
                        @Override
                        public void onResponse(@NonNull Call<GetHomeModel> call, @NonNull Response<GetHomeModel> response) {
                            GetHomeModel home = response.body();
                            swipeRefreshLayout.setRefreshing(false);
                            if (showLoad) {
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setBackgroundColor(Color.parseColor("#1b1b1b"));
                                MainActivity.setBackgroundColor("#1b1b1b");
                            }
                            if( home != null ){
                                recyclerAdapter = new HomeRecyclerAdapter(home.getResults());
                                recyclerView.setAdapter(recyclerAdapter);
                            }else{
                                Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetHomeModel> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Check Your Connection!!!", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
        }catch (Exception error){

        }
    }
}
