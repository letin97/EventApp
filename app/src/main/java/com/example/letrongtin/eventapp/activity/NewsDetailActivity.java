package com.example.letrongtin.eventapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.database.DataSource.RecentRepository;
import com.example.letrongtin.eventapp.database.LocalDatabase.LocalDatabase;
import com.example.letrongtin.eventapp.database.Recent;
import com.example.letrongtin.eventapp.model.Item;
import com.example.letrongtin.eventapp.model.News;
import com.example.letrongtin.eventapp.model.NewsItem;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    ImageView imgNews, imgItem, imgCircleItem;
    TextView txtNewsTitle, txtNewsDescription, txtItemName, txtItemDescription;
    RelativeTimeTextView txtNewsTime;
    FloatingActionButton fabWeb;
    Button btnItemWeb;
    News news;



    //Firebase
    FirebaseDatabase database;
    DatabaseReference news_item;
    DatabaseReference itemRef;

    // Room Database
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    ArrayList<NewsItem> newsItemArrayList;

    // Facebook
    FloatingActionButton shareButton;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;


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
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        imgNews = findViewById(R.id.img_news);
        txtNewsTitle = findViewById(R.id.txt_news_title);
        txtNewsDescription = findViewById(R.id.txt_news_description);
        txtNewsTime = findViewById(R.id.news_time);
        fabWeb = findViewById(R.id.fab_web);
        btnItemWeb = findViewById(R.id.btn_item_web);

        imgItem = findViewById(R.id.item_image);
        imgCircleItem = findViewById(R.id.item_circle_image);
        txtItemName = findViewById(R.id.item_name);
        txtItemDescription = findViewById(R.id.item_description);

        news = new News();

        // Firebase
        database = FirebaseDatabase.getInstance();
        news_item = database.getReference(Common.STR_NEWS_ITEM);
        itemRef = FirebaseDatabase.getInstance().getReference(Common.STR_ITEM);

        // Room Database
        compositeDisposable = new CompositeDisposable();
        LocalDatabase localDatabase = LocalDatabase.getInstance(NewsDetailActivity.this);
        recentRepository = RecentRepository.getInstance(localDatabase.recentDAO());


        newsItemArrayList = new ArrayList<>();

        final ArrayList<String> integers = new ArrayList<>();

        if (getIntent()!=null){
            if (getIntent().getSerializableExtra("news") != null){

                news = (News) getIntent().getSerializableExtra("news");
                Picasso.get()
                        .load(news.getImageLink())
                        .fit()
                        .into(imgNews);

                txtNewsTitle.setText(news.getTitle());
                txtNewsDescription.setText(news.getDescription());

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                try {
                    Date date = sdf.parse(news.getPubDate());
                    if (date != null)
                        txtNewsTime.setReferenceTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String key = getIntent().getStringExtra("key");

                news_item.orderByChild("newskey").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            NewsItem newsItem = snapshot.getValue(NewsItem.class);
                            newsItemArrayList.add(newsItem);
                        }

                        int n = newsItemArrayList.size();
                        if (n != 0) {
                            int random = (int) Math.floor(Math.random() * n);
                            String randomIndex = newsItemArrayList.get(random).getItemkey();
                            itemRef.orderByKey().equalTo(randomIndex).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    final Item item = dataSnapshot.getValue(Item.class);
                                    txtItemName.setText(item.getName());
                                    Picasso.get()
                                            .load(item.getImageLink())
                                            .fit()
                                            .into(imgItem);
                                    Picasso.get()
                                            .load(item.getImageLink())
                                            .fit()
                                            .into(imgCircleItem);

                                    txtItemDescription.setText(item.getDescription());

                                    btnItemWeb.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            addToRecent(item);
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

        shareButton = findViewById(R.id.btn_share_fb);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(NewsDetailActivity.this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(NewsDetailActivity.this, "Chia sẻ thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(NewsDetailActivity.this, "Hủy bỏ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(NewsDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ShareDialog.canShow(ShareLinkContent.class)){
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(news.getLink()))
                            .build();
                    shareDialog.show(shareLinkContent);
                }
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

    private void addToRecent(final Item item){
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String date = dateFormat.format(new Date());

                Recent recent = new Recent(item.getName(), item.getImageLink(), item.getLink(), date, false);

                recentRepository.insertRecent(recent);
                e.onComplete();

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ERROR", throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
