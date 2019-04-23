package com.btdc.demo.service.impl;

import com.btdc.demo.dao.LoginTicketDAO;
import com.btdc.demo.dao.UserDAO;
import com.btdc.demo.model.LoginTicket;
import com.btdc.demo.model.User;
import com.btdc.demo.service.UserService;
import com.btdc.demo.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Override
    public Map<String, Object> register(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user != null){
            map.put("msgname","用户名已经被注册了");
            return  map;
        }

        //密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user == null){
            map.put("msgname","用户名不存在");
            return  map;
        }

        if(!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码不正确");
            return  map;
        }

        map.put("userId",user.getId());
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return  map;
    }

    @Override
    public String addLoginTicket(Integer userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    @Override
    public User getUser(Integer id) {
        return userDAO.selectById(id);
    }

    @Override
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket,1);
    }


}
