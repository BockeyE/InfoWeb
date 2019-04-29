package com.InfoWeb.demo.controller;


import com.InfoWeb.demo.async.EventModel;
import com.InfoWeb.demo.async.EventProducer;
import com.InfoWeb.demo.async.EventType;
import com.InfoWeb.demo.model.HostHolder;
import com.InfoWeb.demo.service.UserService;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(@RequestParam("username") String username,
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
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }


    @RequestMapping(value = {"/login/"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String loginPost(Model model, @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                            HttpServletResponse response, HttpServletRequest request) {

        System.out.println("login ctrl");
        try {


            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                HttpSession session = request.getSession();
                session.setAttribute("user", userService.getUser(username));
                return ToutiaoUtil.getJSONString(0, "登录成功");
//                eventProducer.startEvent(new EventModel(EventType.LOGIN)
//                        .setActorId((int) map.get("userId"))
//                        .setExts("username", "牛客")
//                        .setExts("to", "534634799@qq.com"));
            } else {
                return ToutiaoUtil.getJSONString(1, map);

            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登录异常");

        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        logger.info("logout：跳转到首页");
        return "redirect:/";
    }
}
