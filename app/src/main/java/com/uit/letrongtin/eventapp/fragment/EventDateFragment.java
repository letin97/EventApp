package com.uit.letrongtin.eventapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uit.letrongtin.eventapp.Common.Common;
import com.uit.letrongtin.eventapp.Interface.ItemClickListener;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.activity.ViewEventDayActivity;
import com.uit.letrongtin.eventapp.model.EventDate;
import com.uit.letrongtin.eventapp.viewholder.EventDateViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDateFragment extends Fragment {

    private static EventDateFragment instance;

    public static EventDateFragment getInstance() {
        if (instance == null){
            instance = new EventDateFragment();
        }
        return instance;
    }

    // Firebase
    FirebaseDatabase database;
    DatabaseReference eventDate;
    Query query;

    // FirebaseUI Adapter
    FirebaseRecyclerOptions<EventDate> options;
    FirebaseRecyclerAdapter<EventDate, EventDateViewHolder> adapter;

    // RecyclerView
    RecyclerView recyclerView;


    int minCountDown = 30;
    int positionDate = 0;

    public EventDateFragment() {
        database = FirebaseDatabase.getInstance();
        eventDate = database.getReference(Common.STR_EVENT_DATE);
        query = eventDate.orderByChild("date");
        options = new FirebaseRecyclerOptions.Builder<EventDate>()
                .setQuery(query, EventDate.class)   //select all
                .build();
        adapter = new FirebaseRecyclerAdapter<EventDate, EventDateViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final EventDateViewHolder holder, int position, @NonNull final EventDate model) {

                Picasso.get()
                        .load(model.getImageLink())
                        .fit()
                        .into(holder.imgBackground, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                holder.txtDateCountdown.setText("");
                holder.txtNameDate.setText(model.getName());
                String dateString = Calendar.getInstance().get(Calendar.YEAR) + "-" + model.getDate();
                holder.txtDate.setText(dateString);


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateString);
                    Date currentDate = new Date();
                    if (!currentDate.after(date)){
                        long diff = date.getTime() - currentDate.getTime();
                        int day = (int) (diff / (24*60*60*1000));
                        positionDate = position;
                        holder.txtDateCountdown.setText("" + String.format("%02d", day));

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ViewEventDayActivity.class);
                        Common.EVENT_DAY_SELECT = model;
                        getActivity().startActivityForResult(intent, Common.SEARCH_REQUEST_CODE);
                    }
                });
            }

            @Override
            public EventDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_date_item_row, parent, false);
                return new EventDateViewHolder(view);
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_date, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        //recyclerView.smoothScrollToPosition();

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
