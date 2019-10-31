package com.InfoWeb.demo.dao.JPA;

import com.InfoWeb.demo.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author bockey
 */
public interface FileDao extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {
}
