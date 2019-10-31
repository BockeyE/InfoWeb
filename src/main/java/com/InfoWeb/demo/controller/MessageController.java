package com.InfoWeb.demo.controller;


import com.InfoWeb.demo.model.vo.MessageVO;
import com.InfoWeb.demo.model.Message;
import com.InfoWeb.demo.model.User;
import com.InfoWeb.demo.service.MessageService;
import com.InfoWeb.demo.service.UserService;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setCreatedDate(new Date());
        msg.setToId(toId);
        msg.setFromId(fromId);
        msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) :
                String.format("%d_%d", toId, fromId));
        messageService.addMessage(msg);
        return ToutiaoUtil.getJSONString(msg.getId());
    }

    @RequestMapping(value = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<MessageVO> messages = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationDeatil(conversationId, 0, 10);
            for (Message message : conversationList) {
                MessageVO messageVO = new MessageVO();
                messageVO.setMessage(message);
                User user = userService.getUser(message.getFromId());
                if (user == null) {
                    continue;
                }
                messageVO.setUser(user);
                messages.add(messageVO);
            }
            model.addAttribute("messages", messages);
            return "letterDetail";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(value = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model, HttpSession
            session) {
        try {
            int localUserId = ((User) session.getAttribute("user")).getId();
            List<MessageVO> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            for (Message msg : conversationList) {
                MessageVO messageVO = new MessageVO();
                messageVO.setMessage(msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                messageVO.setUser(user);
                messageVO.setUnReadCount(messageService.getUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(messageVO);
            }
            model.addAttribute("conversations", conversations);
            return "letter";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }

        return "letter";
    }

    @RequestMapping(value = {"/msg/delete/{messageId}"}, method = {RequestMethod.GET})
    public String deleteMessage(@PathVariable("messageId") int messageId,
                                HttpServletRequest request) {
        Message message = null;
        try {
            message = messageService.getMessageById(messageId);
            int restult = messageService.deleteMessage(1, messageId);

            return "redirect:/msg/detail?conversationId=" + message.getConversationId();
        } catch (Exception e) {
            logger.error("删除会话失败！" + e.getMessage());
        }

        return "redirect:/msg/detail?conversationId=" + message.getConversationId();
    }
}












