package com.ge.controller;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/7 17:13
 */
public class PDFUtil {

    public static void main(String[] args) throws IOException, DocumentException {
//        待优化
        String outPath = System.getProperty("user.dir") + "/PDFStore/file";
        String outWaterPath = System.getProperty("user.dir") + "/PDFStore/waterFile";
        File fileDir = new File(outPath);
        File waterFileDir = new File(outWaterPath);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        if (!waterFileDir.exists()) {
            waterFileDir.setWritable(true);
            waterFileDir.mkdirs();
        }


        Document document = new Document();
        try {
            String outPathUrl = outPath + "/HelloWorld.pdf";
            String outWarterPathUrl = outWaterPath + "/HelloWorld1.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outPathUrl));
            document.open();
            document.add(new Paragraph("A Hello World PDF document.11111"));
            document.close();
            writer.close();
            setWaterMarkForPDF(outPathUrl, outWarterPathUrl, "test001");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sourceFilePath    源文件路径
     * @param fileWaterMarkPath 水印生成文件路径
     * @throws Exception
     */
    public static void setWaterMarkForPDF(String sourceFilePath, String fileWaterMarkPath, String waterMarkName) throws Exception {
        String waterPath = Class.class.getClass().getResource("/1.png").getPath();
        PdfReader reader = new PdfReader(sourceFilePath);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileWaterMarkPath));

        int total = reader.getNumberOfPages() + 1;
        PdfContentByte under = null;
        Image img = Image.getInstance(waterPath);
        img.setAbsolutePosition(30, 100);//坐标
        // img.setRotation(-20);//旋转 弧度
        //img.setRotationDegrees(-35);//旋转 角度
        //img.scaleAbsolute(200,100);//自定义大小
        img.scalePercent(100);//依照比例缩放
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        waterMarkName = "www.123.com";
        int j = waterMarkName.length();
        char c = 0;
        int rise = 0;
        for (int i = 1; i < total; i++)  // 每一页都加水印
        {
            rise = 500;
            under = stamp.getUnderContent(i);
            // 添加图片
            under.addImage(img);
            under.beginText();
            under.setColorFill(BaseColor.BLACK);
            under.setFontAndSize(bf, 30);
            // 设置水印文字字体倾斜 开始
            if (j >= 15) {
                under.setTextMatrix(200, 120);
                for (int k = 0; k < j; k++) {
                    under.setTextRise(rise);
                    c = waterMarkName.charAt(k);
                    under.showText(c + "");
                    rise -= 20;
                }
            } else {
                under.setTextMatrix(180, 100);
                for (int k = 0; k < j; k++) {
                    under.setTextRise(rise);
                    c = waterMarkName.charAt(k);
                    under.showText(c + "");
                    rise -= 18;
                }
            }
            // 字体设置结束
            under.endText();

        }

        stamp.close();
        reader.close();
    }


}
