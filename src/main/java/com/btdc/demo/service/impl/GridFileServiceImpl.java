package com.btdc.demo.service.impl;

import com.btdc.demo.service.GridFileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author bockey
 */
@Service
public class GridFileServiceImpl implements GridFileService {

    @Resource
    GridFsTemplate gridFsTemplate;

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getName());
        return "http://localhost:8080/pic/show/" + (objectId.toString());
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
