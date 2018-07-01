package com.uit.letrongtin.eventapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Interface.ItemClickListener;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.activity.NewsDetailActivity;
import com.uit.letrongtin.eventapp.model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> newsArrayList;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{

        public CircleImageView imgNews;

        public TextView txtNewsTitle;

        public RelativeTimeTextView txtNewsTime;

        ItemClickListener listener;
    }

    class ViewHolderTop{

        public KenBurnsView topImage;

        public TextView txtNewsTitle;

        public RelativeTimeTextView txtNewsTime;

        ItemClickListener listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (position == 0) {
            final ViewHolderTop holder;
            if (convertView == null) {
                holder = new ViewHolderTop();
                convertView = LayoutInflater.from(context).inflate(R.layout.news_top, parent, false);
                holder.topImage = convertView.findViewById(R.id.img_news);
                holder.txtNewsTitle = convertView.findViewById(R.id.txt_news_title);
                holder.txtNewsTime = convertView.findViewById(R.id.news_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolderTop) convertView.getTag();
            }

            final News news = (News) getItem(position);
            Picasso.get()
                    .load(news.getImageLink())
                    .fit()
                    .into(holder.topImage);
                            holder.txtNewsTitle.setText(news.getTitle());


                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                try {
                    Date date = sdf.parse(news.getPubDate());
                    if (date != null)
                        holder.txtNewsTime.setReferenceTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detail = new Intent(context, NewsDetailActivity.class);
                        detail.putExtra("news", news);
                        detail.putExtra("key", news.getId());
                        context.startActivity(detail);
                    }
                });


        } else {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.news_item_row, parent, false);
                holder.imgNews = convertView.findViewById(R.id.img_news);
                holder.txtNewsTitle = convertView.findViewById(R.id.txt_news_title);
                holder.txtNewsTime = convertView.findViewById(R.id.news_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final News news = (News) getItem(position);
            Picasso.get()
                    .load(news.getImageLink())
                    .fit()
                    .into(holder.imgNews);
            holder.txtNewsTitle.setText(news.getTitle());


            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            try {
                Date date = sdf.parse(news.getPubDate());
                if (date != null)
                    holder.txtNewsTime.setReferenceTime(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(context, NewsDetailActivity.class);
                    detail.putExtra("news", news);
                    detail.putExtra("key", news.getId());
                    context.startActivity(detail);
                }
            });


        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
