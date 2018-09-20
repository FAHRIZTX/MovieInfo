package me.fahriztx.movieinfo;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fahriztx.movieinfo.Api.ApiServices;
import me.fahriztx.movieinfo.Api.BaseApi;
import me.fahriztx.movieinfo.Models.GetSearchModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    @BindView(R.id.rc_search) RecyclerView recyclerView;
    @BindView(R.id.search_bar) Toolbar toolbar;
    @BindView(R.id.rf_search) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pg_search) ProgressBar progressBar;
    @BindView(R.id.rl_search) RelativeLayout relativeLayout;

    private SearchRecyclerAdapter recyclerAdapter;
    private String _query = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        if (_query == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            _query = simpleDateFormat.format(date).substring(0, 4);
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.TSearchBackgroundDark));

        getData(_query, true);

        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"), Color.parseColor("#C90000"), Color.parseColor("#FFC800"), Color.parseColor("#0FB700"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(_query, false);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setQueryHint("Search");

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                _query = query;
                getData(_query, true);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getData(String query, final Boolean showLoad) {
        ApiServices services = new BaseApi().init();
        if (showLoad) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            MainActivity.setBackgroundColor("#FFFFFF");
        }
        try{
            services.getSearchResults(query, "8a8becf78d444856d44964d686aafe1a")
                    .enqueue(new Callback<GetSearchModel>() {
                        @Override
                        public void onResponse(@NonNull Call<GetSearchModel> call, @NonNull Response<GetSearchModel> response) {
                            GetSearchModel search = response.body();

                            swipeRefreshLayout.setRefreshing(false);

                            if (showLoad) {
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setBackgroundColor(Color.parseColor("#1b1b1b"));
                                MainActivity.setBackgroundColor("#1b1b1b");
                            }

                            if (search != null) {
                                recyclerAdapter = new SearchRecyclerAdapter(search.getResults());
                                recyclerView.setAdapter(recyclerAdapter);
                            } else {
                                Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetSearchModel> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Check Your Connection!!!", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
        }catch (Exception error){

        }
    }

}
