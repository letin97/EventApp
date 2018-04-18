package com.example.letrongtin.eventapp.model;

public class Item {
    private String name;
    private String description;
    private String link;
    private String imageLink;

    public Item() {
    }

    public Item(String name, String description, String link, String imageLink) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.imageLink = imageLink;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
