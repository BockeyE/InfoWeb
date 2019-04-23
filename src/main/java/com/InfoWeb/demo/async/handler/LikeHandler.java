package com.InfoWeb.demo.async.handler;


import com.InfoWeb.demo.async.EventHandler;
import com.InfoWeb.demo.async.EventModel;
import com.InfoWeb.demo.async.EventType;
import com.InfoWeb.demo.model.Message;
import com.InfoWeb.demo.model.User;
import com.InfoWeb.demo.service.MessageService;
import com.InfoWeb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Date;
import java.util.List;



@Component
public class LikeHandler implements EventHandler {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        User user = userService.getUser(model.getActorId());
        int toId = model.getEntityOwnerId();
        message.setToId(model.getEntityOwnerId());
        message.setContent("用户" + user.getName() +
                "赞了你的咨询，http://127.0.0.1:8080/news/" +
                (model.getEntityId()));
        // SYSTEM ACCOUNT
        message.setFromId(3);
        message.setCreatedDate(new Date());
        message.setConversationId(3 < toId ? String.format("%d_%d",3,toId) :
                String.format("%d_%d",toId,3));
        messageService.addMessage(message);
        System.out.println("用户" + user.getName() +
                "赞了你的咨询，http://127.0.0.1:8080/news/" +
                (model.getEntityId()));
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
