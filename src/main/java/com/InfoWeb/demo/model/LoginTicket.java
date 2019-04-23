package com.InfoWeb.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class LoginTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Date expired;
    private Integer status; //0 ：有效，1:无效
    private String ticket;


}
