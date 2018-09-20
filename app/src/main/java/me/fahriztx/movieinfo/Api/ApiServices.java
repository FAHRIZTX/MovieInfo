package me.fahriztx.movieinfo.Api;


import me.fahriztx.movieinfo.Models.GetHomeModel;
import me.fahriztx.movieinfo.Models.GetMovieModel;
import me.fahriztx.movieinfo.Models.GetSearchModel;
import me.fahriztx.movieinfo.Models.GetTrendingModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("/3/discover/movie")
    Call<GetHomeModel> getHomeResults(@Query("api_key") String api_key, @Query("sort_by") String sort_by);

    @GET("/3/movie/{movie_id}")
    Call<GetMovieModel> getMovieResults(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("/3/search/movie")
    Call<GetSearchModel> getSearchResults(@Query("query") String query, @Query("api_key") String api_key);

    @GET("/3/trending/movie/week")
    Call<GetTrendingModel> getTrendingResults(@Query("api_key") String api_key);

}