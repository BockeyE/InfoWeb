package com.InfoWeb.demo.service.impl;

import com.InfoWeb.demo.dao.CommentDAO;
import com.InfoWeb.demo.model.Comment;
import com.InfoWeb.demo.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public List<Comment> getCommentsByEntity(Integer entityId, Integer entityType) {
        return commentDAO.selectByEntity(entityId,entityType);
    }

    @Override
    public Integer addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    @Override
    public Integer getCommentCount(Integer entityId, Integer entityType) {
        return commentDAO.getCommentCount(entityId,entityType);
    }
}
