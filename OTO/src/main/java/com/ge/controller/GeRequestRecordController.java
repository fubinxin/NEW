package com.ge.controller;

import com.ge.po.GeRequestRecord;
import com.ge.service.GeRequestRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.Response;
import utils.ResultHttpStatus;
import utils.TableRes;
import utils.annotations.AuthorizedAccess;

import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/11 12:19
 */
@Controller
@RequestMapping("/getHttpRequest")
public class GeRequestRecordController {
    @Autowired
    public GeRequestRecordService geRequestRecordService;

    @ApiOperation(value = "add addRequestRecord")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addRequestRecord", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "All", permissions = "/getHttpRequest/addRequestRecord", description = "add addRequestRecord")
    public Response addRequestRecord(@RequestBody GeRequestRecord geRequestRecord) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        return response;
    }


    @ApiOperation(value = "count RequestRecord")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/countRequestRecord", method = RequestMethod.GET)
    @AuthorizedAccess(roles = "All", permissions = "/getHttpRequest/countRequestRecord", description = "countRequestRecord")
    protected Response countUserClick(String requestUser, String requesturlSimple) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        response.setData(geRequestRecordService.countUserClick(requestUser, requesturlSimple));
        return response;
    }

    @ResponseBody
    @RequestMapping("queryRecord")
    protected TableRes queryRecord(@RequestParam Map<String, Object> map) {
        return geRequestRecordService.queryRecord(map);
    }
}
