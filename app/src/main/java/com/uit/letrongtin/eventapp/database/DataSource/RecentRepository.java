package com.uit.letrongtin.eventapp.database.DataSource;

import com.uit.letrongtin.eventapp.database.LocalDatabase.RecentDAO;
import com.uit.letrongtin.eventapp.database.Recent;

import java.util.List;

import io.reactivex.Flowable;

public class RecentRepository implements RecentDAO{

    private RecentDAO recentDAO;
    private static RecentRepository instance;

    public static RecentRepository getInstance(RecentDAO recentDAO) {
        if (instance == null){
            instance = new RecentRepository(recentDAO);
        }
        return instance;
    }

    private RecentRepository(RecentDAO recentDAO) {
        this.recentDAO = recentDAO;
    }

    @Override
    public Flowable<List<Recent>> getAllRecent() {
        return recentDAO.getAllRecent();
    }

    @Override
    public void insertRecent(Recent... recents) {
        recentDAO.insertRecent(recents);
    }

    @Override
    public void updateRecent(Recent... recents) {
        recentDAO.updateRecent(recents);
    }

    @Override
    public void deleteRecent(Recent... recents) {
        recentDAO.deleteRecent(recents);
    }

    @Override
    public void deleteAllRecent() {
        recentDAO.deleteAllRecent();
    }

    @Override
    public Recent getRecentByImageLink(String imageLink) {
        return recentDAO.getRecentByImageLink(imageLink);
    }

    @Override
    public void deleteRecentByName(String name) {
        recentDAO.deleteRecentByName(name);
    }


}
