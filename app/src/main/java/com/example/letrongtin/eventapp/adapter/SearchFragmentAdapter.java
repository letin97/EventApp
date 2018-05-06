package com.example.letrongtin.eventapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.model.News;
import com.example.letrongtin.eventapp.viewholder.NewsViewHolder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchFragmentAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    List<News> newsList;
    Context context;

    public SearchFragmentAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_row, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Picasso.get()
                .load(newsList.get(position).getImageLink())
                .into(holder.imgNews);
        holder.txtNewsTitle.setText(newsList.get(position).getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            Date date = sdf.parse(newsList.get(position).getPubDate());
            if (date != null)
                holder.txtNewsTime.setReferenceTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
