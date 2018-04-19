package com.example.letrongtin.eventapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Interface.ItemClickListener;
import com.example.letrongtin.eventapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CircleImageView imgItem;

    public TextView txtItemName;

    public ImageButton imgBtnWebItem;

    ItemClickListener listener;

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);

        imgItem = itemView.findViewById(R.id.img_item);
        txtItemName = itemView.findViewById(R.id.txt_item_name);
        imgBtnWebItem = imgItem.findViewById(R.id.img_btn_web_item);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
}
