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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.model.Item;
import com.example.letrongtin.eventapp.model.News;
import com.example.letrongtin.eventapp.model.NewsItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    ImageView imgNews, imgItem, imgCircleItem;
    TextView txtNewsTitle, txtNewsDescription, txtItemName, txtItemDescription;
    FloatingActionButton fabWeb;
    Button btnItemWeb;
    News news;



    //Firebase
    FirebaseDatabase database;
    DatabaseReference news_item;
    DatabaseReference itemRef;

    ArrayList<NewsItem> newsItemArrayList;


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
        appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout.setTitle("");

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
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        imgNews = findViewById(R.id.img_news);
        txtNewsTitle = findViewById(R.id.txt_news_title);
        txtNewsDescription = findViewById(R.id.txt_news_description);
        fabWeb = findViewById(R.id.fab_web);
        btnItemWeb = findViewById(R.id.btn_item_web);

        imgItem = findViewById(R.id.item_image);
        imgCircleItem = findViewById(R.id.item_circle_image);
        txtItemName = findViewById(R.id.item_name);
        txtItemDescription = findViewById(R.id.item_description);

        news = new News();

        database = FirebaseDatabase.getInstance();
        news_item = database.getReference(Common.STR_NEWS_ITEM);
        itemRef = FirebaseDatabase.getInstance().getReference(Common.STR_ITEM);


        newsItemArrayList = new ArrayList<>();

        final ArrayList<String> integers = new ArrayList<>();

        if (getIntent()!=null){
            if (getIntent().getSerializableExtra("news") != null){

                news = (News) getIntent().getSerializableExtra("news");
                Picasso.get()
                        .load(news.getImageLink())
                        .into(imgNews);
                txtNewsTitle.setText(news.getTitle());
                txtNewsDescription.setText(news.getDescription());

                String key = getIntent().getStringExtra("key");

                news_item.orderByChild("newskey").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            NewsItem newsItem = snapshot.getValue(NewsItem.class);
                            newsItemArrayList.add(newsItem);
                        }

                        int n = newsItemArrayList.size();
                        int random = (int) Math.floor(Math.random() * n);
                        String randomIndex = newsItemArrayList.get(random).getItemkey();
                        itemRef.orderByKey().equalTo(randomIndex).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                final Item item = dataSnapshot.getValue(Item.class);
                                txtItemName.setText(item.getName());
                                Picasso.get()
                                        .load(item.getImageLink())
                                        .into(imgItem);
                                Picasso.get()
                                        .load(item.getImageLink())
                                        .into(imgCircleItem);
                                txtItemDescription.setText(item.getDescription());

                                btnItemWeb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent web = new Intent(NewsDetailActivity.this, NewsWebActivity.class);
                                        web.putExtra("link", item.getLink());
                                        startActivity(web);
                                    }
                                });

                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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
