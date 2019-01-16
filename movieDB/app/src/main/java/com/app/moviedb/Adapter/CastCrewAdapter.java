package com.app.moviedb.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.moviedb.Pojo.Cast;
import com.app.moviedb.Pojo.Crew;
import com.app.moviedb.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CastCrewAdapter extends RecyclerView.Adapter<CastCrewAdapter.ViewHolder> {
    private Context context;
    private RequestOptions requestOptions;
    private List<Cast> castList = new ArrayList<>();
    private List<Crew> crewList = new ArrayList<>();
    private View itemView;
    private int castSize, crewSize = 0;
    private static int ITEM_CAST = 0;
    private static int ITEM_DIRECTOR = 1;
    private boolean hasDirector = true;

    public CastCrewAdapter(Context context, RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cast_crew, parent, false);
        return new CastCrewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        int viewType = viewHolder.getItemViewType();

        if (viewType == ITEM_DIRECTOR) {

            viewHolder.textV_name_row_cast_crew.setText(crewList.get(0).getName());
            viewHolder.textView.setText(crewList.get(0).getJob());
            Glide.with(context).load(crewList.get(0).getProfilePath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_person)).into(viewHolder.imageView);

        } else if (viewType == ITEM_CAST) {

            if (hasDirector) {
                viewHolder.textV_name_row_cast_crew.setText(castList.get(i - 1).getName());
                Glide.with(context).load(castList.get(i - 1).getProfilePath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_person)).into(viewHolder.imageView);
            } else {
                viewHolder.textV_name_row_cast_crew.setText(castList.get(i).getName());
                Glide.with(context).load(castList.get(i).getProfilePath()).apply(requestOptions.centerCrop().placeholder(R.drawable.ic_person)).into(viewHolder.imageView);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (hasDirector) {
            return castList.size() + 1;
        } else {
            return castList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            if (hasDirector) {
                return ITEM_DIRECTOR;
            } else {
                return ITEM_CAST;
            }
        } else {
            return ITEM_CAST;
        }
    }

    public void addList(List<Cast> castList1, List<Crew> crewList1) {
        castList.addAll(castList1);
        crewList.addAll(crewList1);
        castSize = castList.size();
        crewSize = crewList.size();
        hasDirector = crewSize > 0 && crewList.get(0).getJob().equals("Director");
        notifyItemRangeChanged(0, castSize + crewSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView, textV_name_row_cast_crew;
        private ConstraintLayout constraintL_row_castCrew;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintL_row_castCrew = itemView.findViewById(R.id.constraintL_row_castCrew);
            textV_name_row_cast_crew = itemView.findViewById(R.id.textV_name_row_cast_crew);
            textView = itemView.findViewById(R.id.textV_row_castCrew);
            imageView = itemView.findViewById(R.id.imageV_row_castCrew);
        }
    }
}
