package com.example.letrongtin.eventapp.database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.letrongtin.eventapp.database.Recent;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecentDAO {

    @Query("SELECT * FROM recent ORDER BY saveTime DESC LIMIT 10")
    Flowable<List<Recent>> getAllRecent();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecent(Recent...recents);

    @Update
    void updateRecent(Recent...recents);

    @Delete
    void deleteRecent(Recent...recents);

    @Query("DELETE FROM recent")
    void deleteAllRecent();

    @Query("SELECT * FROM recent WHERE imageLink = :imageLink")
    Recent getRecentByImageLink(String imageLink);

    @Query("DELETE FROM recent WHERE name = :name")
    void deleteRecentByName(String name);

}
