package me.fahriztx.movieinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.home) ImageButton btnHome;
    @BindView(R.id.search) ImageButton btnSearch;
    @BindView(R.id.trending) ImageButton btnTrending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.home_bar));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frLayout, new HomeFragment())
                .commit();
    }

    @OnClick(R.id.home)
    public void onBtnHomeClick(View view){
        btnHome.setImageResource(R.drawable.home_black);
        btnSearch.setImageResource(R.drawable.search_stroke);
        btnTrending.setImageResource(R.drawable.trending_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.home_bar));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frLayout, new HomeFragment())
                .commit();
    }
    @OnClick(R.id.search)
    public void onBtnSearchClick(View view){
        btnSearch.setImageResource(R.drawable.search_black);
        btnHome.setImageResource(R.drawable.home_stroke);
        btnTrending.setImageResource(R.drawable.trending_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.search_bar));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frLayout, new SearchFragment())
                .commit();
    }
    @OnClick(R.id.trending)
    public void onBtnTrendingClick(View view){
        btnTrending.setImageResource(R.drawable.trending_black);
        btnHome.setImageResource(R.drawable.home_stroke);
        btnSearch.setImageResource(R.drawable.search_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.trending_bar));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frLayout, new TrendingFragment())
                .commit();
    }

}
