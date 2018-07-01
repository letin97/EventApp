package com.uit.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Common.Common;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.adapter.CalendarAdapter;
import com.uit.letrongtin.eventapp.model.EventDate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CalendarFragment extends Fragment {

    private static CalendarFragment instance;

    public static CalendarFragment getInstance(Context context) {
        if (instance == null){
            instance = new CalendarFragment(context);
        }
        return instance;
    }

    private ArrayList<EventDate> eventDates;
    DatabaseReference eventDateDB;

    private LinearLayout rootLayout;
    public GregorianCalendar monthCalendar;
    private CalendarAdapter adapter;
    private TextView txtMonth;
    private ImageButton imgBtnPrevious,imgBtnNext;
    private GridView gvCalendar;

    public CalendarFragment(Context context) {
        eventDateDB = FirebaseDatabase.getInstance().getReference();
        eventDates = new ArrayList<>();
        monthCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        adapter = new CalendarAdapter(context, monthCalendar, eventDates);
        GetEventDate();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        //cal_month_copy = (GregorianCalendar) cal_month.clone();
        rootLayout = view.findViewById(R.id.root_layout);

        txtMonth = view.findViewById(R.id.txtMoth);
        txtMonth.setText(android.text.format.DateFormat.format("MMMM yyyy", monthCalendar));

        imgBtnPrevious = view.findViewById(R.id.imgBtnPrevious);

        imgBtnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        imgBtnNext = view.findViewById(R.id.imgBtnNext);
        imgBtnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        switch (monthCalendar.get(GregorianCalendar.MONTH))
        {
            case GregorianCalendar.JANUARY:
            case GregorianCalendar.FEBRUARY:
            case GregorianCalendar.MARCH:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_xuan);
                break;
            case GregorianCalendar.APRIL:
            case GregorianCalendar.MAY:
            case GregorianCalendar.JUNE:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_ha);
                break;
            case GregorianCalendar.JULY:
            case GregorianCalendar.AUGUST:
            case GregorianCalendar.SEPTEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_thu);
                break;
            case GregorianCalendar.OCTOBER:
            case GregorianCalendar.NOVEMBER:
            case GregorianCalendar.DECEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_dong);
                break;
        }



        gvCalendar = view.findViewById(R.id.gvCalendar);
        gvCalendar.setAdapter(adapter);
        gvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelectedDay(v,position);
                String selectedGridDate = CalendarAdapter.calendarString
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();

                } else if ((gridvalue < 14) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }

            }

        });



        return view;
    }

    private void GetEventDate() {
        eventDateDB.child(Common.STR_EVENT_DATE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventDate eventDate = dataSnapshot.getValue(EventDate.class);
                eventDates.add(eventDate);
                adapter.notifyDataSetChanged();
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
    }

    protected void setNextMonth() {
        if (monthCalendar.get(GregorianCalendar.MONTH) == monthCalendar
                .getActualMaximum(GregorianCalendar.MONTH)) {
            monthCalendar.set((monthCalendar.get(GregorianCalendar.YEAR) + 1),
                    monthCalendar.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            monthCalendar.set(GregorianCalendar.MONTH,
                    monthCalendar.get(GregorianCalendar.MONTH) + 1);
        }

        switch (monthCalendar.get(GregorianCalendar.MONTH))
        {
            case GregorianCalendar.JANUARY:
            case GregorianCalendar.FEBRUARY:
            case GregorianCalendar.MARCH:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_xuan);
                break;
            case GregorianCalendar.APRIL:
            case GregorianCalendar.MAY:
            case GregorianCalendar.JUNE:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_ha);
                break;
            case GregorianCalendar.JULY:
            case GregorianCalendar.AUGUST:
            case GregorianCalendar.SEPTEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_thu);
                break;
            case GregorianCalendar.OCTOBER:
            case GregorianCalendar.NOVEMBER:
            case GregorianCalendar.DECEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_dong);
                break;
        }

    }

    protected void setPreviousMonth() {
        if (monthCalendar.get(GregorianCalendar.MONTH) == monthCalendar
                .getActualMinimum(GregorianCalendar.MONTH)) {
            monthCalendar.set((monthCalendar.get(GregorianCalendar.YEAR) - 1),
                    monthCalendar.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            monthCalendar.set(GregorianCalendar.MONTH,
                    monthCalendar.get(GregorianCalendar.MONTH) - 1);
        }

        switch (monthCalendar.get(GregorianCalendar.MONTH))
        {
            case GregorianCalendar.JANUARY:
            case GregorianCalendar.FEBRUARY:
            case GregorianCalendar.MARCH:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_xuan);
                break;
            case GregorianCalendar.APRIL:
            case GregorianCalendar.MAY:
            case GregorianCalendar.JUNE:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_ha);
                break;
            case GregorianCalendar.JULY:
            case GregorianCalendar.AUGUST:
            case GregorianCalendar.SEPTEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_thu);
                break;
            case GregorianCalendar.OCTOBER:
            case GregorianCalendar.NOVEMBER:
            case GregorianCalendar.DECEMBER:
                rootLayout.setBackgroundResource(R.drawable.bg_mua_dong);
                break;
        }

    }

    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        txtMonth.setText(android.text.format.DateFormat.format("MMMM yyyy", monthCalendar));
    }

}
