package com.chsra.photosearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class ImageCardAdapter extends RecyclerView.Adapter < ImageCardAdapter.ImageHolder > {

    private ArrayList <Image> mDataSet;
    static Context context;
    private ImageCardAdapter.CallbackInterface cli;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCES = "mypref";
    SharedPreferences.Editor editor;

    public ImageCardAdapter(ImageCardAdapter.CallbackInterface mcontext, ArrayList <Image> mDataSet) {
        cli = (ImageCardAdapter.CallbackInterface) mcontext;
        this.mDataSet = mDataSet;
    }

    @Override
    public ImageCardAdapter.ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card_view, parent, false);
        ImageCardAdapter.ImageHolder userViewHolder = new ImageCardAdapter.ImageHolder(v);
        context = parent.getContext();
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageCardAdapter.ImageHolder holder, int position) {
        final Image image = mDataSet.get(position);
        holder.searchName.setText(image.getImageText());

        Document document = Jsoup.parse(image.getIntroduction());
        holder.introText.setText(document.body().text());
        Uri uri = Uri.parse(image.getImageUrl());
        Picasso.with(context).load(uri).resize(400, 230).into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        TextView searchName, download, introText;
        ImageView imageView;

        ImageHolder(View itemView) {
            super(itemView);
            searchName = (TextView) itemView.findViewById(R.id.searchName_image);
            introText = (TextView) itemView.findViewById(R.id.textView_Intro);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface CallbackInterface {


    }
}