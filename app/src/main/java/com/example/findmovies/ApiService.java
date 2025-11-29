package com.example.findmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movies")
    Call<MovieApiResponse> getMovies(@Query("page") int page);
}
