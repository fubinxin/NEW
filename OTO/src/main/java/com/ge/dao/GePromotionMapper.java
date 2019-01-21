package com.ge.dao;

import java.util.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import com.ge.po.GePromotion;

/**
* ge_news
*  Created by autoGen Tools 
*/
@Repository
public interface GePromotionMapper extends Mapper<GePromotion> {

	
	  
	 List<GePromotion> queryListPage(Map<String, Object> map);
	 

	 int queryCount (Map<String, Object> conditionMap);
	 
	

}