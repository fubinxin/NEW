package com.ge.dao;

import java.util.*;

import com.ge.vo.VcategoryUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import com.ge.po.GeCertifiedUser;

/**
 * ge_certified_user
 * Created by autoGen Tools
 */
@Repository
public interface GeCertifiedUserMapper extends Mapper<GeCertifiedUser> {


    List<GeCertifiedUser> queryListPage(Map<String, Object> map);


    int queryCount(Map<String, Object> conditionMap);

    List<VcategoryUser> queryVcategoryUser(@Param("searchStr") String searchStr, @Param("areaList") List<String> areaList, @Param("modelList") List<String> modelList);

    List<String> quearyArea(String searchStr);

    List<String> quearyModel(String searchStr);

    List<VcategoryUser> queryProductById(String id);

    void batchInsert(@Param("list") List<GeCertifiedUser> list);
}