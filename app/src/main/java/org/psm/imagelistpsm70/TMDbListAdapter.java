package org.psm.imagelistpsm70;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
        ViewHolder vh = null;

        if(convertView == null){
            vh = new ViewHolder();
            LinearLayout llRow = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.tmdb_list_item, parent, false);
            vh.listImageView = (ImageView)llRow.findViewById(R.id.listItemImage);

            llRow.setTag(vh);

            convertView = llRow;
        }

        vh = (ViewHolder)convertView.getTag();

        String imageUrl = urlList.get(position);

        Glide.with(context).load(imageUrl).into(vh.listImageView);

        return convertView;
    }

    class ViewHolder {
        ImageView listImageView;
    }
}
