package com.mahindra.controller;

import com.mahindra.po.OriginalLoopNamePo;
import com.mahindra.service.LoopNameService;
import com.mahindra.service.StepService;
import com.mahindra.util.Response;
import com.mahindra.util.ResultHttpStatus;
import com.mahindra.util.TableRes;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weican on 2018/8/3.
 */
@Api(value = " LoopNameController")
@RestController
@RequestMapping("/loop")
public class LoopNameController {

    private static Logger log = LoggerFactory.getLogger(LoopNameController.class);


    @Autowired
    private LoopNameService loopNameService;

    @Autowired
    private StepService stepService;


    @ApiOperation(value = "fileUpload")
    @PostMapping("/fileUpload")
    @ApiImplicitParam(name = "controllId", value = "控制器ID", paramType = "query", dataType = "String", required = true)
    public Response fileUpload(@ApiParam(value = "上传文件", required = true) @RequestParam("upFile") MultipartFile upFile, String controllId) {
        log.info("fileName ============= " + upFile.getOriginalFilename());
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            if (!(upFile.getOriginalFilename().endsWith(".xlsx") || upFile.getOriginalFilename().endsWith(".xls"))) {
                response = new Response(ResultHttpStatus.INTERNAL_ERROR.getValue(), "文件格式不对!");
            } else if(StringUtils.isEmpty(controllId)){
                response = new Response(ResultHttpStatus.INTERNAL_ERROR.getValue(), "控制器不能为空!");
            } else {
                loopNameService.getUpExcelGoods(upFile, controllId);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }

    @ApiOperation(value = "download")
    @GetMapping("download")
    @ApiImplicitParam(name = "stepNumber", value = "下载文件步骤(1,2,3,4,5)", paramType = "query", dataType = "int", required = true)
    public void downLoad(HttpServletResponse response, Integer stepNumber) {
        String fileName = "Arranged_PROCESS_" + stepNumber + ".xlsx";
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
            response.setContentType("application/octet-stream");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            loopNameService.downloadExcel(toClient, stepNumber);
            toClient.flush();
            response.getOutputStream().close();
            log.info("download fileName = " + fileName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ApiOperation(value = "getGoodsListByDiscernOrder", notes = "获取每一步的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stepNumber", required = true, value = "步骤(1,2,3,4,5)", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "controllId", value = "控制器ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sigType", value = "sigType", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sensorType", value = "sensorType", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cardNo", value = "模块ID", paramType = "query", dataType = "int")
    })
    @GetMapping("getGoodsListByDiscernOrder")
    public TableRes<List<OriginalLoopNamePo>> getGoodsListByDiscernOrder(Integer stepNumber, String controllId, String sigType, String sensorType, Integer cardNo) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            if (stepNumber == null) {
                response = new TableRes(ResultHttpStatus.INTERNAL_ERROR.getValue(), "请求错误，步骤不能为空");
            }

            List<OriginalLoopNamePo> list = new ArrayList<>();
            if (stepNumber == 2) {
                list = stepService.secondStep();
            } else {
                list = loopNameService.getGoodsListByDiscernOrder(controllId, sigType, sensorType, cardNo);
            }

            response.setData(list);
            response.setCount(list.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }


    @ApiOperation(value = "getSigTypeByControll", notes = "获取SIG Type的列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "controllId", value = "控制器ID", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("getSigTypeByControll")
    public Response<List<String>> getSigTypeByControll(String controllId) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(loopNameService.getSigTypeByControll(controllId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }

    /**
    @ApiOperation(value = "getSensorTypeByControll", notes = "获取SENSOR Type的列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "controllId", value = "控制器ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sigType", value = "sigType", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("getSensorTypeByControll")
    public Response<List<String>> getSensorTypeByControll(String controllId, String sigType) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(loopNameService.getSensorTypeByControll(controllId, sigType));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }
     */


    @ApiOperation(value = "step", notes = "标识冗余信号及相关信号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stepNumber", value = "步骤数(1,3,4,5)", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("step")
    public Response step(Integer stepNumber) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            stepService.step(stepNumber);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }


    @ApiOperation(value = "chooseMark", notes = "选中的标识冗余信号、相关信号或不相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1：冗余；2：相关；3：不相关", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orgNos", value = "1,3,4,5,6", required = true, paramType = "query", dataType = "String[]")
    })
    @PostMapping("chooseMark")
    public Response chooseMark(Integer type, String[] orgNos) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());

        try {
            stepService.chooseMark(type, orgNos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
        return response;
    }
}
