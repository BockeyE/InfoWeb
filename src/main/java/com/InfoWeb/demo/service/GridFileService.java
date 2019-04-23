package com.InfoWeb.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface GridFileService {

    String saveImage(MultipartFile file) throws IOException;
}
