package com.btdc.demo.dao.JPA;

import com.btdc.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bockey
 */
@Repository
public interface CommentJPA extends JpaRepository<Comment, Integer> {
}
