package com.ge.service.impl;

import com.ge.dao.GeCategoryMapper;
import com.ge.po.GeCategory;
import com.ge.service.GeCategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/12 15:24
 */
@Service("GeCategoryService")
public class GeCategoryServiceImpl implements GeCategoryService {
    @Autowired
    private GeCategoryMapper geCategoryMapper;

    private Logger logger = LoggerFactory.getLogger(GeCategoryServiceImpl.class);

    @Override
    public Response<List<GeCategory>> getChildParallelCategory(Integer categoryId) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        List<GeCategory> categoryList = geCategoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            response.setMsg("未找到当前分类的子分类");
        } else {
            response.setData(categoryList);
        }
        return response;
    }

    @Override
    public Response addCategory(GeCategory category) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            category.setIsdeleted(0);
            category.setCreateTime(new Date());
            category.setUpdateTime(new Date());
            int i = geCategoryMapper.insert(category);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    @Override
    public Response updateCategory(GeCategory category) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            int i = geCategoryMapper.updateByPrimaryKeySelective(category);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    @Override
    public Response delCategory(Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            //软删除，将isDeleted设置为1，默认不显示
            GeCategory geCategory = new GeCategory();
            geCategory.setId(id);
            geCategory.setIsdeleted(1);
            geCategory.setUpdateTime(new Date());
            int i = geCategoryMapper.updateByPrimaryKeySelective(geCategory);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    @Override
    public TableRes queryCategory(Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            int categoryid = Integer.valueOf(conditionMapPage.get("categoryid").toString());
            int pageNum = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get("limit"));
            GeCategory geCategory = new GeCategory();
            geCategory.setParentId(categoryid);
            Page<GeCategory> page = PageHelper.startPage(pageNum, pageSize);
            List<GeCategory> pageList = geCategoryMapper.selectCategoryChildrenByParentId(categoryid);
            Long count = page.getTotal();
            PageModel<GeCategory> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
            pm.setDataList(pageList);
            response.setData(pm.getDataList());
            response.setCount(pm.getTotalRecord());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;

    }

    @Override
    public TableRes queryCategoryArea(Map<String, Object> conditionMapPage) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {

            int pageNum = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get("limit"));

            Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> pageList = geCategoryMapper.selectArea();
            Long count = page.getTotal();
            PageModel<Map<String, Object>> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
            pm.setDataList(pageList);
            response.setData(pm.getDataList());
            response.setCount(pm.getTotalRecord());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    public Response queryCategoryById(Integer id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            GeCategory geCategory = geCategoryMapper.selectByPrimaryKey(id);
            response.setData(geCategory);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }
}

