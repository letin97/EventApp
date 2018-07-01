package com.uit.letrongtin.eventapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.uit.letrongtin.eventapp.Common.Common;
import com.uit.letrongtin.eventapp.Interface.Communicator;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.fragment.ConfigFragment;
import com.uit.letrongtin.eventapp.fragment.EventMenuFragment;
import com.uit.letrongtin.eventapp.fragment.ItemMenuFragment;
import com.uit.letrongtin.eventapp.fragment.NewsMenuFragment;
import com.uit.letrongtin.eventapp.fragment.SearchFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, Communicator{

    BottomNavigationView navigation;

    MaterialSearchView searchView;

    List<String> listTitle;

    String[] list;

    String dataSearch ="";

    int indexSelect = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        listTitle = new ArrayList<>();

        searchView = findViewById(R.id.search_view);

        searchView.setHint("Tìm kiếm");
        searchView.setHintTextColor(Color.GRAY);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataSearch = query;
                if (query.length() > 0){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment newFragment = new SearchFragment(HomeActivity.this);
                    Bundle bundle = new Bundle();
                    bundle.putString("search", dataSearch);
                    newFragment.setArguments(bundle);
                    transaction.replace(R.id.frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        loadFragment(new NewsMenuFragment(HomeActivity.this));

//        if (getIntent() != null){
//            String key = getIntent().getStringExtra("search");
//            if (key != null){
//                Log.d("AAA",key + "key" );
//
//            }
//        }

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

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.action_news:
                indexSelect = 0;
                fragment = NewsMenuFragment.getInstance(HomeActivity.this);
                break;
            case R.id.action_event:
                indexSelect = 1;
                fragment = EventMenuFragment.getInstance();
                break;
            case R.id.action_item:
                indexSelect = 2;
                fragment = ItemMenuFragment.getInstance();
                break;
            case R.id.action_config:
                indexSelect = 2;
                fragment = ConfigFragment.getInstance(HomeActivity.this);
                break;
            default:
                indexSelect = 5;
                fragment = NewsMenuFragment.getInstance(HomeActivity.this);
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void senData(String data) {
        listTitle.add(data);
        list = listTitle.toArray(new String[listTitle.size()]);
        searchView.setSuggestions(list);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            if (indexSelect == 0){
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                super.onBackPressed();
            } else {
                navigation.setSelectedItemId(R.id.action_news);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, NewsMenuFragment.getInstance(HomeActivity.this)).commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.SEARCH_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Log.d("AAA", data.getStringExtra("search"));
                dataSearch = data.getStringExtra("search");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment newFragment = new SearchFragment(HomeActivity.this);
                Bundle bundle = new Bundle();
                bundle.putString("search", dataSearch);
                newFragment.setArguments(bundle);
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                indexSelect = 3;
            }
        }
    }


}
