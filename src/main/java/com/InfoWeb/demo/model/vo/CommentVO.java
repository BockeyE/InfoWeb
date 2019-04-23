package com.InfoWeb.demo.model.vo;


import com.InfoWeb.demo.model.Comment;
import com.InfoWeb.demo.model.User;


public class CommentVO {
    private User user;
    private Comment comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
