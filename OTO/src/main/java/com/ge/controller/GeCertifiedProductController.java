package com.ge.controller;


import com.common.Const;
import com.ge.po.GeCertifiedProduct;
import com.ge.po.GeUser;
import com.ge.service.GeCertifiedProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.PageModel;
import utils.Response;
import utils.ResultHttpStatus;
import utils.TableRes;
import utils.annotations.AuthorizedAccess;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;


@Api(value = " geCertifiedProduct-controller", description = "ge_certified_product")
@Controller
@RequestMapping("/geCertifiedProduct")
public class GeCertifiedProductController {

    private static Logger logger = Logger.getLogger(GeCertifiedProductController.class);

    //=============================================
    @Autowired
    private GeCertifiedProductService geCertifiedProductService;


    //=============================================

    @ApiOperation(value = "add ge_certified_product")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/addGeCertifiedProduct", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedProduct/addGeCertifiedProduct", description = "add ge_certified_product")
    public Response addGeCertifiedProduct(HttpSession session, @RequestBody GeCertifiedProduct geCertifiedProduct) {

        geCertifiedProduct.setId(null);
        geCertifiedProduct.setCreateTime(new Date());
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
        if ((user == null)) {
            response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
            response.setMsg("用户未登陆,请登陆");
            return response;
        }
        try {
            geCertifiedProductService.addGeCertifiedProduct(geCertifiedProduct);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }

    }


    @ApiOperation(value = "update ge_certified_product")
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping(value = "/updateGeCertifiedProduct", method = RequestMethod.POST)
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedProduct/updateGeCertifiedProduct", description = "update ge_certified_product")
    public Response updateGeCertifiedProduct(HttpSession session, @RequestBody GeCertifiedProduct geCertifiedProduct) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
        if ((user == null)) {
            response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
            response.setMsg("用户未登陆,请登陆");
            return response;
        }
        try {
            geCertifiedProductService.updateGeCertifiedProduct(geCertifiedProduct);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    /// public void deleteGeCertifiedProductByPK(java.lang.Integer id) throws MyException ;

    @ApiOperation(value = "delete ge_certified_product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes"})
    @ResponseBody
    @RequestMapping("/deleteGeCertifiedProduct")
    @AuthorizedAccess(roles = "admin", permissions = "/geCertifiedProduct/deleteGeCertifiedProduct", description = "delete ge_certified_product")
    public Response deleteGeCertifiedProduct(HttpSession session, @RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            int count = geCertifiedProductService.checkProduct(id);
            if (count > 0) {
                response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
                response.setMsg("产品已绑定，不可删除");
            } else {
                geCertifiedProductService.deleteGeCertifiedProductByPK(id);
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }


    @ApiOperation(value = "get  ge_certified_product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PK ", required = true, paramType = "delete", dataType = "java.lang.Integer")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/getGeCertifiedProduct")
    @AuthorizedAccess(permissions = "/geCertifiedProduct/getGeCertifiedProduct", description = "get ge_certified_product")
    public Response getGeCertifiedProduct(@RequestParam java.lang.Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(geCertifiedProductService.getGeCertifiedProductByPK(id));
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation(value = "query ge_certified_product by condition 查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionMap", value = "查询的关键字Map", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @SuppressWarnings({"rawtypes",
            "unchecked"
    })
    @ResponseBody
    @RequestMapping("/queryGeCertifiedProduct")
    @AuthorizedAccess(permissions = "/geCertifiedProduct/queryGeCertifiedProduct", description = "query ge_certified_product")
    public TableRes queryGeCertifiedProduct(@RequestParam Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            Map<String, Object> conditionMap = (Map<String, Object>) conditionMapPage.get(
                    "conditionMap");
            int page = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get(
                    "limit"));

            PageModel<GeCertifiedProduct> queryData = geCertifiedProductService.queryGeCertifiedProductBySearch(conditionMap,
                    page, pageSize);
            response.setData(queryData.getDataList());
            response.setCount(queryData.getTotalRecord());

            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return response;
        }
    }

    @RequestMapping("getProductByCategory")
    @ResponseBody
    public Response getProductByCategory(Integer categoryId) {
        return geCertifiedProductService.getProductByCategory(categoryId);
    }

}

