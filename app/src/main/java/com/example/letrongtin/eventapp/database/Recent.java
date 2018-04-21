package com.example.letrongtin.eventapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "recent",primaryKeys = {"imageLink"})
public class Recent {

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "imageLink")
    @NonNull
    private String imageLink;

    @ColumnInfo(name = "link")
    @NonNull
    private String link;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    @ColumnInfo(name = "isLove")
    private boolean isLove;

    public Recent(@NonNull String name, @NonNull String imageLink, @NonNull String link, String saveTime, boolean isLove) {
        this.name = name;
        this.imageLink = imageLink;
        this.link = link;
        this.saveTime = saveTime;
        this.isLove = isLove;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(@NonNull String imageLink) {
        this.imageLink = imageLink;
    }

    @NonNull
    public String getLink() {
        return link;
    }

    public void setLink(@NonNull String link) {
        this.link = link;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }
}
