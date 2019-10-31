package com.InfoWeb.demo.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author bockey
 */
@Entity
@Data
public class FileEntity {

    @Id
    @GeneratedValue
    Long id;

    String fileMD5;

    String fileName;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    byte[] fileBytes;

}
