package com.ge.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import  com.ge.po . GeUser ;

/**
* ge_user
*  Created by autoGen Tools 
*/
@Repository
public interface GeUserMapper  extends Mapper<GeUser> {

	
	  
	 List<GeUser> queryListPage(Map<String, Object> map);


	int checkUsername(String loginName);

	GeUser selectLogin(@Param("loginName") String username, @Param("password") String password);

}