package com.app.moviedb.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.moviedb.Adapter.CastCrewAdapter;
import com.app.moviedb.Api.RetrofitApiClient;
import com.app.moviedb.Api.RetrofitService;
import com.app.moviedb.Utils.Message;
import com.app.moviedb.Pojo.CastCrewResponse;
import com.app.moviedb.Pojo.Detail;
import com.app.moviedb.R;
import com.app.moviedb.Utils.SpaceItemDecoration;
import com.app.moviedb.databinding.FragmentDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "type";
    private FragmentDetailBinding fragmentDetailBinding;
    private int id;
    private String type;
    private Detail detail;
    private CastCrewResponse castCrewResponse;
    private View view;
    private CastCrewAdapter castCrewAdapter;
    private List<ImageView> imageViewList = new ArrayList<>();
    private String API_KEY = "d0c67ddbb6f821fb634fa40880d135bf";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int id, String type) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        view = fragmentDetailBinding.getRoot();
        init();
        registerHandlers();
        getData(type, id);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().post(new Message(2));
    }
    /////--------------------------------------------------------------------------------------/////

    protected void init() {
        fragmentDetailBinding.frameLLoadingFrDetail.setVisibility(View.VISIBLE);
        fragmentDetailBinding.nestedScrollVFrDetail.setVisibility(View.GONE);
        fragmentDetailBinding.frameLErrorFrDetail.setVisibility(View.GONE);
        imageViewList.add(fragmentDetailBinding.imageVStar1);
        imageViewList.add(fragmentDetailBinding.imageVStar2);
        imageViewList.add(fragmentDetailBinding.imageVStar3);
        imageViewList.add(fragmentDetailBinding.imageVStar4);
        imageViewList.add(fragmentDetailBinding.imageVStar5);

        fragmentDetailBinding.recyclerVCastCrewFrDetail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentDetailBinding.recyclerVCastCrewFrDetail.addItemDecoration(new SpaceItemDecoration(48));

        castCrewAdapter = new CastCrewAdapter(getActivity(), new RequestOptions());

    }

    private void registerHandlers() {
        fragmentDetailBinding.imageVBackFrDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        fragmentDetailBinding.textVBackFrDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    protected void getDetails(String type, int id) {
        if (isNetworkConnected()) {
            RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
            Call<Detail> call = retrofitService.getDetail(type, id, API_KEY);
            call.enqueue(new Callback<Detail>() {
                @Override
                public void onResponse(Call<Detail> call, Response<Detail> response) {

                    if (response.isSuccessful()) {
                        detail = response.body();
                        generateDetails(detail);
                        caseSucceededData();
                    } else {
                        caseFailedData();
                    }
                }

                @Override
                public void onFailure(Call<Detail> call, Throwable t) {
                    caseFailedData();
                }
            });
        } else {
            caseFailedData();
        }
    }

    protected void generateDetails(Detail detail) {

        // gelen veri türüne göre("movie" yada "tv") title yada name seçimi yapılıyor.
        if (type.equals("movie")) {
            fragmentDetailBinding.textVTitleFrDetail.setText(detail.getTitle());
        } else if (type.equals("tv")) {
            fragmentDetailBinding.textVTitleFrDetail.setText(detail.getName());
        }

        fragmentDetailBinding.textVOverviewFrDetail.setText(detail.getOverview());
        String str = detail.getGenreList().toString().replace("[", "").replace("]", "");
        fragmentDetailBinding.textVGenreFrDetail.setText(str);
        replaceStars(detail.getVoteAverage());

        fragmentDetailBinding.textVVoteAverageFrDetail.setText(detail.getVoteAverage().toString());
        Glide.with(getActivity()).load(detail.getPosterPath()).apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_play)).into(fragmentDetailBinding.imageVThumbnailFrDetail);
        Glide.with(getActivity()).load(detail.getBackdropPath()).apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_play)).into(fragmentDetailBinding.imageVCoverFrDetail);

    }

    protected void replaceStars(double voteAverage) {
        int i = (int) (voteAverage / 2);
        for (int a = 0; a < i; a++) {
            imageViewList.get(a).setImageResource(R.drawable.ic_starselected);
        }
    }

    protected void getData(final String type, final int id) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getDetails(type, id);

            }
        }, 160);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getCastAndCrew(type, id);
            }
        }, 200);

    }

    protected void getCastAndCrew(String type, int id) {

        RetrofitService retrofitService = RetrofitApiClient.getClient().create(RetrofitService.class);
        Call<CastCrewResponse> call = retrofitService.getMovieCastCrew(type, id, "d0c67ddbb6f821fb634fa40880d135bf");
        call.enqueue(new Callback<CastCrewResponse>() {
            @Override
            public void onResponse(Call<CastCrewResponse> call, Response<CastCrewResponse> response) {

                if (response.isSuccessful()) {
                    castCrewResponse = response.body();
                    generateCastAndCrew(castCrewResponse);
                } else {
                }
            }

            @Override
            public void onFailure(Call<CastCrewResponse> call, Throwable t) {
            }
        });
    }

    protected void generateCastAndCrew(CastCrewResponse castCrewResponse) {
        castCrewAdapter.addList(castCrewResponse.getCast(), castCrewResponse.getCrew());
        fragmentDetailBinding.recyclerVCastCrewFrDetail.setAdapter(castCrewAdapter);
    }

    protected void caseSucceededData() {
        detailInfoAnimation();
        fragmentDetailBinding.frameLLoadingFrDetail.setVisibility(View.GONE);
        fragmentDetailBinding.nestedScrollVFrDetail.setVisibility(View.VISIBLE);
        fragmentDetailBinding.frameLErrorFrDetail.setVisibility(View.GONE);
    }

    protected void caseFailedData() {
        fragmentDetailBinding.frameLLoadingFrDetail.setVisibility(View.GONE);
        fragmentDetailBinding.nestedScrollVFrDetail.setVisibility(View.GONE);
        fragmentDetailBinding.frameLErrorFrDetail.setVisibility(View.VISIBLE);
    }

    protected void detailInfoAnimation() {
        fragmentDetailBinding.textVTitleFrDetail.animate().alpha(1).scaleX(1).scaleY(1).setDuration(280).setStartDelay(440).start();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    /////--------------------------------------------------------------------------------------/////
}
