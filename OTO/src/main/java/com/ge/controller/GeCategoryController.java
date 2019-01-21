package com.ge.controller;

import com.common.Const;
import com.ge.po.GeCategory;
import com.ge.po.GeUser;
import com.ge.service.GeCategoryService;
import com.ge.service.GeUserService;
import io.swagger.annotations.Api;
import org.apache.log4j.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.Response;
import utils.ResultHttpStatus;
import utils.TableRes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/12 15:54
 */
@Api(value = " geCategory-controller", description = "geCategory")
@Controller
@RequestMapping("geCategory")
public class GeCategoryController {

    @Autowired
    private GeUserService geUserService;

    @Autowired
    private GeCategoryService geCategoryService;

    @RequestMapping("get_category")
    @ResponseBody
    public Response getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
//        GeUser user = (GeUser) session.getAttribute(Const.CURRENT_USER);
//        if ((user == null)) {
//            response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
//            response.setMsg("用户未登陆,请登陆");
//            return response;
//        }
        return geCategoryService.getChildParallelCategory(categoryId);
    }

    @RequestMapping("add_category")
    @ResponseBody
    public Response addCategory(@RequestBody GeCategory geCategory) {
        return geCategoryService.addCategory(geCategory);
    }

    @RequestMapping("update_category")
    @ResponseBody
    public Response updateCategory(@RequestBody GeCategory geCategory) {
        return geCategoryService.updateCategory(geCategory);
    }

    @RequestMapping("delete_category")
    @ResponseBody
    public Response delCategory(Integer id) {
        return geCategoryService.delCategory(id);
    }
    @ResponseBody
    @RequestMapping("/query_category")
    public TableRes queryCategory(@RequestParam Map<String, Object> conditionMapPage) throws Exception {
        return geCategoryService.queryCategory(conditionMapPage);
    }
    @RequestMapping("/queryCategoryArea")
    @ResponseBody
    public TableRes queryCategoryArea(@RequestParam Map<String, Object> conditionMapPage) throws Exception {
        return geCategoryService.queryCategoryArea(conditionMapPage);
    }
    @RequestMapping("/queryCategoryById")
    @ResponseBody
    public Response queryCategoryById(Integer id) throws Exception {
        return geCategoryService.queryCategoryById(id);
    }
}
