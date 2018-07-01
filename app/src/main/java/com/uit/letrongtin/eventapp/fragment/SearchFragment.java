package com.uit.letrongtin.eventapp.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.uit.letrongtin.eventapp.R;
import com.uit.letrongtin.eventapp.adapter.SearchFragmentAdapter;
import com.uit.letrongtin.eventapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {

    // RecyclerView
    RecyclerView recyclerView;
    TextView notification;

    // Word Search
    String str_search ="";
    Context context;

    List<News> newsList;
    SearchFragmentAdapter adapter;

    public SearchFragment(Context context) {
        newsList = new ArrayList<>();
        this.context = context;
        adapter = new SearchFragmentAdapter(newsList, context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            newsList.clear();

             str_search = bundle.getString("search");

             Client client = new Client("L56233Y3TS", "66fd1f52283af6ad31d6186202df5361");
             Index index = client.getIndex("News");

             Query querySearch = new com.algolia.search.saas.Query(str_search)
                     .setAttributesToRetrieve("*")
                     .setHitsPerPage(10)
                     .setAnalytics(false);

             index.searchAsync(querySearch, new CompletionHandler() {
                 @Override
                 public void requestCompleted(JSONObject jsonObject, AlgoliaException e) {
                     try {
                         if (jsonObject != null){
                             JSONArray hits = jsonObject.getJSONArray("hits");
                             for (int i = 0; i < hits.length(); i++) {
                                 JSONObject object = hits.getJSONObject(i);
                                 String objectID = object.getString("objectID");
                                 String source = object.getString("source");
                                 String title = object.getString("title");
                                 String description = object.getString("description");
                                 String link = object.getString("link");
                                 String imageLink = object.getString("imageLink");
                                 String type = object.getString("type");
                                 String pubDate = object.getString("pubDate");
                                 newsList.add(new News(objectID, source, type, title, description, link, imageLink, pubDate));
                                 adapter.notifyDataSetChanged();
                             }
                         }

                     } catch (JSONException e1) {
                         e1.printStackTrace();
                     }
                 }
             });
        }

        notification = view.findViewById(R.id.notification);

        if (newsList.size() == 0){
            notification.setVisibility(View.VISIBLE);
        } else {
            notification.setVisibility(View.INVISIBLE);
        }

        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
