package com.example.wine.Bean;

public class NewBean {
    private String title;
    private String time;
    private String content;
    private String imgurl;
    private String url;
    public NewBean() {
    }

    public NewBean(String title, String time, String content, String imgurl, String url) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.imgurl = imgurl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewBean{" +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
