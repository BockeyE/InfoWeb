package com.InfoWeb.demo.service.impl;

import com.InfoWeb.demo.dao.NewsDAO;
import com.InfoWeb.demo.model.News;
import com.InfoWeb.demo.service.NewsService;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Override
    public List<News> getLatestNews(Integer userid, Integer offset, Integer limit) {
        return newsDAO.selectByUserIdAndOffset(userid,offset,limit);
    }

    @Override
    public Integer addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

    @Override
    public News getById(Integer newsId) {
        return newsDAO.getById(newsId);
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        //xxx. = a.jpg
        Integer dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos < 0){
            return  null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-","") + "."+fileExt;
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name=" + fileName;
    }

    @Override
    public Integer updateCommentCount(Integer id, Integer count) {
        return newsDAO.updateCommentCount(id,count);
    }

    @Override
    public Integer updateLikeCount(Integer id, Integer count) {
        return newsDAO.updateLikeCount(id,count);
    }

    @Override
    public List<News> getLatestNews(Integer offset, Integer limit) {
        return newsDAO.selectNewsByOffset(offset,limit);
    }
}
