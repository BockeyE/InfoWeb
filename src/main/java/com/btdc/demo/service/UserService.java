package com.btdc.demo.service;


import com.btdc.demo.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


public interface UserService {

    Map<String, Object> register(String username, String password);

    Map<String, Object> login(String username, String password);

    String addLoginTicket(Integer userId);

    User getUser(Integer id);

    void logout(String ticket);


}
