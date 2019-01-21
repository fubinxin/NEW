package com.ge.service;

import com.ge.po.GeCategory;
import org.springframework.stereotype.Service;
import utils.Response;
import utils.TableRes;

import java.util.List;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/12 15:24
 */
@Service
public interface GeCategoryService {
    Response<List<GeCategory>> getChildParallelCategory(Integer categoryId);

    Response addCategory(GeCategory geCategory);

    Response updateCategory(GeCategory geCategory);

    Response delCategory(Integer id);

    TableRes queryCategory(Map<String, Object> conditionMapPage) throws Exception;

    TableRes queryCategoryArea(Map<String, Object> conditionMapPage) throws Exception;

    Response queryCategoryById(Integer id);
}
