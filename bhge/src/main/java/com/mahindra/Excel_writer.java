package com.mahindra;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

//import com.exception.SimpleException;


/**
 * 从excel读取数据/往excel中写入 excel有表头，表头每列的内容对应实体类的属性
 *
 * @author nagsh
 */
public class Excel_writer {


    public static void main(String[] args) throws IOException {
        String path = "E:/";
        String fileName = "被保险人员清单(新增)04";
        String fileType = "xlsx";
//        List<InsuraceExcelBean> list = new ArrayList<>();
//        for(int i=0; i<6; i++){
//            InsuraceExcelBean bean = new InsuraceExcelBean();
//            bean.setInsuraceUser("test"+i);
//            bean.setBankCardId("4444444444"+i+","+"55544444444444"+i+","+"999999999999999"+i);
//            bean.setIdCard("666666"+i);
//            bean.setBuyTime("2016-05-06");
//            bean.setInsEndTime("2016-05-07");
//            bean.setInsStartTime("2017-05-06");
//            bean.setMoney("20,000");
//            bean.setType("储蓄卡");
//
//            list.add(bean);
//        }
        String title[] = {"被保险人姓名", "身份证号", "账户类型", "银行卡号", "保险金额(元)", "购买时间", "保单生效时间", "保单失效时间"};
//        createExcel("E:/被保险人员清单(新增).xlsx","sheet1",fileType,title);

//        writer(path, fileName, fileType,list,title);
    }

    public static void writer(OutputStream os, String fileType, List<List<String>> list, String titleRow[]) throws IOException {
        writer(os, fileType, list, titleRow, false);
    }

    @SuppressWarnings("resource")
    public static void writer(OutputStream os, String fileType, List<List<String>> list, String titleRow[], boolean needColor) throws IOException {
        Workbook wb = null;
        Sheet sheet = null;
        //创建工作文档对象
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();

        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook();

        } else {
            throw new SimpleException("文件格式不正确");
        }
        //创建sheet对象
        if (sheet == null) {
            sheet = (Sheet) wb.createSheet("sheet1");
        }

        //添加表头
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        row.setHeight((short) 540);
//        cell.setCellValue("被保险人员清单");    //创建第一行
//
        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);

//        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
//        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
//
//        cell.setCellStyle(style); // 样式，居中
//
//        Font font = wb.createFont();
////        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        font.setFontName("宋体");
//        font.setFontHeight((short) 280);
//        style.setFont(font);
//        // 单元格合并
//        // 四个参数分别是：起始行，起始列，结束行，结束列
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
//        sheet.autoSizeColumn(5200);

        Row row = sheet.createRow(0);    //创建第二行
        Cell cell;
        int fi = 0;
        if (titleRow != null && titleRow.length > 0) {
            fi++;
            for (int i = 0; i < titleRow.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(titleRow[i]);
                cell.setCellStyle(style); // 样式，居中
                sheet.setColumnWidth(i, 10 * 256);
            }
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(4, 40 * 256);
        }
        row.setHeight((short) 540);


        if (fi == 0) {
            //循环写入行数据
            for (fi = 0; fi < list.size(); fi++) {
                row = (Row) sheet.createRow(fi);
                row.setHeight((short) 500);
                List<String> rowList = list.get(fi);
                if (rowList == null || rowList.isEmpty()) continue;

                for (int j = 0; j < rowList.size(); j++) {
                    Cell fcell = row.createCell(j);

                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);

                    fcell.setCellValue((rowList.get(j)));
                    fcell.setCellStyle(style);
                }

            }
        } else {
            //循环写入行数据
            for (fi = 1; fi <= list.size(); fi++) {
                row = (Row) sheet.createRow(fi);
                row.setHeight((short) 500);
                List<String> rowList = list.get(fi - 1);
                if (rowList == null || rowList.isEmpty()) continue;

                for (int j = 0; j < rowList.size(); j++) {
                    //row.createCell(j).setCellValue((rowList.get(j)));
                    Cell fcell = row.createCell(j);


                    fcell.setCellValue((rowList.get(j)));

                    if (needColor && j == 14) {
                        try {
                            CellStyle stylef = wb.createCellStyle();

                            stylef.setFillPattern(CellStyle.SOLID_FOREGROUND);
                            int ch = Integer.parseInt(rowList.get(j));
                            if (ch < 8)
                                stylef.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
                            else
                                stylef.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);

                            fcell.setCellStyle(stylef);
                        } catch (Exception e) {

                        }

                    }

//			if (needColor && j == 15 )
//			{
//				try
//				{
//					CellStyle stylef = wb.createCellStyle();
//
//					stylef.setFillPattern(FillPatternType. SOLID_FOREGROUND );
//					int ch = Integer.parseInt(rowList.get(14));
//					if (ch< 5 )
//						stylef.setFillForegroundColor(HSSFColor.DARK_GREEN.index);
//					else if(ch >=5 && ch < 9 )
//						stylef.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
//					else if (ch >= 9 && ch < 13)
//						stylef.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
//
//					fcell.setCellStyle(stylef);
//				}catch (Exception e)
//				{
//
//				}
//
//				//fcell.setCellStyle(style);
//			}
                }

            }
        }

        //创建文件流
        //写入数据
        wb.write(os);
        //关闭文件流
        os.close();
    }

}