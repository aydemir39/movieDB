package com.app.moviedb.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.moviedb.Adapter.MovieAdapter;
import com.app.moviedb.Api.RetrofitApiClient;
import com.app.moviedb.Api.RetrofitService;
import com.app.moviedb.Pojo.MovieResponse;
import com.app.moviedb.R;
import com.app.moviedb.Utils.SpaceItemDecoration;
import com.app.moviedb.databinding.FragmentMoviesBinding;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding fragmentMoviesBinding;
    private View view;
    private MovieAdapter movieAdapterTopRatedMovies, movieAdapterNowPlaying, movieAdapterPopular;
    private String API_KEY = "d0c67ddbb6f821fb634fa40880d135bf";

    public MoviesFragment() {
        // Required empty public constructor
    }


    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        view = fragmentMoviesBinding.getRoot();
        init();
        getTopRatedMovies();
        getNowPlaying();
        getPopular();
        return view;
    }
    /////--------------------------------------------------------------------------------------/////
    protected void init() {

        fragmentMoviesBinding.recyclerVTopRatedMoviesFrMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMoviesBinding.recyclerVTopRatedMoviesFrMovies.addItemDecoration(new SpaceItemDecoration(48));
        movieAdapterTopRatedMovies = new MovieAdapter(getActivity(), 2, false, new RequestOptions());
        fragmentMoviesBinding.recyclerVTopRatedMoviesFrMovies.setAdapter(movieAdapterTopRatedMovies);

        fragmentMoviesBinding.recyclerVNowPlayingFrMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMoviesBinding.recyclerVNowPlayingFrMovies.addItemDecoration(new SpaceItemDecoration(48));
        movieAdapterNowPlaying = new MovieAdapter(getActivity(), 1, false, new RequestOptions());
        fragmentMoviesBinding.recyclerVNowPlayingFrMovies.setAdapter(movieAdapterNowPlaying);

        fragmentMoviesBinding.recyclerVPopularFrMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMoviesBinding.recyclerVPopularFrMovies.addItemDecoration(new SpaceItemDecoration(48));
        movieAdapterPopular = new MovieAdapter(getActivity(), 1, true, new RequestOptions());
        fragmentMoviesBinding.recyclerVPopularFrMovies.setAdapter(movieAdapterPopular);
    }

    protected void getTopRatedMovies() {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<MovieResponse> call = retrofitService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    response.body().getResults();
                    movieAdapterTopRatedMovies.addList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }

    protected void getNowPlaying() {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<MovieResponse> call = retrofitService.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    response.body().getResults();
                    movieAdapterNowPlaying.addList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

    }

    protected void getPopular() {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<MovieResponse> call = retrofitService.getPupularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {

                    response.body().getResults();
                    movieAdapterPopular.addList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

    }
}
