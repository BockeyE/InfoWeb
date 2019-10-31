package com.InfoWeb.demo.controller;

import com.InfoWeb.demo.model.FileEntity;
import com.InfoWeb.demo.service.impl.FileToDbService;
import com.InfoWeb.demo.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //    @Resource
//    GridFileServiceImpl gridFileService;
    @Resource
    FileToDbService fileToDbService;
    @Value("${localURL}")
    String localURL;

    @RequestMapping("/pic/show/{md}")
    public Map showFile(@PathVariable String md, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            InputStream fis = gridFileService.getFile(md);
            FileEntity byMD5 = fileToDbService.findByMD5(md);
            if (byMD5 != null) FileWriteToOut(byMD5.getFileBytes(), outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/image", method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new
                    File(ToutiaoUtil.IMAGE_DIR + imageName)), response.getOutputStream());
        } catch (Exception e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping(value = "/uploadImage/", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
//            String mongoid = gridFileService.saveImage(file);
            String hash = fileToDbService.saveFile(file.getBytes(), file.getOriginalFilename());
            if (hash == null) {
                return ToutiaoUtil.getJSONString(1, "上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0, localURL + "/pic/show/" + hash);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传图片失败");
        }

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


    private void FileWriteToOut(byte[] bytes, OutputStream out) throws IOException {
        out.write(bytes);
        out.flush();
        out.close();
    }


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


}
