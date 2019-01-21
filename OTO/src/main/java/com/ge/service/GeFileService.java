package com.ge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/13 13:46
 */
@Service
public interface GeFileService {
    String upload(MultipartFile file, String path);

    String setImageHttpUrl(HttpServletRequest request, String photoUrlPath, String imgPath);

    Response htmlToPdf(HttpServletResponse response, String httpUrl, String downName);

    void download(HttpServletResponse response);

    String importExcel(String fileName, MultipartFile file) throws Exception;
}
