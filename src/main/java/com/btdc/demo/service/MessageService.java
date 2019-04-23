package com.btdc.demo.service;

import com.btdc.demo.model.Message;

import java.util.List;


public interface MessageService {

    Integer addMessage(Message message);

    List<Message> getConversationList(Integer userId, Integer offset, Integer limit);

    List<Message> getConversationDeatil(String conversationId, Integer offset, Integer limit);

    Integer getUnreadCount(Integer userId, String conversationId);

    Integer deleteMessage(Integer hasRead, Integer id);

    Message getMessageById(Integer id);
}
