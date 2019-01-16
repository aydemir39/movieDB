package com.app.moviedb.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.moviedb.Fragment.DetailFragment;
import com.app.moviedb.Utils.Message;
import com.app.moviedb.Pojo.Tv;
import com.app.moviedb.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter {
    private List<Tv> tvList = new ArrayList<>();
    private int itemType;
    private Context context;
    private View itemView;
    private RequestOptions requestOptions;

    public TvAdapter(Context context, int itemType, RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        this.context = context;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (itemType == 1) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_img_tv_vertical, parent, false);
            return new ViewHolderTypeMovieVertical(itemView);
        } else if (itemType == 2) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_img_tv_horizontal, parent, false);
            return new ViewHolderTypeHorizontal(itemView);
        } else return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (itemType == 1) {
            ViewHolderTypeMovieVertical viewHolderTypeMovieVertical = (ViewHolderTypeMovieVertical) viewHolder;
            Tv tv = tvList.get(position);
            Glide.with(context).load(tv.getPosterPath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_play)).into(viewHolderTypeMovieVertical.imageV_row_img_vertical);
        } else if (itemType == 2) {
            ViewHolderTypeHorizontal viewHolderTypeHorizontal = (ViewHolderTypeHorizontal) viewHolder;
            Tv tv = tvList.get(position);
            viewHolderTypeHorizontal.textV_voteAverage_row_img_tv_horizontal.setText(tv.getVoteAverage().toString());
            Glide.with(context).load(tv.getBackdropPath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_play)).into(viewHolderTypeHorizontal.imageV_row_img_horizontal);
        }
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public void addList(List<Tv> tvs) {
        int firstRange = tvs.size();
        tvList.addAll(tvs);
        int lastRange = tvList.size();
        notifyItemRangeChanged(firstRange, lastRange);
    }

    class ViewHolderTypeMovieVertical extends RecyclerView.ViewHolder {
        ImageView imageV_row_img_vertical;

        ViewHolderTypeMovieVertical(View itemView) {
            super(itemView);
            imageV_row_img_vertical = itemView.findViewById(R.id.imageV_row_img_tv_vertical);
            imageV_row_img_vertical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailFragment(tvList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    class ViewHolderTypeHorizontal extends RecyclerView.ViewHolder {
        ImageView imageV_row_img_horizontal;
        TextView textV_voteAverage_row_img_tv_horizontal;
        FrameLayout frameL_row_img_tv_horizontal;


        ViewHolderTypeHorizontal(View itemView) {
            super(itemView);
            frameL_row_img_tv_horizontal = itemView.findViewById(R.id.frameL_row_img_tv_horizontal);
            textV_voteAverage_row_img_tv_horizontal = itemView.findViewById(R.id.textV_voteAverage_row_img_tv_horizontal);
            imageV_row_img_horizontal = itemView.findViewById(R.id.imageV_row_img_horizontal);
            imageV_row_img_horizontal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailFragment(tvList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    private void showDetailFragment(int id) {
        EventBus.getDefault().post(new Message(1));

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Fragment detailFragment = DetailFragment.newInstance(id, "tv");
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_left_back, R.anim.slide_right_back)
                .add(R.id.frameL_main_ac_main, detailFragment, "detailFragment")
                .addToBackStack(null)
                .commit();
    }
}
