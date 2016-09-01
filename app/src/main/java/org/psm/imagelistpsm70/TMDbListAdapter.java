package org.psm.imagelistpsm70;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by PSM on 2016. 9. 1..
 */
public class TMDbListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> urlList;

    public TMDbListAdapter(Context context, ArrayList<String> urlList){
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LinearLayout llRow = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.tmdb_list_item, parent, false);
            viewHolder.listImageView = (ImageView)llRow.findViewById(R.id.tmdbListItemImage);

            llRow.setTag(viewHolder);

            convertView = llRow;
        }

        viewHolder = (ViewHolder)convertView.getTag();

        String imageUrl = urlList.get(position);

        Glide.with(context).load(imageUrl).into(viewHolder.listImageView);

        return convertView;
    }

    class ViewHolder {
        ImageView listImageView;
    }
}
