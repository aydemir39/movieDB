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
import com.app.moviedb.Pojo.Movie;
import com.app.moviedb.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {
    private List<Movie> movieList = new ArrayList<>();
    private int itemType;
    private Context context;
    private RequestOptions requestOptions;
    private View itemView;
    private boolean withVoteAverage;


    public MovieAdapter(Context context, int itemType, boolean withVoteAverage, RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        this.context = context;
        this.itemType = itemType;
        this.withVoteAverage = withVoteAverage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (itemType == 1) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_img_movie_vertical, parent, false);
            return new ViewHolderTypeMovieVertical(itemView);
        } else if (itemType == 2) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_img_movie_horizontal, parent, false);
            return new ViewHolderTypeHorizontal(itemView);
        } else return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (itemType == 1) {

            ViewHolderTypeMovieVertical viewHolderTypeMovieVertical = (ViewHolderTypeMovieVertical) viewHolder;
            Movie movie = movieList.get(position);
            viewHolderTypeMovieVertical.textV_voteAverage_row_img_vertical.setText(movie.getVoteAverage().toString());
            viewHolderTypeMovieVertical.textV_voteAverage_row_img_vertical.setText(movie.getVoteAverage().toString());
            Glide.with(context).load(movie.getPosterPath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_play)).into(viewHolderTypeMovieVertical.imageV_row_img_vertical);

            //  ortalama puan gösterilip gösterilmeme durumuna göre puan view'i gizlenir yada gösterilir.
            if (!withVoteAverage) {
                viewHolderTypeMovieVertical.frameL_row_img_vertical.setVisibility(View.GONE);
            }
        } else if (itemType == 2) {
            ViewHolderTypeHorizontal viewHolderTypeHorizontal = (ViewHolderTypeHorizontal) viewHolder;
            Movie movie = movieList.get(position);
            Glide.with(context).load(movie.getBackdropPath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_play)).into(viewHolderTypeHorizontal.imageV_row_img_horizontal);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addList(List<Movie> movies) {
        int firstRange = movies.size();
        movieList.addAll(movies);
        int lastRange = movieList.size();
        notifyItemRangeChanged(firstRange, lastRange);
    }

    class ViewHolderTypeMovieVertical extends RecyclerView.ViewHolder {
        ImageView imageV_row_img_vertical;
        FrameLayout frameL_row_img_vertical;
        TextView textV_voteAverage_row_img_vertical;

        ViewHolderTypeMovieVertical(View itemView) {
            super(itemView);
            textV_voteAverage_row_img_vertical = itemView.findViewById(R.id.textV_voteAverage_row_img_vertical);
            frameL_row_img_vertical = itemView.findViewById(R.id.frameL_row_img_vertical);
            imageV_row_img_vertical = itemView.findViewById(R.id.imageV_row_img_vertical);
            imageV_row_img_vertical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailFragment(movieList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    class ViewHolderTypeHorizontal extends RecyclerView.ViewHolder {
        ImageView imageV_row_img_horizontal;

        ViewHolderTypeHorizontal(View itemView) {
            super(itemView);
            imageV_row_img_horizontal = itemView.findViewById(R.id.imageV_row_img_horizontal);
            imageV_row_img_horizontal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailFragment(movieList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    private void showDetailFragment(int id) {

        EventBus.getDefault().post(new Message(1));

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Fragment detailFragment = DetailFragment.newInstance(id, "movie");
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_left_back, R.anim.slide_right_back)
                .add(R.id.frameL_main_ac_main, detailFragment, "detailFragment")
                .addToBackStack(null)
                .commit();


    }

}
