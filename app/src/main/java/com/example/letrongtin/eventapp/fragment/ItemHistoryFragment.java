package com.example.letrongtin.eventapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letrongtin.eventapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemHistoryFragment extends Fragment {

    private static ItemHistoryFragment instance;

    public static ItemHistoryFragment getInstance() {
        if (instance == null){
            instance = new ItemHistoryFragment();
        }
        return instance;
    }

    public ItemHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_history, container, false);
    }

}
