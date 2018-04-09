package com.example.letrongtin.eventapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Interface.ItemClickListener;
import com.example.letrongtin.eventapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CircleImageView imgNews;

    public TextView txtNewsTitle;

    ItemClickListener listener;

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public NewsViewHolder(View itemView) {
        super(itemView);

        imgNews = itemView.findViewById(R.id.img_news);
        txtNewsTitle = itemView.findViewById(R.id.txt_news_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
}
