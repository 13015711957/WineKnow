package com.example.wine.Bean;

public class Favorite_item {
    String title;
    String url;
    String time;
    String id;
    int type;
    public Favorite_item() {
    }

    public Favorite_item(String title, String url, String time, String id, int type) {
        this.title = title;
        this.url = url;
        this.time = time;
        this.id = id;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorite_item{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
