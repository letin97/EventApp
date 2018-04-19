package com.example.letrongtin.eventapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.Interface.ItemClickListener;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.model.Item;
import com.example.letrongtin.eventapp.viewholder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private static ItemFragment instance;

    public static ItemFragment getInstance() {
        if (instance == null){
            instance = new ItemFragment();
        }
        return instance;
    }

    // Firebase
    FirebaseDatabase database;
    DatabaseReference item_db;
    FirebaseRecyclerOptions<Item> options;
    FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter;
    Query query;

    // RecyclerView
    RecyclerView recyclerView;

    public ItemFragment() {
        database = FirebaseDatabase.getInstance();
        item_db = database.getReference(Common.STR_ITEM);
        query = item_db.orderByChild("name");
        options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ItemViewHolder holder, int position, Item model) {
                Picasso.get()
                        .load(model.getImageLink())
                        .into(holder.imgItem, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                holder.txtItemName.setText(model.getName());
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent,false);
                return new ItemViewHolder(view);
            }
        };

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }

}
