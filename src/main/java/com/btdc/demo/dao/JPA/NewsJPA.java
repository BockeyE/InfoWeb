package com.btdc.demo.dao.JPA;

import com.btdc.demo.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bockey
 */
@Repository
public interface NewsJPA extends JpaRepository<News, Integer> {
}
