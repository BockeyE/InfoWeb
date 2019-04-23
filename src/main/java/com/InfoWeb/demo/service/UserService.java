package com.InfoWeb.demo.service;


import com.InfoWeb.demo.model.User;

import java.util.Map;


public interface UserService {

    Map<String, Object> register(String username, String password);

    Map<String, Object> login(String username, String password);

    String addLoginTicket(Integer userId);

    User getUser(Integer id);

    void logout(String ticket);


}
