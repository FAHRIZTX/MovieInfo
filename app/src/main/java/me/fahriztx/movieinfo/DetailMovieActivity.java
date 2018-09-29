package me.fahriztx.movieinfo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fahriztx.movieinfo.Api.ApiServices;
import me.fahriztx.movieinfo.Api.BaseApi;
import me.fahriztx.movieinfo.Models.GenresItem;
import me.fahriztx.movieinfo.Models.GetMovieModel;
import me.fahriztx.movieinfo.Models.ProductionCompaniesItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.img_dm) ImageView imageView;
    @BindView(R.id.movie_bar) Toolbar toolbar;
    @BindView(R.id.title_dm) TextView txtTitle;
    @BindView(R.id.desc_dm) TextView txtDesc;
    @BindView(R.id.rl_detail) RelativeLayout relativeLayout;
    @BindView(R.id.pg_detail) ProgressBar progressBar;
    @BindView(R.id.genre_dm) TextView txtGenre;
    @BindView(R.id.companies_dm) TextView txtCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.TDetailBackgroundDark));

        final int movie_id = getIntent().getIntExtra("MOVIE_ID",0);

        ApiServices services = new BaseApi().init();
        try{
            services.getMovieResults(movie_id,"8a8becf78d444856d44964d686aafe1a")
                    .enqueue(new Callback<GetMovieModel>() {
                        @Override
                        public void onResponse(@NonNull Call<GetMovieModel> call, @NonNull Response<GetMovieModel> response) {
                            GetMovieModel movie = response.body();
                            progressBar.setVisibility(View.GONE);
                            if( movie != null ){

                                StringBuilder genres = new StringBuilder();
                                StringBuilder companies = new StringBuilder("Companies : ");
                                for (GenresItem genre : movie.getGenres()){
                                    genres.append(genre.getName()+", ");
                                }

                                for (ProductionCompaniesItem comp : movie.getProductionCompanies()){
                                    companies.append(comp.getName()+", ");
                                }

                                relativeLayout.setBackgroundColor(Color.parseColor("#1b1b1b"));
                                Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath()).into(imageView);
                                toolbar.setTitle(movie.getOriginalTitle());
                                txtTitle.setText(movie.getOriginalTitle());
                                txtDesc.setText(movie.getOverview());
                                txtGenre.setText(genres.substring(0,genres.length()-2));
                                txtCompanies.setText(companies.substring(0, companies.length()-2));
                            }else{
                                Toast.makeText(DetailMovieActivity.this, "Failed Get Data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetMovieModel> call, @NonNull Throwable t) {
                            Toast.makeText(DetailMovieActivity.this, "Check Your Connection", Toast.LENGTH_SHORT).show();

                        }
                    });
        }catch (Exception error){

        }
    }
}
