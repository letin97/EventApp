package com.example.letrongtin.eventapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "typenews",primaryKeys = {"type"})
public class TypeNews {

    @ColumnInfo(name = "type")
    @NonNull
    private String type;

    public TypeNews(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}
