package com.example.letrongtin.eventapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.letrongtin.eventapp.Interface.Communicator;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.fragment.EventMenuFragment;
import com.example.letrongtin.eventapp.fragment.ItemMenuFragment;
import com.example.letrongtin.eventapp.fragment.NewsMenuFragment;
import com.example.letrongtin.eventapp.fragment.SearchFragment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        listTitle = new ArrayList<>();

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataSearch = query;
                if (query.length() > 0){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment newFragment = new SearchFragment();
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
                fragment = NewsMenuFragment.getInstance();
                break;
            case R.id.action_event:
                fragment = new EventMenuFragment();
                break;
            case R.id.action_item:
                fragment = new ItemMenuFragment();
                break;
            default:
                fragment = NewsMenuFragment.getInstance();
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
}
