package com.InfoWeb.demo.controller;


import com.InfoWeb.demo.model.User;
import com.InfoWeb.demo.model.vo.CommentVO;
import com.InfoWeb.demo.model.Comment;
import com.InfoWeb.demo.model.EntityType;
import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.service.*;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

//    @Autowired
//    private GridFileService gridService;


    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;


    @RequestMapping(value = "/user/addNews", method = {RequestMethod.GET})
    public String addNews() {
        System.out.println("get new add");
        return "addNews";
    }

    @RequestMapping(value = "/user/addNews/", method = {RequestMethod.POST})
    @ResponseBody
    public String addNewsPost(@RequestParam("image") String image,
                              @RequestParam("title") String title,
                              @RequestParam("link") String link, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            News news = new News();
            news.setCreateDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            if (user != null) {
                news.setUserId(user.getId());
            } else {
                //设置一个匿名用户
                news.setUserId(0);
            }
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0, image);
//            return  "redirect:/";
        } catch (Exception e) {
            logger.error("添加资讯失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布资讯失败");
        }
    }

    @RequestMapping(value = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model,HttpSession session) {
        try {
            News news = newsService.getById(newsId);
            User user = (User) session.getAttribute("user");
            if (news != null) {
                int localUserId = user != null ? user.getId() : 0;
                if (localUserId != 0) {
                    model.addAttribute("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news
                            .getId()));
                } else {
                    model.addAttribute("like", 0);
                }
                List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
                List<CommentVO> commentVOs = new ArrayList<>();
                for (Comment comment : comments) {
                    CommentVO commentVO = new CommentVO();
                    commentVO.setComment(comment);
                    commentVO.setUser(userService.getUser(comment.getUserId()));
                    commentVOs.add(commentVO);
                }
                model.addAttribute("comments", commentVOs);
            }
            model.addAttribute("news", news);
            Integer userId = news.getUserId();
            if (userId != null) {
                model.addAttribute("owner", userService.getUser(userId));
            } else {
                model.addAttribute("owner", userService.getUser(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取资讯明细错误" + e.getMessage());
        }
        return "detail";
    }
}
