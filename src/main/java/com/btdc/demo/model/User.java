package com.btdc.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;


    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }


}
