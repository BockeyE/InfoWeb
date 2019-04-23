package com.InfoWeb.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Date createdDate;
    private Integer hasRead;
    private String conversationId;


}
