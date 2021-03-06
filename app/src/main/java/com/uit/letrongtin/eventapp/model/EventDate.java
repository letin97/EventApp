package com.uit.letrongtin.eventapp.model;

/**
 * Created by Le Trong Tin on 3/27/2018.
 */

public class EventDate {

    private String name;
    private String description;
    private String imageLink;
    private String date;

    public EventDate() {
    }

    public EventDate(String name, String description, String imageLink, String date) {
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
