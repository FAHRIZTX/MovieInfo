package me.fahriztx.movieinfo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.home) ImageButton btnHome;
    @BindView(R.id.search) ImageButton btnSearch;
    @BindView(R.id.trending) ImageButton btnTrending;

    static LinearLayout linearLayout;

    FragmentTransaction ft;

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    TrendingFragment trendingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.home_bar));
        ft = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            searchFragment = new SearchFragment();
            trendingFragment = new TrendingFragment();
        }

        if (homeFragment.isAdded()) {
            ft.show(homeFragment);
        } else {
            ft.add(R.id.frLayout, homeFragment);
        }
        if (searchFragment.isAdded()) { ft.hide(searchFragment);}
        if (trendingFragment.isAdded()) { ft.hide(trendingFragment);}
        ft.commit();

        linearLayout = findViewById(R.id.container);
    }

    @OnClick(R.id.home)
    public void onBtnHomeClick(View view){
        btnHome.setImageResource(R.drawable.home_black);
        btnSearch.setImageResource(R.drawable.search_stroke);
        btnTrending.setImageResource(R.drawable.trending_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.home_bar));
        ft = getSupportFragmentManager().beginTransaction();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.THomeBackgroundDark));

        if (homeFragment.isAdded()) {
            ft.show(homeFragment);
        } else {
            ft.add(R.id.frLayout, homeFragment);
        }
        if (searchFragment.isAdded()) { ft.hide(searchFragment);}
        if (trendingFragment.isAdded()) { ft.hide(trendingFragment);}
        ft.commit();
    }
    @OnClick(R.id.search)
    public void onBtnSearchClick(View view){
        btnSearch.setImageResource(R.drawable.search_black);
        btnHome.setImageResource(R.drawable.home_stroke);
        btnTrending.setImageResource(R.drawable.trending_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.search_bar));
        ft = getSupportFragmentManager().beginTransaction();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.TSearchBackgroundDark));

        if (searchFragment.isAdded()) {
            ft.show(searchFragment);
        } else {
            ft.add(R.id.frLayout, searchFragment);
        }
        if (homeFragment.isAdded()) { ft.hide(homeFragment);}
        if (trendingFragment.isAdded()) { ft.hide(trendingFragment);}
        ft.commit();
    }
    @OnClick(R.id.trending)
    public void onBtnTrendingClick(View view){
        btnTrending.setImageResource(R.drawable.trending_black);
        btnHome.setImageResource(R.drawable.home_stroke);
        btnSearch.setImageResource(R.drawable.search_stroke);

        setSupportActionBar((Toolbar) findViewById(R.id.trending_bar));
        ft = getSupportFragmentManager().beginTransaction();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.TTrandingBackgroundDark));

        if (trendingFragment.isAdded()) {
            ft.show(trendingFragment);
        } else {
            ft.add(R.id.frLayout, trendingFragment);
        }
        if (homeFragment.isAdded()) { ft.hide(homeFragment);}
        if (searchFragment.isAdded()) { ft.hide(searchFragment);}
        ft.commit();
    }

    public static void setBackgroundColor(String color){
        linearLayout.setBackgroundColor(Color.parseColor(color));
    }

}
