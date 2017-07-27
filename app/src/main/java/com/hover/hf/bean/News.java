package com.hover.hf.bean;

import java.io.Serializable;

/**
 * Created by huanghaibin
 * on 16-5-25.
 */
public class News implements Serializable {

    public static final int TYPE_HREF = 0;
    public static final int TYPE_SOFTWARE = 1;
    public static final int TYPE_QUESTION = 2;
    public static final int TYPE_BLOG = 3;
    public static final int TYPE_TRNSLATE = 4;
    public static final int TYPE_EVENT = 5;
    public static final int TYPE_NEWS = 6;

    protected long id;
    protected int commentCount;
    protected int type;
    protected int recommend;
    protected String title;
    protected String body;
    protected String author;
    protected String href;
    protected String pubDate;
    protected int viewCount;
    protected String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRecommend() {
        if (recommend == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setRecommend(boolean recommend) {
        if (recommend == true) {
            this.recommend = 1;
        } else {
            this.recommend = 0;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
