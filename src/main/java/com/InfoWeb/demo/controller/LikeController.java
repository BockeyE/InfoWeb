package com.InfoWeb.demo.controller;


import com.InfoWeb.demo.async.EventModel;
import com.InfoWeb.demo.async.EventProducer;
import com.InfoWeb.demo.async.EventType;
import com.InfoWeb.demo.model.EntityType;
import com.InfoWeb.demo.model.HostHolder;
import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.service.LikeService;
import com.InfoWeb.demo.service.NewsService;
//import org.apache.ibatis.annotations.Param;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = {"/like"},method = {RequestMethod.GET, RequestMethod.POST})
    //@ResponseBody
    public String like(@RequestParam ("newsId") int newsId){
        long likeCount = likeService.like(hostHolder.getUser().getId(),
                 EntityType.ENTITY_NEWS,newsId);
        System.out.println("like likecount " + likeCount);
        //更新喜欢数
        News news = newsService.getById(newsId);
        newsService.updateLikeCount(newsId,(int)likeCount);
        eventProducer.startEvent(new EventModel(EventType.LIKE)
                .setEntityOwnerId(news.getUserId())
                .setActorId(hostHolder.getUser().getId())
                .setEntityId(newsId));
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
//        return  "redirect:/";
    }

    @RequestMapping(value = {"/dislike"},method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId){
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS,newsId);
        //更新喜欢数
        newsService.updateLikeCount(newsId,(int)likeCount);

        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
//        return  "redirect:/";
    }
}
