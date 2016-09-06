package org.psm.imagelistpsm70;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by PSM on 2016. 9. 6..
 */
public class TMDbRecycleAdapter extends RecyclerView.Adapter<TMDbRecycleAdapter.ViewHolder> {

    Context context;
    ArrayList<String> urlList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ViewHolder(View v){
            super(v);
            mImageView = (ImageView)v.findViewById(R.id.recycleItemImage);
        }
    }

    public TMDbRecycleAdapter (ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.tmdb_recycle_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageUrl = urlList.get(position);

        Glide.with(context).load(imageUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }
}
