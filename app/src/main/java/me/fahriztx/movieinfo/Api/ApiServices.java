package me.fahriztx.movieinfo.Api;


import me.fahriztx.movieinfo.Models.GetHomeModel;
import me.fahriztx.movieinfo.Models.GetMovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("/3/discover/movie")
    Call<GetHomeModel> getHomeResults(@Query("api_key") String api_key);

    @GET("/3/movie/{movie_id}")
    Call<GetMovieModel> getMovieResults(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("/3/search/movie")
    Call<GetMovieModel> getSearchResults(@Query("query") String query, @Query("api_key") String api_key);

}