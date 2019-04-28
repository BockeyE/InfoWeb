package com.InfoWeb.demo.model.vo;


import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.model.User;
import lombok.Data;

@Data
public class ViewResult {
    private News news;
    private User user;
    private int like;

}
