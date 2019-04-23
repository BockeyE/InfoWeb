package com.InfoWeb.demo.service.impl;

import com.InfoWeb.demo.dao.MessageDAO;
import com.InfoWeb.demo.model.Message;
import com.InfoWeb.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDAO messageDAO;


    @Override
    public Integer addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    @Override
    public List<Message> getConversationList(Integer userId, Integer offset, Integer limit) {
        return messageDAO.getConversationList(userId,offset,limit);
    }

    @Override
    public List<Message> getConversationDeatil(String conversationId, Integer offset, Integer limit) {
        return messageDAO.getConversationDetail(conversationId,offset,limit);
    }

    @Override
    public Integer getUnreadCount(Integer userId,String conversationId) {
        return messageDAO.getConversationUnReadCount(userId,conversationId);
    }

    @Override
    public Integer deleteMessage(Integer hasRead, Integer id) {
        return messageDAO.updateMessageHasRead(hasRead,id);
    }

    @Override
    public Message getMessageById(Integer id) {
        return messageDAO.getMessageById(id);
    }
}
