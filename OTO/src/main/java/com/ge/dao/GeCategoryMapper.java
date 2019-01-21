package com.ge.dao;

import com.ge.po.GeCategory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GeCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GeCategory record);


    GeCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GeCategory record);

    int updateByPrimaryKey(GeCategory record);

    List<GeCategory> selectCategoryChildrenByParentId(Integer parentId);

    List<Map<String, Object>> selectArea();

    List<Map<String, String>> selectAllAreaMap();
}