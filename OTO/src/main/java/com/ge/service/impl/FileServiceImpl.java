package com.ge.service.impl;

import com.common.exception.MyException;
import com.ge.dao.GeCategoryMapper;
import com.ge.dao.GeCertifiedUserMapper;
import com.ge.po.GeCertifiedUser;
import com.ge.service.GeFileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pdf.HtmlToPdfUtil;
import utils.Response;
import utils.ResultHttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static utils.PropertiesUtil.getProperty;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/13 13:47
 */
@Service("GeFileService")
public class FileServiceImpl implements GeFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private GeCategoryMapper geCategoryMapper;

    @Autowired
    private GeCertifiedUserMapper geCertifiedUserMapper;

    public static String pdfLocationPath;

    public static String waterPdfLocationPath;

    public static String systemOs;

    /**
     * 根据服务器系统设置pdf文件路径
     */
    static {
        systemOs = System.getProperty("os.name");
        if (systemOs.toLowerCase().startsWith("win")) {
            pdfLocationPath = getProperty("pdfLocationPath");
            waterPdfLocationPath = getProperty("waterPdfLocationPath");
        } else {
            pdfLocationPath = getProperty("pdfLocationPathForLinux");
            waterPdfLocationPath = getProperty("waterPdfLocationPathForLinux");
        }
    }

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            //把内存图片写入磁盘中
            file.transferTo(targetFile);
            //文件已经成功上传

        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

    @Override
    public String setImageHttpUrl(HttpServletRequest request, String photoUrlPath, String fileExtensionName) {
        String ip = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//        String suffix = fileExtensionName.substring(fileExtensionName.lastIndexOf("." )+ 1);
        String httpurl = "http://" + ip + photoUrlPath + "/" + fileExtensionName;
        return httpurl;
    }

    @Override
    public Response htmlToPdf(HttpServletResponse res, String httpUrl, String downName) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());

        File fileDir = new File(pdfLocationPath);
        File waterFileDir = new File(waterPdfLocationPath);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        if (!waterFileDir.exists()) {
            waterFileDir.setWritable(true);
            waterFileDir.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + ".pdf";
        String source = pdfLocationPath + fileName;
        String outPath = waterPdfLocationPath + fileName;
        try {
            boolean isSuccess = HtmlToPdfUtil.convert(httpUrl, source, systemOs);
            if (isSuccess) {
                //水印加密
                HtmlToPdfUtil.setWaterMarkForPDF(source, outPath, "");
                download(res, downName, fileName);
            } else {
                throw new MyException("pdf下载异常");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    /**
     * 响应下载
     *
     * @param resp
     * @param downloadName 下载的pdf文件名
     * @param fileName     系统中真正的文件名
     */
    public void download(HttpServletResponse resp, String downloadName, String fileName) {
        try {
            downloadName = new String(downloadName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        String pdfPath = pdfLocationPath + fileName;
        String waterPdfPath = waterPdfLocationPath + fileName;
        File modelFile = new File(pdfPath);
        File file = new File(waterPdfPath);
        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + downloadName + ".pdf");
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                //完成后删除本地pdf文件
                modelFile.delete();
                file.delete();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

    }

    public void download(HttpServletResponse resp) {
        String path = HtmlToPdfUtil.class.getClassLoader().getResource("GeCertifiedUser.zip").getPath();
        File file = new File(path);
        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + "GeCertifiedUser" + ".zip");
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }


    public String importExcel(String fileName, MultipartFile file) throws Exception {
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }

        Sheet sheet = wb.getSheetAt(0);

        List<GeCertifiedUser> list = new ArrayList<>();
        List<Map<String, String>> mapList = geCategoryMapper.selectAllAreaMap();
        Map enumMap = new HashMap();
        for (Map<String, String> map : mapList) {
            Set entrySet = map.entrySet();//entrySet()方法返回反应map键值的映射关系，存储在set集合中
            enumMap.put(((Map.Entry) entrySet.toArray()[1]).getValue(), ((Map.Entry) entrySet.toArray()[0]).getValue());
        }
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            GeCertifiedUser geCertifiedUser = new GeCertifiedUser();

            row.getCell(5).setCellType(CellType.STRING);
            if (row.getCell(0) == null) {
                throw new MyException("导入失败(第" + (r + 1) + "行,【姓名】不能为空)");
            } else {
                row.getCell(1).setCellType(CellType.STRING);
                if (StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                    throw new MyException("导入失败(第" + (r + 1) + "行,【姓名】不能为空)");
                }
            }
            //姓名
            geCertifiedUser.setName(row.getCell(0).getStringCellValue());

            if (row.getCell(1) != null) {
                geCertifiedUser.setGrade(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null) {
                geCertifiedUser.setOrganization(row.getCell(2).getStringCellValue());
            }
            if (row.getCell(3) != null) {
                Date date = row.getCell(3).getDateCellValue();
                geCertifiedUser.setEntryTime(date);
            }
            String[] areaArr = new String[2];
            if (row.getCell(4) != null) {
                String area = row.getCell(4).getStringCellValue();
                areaArr = row.getCell(4).getStringCellValue().split(",");
                if (areaArr.length < 2 || enumMap.get(areaArr[0]) == null || enumMap.get(areaArr[1]) == null) {
                    throw new MyException("导入失败(第" + (r + 1) + "行,【省市】格式不正确)或者类型不存在。输入格式为：【北区,北京】");
                }
            }
            geCertifiedUser.setArea(enumMap.get(areaArr[0]) + " " + enumMap.get(areaArr[1]));
            geCertifiedUser.setCreateTime(new Date());
            if (row.getCell(5) != null) {
                geCertifiedUser.setMobile(row.getCell(5).getStringCellValue());
            }
            list.add(geCertifiedUser);
        }
        geCertifiedUserMapper.batchInsert(list);
        return "导入成功";
    }

}
