package com.uit.letrongtin.eventapp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.adapter.EventDateFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventMenuFragment extends Fragment {

    private static EventMenuFragment instance;

    public static EventMenuFragment getInstance() {
        if (instance == null){
            instance = new EventMenuFragment();
        }
        return instance;
    }

    TabLayout tabLayout;
    ViewPager viewPager;


    public EventMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_menu, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        EventDateFragmentPagerAdapter adapter = new EventDateFragmentPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
