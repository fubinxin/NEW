package com.mahindra;

/**
 * Created by Administrator on 2018/4/16.
 */

import com.mahindra.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Excel_reader {
    public static final String Empty_str = " ";
    private static final boolean zero_row_filter = true;

    //*************xlsx文件读取函数************************

    public static ArrayList<String>  xlsx_reade_head(String excel_url ) throws IOException {

        //读取xlsx文件
        HSSFWorkbook xssfWorkbook = null;
        //寻找目录读取文件
        File excelFile = new File(excel_url);
        InputStream is = new FileInputStream(excelFile);
        xssfWorkbook = new HSSFWorkbook(is);

        if(xssfWorkbook==null){
            System.out.println("未读取到内容,请检查路径！");
            return null;
        }

       // ArrayList<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
        //遍历xlsx中的sheet

            HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet == null) {
                return null;
            }
            int maxCols =0;
            // 对于每个sheet，读取其中的每一行
            int rowNum = 0;
            {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null)  return null;

                ArrayList<String> curarr=new ArrayList<String>();
                int nowCols = xssfRow.getLastCellNum();
                if (maxCols < nowCols) maxCols = nowCols;

                for(int columnNum = 0 ; columnNum< maxCols ; columnNum++){
                    HSSFCell cell = xssfRow.getCell(columnNum );

                    String cellValue = getValue(cell);
                    if (cellValue !=null && !cellValue.isEmpty()) {
                        curarr.add(Trim_str(getValue(cell)));
                    }
                }
                return curarr;
           //     ans.add(curarr);
            }


    }

    public ArrayList<ArrayList<String>> xlsx_reader(String excel_url ) throws IOException {

        //读取xlsx文件
        HSSFWorkbook xssfWorkbook = null;
        //寻找目录读取文件
        File excelFile = new File(excel_url);
        InputStream is = new FileInputStream(excelFile);
        xssfWorkbook = new HSSFWorkbook(is);

        if(xssfWorkbook==null){
            System.out.println("未读取到内容,请检查路径！");
            return null;
        }

        ArrayList<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
        int numSheet = 0;
        {
            HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                return null;
            }

            int maxCols =0;
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) continue;

                //System.out.println("本行行高是："+ xssfRow.getZeroHeight());
                if (zero_row_filter && xssfRow.getZeroHeight()) continue;
                ArrayList<String> curarr=new ArrayList<String>();
                int nowCols = xssfRow.getLastCellNum();
                if (maxCols < nowCols) maxCols = nowCols;

                for(int columnNum = 0 ; columnNum< maxCols ; columnNum++){
                    HSSFCell cell = xssfRow.getCell(columnNum );

                    String cellValue = getValue(cell);
                    if (cellValue !=null && !cellValue.isEmpty()) {
                        curarr.add(Trim_str(getValue(cell)));
                    }
                }
                ans.add(curarr);
            }
        }
        return ans;
    }

    //excel_name为文件名，arg为需要查询的列号
    //返回二维字符串数组
    @SuppressWarnings({ "resource", "unused" })
    public ArrayList<ArrayList<String>> xlsx_reader(InputStream excelInput,int ... args) throws Exception {

        //读取xlsx文件
        Workbook wb = ExcelUtil.createExcel(excelInput);
        //寻找目录读取文件
//        wb = new HSSFWorkbook(excelInput);

        if(wb==null){
            System.out.println("未读取到内容,请检查路径！");
            return null;
        }

        ArrayList<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
        //遍历xlsx中的sheet
        int numSheet = 0;
        {
            Sheet sheet = wb.getSheetAt(numSheet);
            if (sheet == null) {
                return null;
            }
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;
                //System.out.println("本行行高是："+ xssfRow.getZeroHeight());
                if (zero_row_filter && row.getZeroHeight()) continue;

                ArrayList<String> curarr=new ArrayList<String>();
                for(int columnNum = 0 ; columnNum< args.length ; columnNum++){
                    Cell cell = row.getCell(args[columnNum]);


                    String cellValue = getValue(cell);
                    if (cellValue !=null && !cellValue.isEmpty()) {
                        curarr.add(Trim_str(getValue(cell)));
                    }
                }
                ans.add(curarr);
            }
        }
        return ans;
    }

//    //判断后缀为xlsx的excel文件的数据类
//    @SuppressWarnings("deprecation")
//    private static String getValue(HSSFCell xssfRow) {
//        if(xssfRow==null){
//            return "---";
//        }
//        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(xssfRow.getBooleanCellValue());
//        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
//            double cur=xssfRow.getNumericCellValue();
//            long longVal = Math.round(cur);
//            Object inputValue = null;
//            if(Double.parseDouble(longVal + ".0") == cur)
//                inputValue = longVal;
//            else
//                inputValue = cur;
//            return String.valueOf(inputValue);
//        } else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_BLANK || xssfRow.getCellType() == xssfRow.CELL_TYPE_ERROR){
//            return "---";
//        }
//        else {
//            return String.valueOf(xssfRow.getStringCellValue());
//        }
//    }

    //判断后缀为xls的excel文件的数据类型
    @SuppressWarnings("deprecation")
    private static String getValue(Cell cell) {
        if(cell == null){
            return Empty_str ;
        }
        int cellType = cell.getCellType();
        //System.out.println("hssfCell.getCellType()"+cellType);
        if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if(cellType == Cell.CELL_TYPE_STRING ){
            return String.valueOf(cell.getStringCellValue());
        }
        else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            double cur=cell.getNumericCellValue();
            long longVal = Math.round(cur);
            Object inputValue = null;
            if(Double.parseDouble(longVal + ".0") == cur)
                inputValue = longVal;
            else
                inputValue = cur;
            return String.valueOf(inputValue);
        } else if(cellType == Cell.CELL_TYPE_BLANK ||cellType == Cell.CELL_TYPE_FORMULA  || cellType == Cell.CELL_TYPE_ERROR){
            return Empty_str ;
        }
        else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    //字符串修剪  去除所有空白符号 ， 问号 ， 中文空格
    static private String Trim_str(String str){
        if(str==null)
            return null;
        return str.replaceAll("[\\s\\?]", "").replace("　", "");
    }
}
