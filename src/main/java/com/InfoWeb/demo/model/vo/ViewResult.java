package com.InfoWeb.demo.model.vo;


import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.model.User;


public class ViewResult {
    private News news;
    private User user;
    private int like;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

}
