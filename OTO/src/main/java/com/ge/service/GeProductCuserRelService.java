package com.ge.service;

import java.util.*;

import com.ge.po.GeProductCuserRel;
import com.common.exception.*;
import utils.PageModel;
import utils.Response;

/**
 * ge_product_cuser_rel
 * Created by autoGen Tools
 */

public interface GeProductCuserRelService {

    /**
     * delete ge_product_cuser_rel
     *
     * @param id PKID
     * @throws MyException
     */
    public void deleteGeProductCuserRelByPK(java.lang.Integer id) throws MyException;

    /**
     * add ge_product_cuser_rel
     *
     * @param geProductCuserRel ge_product_cuser_rel
     * @throws MyException
     */
    void addGeProductCuserRel(GeProductCuserRel geProductCuserRel) throws MyException;

    /**
     * edit ge_product_cuser_rel
     *
     * @param geProductCuserRel ge_product_cuser_rel
     * @throws MyException
     */
    void updateGeProductCuserRel(GeProductCuserRel geProductCuserRel) throws MyException;

    /**
     * get ge_product_cuser_rel
     *
     * @param id ge_product_cuser_rel PK (java.lang.Integer  )
     * @throws MyException
     */
    public GeProductCuserRel getGeProductCuserRelByPK(java.lang.Integer id) throws MyException;

    /**
     * search paged list by query condition
     *
     * @param conditionMap query condition
     * @param page         now page
     * @param pageSize     size perPage
     */
    public PageModel<GeProductCuserRel> queryGeProductCuserRelBySearch(Map<String, Object> conditionMap, Integer page, Integer pageSize);

    public void relationProductAndUser(String cid,  List<Map<String, String>>  list);

    Response queryGeProductByCId(Integer cid);

}