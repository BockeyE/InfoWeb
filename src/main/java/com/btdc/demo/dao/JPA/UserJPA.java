package com.btdc.demo.dao.JPA;

import com.btdc.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bockey
 */
@Repository
public interface UserJPA extends JpaRepository<User, Integer> {
}
