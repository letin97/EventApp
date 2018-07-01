package com.uit.letrongtin.eventapp.model;

public class Item {
    private String name;
    private String description;
    private String link;
    private String imageLink;
    private int star;

    public Item() {
    }

    public Item(String name, String description, String link, String imageLink, int star) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.imageLink = imageLink;
        this.star = star;
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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
