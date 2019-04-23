package com.InfoWeb.demo.service;



import com.InfoWeb.demo.model.News;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface NewsService {

     List<News> getLatestNews(Integer userid, Integer offset, Integer limit);

     Integer addNews(News news);

     News getById(Integer newsId);

     String saveImage(MultipartFile file) throws IOException;

     Integer updateCommentCount(Integer id, Integer count);

     Integer updateLikeCount(Integer id, Integer count);

     List<News> getLatestNews(Integer offset, Integer limit);
}
