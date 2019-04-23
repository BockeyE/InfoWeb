package com.InfoWeb.demo.service;




import com.InfoWeb.demo.model.Comment;

import java.util.List;


public interface CommentService {

     List<Comment> getCommentsByEntity(Integer entityId, Integer entityType);

     Integer addComment(Comment comment);

     Integer getCommentCount(Integer entityId, Integer entityType);
}
