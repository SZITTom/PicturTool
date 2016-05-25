package com.szittom.picturtool.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * Created by SZITTom on 2016/4/20.
 */
public class SampleScrollListener extends RecyclerView.OnScrollListener{

    private Context mContext;

    public SampleScrollListener(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final Picasso picasso = Picasso.with(mContext);
        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            picasso.resumeTag(mContext);
        } else {
            picasso.pauseTag(mContext);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
