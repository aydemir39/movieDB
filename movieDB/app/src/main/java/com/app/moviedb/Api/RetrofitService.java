package com.app.moviedb.Api;


import com.app.moviedb.Pojo.CastCrewResponse;
import com.app.moviedb.Pojo.Detail;
import com.app.moviedb.Pojo.MovieResponse;
import com.app.moviedb.Pojo.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPupularMovies(@Query("api_key") String apiKey);

    //-------------------------------------//
    @GET("tv/top_rated")
    Call<TvResponse> getTopRatedTv(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvResponse> getPopularTv(@Query("api_key") String apiKey);


    //-------------------------------------//
    @GET("{type}/{id}")
    Call<Detail> getDetail(@Path("type") String type, @Path("id") int id, @Query("api_key") String apiKey);

    @GET("{type}/{id}/credits")
    Call<CastCrewResponse> getMovieCastCrew(@Path("type") String type, @Path("id") int id, @Query("api_key") String apiKey);

}
