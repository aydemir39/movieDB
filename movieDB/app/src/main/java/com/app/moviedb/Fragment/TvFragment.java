package com.app.moviedb.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.moviedb.Adapter.TvAdapter;
import com.app.moviedb.Api.RetrofitApiClient;
import com.app.moviedb.Api.RetrofitService;
import com.app.moviedb.Pojo.TvResponse;
import com.app.moviedb.R;
import com.app.moviedb.Utils.SpaceItemDecoration;
import com.app.moviedb.databinding.FragmentTvBinding;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TvFragment extends Fragment {

    private FragmentTvBinding fragmentTvBinding;
    private View view;
    private TvAdapter tvAdapterTopRatedTv, tvAdapterPopular;
    private String API_KEY = "d0c67ddbb6f821fb634fa40880d135bf";

    public TvFragment() {
        // Required empty public constructor
    }

    public static TvFragment newInstance() {
        return new TvFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTvBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv, container, false);
        view = fragmentTvBinding.getRoot();
        init();
        getTopRatedTvSeries();
        getPopular();
        return view;
    }
    /////------------------------------------------------------------------------------------/////

    protected void init() {

        fragmentTvBinding.recyclerVTopRatedTvSeriesFrTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentTvBinding.recyclerVTopRatedTvSeriesFrTv.addItemDecoration(new SpaceItemDecoration(48));
        tvAdapterTopRatedTv = new TvAdapter(getActivity(), 1, new RequestOptions());
        fragmentTvBinding.recyclerVTopRatedTvSeriesFrTv.setAdapter(tvAdapterTopRatedTv);

        fragmentTvBinding.recyclerVPopularFrTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tvAdapterPopular = new TvAdapter(getActivity(), 2, new RequestOptions());
        fragmentTvBinding.recyclerVPopularFrTv.setAdapter(tvAdapterPopular);
    }

    protected void getTopRatedTvSeries() {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<TvResponse> call = retrofitService.getTopRatedTv(API_KEY);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                if (response.isSuccessful()) {
                    response.body().getResults();
                    tvAdapterTopRatedTv.addList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
            }
        });
    }

    protected void getPopular() {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<TvResponse> call = retrofitService.getPopularTv("d0c67ddbb6f821fb634fa40880d135bf");
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                if (response.isSuccessful()) {
                    response.body().getResults();
                    tvAdapterPopular.addList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
            }
        });

    }
}
