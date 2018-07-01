package com.uit.letrongtin.eventapp.database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.uit.letrongtin.eventapp.database.TypeNews;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TypeNewsDAO {

    @Query("SELECT * FROM typenews")
    Flowable<List<TypeNews>> getAllType();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertType(TypeNews...typeNews);

    @Update
    void updateType(TypeNews...typeNews);

    @Delete
    void deleteType(TypeNews...typeNews);

    @Query("DELETE FROM typenews")
    void deleteAllType();

    @Query("SELECT * FROM typenews WHERE type = :type")
    TypeNews getTypeByTypeChose(String type);

    @Query("DELETE FROM typenews WHERE type = :type")
    void deleteTypeByTypeChose(String type);
}
