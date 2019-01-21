package com.ge.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import com.ge.po.GeCertifiedProduct;

/**
 * ge_certified_product
 * Created by autoGen Tools
 */
@Repository
public interface GeCertifiedProductMapper extends Mapper<GeCertifiedProduct> {


    List<GeCertifiedProduct> queryListPage(Map<String, Object> map);


    Long queryCount(@Param("id") Integer id, @Param("name") String name);

    List<GeCertifiedProduct> getProductByCategory(Integer catagory);

}