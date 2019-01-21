package com.ge.service;

import java.util.*;
import  com.ge.po . GeCertifiedUser ;
import com.common.exception.*;
import org.apache.commons.lang3.StringUtils;
import utils.PageModel;
import utils.Response;
import utils.ResultHttpStatus;

/**
* ge_certified_user
*  Created by autoGen Tools 
*/

public interface GeCertifiedUserService  {

	 /**
	     * delete ge_certified_user
	     * @param id PKID
	     * @throws MyException
	     */
	 public void deleteGeCertifiedUserByPK(java.lang.Integer id) throws MyException ;
	 
	  /**
	     * add ge_certified_user
	     * @param  geCertifiedUser ge_certified_user
	     * @throws MyException
	     */
	  void addGeCertifiedUser (GeCertifiedUser  geCertifiedUser)  throws MyException;
	  
	   /**
	     * edit ge_certified_user
	     * @param geCertifiedUser ge_certified_user
	     * @throws MyException
	     */
	  void updateGeCertifiedUser (GeCertifiedUser   geCertifiedUser)  throws MyException;
	    /**
	     * get ge_certified_user
	     * @param id  ge_certified_user PK (java.lang.Integer  )
	     * @throws MyException
	     */
	 public GeCertifiedUser getGeCertifiedUserByPK(java.lang.Integer id) throws MyException;
	  
	  /**
	 * search paged list by query condition
	 * 
	 * @param conditionMap  query condition
	 * @param page  now page
	 * @param pageSize size perPage
	 */
	 public PageModel<GeCertifiedUser> queryGeCertifiedUserBySearch(Map<String, Object> conditionMap, Integer page, Integer pageSize) throws Exception;

	Response queryVcategoryUser(String searchStr, String area, String model,Integer page, Integer pageSize);

	Response queryProductById(String id);

}