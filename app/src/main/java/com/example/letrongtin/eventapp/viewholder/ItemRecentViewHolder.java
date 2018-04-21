package com.example.letrongtin.eventapp.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Interface.ItemRecentAdapterClickListener;
import com.example.letrongtin.eventapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemRecentViewHolder extends RecyclerView.ViewHolder {

    public CardView cardRecent;

    public CircleImageView imgItem;

    public TextView txtItemName, txtItemTime;

    public ImageView iconMore;

    ItemRecentAdapterClickListener listener;

    public ItemRecentViewHolder(View itemView) {
        super(itemView);

        cardRecent = itemView.findViewById(R.id.card_recent);
        imgItem = itemView.findViewById(R.id.img_item);
        txtItemName = itemView.findViewById(R.id.txt_item_name);
        txtItemTime = itemView.findViewById(R.id.txt_item_time);
        iconMore = itemView.findViewById(R.id.ic_more);

    }

}
