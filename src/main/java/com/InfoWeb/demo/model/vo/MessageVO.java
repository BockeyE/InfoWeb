package com.InfoWeb.demo.model.vo;


import com.InfoWeb.demo.model.Message;
import com.InfoWeb.demo.model.User;


public class MessageVO {
    private Message message;
    private User user;
    private int unReadCount;


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }
}
