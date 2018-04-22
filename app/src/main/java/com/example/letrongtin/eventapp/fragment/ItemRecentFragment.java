package com.example.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.letrongtin.eventapp.Interface.ItemRecentAdapterClickListener;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.activity.NewsWebActivity;
import com.example.letrongtin.eventapp.adapter.ItemRecentAdapter;
import com.example.letrongtin.eventapp.database.DataSource.RecentRepository;
import com.example.letrongtin.eventapp.database.LocalDatabase.LocalDatabase;
import com.example.letrongtin.eventapp.database.Recent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class ItemRecentFragment extends Fragment implements ItemRecentAdapterClickListener {

    private static ItemRecentFragment instance;

    RecyclerView recyclerView;
    List<Recent> recentList;
    ItemRecentAdapter adapter;

    // Room Databasevs
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    public static ItemRecentFragment getInstance(Context context) {
        if (instance == null){
            instance = new ItemRecentFragment(context);
        }
        return instance;
    }

    public ItemRecentFragment(Context context) {

        recentList = new ArrayList<>();
        adapter = new ItemRecentAdapter(getContext(), recentList, this);

        // Room Database
        compositeDisposable = new CompositeDisposable();
        LocalDatabase localDatabase = LocalDatabase.getInstance(context);
        recentRepository = RecentRepository.getInstance(localDatabase.recentDAO());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_recent, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setAdapter(adapter);
        loadItemRecents();
        return view;

    }

    private void loadItemRecents() {
        Disposable disposable = recentRepository.getAllRecent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recent>>() {
                    @Override
                    public void accept(List<Recent> recents) throws Exception {
                        getAllItemRecentsSuccess(recents);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    private void getAllItemRecentsSuccess(List<Recent> recents) {
        recentList.clear();
        recentList.addAll(recents);
        adapter.notifyDataSetChanged();
    }

    private void updateIsLoveItemRecent(final int position){

        recentList.get(position).setLove(!recentList.get(position).isLove());

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {

                recentRepository.updateRecent(recentList.get(position));
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

    private void updateSaveTimeItemRecent(final int position){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = dateFormat.format(new Date());

        recentList.get(position).setSaveTime(date);

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {

                recentRepository.updateRecent(recentList.get(position));
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

    private void deleteItemRecent(final int position){

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {

                recentRepository.deleteRecent(recentList.get(position));
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
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent web = new Intent(getContext(), NewsWebActivity.class);
        web.putExtra("link", recentList.get(position).getLink());
        startActivity(web);
        updateSaveTimeItemRecent(position);
    }

    @Override
    public void onIconClick(View view, int position) {
        showPopupMenu(view, position);
    }

    private void showPopupMenu(View view, final int positon){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        if (recentList.get(positon).isLove()){
            popupMenu.getMenu().getItem(0).setTitle("Xóa vào yêu thích");
        }else {
            popupMenu.getMenu().getItem(0).setTitle("Thêm khỏi yêu thích");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_favourite:
                        updateIsLoveItemRecent(positon);
                        return true;
                    case R.id.action_remove_recent:
                        deleteItemRecent(positon);
                        return true;
                    default:
                }
                return false;
            }
        });

        popupMenu.show();

    }
}
