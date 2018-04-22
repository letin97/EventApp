package com.example.letrongtin.eventapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.fragment.EventMenuFragment;
import com.example.letrongtin.eventapp.fragment.ItemMenuFragment;
import com.example.letrongtin.eventapp.fragment.NewsMenuFragment;

public class HomeActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new NewsMenuFragment());

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.action_news:
                fragment = new NewsMenuFragment();
                break;
            case R.id.action_event:
                fragment = new EventMenuFragment();
                break;
            case R.id.action_item:
                fragment = new ItemMenuFragment();
        }
        return loadFragment(fragment);
    }
}
