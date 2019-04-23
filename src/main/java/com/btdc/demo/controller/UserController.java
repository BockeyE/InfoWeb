package com.btdc.demo.controller;


import com.btdc.demo.dao.UserDAO;
import com.btdc.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/test")
    public String test() {
        return "hello,test!";
    }

    @RequestMapping(value = "/find")
    User findUser(@RequestParam String id) {
        return userDAO.selectById(Integer.valueOf(id));
    }

}
