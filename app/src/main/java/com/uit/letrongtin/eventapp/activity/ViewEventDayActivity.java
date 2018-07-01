package com.uit.letrongtin.eventapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.letrongtin.eventapp.Common.Common;
import com.uit.letrongtin.eventapp.R;
import com.squareup.picasso.Picasso;

public class ViewEventDayActivity extends AppCompatActivity {

    CoordinatorLayout rootLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fabEventDayNews;
    ImageView imgThumb;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_day);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rootLayout = findViewById(R.id.root_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setTitle(Common.EVENT_DAY_SELECT.getName());

        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(Common.EVENT_DAY_SELECT.getName());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        imgThumb = findViewById(R.id.img_thumb);


        Picasso.get()
                .load(Common.EVENT_DAY_SELECT.getImageLink())
                .fit()
                .into(imgThumb);

        fabEventDayNews = findViewById(R.id.fab_event_day_news);
        fabEventDayNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ViewEventDayActivity.this, HomeActivity.class);
                //intent.putExtra("search", Common.EVENT_DAY_SELECT.getName());
                //startActivity(intent);
                Intent intent = new Intent();
                intent.putExtra("search",Common.EVENT_DAY_SELECT.getName());
                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });

        description = findViewById(R.id.description);
        description.setText(Common.EVENT_DAY_SELECT.getDescription());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
