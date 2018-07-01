package com.uit.letrongtin.eventapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Interface.CheckItemClickListener;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.database.TypeNews;

import java.util.ArrayList;

public class ConfigAdapter extends BaseAdapter {

    String[] nameTypeList;
    Context context;
    ArrayList<TypeNews> typeNewsArrayList;
    CheckItemClickListener listener;

    public ConfigAdapter(String[] nameTypeList, Context context, ArrayList<TypeNews> typeNewsArrayList, CheckItemClickListener listener) {
        this.nameTypeList = nameTypeList;
        this.context = context;
        this.typeNewsArrayList = typeNewsArrayList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return nameTypeList.length;
    }

    @Override
    public Object getItem(int position) {
        return nameTypeList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{

        public TextView txtNameType;

        public CheckBox checkBox;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.type_news_row, parent, false);
            holder.txtNameType = convertView.findViewById(R.id.txt_ten_loai);
            holder.checkBox = convertView.findViewById(R.id.cb_loaitin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtNameType.setText(nameTypeList[position]);

        for (TypeNews typeNews : typeNewsArrayList){
            if (typeNews.getType().equals(nameTypeList[position])){
                holder.checkBox.setChecked(true);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, position, holder.checkBox.isChecked(), nameTypeList[position]);
            }
        });

        return convertView;
    }
}
