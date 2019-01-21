package com.ge.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import com.ge.po.GeProductCuserRel;

/**
 * ge_product_cuser_rel
 * Created by autoGen Tools
 */
@Repository
public interface GeProductCuserRelMapper extends Mapper<GeProductCuserRel> {


    List<GeProductCuserRel> queryListPage(Map<String, Object> map);


    int queryCount(Map<String, Object> conditionMap);


    int deleteByCid(Integer cid);


    List<GeProductCuserRel>queryByCId(Integer cid);

    int queryCountByPid(Integer pid);
}