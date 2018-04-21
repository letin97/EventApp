package com.example.letrongtin.eventapp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.adapter.EventDateFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventMenuFragment extends Fragment {

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
