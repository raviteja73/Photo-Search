package com.chsra.photosearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

//adapter class for holding complete history list view
public class CompleteHistoryAdapter extends ArrayAdapter <History> {
    Context mContext;
    int mResource;
    List <History> mTopThreeSearchList;

    public CompleteHistoryAdapter(Context context, int resource, List <History> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mTopThreeSearchList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mResource, parent, false);
        }

        History history = mTopThreeSearchList.get(position);

        TextView searchDate = (TextView) convertView.findViewById(R.id.searchDate);
        searchDate.setText(history.getDate());

        TextView searchText = (TextView) convertView.findViewById(R.id.searchText);
        searchText.setText(history.getSearchTerm());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);

        if (history.getSearchTerm().length() > 0) {
            TextDrawable drawable = null;
            int c = MyColor.getColor(history.getSearchTerm().substring(0, 1).toLowerCase());
            drawable = TextDrawable.builder().buildRound(history.getSearchTerm().substring(0, 1), c);
            imageView.setImageDrawable(drawable);

        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.rgb(238, 238, 238));
        } else {
            convertView.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        return convertView;
    }
}