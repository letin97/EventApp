package com.example.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.Interface.Communicator;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.adapter.NewsAdapter;
import com.example.letrongtin.eventapp.database.DataSource.TypeNewsRepository;
import com.example.letrongtin.eventapp.database.LocalDatabase.LocalDatabase;
import com.example.letrongtin.eventapp.database.TypeNews;
import com.example.letrongtin.eventapp.model.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class NewsMenuFragment extends Fragment {

    private static NewsMenuFragment instance;

    public static NewsMenuFragment getInstance(Context context) {
        if (instance == null){
            instance = new NewsMenuFragment(context);
        }
        return instance;
    }

    AlertDialog alertDialog;

    List<TypeNews> typeNewsList;
    CompositeDisposable compositeDisposable;
    TypeNewsRepository typeNewsRepository;

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    NewsAdapter adapter;
    ArrayList<News> arrayList;

    // Firebase
    FirebaseDatabase database;
    DatabaseReference news;
    //Query query;
    private String oldestPostId;
    private int page = 0;

    // FirebaseUI Adapter
    //FirebaseRecyclerOptions<News> options;
    //FirebaseRecyclerAdapter<News, NewsViewHolder> adapter;

    // Communnicator
    Communicator communicator;


    public NewsMenuFragment(Context context) {
        database = FirebaseDatabase.getInstance();
        news = database.getReference(Common.STR_NEWS);

        typeNewsList = new ArrayList<>();

        // Room Database
        compositeDisposable = new CompositeDisposable();
        LocalDatabase localDatabase = LocalDatabase.getInstance(context);
        typeNewsRepository = TypeNewsRepository.getInstance(localDatabase.typeNewsDAO());

//        query = news.orderByChild("pubDate")
//                    .limitToFirst(TOTAL_ITEM_EACH_LOAD)
//                    .startAt(currentPage*TOTAL_ITEM_EACH_LOAD);
//
//        options = new FirebaseRecyclerOptions.Builder<News>()
//                .setQuery(query, News.class)
//                .build();
//
//        adapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(final NewsViewHolder holder, int position, final News model) {
//
//                if (position == 0) {
//                    Picasso.get()
//                            .load(model.getImageLink())
//                            .fit()
//                            .into(holder.topImage);
//                } else{
//                    Picasso.get()
//                            .load(model.getImageLink())
//                            .fit()
//                            .into(holder.imgNews);
//                }
//
//
//                holder.txtNewsTitle.setText(model.getTitle());
//
//
//                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
//                try {
//                    Date date = sdf.parse(model.getPubDate());
//                    if (date != null)
//                        holder.txtNewsTime.setReferenceTime(date.getTime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                // communicator
//                communicator.senData(model.getTitle());
//
//                alertDialog.dismiss();
//
//                holder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position) {
//                        Intent detail = new Intent(getContext(), NewsDetailActivity.class);
//                        detail.putExtra("news", model);
//                        detail.putExtra("key", adapter.getRef(position).getKey());
//                        startActivity(detail);
//                    }
//                });
//
//            }
//
//            @Override
//            public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view;
//
//                if (viewType == 1) {
//                    view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_top, parent, false);
//                    return new NewsViewHolder(view, true);
//                }else {
//                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_row, parent, false);
//                    return new NewsViewHolder(view);
//                }
//            }
//
//            @Override
//            public int getItemViewType(int position) {
//                if (position == 0) return 1;
//                else return 2;
//            }
//        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_menu, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });
        alertDialog = new SpotsDialog(getContext());


//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);

        listView = view.findViewById(R.id.list_view);

        loadTypeNews();

        loadNews();

//        if (arrayList == null){
//            arrayList = new ArrayList<>();
//
//            news.orderByChild("pubDate").limitToFirst(10)
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot child : dataSnapshot.getChildren()){
//                                News news = child.getValue(News.class);
//                                news.setId(child.getKey());
//                                oldestPostId = child.child("pubDate").getValue(String.class);
//
//                                for (TypeNews typeNews : typeNewsList){
//                                    if (news.getType().equals(typeNews.getType())){
//                                        arrayList.add(news);
//                                        break;
//                                    }
//                                }
//
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//        }

        adapter = new NewsAdapter(getContext(), arrayList );

        listView.setAdapter(adapter);



        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/
                    arrayList.remove(arrayList.size()-1);
                    //adapter.notifyDataSetChanged();

                    news.orderByChild("pubDate").startAt(oldestPostId).limitToFirst(15).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                oldestPostId = child.child("pubDate").getValue(String.class);
                                News news = child.getValue(News.class); //Event is a model class for list items
                                news.setId(child.getKey());

                                for (TypeNews typeNews : typeNewsList){
                                    if (news.getType().equals(typeNews.getType())){
                                        arrayList.add(news);
                                        break;
                                    }
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            };

        });
//        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                currentPage++;
//
//                query = news.orderByChild("pubDate")
//                        .limitToFirst(TOTAL_ITEM_EACH_LOAD)
//                        .startAt(currentPage*TOTAL_ITEM_EACH_LOAD);
//
//
//            }
//        });

        return view;

    }

    private void loadTypeNews() {
        Disposable disposable = typeNewsRepository.getAllType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<TypeNews>>() {
                    @Override
                    public void accept(List<TypeNews> typeNews) throws Exception {
                        getAllTypeNewsSuccess(typeNews);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    private void getAllTypeNewsSuccess(List<TypeNews> typeNews) {
        typeNewsList.clear();
        typeNewsList.addAll(typeNews);
    }

    private void loadNews(){

        swipeRefreshLayout.setRefreshing(true);

        oldestPostId ="";

        //listView.setAdapter(null);

        loadTypeNews();

        if (arrayList == null){
            arrayList = new ArrayList<>();
        } else
            arrayList.clear();

        //int numNews = 0;

        news.orderByChild("pubDate").limitToFirst(50)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            if (arrayList.size() > 10) return;

                            News news = child.getValue(News.class);
                            news.setId(child.getKey());
                            oldestPostId = child.child("pubDate").getValue(String.class);

                            for (TypeNews typeNews : typeNewsList){
                                if (news.getType().equals(typeNews.getType())){
                                    arrayList.add(news);
                                    break;
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (adapter!=null)
//            adapter.startListening();
    }

    @Override
    public void onStop() {
//        if (adapter!=null)
//            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (adapter!=null)
//            adapter.startListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }


}
