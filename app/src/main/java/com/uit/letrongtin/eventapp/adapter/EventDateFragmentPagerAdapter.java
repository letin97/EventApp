package com.uit.letrongtin.eventapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uit.letrongtin.eventapp.fragment.CalendarFragment;
import com.uit.letrongtin.eventapp.fragment.EventDateFragment;


/**
 * Created by Le Trong Tin on 3/27/2018.
 */

public class EventDateFragmentPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public EventDateFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return EventDateFragment.getInstance();
            case 1:
                return CalendarFragment.getInstance(context);

        }
        return EventDateFragment.getInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Danh sách";
            case 1:
                return "Lịch";
        }
        return "";
    }
}
