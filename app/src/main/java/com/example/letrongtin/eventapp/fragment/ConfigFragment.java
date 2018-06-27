package com.example.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.letrongtin.eventapp.Interface.CheckItemClickListener;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.adapter.ConfigAdapter;
import com.example.letrongtin.eventapp.database.DataSource.TypeNewsRepository;
import com.example.letrongtin.eventapp.database.LocalDatabase.LocalDatabase;
import com.example.letrongtin.eventapp.database.TypeNews;

import java.util.ArrayList;
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
public class ConfigFragment extends Fragment implements CheckItemClickListener{

    private static ConfigFragment instance;

    public static ConfigFragment getInstance(Context context) {
        if (instance == null){
            instance = new ConfigFragment(context);
        }
        return instance;
    }

    ArrayList<TypeNews> typeNewsList;
    CompositeDisposable compositeDisposable;
    TypeNewsRepository typeNewsRepository;

    ListView listView;
    ConfigAdapter adapter;

    public ConfigFragment(Context context) {
        typeNewsList = new ArrayList<>();

        String[] nameType = new String[]{"Thể thao", "Văn hóa", "Đời sống", "Pháp luật"};

        adapter = new ConfigAdapter(nameType,context, typeNewsList, this);

        // Room Database
        compositeDisposable = new CompositeDisposable();
        LocalDatabase localDatabase = LocalDatabase.getInstance(context);
        typeNewsRepository = TypeNewsRepository.getInstance(localDatabase.typeNewsDAO());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        listView = view.findViewById(R.id.list_view);

        listView.setAdapter(adapter);

        loadTypeNews();

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
        adapter.notifyDataSetChanged();
    }

    private void addToTypeNews(final String type){
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                TypeNews typeNews = new TypeNews(type);
                typeNewsRepository.insertType(typeNews);
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

    private void deleteTypeNews(final String type){

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {

                typeNewsRepository.deleteTypeByTypeChose(type);
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
    public void onClick(View view, int position, boolean ischeck, String name) {
        if (ischeck){
            addToTypeNews(name);
        } else {
            deleteTypeNews(name);
        }
    }
}
