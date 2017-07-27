package com.hover.hf.bean;

import java.io.Serializable;

/**
 * Created by hover on 2017/6/16.
 */

public class Hot implements Serializable{
    private int id;
    private String source;
    private String thumb;
    private String title;
    private String url;
    private String view_type;
    private int isVide;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    public boolean isVide() {
        if (isVide == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setVide(boolean vide) {
        if (vide == false) {
            isVide = 0;
        } else {
            isVide = 1;
        }
    }
}
