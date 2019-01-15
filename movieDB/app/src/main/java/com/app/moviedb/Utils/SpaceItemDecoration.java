package com.app.moviedb.Utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    int spaceHeight;

    public SpaceItemDecoration(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spaceHeight;
        } else {

        }
        //outRect.left = spaceHeight;
        // outRect.right = spaceHeight;
        //outRect.bottom = spaceHeight;

    }
}
