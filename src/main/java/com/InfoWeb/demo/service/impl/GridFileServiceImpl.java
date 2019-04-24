package com.InfoWeb.demo.service.impl;

import com.InfoWeb.demo.service.GridFileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author bockey
 */
@Service
public class GridFileServiceImpl implements GridFileService {

    @Resource
    GridFsTemplate gridFsTemplate;
    @Value("${localURL}")
    String url;

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getName());
        return url + "/pic/show/" + (objectId.toString());
    }

    public InputStream getFile(String id) throws IOException {
        Query q = new Query(Criteria.where("_id").is(id));
        GridFSFile one = gridFsTemplate.findOne(q);
        if (one != null) {
            return gridFsTemplate.getResource(one).getInputStream();
        }
        return null;

    }
}
