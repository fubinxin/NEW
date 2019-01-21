package com.ge.controller;


import com.ge.dao.GeProductCuserRelMapper;
import com.ge.po.GeCertifiedUser;
import com.ge.service.GeCertifiedUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import utils.PageModel;
import utils.Response;
import utils.ResultHttpStatus;
import utils.TableRes;
import utils.annotations.AuthorizedAccess;

import java.util.Map;


@Api(value = " geCertifiedUser-controller", description = "ge_certified_user")
@Controller
@RequestMapping("/geCertifiedUser")
public class GeCertifiedUserController {

    private static Logger logger = Logger.getLogger(GeCertifiedUserController.class);

    //=============================================
    @Autowired
    private GeCertifiedUserService geCertifiedUserService;

    @Autowired
    private GeProductCuserRelMapper geProductCuserRelMapper;

    //=============================================

    @ApiOperation(value = "add ge_certified_user")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addGeCertifiedUser", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedUser/addGeCertifiedUser", description = "add ge_certified_user")
    public Response addGeCertifiedUser(@RequestBody GeCertifiedUser geCertifiedUser) {

        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geCertifiedUserService.addGeCertifiedUser(geCertifiedUser);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }

    }


    @ApiOperation(value = "update ge_certified_user")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/updateGeCertifiedUser", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedUser/updateGeCertifiedUser", description = "update ge_certified_user")
    public Response updateGeCertifiedUser(@RequestBody GeCertifiedUser geCertifiedUser) {


        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geCertifiedUserService.updateGeCertifiedUser(geCertifiedUser);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    /// public void deleteGeCertifiedUserByPK(java.lang.Integer id) throws MyException ;

    @ApiOperation(value = "delete ge_certified_user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping("/deleteGeCertifiedUser")
    @Transactional(rollbackFor = Exception.class)
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedUser/deleteGeCertifiedUser", description = "delete ge_certified_user")
    public Response deleteGeCertifiedUser(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            geCertifiedUserService.deleteGeCertifiedUserByPK(id);
            geProductCuserRelMapper.deleteByCid(Integer.valueOf(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "get  ge_certified_user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/getGeCertifiedUser")
    @AuthorizedAccess(permissions = "/geCertifiedUser/getGeCertifiedUser", description = "get ge_certified_user")
    public Response getGeCertifiedUser(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(geCertifiedUserService.getGeCertifiedUserByPK(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation(value = "query ge_certified_user by condition 查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionMap", value = "查询的关键字Map", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/queryGeCertifiedUser")
    @AuthorizedAccess(permissions = "/geCertifiedUser/queryGeCertifiedUser", description = "query ge_certified_user")
    public TableRes queryGeCertifiedUser(@RequestParam Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            Map<String, Object> conditionMap = (Map<String, Object>) conditionMapPage.get(
                    "conditionMap");
            int page = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get(
                    "limit"));

            PageModel<GeCertifiedUser> queryData = geCertifiedUserService.queryGeCertifiedUserBySearch(conditionMap,
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
     * 资质人模糊查询
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryVcategoryUserSimple")
    public Response queryVcategoryUserSimple(@RequestParam Map<String, String> map) {
        String searchStr = map.get("searchStr");
        String area = map.get("area");
        String model = map.get("model");
        int page = Integer.parseInt((String) map.get("page"));
        int pageSize = Integer.parseInt((String) map.get( "limit"));
        return geCertifiedUserService.queryVcategoryUser(searchStr, area, model,page,pageSize);
    }


    @ResponseBody
    @RequestMapping("/queryProductById")
    public Response queryProductById(String id) {
        return geCertifiedUserService.queryProductById(id);
    }
}

