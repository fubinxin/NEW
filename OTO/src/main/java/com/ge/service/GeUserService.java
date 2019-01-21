package com.ge.service;

import java.util.*;
import  com.ge.po . GeUser ;
import com.common.exception.*;
import utils.PageModel;
import utils.Response;

/**
* ge_user
*  Created by autoGen Tools 
*/

public interface GeUserService  {

	 /**
	     * delete ge_user
	     * @param id PKID
	     * @throws MyException
	     */
	 public void deleteGeUserByPK(java.lang.Integer id) throws MyException ;
	 
	  /**
	     * add ge_user
	     * @param  geUser ge_user
	     * @throws MyException
	     */
	  void addGeUser (GeUser  geUser)  throws MyException;
	  
	   /**
	     * edit ge_user
	     * @param geUser ge_user
	     * @throws MyException
	     */
	  void updateGeUser (GeUser   geUser)  throws MyException;
	    /**
	     * get ge_user
	     * @param id  ge_user PK (java.lang.Integer  )
	     * @throws MyException
	     */
	 public GeUser getGeUserByPK(java.lang.Integer id) throws MyException;
	  
	  /**
	 * search paged list by query condition
	 * 
	 * @param conditionMap  query condition
	 * @param page  now page
	 * @param pageSize size perPage
	 */
	 public PageModel<GeUser> queryGeUserBySearch(Map<String, Object> conditionMap, Integer page, Integer pageSize) ;

	Response login(String loginName, String password);

	Response checkAdminRole(GeUser geUser);
}