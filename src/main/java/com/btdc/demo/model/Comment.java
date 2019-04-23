package com.btdc.demo.model;



import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer entityId;
    private Integer entityType;
    private String content;
    private Date createdDate;
    private Integer status;


}
