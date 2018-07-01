package com.uit.letrongtin.eventapp.model;

public class NewsItem {

    private String newskey;
    private String itemkey;

    public NewsItem() {
    }

    public NewsItem(String newskey, String itemkey) {
        this.newskey = newskey;
        this.itemkey = itemkey;
    }

    public String getNewskey() {
        return newskey;
    }

    public void setNewskey(String newskey) {
        this.newskey = newskey;
    }

    public String getItemkey() {
        return itemkey;
    }

    public void setItemkey(String itemkey) {
        this.itemkey = itemkey;
    }
}
