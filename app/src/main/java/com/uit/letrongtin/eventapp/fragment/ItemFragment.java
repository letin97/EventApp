package com.uit.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uit.letrongtin.eventapp.Common.Common;
import com.uit.letrongtin.eventapp.Interface.ItemClickListener;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.activity.NewsWebActivity;
import com.uit.letrongtin.eventapp.database.DataSource.RecentRepository;
import com.uit.letrongtin.eventapp.database.LocalDatabase.LocalDatabase;
import com.uit.letrongtin.eventapp.database.Recent;
import com.uit.letrongtin.eventapp.model.Item;
import com.uit.letrongtin.eventapp.viewholder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ItemFragment extends Fragment {

    private static ItemFragment instance;

    public static ItemFragment getInstance(Context context) {
        if (instance == null){
            instance = new ItemFragment(context);
        }
        return instance;
    }

    // Firebase
    FirebaseDatabase database;
    DatabaseReference item_db;
    FirebaseRecyclerOptions<Item> options;
    FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter;
    Query query;

    // Room Database
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    // RecyclerView
    RecyclerView recyclerView;

    boolean isFavorite = false;

    public ItemFragment(Context context) {

        // Firebase
        database = FirebaseDatabase.getInstance();
        item_db = database.getReference(Common.STR_ITEM);
        query = item_db.orderByChild("star").limitToFirst(10);
        options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ItemViewHolder holder, int position, final Item model) {
                Picasso.get()
                        .load(model.getImageLink())
                        .fit()
                        .into(holder.imgItem, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                holder.txtItemName.setText(model.getName());
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent web = new Intent(getContext(), NewsWebActivity.class);
                        web.putExtra("link", model.getLink());
                        startActivity(web);
                    }
                });

                Drawable myDrawable = getResources().getDrawable(R.drawable.ic_star_fill);

                switch (model.getStar()){
                    case 1:
                    case 2:
                        holder.star1.setImageDrawable(myDrawable);
                        holder.star2.setImageDrawable(myDrawable);
                        break;
                    case 3:
                        holder.star1.setImageDrawable(myDrawable);
                        holder.star2.setImageDrawable(myDrawable);
                        holder.star3.setImageDrawable(myDrawable);
                        break;
                    case 4:
                        holder.star1.setImageDrawable(myDrawable);
                        holder.star2.setImageDrawable(myDrawable);
                        holder.star3.setImageDrawable(myDrawable);
                        holder.star4.setImageDrawable(myDrawable);
                        break;
                    case 5:
                        holder.star1.setImageDrawable(myDrawable);
                        holder.star2.setImageDrawable(myDrawable);
                        holder.star3.setImageDrawable(myDrawable);
                        holder.star4.setImageDrawable(myDrawable);
                        holder.star5.setImageDrawable(myDrawable);
                        break;
                }

                checkItemFavorite(holder, model.getImageLink());

                holder.imgLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFavorite){
                            isFavorite = false;
                            deleteItemRecent(model);
                            holder.imgLove.setImageDrawable(getResources().getDrawable(R.drawable.ic_love));

                        }else {
                            isFavorite = true;
                            addToRecent(model);
                            holder.imgLove.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_fill));

                        }
                    }
                });

            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,false);
                return new ItemViewHolder(view);
            }
        };

        // Room Database
        compositeDisposable = new CompositeDisposable();
        LocalDatabase localDatabase = LocalDatabase.getInstance(context);
        recentRepository = RecentRepository.getInstance(localDatabase.recentDAO());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addToRecent(final Item item){
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String date = dateFormat.format(new Date());

                Recent recent = new Recent(item.getName(), item.getImageLink(),item.getLink(), date, false);

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

    private void checkItemFavorite(final ItemViewHolder holder, final String imageLinkR) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Recent recent = recentRepository.getRecentByImageLink(imageLinkR);

                if (recent!=null){
                    isFavorite = true;
                    holder.imgLove.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_fill));
                }

                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

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

    private void deleteItemRecent(final Item item){

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {

                recentRepository.deleteRecentByName(item.getName());
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
    public void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
