package com.InfoWeb.demo.interceptor;



import com.InfoWeb.demo.dao.LoginTicketDAO;
import com.InfoWeb.demo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if(httpServletRequest.getCookies() != null){
            for(Cookie cookie :httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

//        if(ticket != null){
//            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
//            if(loginTicket == null || loginTicket.getExpired().before(new Date())
//                    || loginTicket.getStatus() != 0){
//                return  true;
//            }
//
//            User user = userDAO.selectById(loginTicket.getUserId());
//            hostHolder.setUser(user);
//        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        if(modelAndView != null && hostHolder.getUser() != null){
//            modelAndView.addObject("user",hostHolder.getUser());
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        hostHolder.clear();
    }
}
