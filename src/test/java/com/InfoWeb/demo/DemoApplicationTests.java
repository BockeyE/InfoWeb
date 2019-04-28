package com.InfoWeb.demo;

import com.InfoWeb.demo.dao.JPA.UserJPA;
import com.InfoWeb.demo.dao.NewsDAO;
import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    UserJPA userJPA;
    @Resource
    NewsDAO dao;

    @Test
    public void contextLoads1() {

        List<News> news = dao.selectRecentNewsByOffset(0, 1);
        System.out.println(news);
    }

}
