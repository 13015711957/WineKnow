package com.example.wine.Bean;

import java.io.Serializable;

public class NewBean implements Serializable {
    private String title;
    private String type;
    private String time;
    private String content;
    private String imgurl;
    private String url;
    private int flag;
    public NewBean() {
        this.flag=0;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public NewBean(String title, String type, String time, String content, String imgurl, String url) {
        this.title = title;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NewBean{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", url='" + url + '\'' +
                ", flag=" + flag +
                '}';
    }
}
