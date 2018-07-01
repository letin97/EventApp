package com.uit.letrongtin.eventapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uit.letrongtin.eventapp.Interface.ItemRecentAdapterClickListener;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.database.Recent;
import com.uit.letrongtin.eventapp.viewholder.ItemRecentViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecentAdapter extends RecyclerView.Adapter<ItemRecentViewHolder> {

    private Context context;
    private List<Recent> recents;
    private ItemRecentAdapterClickListener listener;

    public ItemRecentAdapter(Context context, List<Recent> recents, ItemRecentAdapterClickListener listener) {
        this.context = context;
        this.recents = recents;
        this.listener = listener;
    }

    @Override
    public ItemRecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_row, parent,false);
        return new ItemRecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemRecentViewHolder holder, final int position) {
        Picasso.get()
                .load(recents.get(position).getImageLink())
                .fit()
                .into(holder.imgItem, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.txtItemName.setText(recents.get(position).getName());
        holder.txtItemTime.setText(recents.get(position).getSaveTime());

        if (recents.get(position).isLove()){
            holder.cardRecent.setCardBackgroundColor(Color.parseColor("#b5f8ff"));
        }else {
            holder.cardRecent.setCardBackgroundColor(Color.WHITE);
        }

        holder.iconMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIconClick(v, position);
            }
        });


        holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recents.size();
    }
}
