package com.example.letrongtin.eventapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.model.News;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    ImageView imgNews;
    TextView txtNewsTitle, txtNewsDescription;
    FloatingActionButton fabWeb;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle("");
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
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        imgNews = findViewById(R.id.img_news);
        txtNewsTitle = findViewById(R.id.txt_news_title);
        txtNewsDescription = findViewById(R.id.txt_news_description);
        fabWeb = findViewById(R.id.fab_web);

        news = new News();

        if (getIntent()!=null){
            if (getIntent().getSerializableExtra("news") != null){

                news = (News) getIntent().getSerializableExtra("news");
                Picasso.get()
                        .load(news.getImageLink())
                        .into(imgNews);
                txtNewsTitle.setText(news.getTitle());
                txtNewsDescription.setText(news.getDescription());
            }
        }

        fabWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(NewsDetailActivity.this, NewsWebActivity.class);
                web.putExtra("link", news.getLink());
                startActivity(web);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
