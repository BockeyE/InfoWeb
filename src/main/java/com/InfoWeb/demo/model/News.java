package com.InfoWeb.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String link;
    private String image;
    private Integer likeCount;
    private Integer commentCount;
    private Date createDate;
    private Integer userId;

}
