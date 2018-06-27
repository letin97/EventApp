package com.example.letrongtin.eventapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.letrongtin.eventapp.fragment.ItemFragment;
import com.example.letrongtin.eventapp.fragment.ItemRecentFragment;

public class ItemFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ItemFragmentPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ItemFragment.getInstance(context);
            case 1:
                return ItemRecentFragment.getInstance(context);

        }
        return ItemFragment.getInstance(context);
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
                return "Yêu thích";
        }
        return "";
    }
}
