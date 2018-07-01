package com.uit.letrongtin.eventapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Interface.ItemClickListener;
import com.uit.letrongtin.eventapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CircleImageView imgNews;

    public TextView txtNewsTitle;

    public RelativeTimeTextView txtNewsTime;

    public KenBurnsView topImage;

    ItemClickListener listener;

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public NewsViewHolder(View itemView) {
        super(itemView);

        imgNews = itemView.findViewById(R.id.img_news);
        txtNewsTitle = itemView.findViewById(R.id.txt_news_title);
        txtNewsTime = itemView.findViewById(R.id.news_time);

        itemView.setOnClickListener(this);
    }

    public NewsViewHolder(View itemView, boolean header) {
        super(itemView);

        topImage = itemView.findViewById(R.id.img_news);
        txtNewsTitle = itemView.findViewById(R.id.txt_news_title);
        //txtNewsTime = itemView.findViewById(R.id.news_time);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //listener.onClick(v, getAdapterPosition());
    }
}

