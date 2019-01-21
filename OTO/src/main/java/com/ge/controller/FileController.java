package com.ge.controller;

import com.ge.service.GeFileService;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.QRCodeUtil;
import utils.Response;
import utils.ResultHttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/13 14:04
 */
@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    private GeFileService geFileService;

    @Value("${photoLocationPath}")
    private String photoLocationPath;

    @Value("${photoUrlPath}")
    private String photoUrlPath;

    @Value("${maxUploadSize}")
    private long maxUploadSize;

    @RequestMapping("upload")
    @ResponseBody
    public Response upload(@RequestParam(value = "headUrl", required = false) MultipartFile file,
                           HttpServletRequest request) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        long muSize = maxUploadSize / 2;
        long fileSize = file.getSize();
        if (muSize < fileSize) {
            response = new Response(ResultHttpStatus.INTERNAL_ERROR.getValue(), "图片超过" + muSize / 1024 / 1024 + "M，请重新上传");
        } else {
            String targetFileName = geFileService.upload(file, photoLocationPath);
            //return url： http://{url}/photo/{targetFileName}
            String url = geFileService.setImageHttpUrl(request, photoUrlPath, targetFileName);
            Map fileMap = Maps.newHashMap();
            fileMap.put("fileName", targetFileName);
            fileMap.put("url", url);
            response.setData(fileMap);
        }
        return response;
    }

    @RequestMapping("downExcelTemp")
    @ResponseBody
    public Response downExcelTemp(HttpServletResponse res) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geFileService.download(res);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public Response importExcel(@RequestParam(value = "excel", required = false) MultipartFile file) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        String fileName = file.getOriginalFilename();
        try {

            response.setMsg(geFileService.importExcel(fileName, file));
        } catch (Exception e) {
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
        }
        return response;

    }

    /**
     * 方法废弃
     * 图片访问，请使用 http://{url}/photo/{xxx.jpg}
     */
    @Deprecated
    @RequestMapping(value = "/getImage/{fileName}/{suffix}", method = RequestMethod.GET)
    public void testpic(HttpServletResponse response, @PathVariable String fileName, @PathVariable String suffix) throws IOException {
        FileInputStream fis = null;
        File file = new File(photoLocationPath + "/" + fileName);
        fis = new FileInputStream(file);
        String fileExtensionName = "image/" + suffix;
        //设置返回的文件类型
        response.setContentType(fileExtensionName);
        IOUtils.copy(fis, response.getOutputStream());
    }

    /**
     * 生成二维码
     *
     * @param response
     * @param httpUrl
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("qrCode")
    @ResponseBody
    public void qrCode(HttpServletResponse response, @RequestParam String httpUrl) throws ServletException, IOException {
        QRCodeUtil.qrCode(response, httpUrl);
    }

    /**
     * @param response
     * @param httpUrl  需要生成pdf的请求地址
     * @param downName 生成pdf后的文件名
     * @return
     */
    @RequestMapping("pdfConvert")
    @ResponseBody
    public Response htmlToPdf(HttpServletResponse response, @RequestParam String httpUrl, String downName) {
        return geFileService.htmlToPdf(response, httpUrl, downName);
    }
}
