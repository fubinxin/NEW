package com.ge.service;

import java.util.*;
import  com.ge.po . GeCertifiedProduct ;
import com.common.exception.*;
import org.exolab.castor.dsml.Producer;
import utils.PageModel;
import utils.Response;

/**
* ge_certified_product
*  Created by autoGen Tools 
*/

public interface GeCertifiedProductService  {

	 /**
	     * delete ge_certified_product
	     * @param id PKID
	     * @throws MyException
	     */
	 public void deleteGeCertifiedProductByPK(java.lang.Integer id) throws MyException ;
	 
	  /**
	     * add ge_certified_product
	     * @param  geCertifiedProduct ge_certified_product
	     * @throws MyException
	     */
	  void addGeCertifiedProduct (GeCertifiedProduct  geCertifiedProduct)  throws MyException;
	  
	   /**
	     * edit ge_certified_product
	     * @param geCertifiedProduct ge_certified_product
	     * @throws MyException
	     */
	  void updateGeCertifiedProduct (GeCertifiedProduct   geCertifiedProduct)  throws MyException;
	    /**
	     * get ge_certified_product
	     * @param id  ge_certified_product PK (java.lang.Integer  )
	     * @throws MyException
	     */
	 public GeCertifiedProduct getGeCertifiedProductByPK(java.lang.Integer id) throws MyException;
	  
	  /**
	 * search paged list by query condition
	 * 
	 * @param conditionMap  query condition
	 * @param page  now page
	 * @param pageSize size perPage
	 */
	 public PageModel<GeCertifiedProduct> queryGeCertifiedProductBySearch(Map<String, Object> conditionMap, Integer page, Integer pageSize) throws Exception;

	 Response<List<GeCertifiedProduct>> getProductByCategory(Integer category);

	 int checkProduct(Integer pid);
}