package com.btdc.demo.controller;


import com.btdc.demo.async.EventModel;
import com.btdc.demo.async.EventProducer;
import com.btdc.demo.async.EventType;
import com.btdc.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    //@ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      HttpServletResponse response) {
        System.out.println("reg");
        logger.info("/reg/ " + username + " " + password + " ");
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");

                response.addCookie(cookie);
                logger.info("cookie : " + cookie.getValue() + " " + cookie.getName());
                //return ToutiaoUtil.getJSONString(0,"注册成功");
                return "redirect:/";
            } else {
                // return ToutiaoUtil.getJSONString(1,map);
                model.addAttribute("error", "注册失败，请重新注册");
                return "redirect:/register";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            //return  ToutiaoUtil.getJSONString(1,"注册异常");
            model.addAttribute("error", "注册异常");
            return "redirect:/register";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {

        return "register";
    }

    @RequestMapping(value = {"/login/"}, method = {RequestMethod.POST})
    //@ResponseBody
    public String loginPost(Model model, @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                            HttpServletResponse response) {

        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                //return ToutiaoUtil.getJSONString(0,"登录成功");
                eventProducer.startEvent(new EventModel(EventType.LOGIN)
                        .setActorId((int) map.get("userId"))
                        .setExts("username", "牛客")
                        .setExts("to", "534634799@qq.com"));

                return "redirect:/";
            } else {
                //return ToutiaoUtil.getJSONString(1,map);
                model.addAttribute("error", "登录失败,请重新登录!");
                return "redirect:/login";
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            //return  ToutiaoUtil.getJSONString(1,"登录异常");
            model.addAttribute("error", "登录异常");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        logger.info("logout：跳转到首页");
        return "redirect:/";
    }
}
