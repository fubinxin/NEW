package com.ge.controller;


import java.util.*;

import com.common.Const;
import com.ge.po.GePromotion;
import com.ge.service.GePromotionService;
import utils.*;
import utils.annotations.AuthorizedAccess;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


import com.ge.po.*;
import com.ge.service.*;

import javax.servlet.http.HttpSession;


@Api(value = " geNews-controller", description = "ge_news")
@Controller
@RequestMapping("/geNews")
public class GeNewsController {

    private static Logger logger = Logger.getLogger(GeNewsController.class);

    //=============================================
    @Autowired
    private GePromotionService gePromotionService;

    @Autowired
    private GeUserService geUserService;


    //=============================================

    @ApiOperation(value = "add ge_news")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addGeNews", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geNews/addGeNews", description = "add ge_news")
    public Response addGeNews(HttpSession session, @RequestBody GePromotion gePromotion) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
//            if ((user == null)) {
//                response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
//                response.setMsg("用户未登陆,请登陆");
//                return response;
//            } else {
//                if (!geUserService.checkAdminRole(user).isSuccess()) {
//                    response.setMsg("无权限操作");
//                    return response;
//                }
//            }
            gePromotion.setCreateTime(new Date());
            gePromotionService.addGeNews(gePromotion);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }

    }


    @ApiOperation(value = "update ge_news")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/updateGeNews", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geNews/updateGeNews", description = "update ge_news")
    public Response updateGeNews(HttpSession session, @RequestBody GePromotion gePromotion) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
//            if ((user == null)) {
//                response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
//                response.setMsg("用户未登陆,请登陆");
//                return response;
//            } else {
//                if (!geUserService.checkAdminRole(user).isSuccess()) {
//                    response.setMsg("无权限操作");
//                    return response;
//                }
//            }
            gePromotionService.updateGeNews(gePromotion);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    /// public void deleteGeNewsByPK(java.lang.Integer id) throws MyException ;

    @ApiOperation(value = "delete ge_news")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping("/deleteGeNews")
    @AuthorizedAccess(roles = "admin", permissions = "/geNews/deleteGeNews", description = "delete ge_news")
    public Response deleteGeNews(HttpSession session, @RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
//            GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
//            if ((user == null)) {
//                response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
//                response.setMsg("用户未登陆,请登陆");
//                return response;
//            } else {
//                if (!geUserService.checkAdminRole(user).isSuccess()) {
//                    response.setMsg("无权限操作");
//                    return response;
//                }
//            }
            gePromotionService.deleteGeNewsByPK(id);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "get  ge_news")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/getGeNews")
    @AuthorizedAccess(permissions = "/geNews/getGeNews", description = "get ge_news")
    public Response getGeNews(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(gePromotionService.getGeNewsByPK(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation(value = "query ge_news by condition 查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionMap", value = "查询的关键字Map", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/queryGeNews")
    @AuthorizedAccess(permissions = "/geNews/queryGeNews", description = "query ge_news")
    public TableRes queryGeNews(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 1, false);
    }

    @RequestMapping("/queryAllGeNews")
    @ResponseBody
    public TableRes queryAllGeNews(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 1, true);
    }

    @RequestMapping("/getHeardTitle")
    @ResponseBody
    public TableRes queryGeNewsExport(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 2, false);
    }

    @RequestMapping("/getAllHeardTitle")
    @ResponseBody
    public TableRes queryAllGeNewsExport(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 2, true);
    }

    @RequestMapping("/getModel")
    @ResponseBody
    public TableRes queryModelExport(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 3, false);
    }

    @RequestMapping("/getAllModel")
    @ResponseBody
    public TableRes queryAllModelExport(@RequestParam Map<String, Object> conditionMapPage) {
        return gePromotionService.queryGeNews(conditionMapPage, 3, true);
    }
}


