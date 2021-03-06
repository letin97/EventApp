package com.uit.letrongtin.eventapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.activity.NewsDetailActivity;
import com.uit.letrongtin.eventapp.model.News;
import com.uit.letrongtin.eventapp.viewholder.NewsViewHolder;
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
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        Picasso.get()
                .load(newsList.get(position).getImageLink())
                .fit()
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

        holder.txtNewsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, NewsDetailActivity.class);
                detail.putExtra("news", newsList.get(position));
                detail.putExtra("key", newsList.get(position).getId());
                context.startActivity(detail);
            }
        });

        holder.imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, NewsDetailActivity.class);
                detail.putExtra("news", newsList.get(position));
                detail.putExtra("key", newsList.get(position).getId());
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
