package com.btdc.demo.controller;

import com.btdc.demo.service.impl.GridFileServiceImpl;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author bockey
 */
@Controller
public class FileController {


    @Resource
    GridFileServiceImpl gridFileService;


//    @RequestMapping("/download/{id}")
//    public Map downloadFile(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
//        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            Object[] ret = gridFileService.downloadFile(id, outputStream);
//            String file = (String) ret[0];
//            System.out.println(file);
//            Integer len = (Integer) ret[1];
//            InputStream ips = (InputStream) ret[2];
//            FileDownload.setHeader(request, response, file, len);
//            mongoFileService.inputReadWriteToOutput(ips, outputStream);
//            closeAll(ips, outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @RequestMapping("/pic/show/{id}")
    public Map showFile(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "inline");
//            response.setHeader("Connection", "keep-alive");

            InputStream fis = gridFileService.getFile(id);
            FileWriteToOut(fis, outputStream);
//            response.setContentType("application/pdf");
//            response.setHeader("Content-MD5", (String) ret[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void FileWriteToOut(InputStream fis, OutputStream out) throws IOException {
        byte[] buf = new byte[1024 * 10];
        int read = 0;
        while ((read = fis.read(buf)) != -1) {
            out.write(buf, 0, read);
        }
        out.flush();
        fis.close();

    }

}
