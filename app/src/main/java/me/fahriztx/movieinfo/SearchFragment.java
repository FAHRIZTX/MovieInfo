package me.fahriztx.movieinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fahriztx.movieinfo.Api.ApiServices;
import me.fahriztx.movieinfo.Api.BaseApi;
import me.fahriztx.movieinfo.Models.GetMovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    @BindView(R.id.rc_search) RecyclerView recyclerView;
    private HomeRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        ApiServices services = new BaseApi().init();
        services.getSearchResults("anvengers","8a8becf78d444856d44964d686aafe1a")
                .enqueue(new Callback<GetMovieModel>() {
                    @Override
                    public void onResponse(Call<GetMovieModel> call, Response<GetMovieModel> response) {

                    }

                    @Override
                    public void onFailure(Call<GetMovieModel> call, Throwable t) {

                    }
                });

        return view;
    }

}
