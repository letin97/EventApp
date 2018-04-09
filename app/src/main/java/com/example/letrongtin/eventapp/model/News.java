package com.example.letrongtin.eventapp.model;

import java.io.Serializable;

public class News implements Serializable {

    private String source;
    private String title;
    private String description;
    private String link;
    private String imageLink;
    private String pubDate;

    public News() {
    }

    public News(String source, String title, String description, String link, String imageLink, String pubDate) {
        this.source = source;
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageLink = imageLink;
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
