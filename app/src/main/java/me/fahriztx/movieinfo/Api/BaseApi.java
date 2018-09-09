package me.fahriztx.movieinfo.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {

    public ApiServices init(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .build();

        ApiServices service = retrofit.create(ApiServices.class);
        return service;
    }

}
