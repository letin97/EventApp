package com.example.letrongtin.eventapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Interface.ItemClickListener;
import com.example.letrongtin.eventapp.R;


public class EventDateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtDateCountdown, txtNameDate, txtDate;
    public ImageView imgBackground, imgShadow;

    ItemClickListener listener;

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public EventDateViewHolder(View itemView) {
        super(itemView);

        txtDateCountdown = itemView.findViewById(R.id.txt_date_countdown);
        txtNameDate = itemView.findViewById(R.id.txt_name_date);
        txtDate = itemView.findViewById(R.id.txt_date);
        imgBackground = itemView.findViewById(R.id.img_background);
        imgShadow = itemView.findViewById(R.id.img_shadow);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }

}
