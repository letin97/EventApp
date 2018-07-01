package com.uit.letrongtin.eventapp.database.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.uit.letrongtin.eventapp.database.Recent;
import com.uit.letrongtin.eventapp.database.TypeNews;

import static com.uit.letrongtin.eventapp.database.LocalDatabase.LocalDatabase.DATABASE_VERSION;

@Database(entities = {Recent.class, TypeNews.class}, version = DATABASE_VERSION)
public abstract class LocalDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "EventApp";

    public abstract RecentDAO recentDAO();

    public abstract TypeNewsDAO typeNewsDAO();

    private static LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, LocalDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
