package com.btdc.demo.dao.JPA;

import com.btdc.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bockey
 */
@Repository
public interface MessageJPA extends JpaRepository<Message, Integer> {
}
