package com.ge.controller;


import java.util.*;
import java.text.*;

import com.common.Const;
import com.ge.po.GeUser;
import com.ge.service.GeUserService;
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

import javax.servlet.http.HttpSession;


@Api(value = " geUser-controller", description = "ge_user")
@Controller
@RequestMapping("/geUser")
public class GeUserController {

    private static Logger logger = Logger.getLogger(GeUserController.class);

    //=============================================
    @Autowired
    private GeUserService geUserService;


    //=============================================

    @ApiOperation(value = "add ge_user")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addGeUser", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geUser/addGeUser", description = "add ge_user")
    public Response addGeUser(@RequestBody GeUser geUser) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geUserService.addGeUser(geUser);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }

    }


    @ApiOperation(value = "update ge_user")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/updateGeUser", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geUser/updateGeUser", description = "update ge_user")
    public Response updateGeUser(@RequestBody GeUser geUser) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geUserService.updateGeUser(geUser);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "delete ge_user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping("/deleteGeUser")
    @AuthorizedAccess(roles = "admin", permissions = "/geUser/deleteGeUser", description = "delete ge_user")
    public Response deleteGeUser(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geUserService.deleteGeUserByPK(id);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "get  ge_user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/getGeUser")
    @AuthorizedAccess(permissions = "/geUser/getGeUser", description = "get ge_user")
    public Response getGeUser(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(geUserService.getGeUserByPK(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation(value = "query ge_user by condition 查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionMap", value = "查询的关键字Map", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/queryGeUser")
    @AuthorizedAccess(permissions = "/geUser/queryGeUser", description = "query ge_user")
    public TableRes queryGeUser(@RequestParam Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            Map<String, Object> conditionMap = (Map<String, Object>) conditionMapPage.get(
                    "conditionMap");
            int page = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get(
                    "limit"));

            PageModel<GeUser> queryData = geUserService.queryGeUserBySearch(conditionMap,
                    page, pageSize);
            response.setData(queryData.getDataList());
            response.setCount(queryData.getTotalRecord());

            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return response;
        }
    }

    /**
     * 用户登陆
     *
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Response<GeUser> login(@RequestParam Map<String, String> map, HttpSession session) {
        String username = map.get("username");
        String password = map.get("password");
        Response<GeUser> response = geUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout")
    @ResponseBody
    public Response<String> logout(HttpSession session) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        session.removeAttribute(Const.CURRENT_USER);
        response.setMsg("注销成功");
        return response;
    }
}

