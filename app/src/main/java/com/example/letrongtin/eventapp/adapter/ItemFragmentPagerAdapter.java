package com.example.letrongtin.eventapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.letrongtin.eventapp.fragment.ItemFragment;
import com.example.letrongtin.eventapp.fragment.ItemHistoryFragment;

public class ItemFragmentPagerAdapter extends FragmentPagerAdapter {

    public ItemFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ItemFragment.getInstance();
            case 1:
                return ItemHistoryFragment.getInstance();

        }
        return ItemFragment.getInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Hot";
            case 1:
                return "Lịch sử";
        }
        return "";
    }
}
