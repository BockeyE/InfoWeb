package com.InfoWeb.demo.async.handler;

import com.InfoWeb.demo.async.EventHandler;
import com.InfoWeb.demo.async.EventModel;
import com.InfoWeb.demo.async.EventType;
import com.InfoWeb.demo.model.Message;
import com.InfoWeb.demo.service.EmailService;
import com.InfoWeb.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;



@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailService emailService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        int toId = model.getActorId();
        message.setToId(model.getActorId());
        message.setContent("你上次的登录IP异常");
        // SYSTEM ACCOUNT
        message.setFromId(3);
        message.setCreatedDate(new Date());
        message.setConversationId(3 < toId ? String.format("%d_%d",3,toId) :
                String.format("%d_%d",toId,3));
        messageService.addMessage(message);
        System.out.println("你上次的登录IP异常");

       // emailService.sendSimpleMail("534634799@qq.com","头条","你上次的登录IP异常");
        Map<String ,Object> map = new HashMap<>();
        map.put("username","qq1");
        map.put("text","你上次的登录IP异常");
        emailService.sendTemplateMail("534634799@qq.com","头条资讯",
                "email/email",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
