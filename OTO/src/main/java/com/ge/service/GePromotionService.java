package com.ge.service;

import java.util.*;
import com.ge.po.GePromotion;
import com.common.exception.*;
import utils.PageModel;
import utils.TableRes;

/**
* ge_news
*  Created by autoGen Tools 
*/

public interface GePromotionService {

	 /**
	     * delete ge_news
	     * @param id PKID
	     * @throws MyException
	     */
	 public void deleteGeNewsByPK(java.lang.Integer id) throws MyException ;
	 
	  /**
	     * add ge_news
	     * @param  gePromotion ge_news
	     * @throws MyException
	     */
	  void addGeNews (GePromotion gePromotion)  throws MyException;
	  
	   /**
	     * edit ge_news
	     * @param gePromotion ge_news
	     * @throws MyException
	     */
	  void updateGeNews (GePromotion gePromotion)  throws MyException;
	    /**
	     * get ge_news
	     * @param id  ge_news PK (java.lang.Integer  )
	     * @throws MyException
	     */
	 public GePromotion getGeNewsByPK(java.lang.Integer id) throws MyException;
	  
	  /**
	 * search paged list by query condition
	 * 
	 * @param conditionMap  query condition
	 * @param page  now page
	 * @param pageSize size perPage
	 */
	 public PageModel<GePromotion> queryGeNewsBySearch(Map<String, Object> conditionMap, Integer page, Integer pageSize) throws Exception;

	TableRes queryGeNews( Map<String, Object> conditionMapPage, Integer type , Boolean isAll);
}