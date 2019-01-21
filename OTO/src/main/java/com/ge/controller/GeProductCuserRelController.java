package com.ge.controller;


import java.util.*;
import java.text.*;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ge.po.GeProductCuserRel;
import com.ge.service.GeProductCuserRelService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.annotations.Param;
import utils.*;
import utils.annotations.AuthorizedAccess;

import com.common.exception.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


import com.ge.dao.*;
import com.ge.po.*;
import com.ge.service.*;


@Api(value = " geProductCuserRel-controller", description = "ge_product_cuser_rel")
@Controller
@RequestMapping("/geProductCuserRel")
public class GeProductCuserRelController {

    private static Logger logger = Logger.getLogger(GeProductCuserRelController.class);

    //=============================================
    @Autowired
    private GeProductCuserRelService geProductCuserRelService;


    //=============================================

    @ApiOperation(value = "add ge_product_cuser_rel")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addGeProductCuserRel", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geProductCuserRel/addGeProductCuserRel", description = "add ge_product_cuser_rel")
    public Response addGeProductCuserRel(@RequestBody GeProductCuserRel geProductCuserRel) {

        geProductCuserRel.setId(null);      // 看系统需要，是不是自增长

//	  GeProductCuserRel geProductCuserRel = new GeProductCuserRel ();
//	   也可以传MAP格式的自己解析 
//		    
//	//		
//		//				geProductCuserRel . setId ( Integer.parseInt((String) geProductCuserRelMap.get("id") ));
//		//		
//	//		
//		//				geProductCuserRel . setCid ( Integer.parseInt((String) geProductCuserRelMap.get("cid") ));
//		//		
//	//		
//		//				geProductCuserRel . setPid ( Integer.parseInt((String) geProductCuserRelMap.get("pid") ));
//		//		
//	//		
//		//			  DateFormat validTimeDf = new SimpleDateFormat("yyyy-mm-dd");
//	        try {
//	            Date validTimeValue = validTimeDf.parse((String) geProductCuserRelMap.get("validTime"));
//	            geProductCuserRel . setValidTime ( validTimeValue );
//	        } catch (ParseException e) {
//	            e.printStackTrace();
//	        } 
//		      
//		//		
//	//		
//		//			  DateFormat createTimeDf = new SimpleDateFormat("yyyy-mm-dd");
//	        try {
//	            Date createTimeValue = createTimeDf.parse((String) geProductCuserRelMap.get("createTime"));
//	            geProductCuserRel . setCreateTime ( createTimeValue );
//	        } catch (ParseException e) {
//	            e.printStackTrace();
//	        } 
//		      
//		//		
//		
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geProductCuserRelService.addGeProductCuserRel(geProductCuserRel);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }

    }


    @ApiOperation(value = "update ge_product_cuser_rel")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/updateGeProductCuserRel", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geProductCuserRel/updateGeProductCuserRel", description = "update ge_product_cuser_rel")
    public Response updateGeProductCuserRel(@RequestBody GeProductCuserRel geProductCuserRel) {


        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geProductCuserRelService.updateGeProductCuserRel(geProductCuserRel);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    /// public void deleteGeProductCuserRelByPK(java.lang.Integer id) throws MyException ;

    @ApiOperation(value = "delete ge_product_cuser_rel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping("/deleteGeProductCuserRel")
    @AuthorizedAccess(roles = "admin", permissions = "/geProductCuserRel/deleteGeProductCuserRel", description = "delete ge_product_cuser_rel")
    public Response deleteGeProductCuserRel(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geProductCuserRelService.deleteGeProductCuserRelByPK(id);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "get  ge_product_cuser_rel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/getGeProductCuserRel")
    @AuthorizedAccess(permissions = "/geProductCuserRel/getGeProductCuserRel", description = "get ge_product_cuser_rel")
    public Response getGeProductCuserRel(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(geProductCuserRelService.getGeProductCuserRelByPK(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation(value = "query ge_product_cuser_rel by condition 查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionMap", value = "查询的关键字Map", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/queryGeProductCuserRel")
    @AuthorizedAccess(permissions = "/geProductCuserRel/queryGeProductCuserRel", description = "query ge_product_cuser_rel")
    public TableRes queryGeProductCuserRel(@RequestParam Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            Map<String, Object> conditionMap = (Map<String, Object>) conditionMapPage.get(
                    "conditionMap");
            int page = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get(
                    "limit"));

            PageModel<GeProductCuserRel> queryData = geProductCuserRelService.queryGeProductCuserRelBySearch(conditionMap,
                    page, pageSize);
            response.setData(queryData.getDataList());
            response.setCount(queryData.getTotalRecord());

            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return response;
        }
    }

    @ResponseBody
    @RequestMapping("/relationProduct")
    public void relationProductAndUser(@RequestParam Map<String, Object> map) {
        String cid = map.get("cid").toString();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(map.get("product").toString()).getAsJsonArray();
        List<Map<String, String>> list = gson.fromJson(Jarray, new TypeToken<List<Map<String, String>>>() {
        }.getType());

        geProductCuserRelService.relationProductAndUser(cid, list);

    }

    @ResponseBody
    @RequestMapping("/queryGeProductByCId")
    public Response queryGeProductByCId(@RequestParam Integer cid) {
        return geProductCuserRelService.queryGeProductByCId(cid);
    }

}

