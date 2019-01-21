package com.mahindra.util;

import com.mahindra.common.exception.MyException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

/**
 * Created by weican
 * Date：2018/9/18 11:06
 * Description：
 */
public class ExcelUtil {

    public static int[] checkExcelTitle(InputStream excelInput) throws Exception, MyException {
        Integer[] selectCols = new Integer[6];

        //读取xlsx文件
        Workbook wb = ExcelUtil.createExcel(excelInput);
        if (wb == null) {
            throw new MyException("未读取到内容,请检查路径！");
        }

        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            throw new MyException("当前sheet中无内容,请检查excel！");
        }

        Row row = sheet.getRow(0);
        if (row == null) {
            throw new MyException("读取第一行表头数据错误！");
        }

        if (row.getPhysicalNumberOfCells() > 30) {
            throw new MyException("读取第一行表头列总数不能超过30列！");
        }


        for (ExcelTitle excelTitle : ExcelTitle.values()) {
            int index = -1;
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                if (excelTitle.getName().equals(row.getCell(i).toString().trim())
                        || excelTitle.getCode().equals(row.getCell(i).toString().trim())) {
                    index = i;
                    break;
                }
            }

            if (index == -1 && !(excelTitle.getCode().equals(ExcelTitle.Redundant.getCode()) || excelTitle.getCode().equals(ExcelTitle.RepBlock.getCode()))) {
                throw new MyException("[" + excelTitle.getCode() + "]表头不存在！");
            } else {
                selectCols = selectColsIndex(selectCols, index, excelTitle.getCode());
            }
        }
        //Integer[] 转 int[]
        return Arrays.stream(selectCols).mapToInt(Integer::valueOf).toArray();
    }


    //NO TAG_NAME PID SIG_TYPE SENSOR_TYPE DESCRIPTION TAG_NAME Redundant

    /**
     * 识别中英文Title(若是中文和英文都出现，则以先出现的先识别)
     * @param selectCols
     * @param index
     * @param titleName
     */
    public static Integer[] selectColsIndex(Integer[] selectCols, int index, String titleName) {
        if (titleName.equals(ExcelTitle.NO.getCode()) || titleName.equals(ExcelTitle.NO.getName())) {
            selectCols[0] = selectCols[0] == null ? index : selectCols[0];
        } else if (titleName.equals(ExcelTitle.TAGNAME.getCode()) || titleName.equals(ExcelTitle.TAGNAME.getName())) {
            selectCols[1] = selectCols[1] == null ? index : selectCols[1];
        } else if (titleName.equals(ExcelTitle.PID.getCode()) || titleName.equals(ExcelTitle.PID.getName())) {
            selectCols[2] = selectCols[2] == null ? index : selectCols[2];
        } else if (titleName.equals(ExcelTitle.SIGTYPE.getCode()) || titleName.equals(ExcelTitle.SIGTYPE.getName())) {
            selectCols[3] = selectCols[3] == null ? index : selectCols[3];
        } else if (titleName.equals(ExcelTitle.SENSORTYPE.getCode()) || titleName.equals(ExcelTitle.SENSORTYPE.getName())) {
            selectCols[4] = selectCols[4] == null ? index : selectCols[4];
        } else if (titleName.equals(ExcelTitle.DESCRIPTION.getCode()) || titleName.equals(ExcelTitle.DESCRIPTION.getName())) {
            selectCols[5] = selectCols[5] == null ? index : selectCols[5];
        } else if (titleName.equals(ExcelTitle.Redundant.getCode()) || titleName.equals(ExcelTitle.Redundant.getName())) {
            if (index != -1) {
                selectCols = ArrayUtils.add(selectCols, 6, index);
            }
        } else if (titleName.equals(ExcelTitle.RepBlock.getCode()) || titleName.equals(ExcelTitle.RepBlock.getName())) {
            if (index != -1) {
                selectCols = ArrayUtils.add(selectCols, 7, index);
            }
        }
        return selectCols;
    }


    public static Workbook createExcel(InputStream inp) throws IOException, InvalidFormatException {
        // If clearly doesn't do mark/reset, wrap up
        if(! inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }

        if(POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if(POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }
}
