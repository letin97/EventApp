package com.uit.letrongtin.eventapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Interface.ItemClickListener;
import com.uit.letrongtin.eventapp.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView imgItem, star1, star2, star3, star4, star5, imgLove;

    public TextView txtItemName;

    //public ImageButton imgBtnWebItem;

    ItemClickListener listener;

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);

        imgItem = itemView.findViewById(R.id.img_item);
        txtItemName = itemView.findViewById(R.id.txt_item_name);
        //imgBtnWebItem = imgItem.findViewById(R.id.img_btn_web_item);
        star1 = itemView.findViewById(R.id.star_1);
        star2 = itemView.findViewById(R.id.star_2);
        star3 = itemView.findViewById(R.id.star_3);
        star4 = itemView.findViewById(R.id.star_4);
        star5 = itemView.findViewById(R.id.star_5);
        imgLove = itemView.findViewById(R.id.ic_love);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
}
