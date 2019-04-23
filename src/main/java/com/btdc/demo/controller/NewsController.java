package com.btdc.demo.controller;



import com.btdc.demo.model.vo.CommentVO;
import com.btdc.demo.model.Comment;
import com.btdc.demo.model.EntityType;
import com.btdc.demo.model.HostHolder;
import com.btdc.demo.model.News;
import com.btdc.demo.service.*;
import com.btdc.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private GridFileService gridService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/image",method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new
                    File(ToutiaoUtil.IMAGE_DIR + imageName)),response.getOutputStream());

        }catch (Exception e){
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping(value = "/uploadImage/",method = {RequestMethod.GET})
    public String uploadImage(){
        return "upload";
    }


    @RequestMapping(value = "/uploadImage/",method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file")MultipartFile file){
        try{
            //String fileUrl = newsService.saveImage(file);
            String mongoid = gridService.saveImage(file);
            if(mongoid == null){
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0,mongoid);
        }catch (Exception e){
            logger.error("上传图片失败" + e.getMessage());
            return  ToutiaoUtil.getJSONString(1,"上传图片失败");
        }

    }

    @RequestMapping(value = "/user/addNews",method = {RequestMethod.GET})
    public String addNews(){
        return "addNews";
    }

    @RequestMapping(value = "/user/addNews/",method = {RequestMethod.POST})
    // @ResponseBody
    public String addNewsPost(@RequestParam("image")MultipartFile image,
                              @RequestParam("title") String title,
                              @RequestParam("link") String link){
        try{
            String fileUrl = gridService.saveImage(image);
            if(fileUrl == null){
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }

            News news = new News();
            news.setCreateDate(new Date());
            news.setTitle(title);
            news.setImage(fileUrl);
            news.setLink(link);
            if(hostHolder.getUser() != null){
                news.setUserId(hostHolder.getUser().getId());
            }else{
                //设置一个匿名用户
                news.setUserId(3);
            }
            newsService.addNews(news);

            // return ToutiaoUtil.getJSONString(0,fileUrl);
            return  "redirect:/";
        }catch (Exception e){
            logger.error("添加资讯失败" + e.getMessage());
            return  ToutiaoUtil.getJSONString(1,"发布资讯失败");
        }
    }

    @RequestMapping(value = {"/news/{newsId}"} ,method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model){
        try{
            News news = newsService.getById(newsId);

            if(news != null){
                int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0 ;
                if(localUserId != 0){
                    model.addAttribute("like",likeService.getLikeStatus(localUserId,EntityType.ENTITY_NEWS,news
                            .getId()));
                }else {
                    model.addAttribute("like",0);
                }
                List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
                List<CommentVO> commentVOs = new ArrayList<>();
                for(Comment comment : comments){
                    CommentVO commentVO = new CommentVO();
                    commentVO.setComment(comment);
                    commentVO.setUser(userService.getUser(comment.getUserId()));
                    commentVOs.add(commentVO);
                }
                model.addAttribute("comments",commentVOs);
            }

            model.addAttribute("news",news);
            model.addAttribute("owner",userService.getUser(news.getUserId()));
        }catch (Exception e){
            logger.error("获取资讯明细错误"+e.getMessage());
        }

        return "detail";
    }
}
