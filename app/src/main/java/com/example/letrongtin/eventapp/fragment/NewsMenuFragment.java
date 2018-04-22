package com.example.letrongtin.eventapp.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.Interface.Communicator;
import com.example.letrongtin.eventapp.Interface.ItemClickListener;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.activity.NewsDetailActivity;
import com.example.letrongtin.eventapp.model.News;
import com.example.letrongtin.eventapp.viewholder.NewsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */

public class NewsMenuFragment extends Fragment{

    private static NewsMenuFragment instance;

    public static NewsMenuFragment getInstance() {
        if (instance == null){
            instance = new NewsMenuFragment();
        }
        return instance;
    }


    KenBurnsView topImage;

    DiagonalLayout diagonalLayout;

    AlertDialog alertDialog;

    TextView txtTopTitle;

    // RecyclerView
    RecyclerView recyclerView;

    // Firebase
    FirebaseDatabase database;
    DatabaseReference news;
    Query queryTop, query;
    News lastNews;

    // FirebaseUI Adapter
    FirebaseRecyclerOptions<News> options;
    FirebaseRecyclerAdapter<News, NewsViewHolder> adapter;

    // Communnicator
    Communicator communicator;

    public NewsMenuFragment() {
        database = FirebaseDatabase.getInstance();
        news = database.getReference(Common.STR_NEWS);

        queryTop = news.limitToLast(1);
        queryTop.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //lastNews = dataSnapshot.getValue(News.class);
//                Picasso.get()
//                        .load(lastNews.getImageLink())
//                        .into(topImage);
                //txtTopTitle.setText(lastNews.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        query = news.orderByChild("pubDate").startAt(0);


        options = new FirebaseRecyclerOptions.Builder<News>()
                .setQuery(query, News.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final NewsViewHolder holder, int position, final News model) {

                //if (lastNews.getTitle().equals(model.getTitle())) return;
//                Picasso.get()
//                        .load(model.getImageLink())
//                        .into(topImage);

                Picasso.get()
                        .load(model.getImageLink())
                        .into(holder.imgNews);

                holder.txtNewsTitle.setText(model.getTitle());

                // communicator
                communicator.senData(model.getTitle());

                alertDialog.dismiss();

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent detail = new Intent(getContext(), NewsDetailActivity.class);
                        detail.putExtra("news", model);
                        detail.putExtra("key", adapter.getRef(position).getKey());
                        startActivity(detail);
                    }
                });
            }

            @Override
            public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_row, parent, false);
                return new NewsViewHolder(view);
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_menu, container, false);

        topImage = view.findViewById(R.id.top_image);
        diagonalLayout = view.findViewById(R.id.diagonal_layout);
        alertDialog = new SpotsDialog(getContext());
        txtTopTitle = view.findViewById(R.id.txt_top_title);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

}
